package com.nttdata.bootcamp.pfinal.web;

import com.nttdata.bootcamp.pfinal.producer.BootcoinWalletProducer;
import com.nttdata.bootcamp.pfinal.service.BootcoinTransactionService;
import com.nttdata.bootcamp.pfinal.service.BootcoinWalletService;
import com.nttdata.bootcamp.pfinal.web.mapper.BootcoinWalletMapper;
import com.nttdata.bootcamp.pfinal.web.model.BootcoinWalletModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.net.URI;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/bootcoin/wallet")
@RefreshScope
public class BootcoinWalletController {

    @Value("${spring.application.name}")
    String name;

    @Value("${server.port}")
    String port;

    @Autowired
    BootcoinWalletService bootcoinWalletService;

    @Autowired
    BootcoinWalletMapper bootcoinWalletMapper;

    @Autowired
    BootcoinWalletProducer bootcoinWalletProducer;

    @Autowired
    BootcoinTransactionService bootcoinTransactionService;

    @GetMapping
    public Mono<ResponseEntity<Flux<BootcoinWalletModel>>> getAll() {
        log.info("getAll executed");
        return Mono.just(ResponseEntity.ok()
                .body(bootcoinWalletService.findAll().map(transaction -> bootcoinWalletMapper.entityToModel(transaction))));
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<BootcoinWalletModel>> getById(@PathVariable String id) {
        log.info("getById executed {}", id);
        return bootcoinWalletService.findById(id).map(transaction -> bootcoinWalletMapper.entityToModel(transaction))
                .map(ResponseEntity::ok).defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<ResponseEntity<BootcoinWalletModel>> create(@Valid @RequestBody BootcoinWalletModel request) {
        log.info("create BootcoinWallet executed {}", request);
        return bootcoinWalletService.create(bootcoinWalletMapper.modelToEntity(request))
                .map(transaction -> bootcoinWalletMapper.entityToModel(transaction)) // envio el objeto convertido
                .map(this::sendBootcoinWallet) //envio a kafka
                .flatMap(c -> Mono.just(ResponseEntity
                        .created(URI.create(String.format("http://%s:%s/%s/%s", name, port, "/v1/bootcoin/wallet/", c.getId())))
                        .body(c))) // hace una solicitud get a un endpoint para devolver el objeto creado
                .defaultIfEmpty(ResponseEntity.notFound().build()); // respuesta si el mono esta en blanco

    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<BootcoinWalletModel>> updateById(@PathVariable String id,
                                                        @Valid @RequestBody BootcoinWalletModel request) {
        log.info("updateById executed {}:{}", id, request);
        return bootcoinWalletService.update(id, bootcoinWalletMapper.modelToEntity(request))
                .map(transaction -> bootcoinWalletMapper.entityToModel(transaction))
                .flatMap(c -> Mono.just(ResponseEntity
                        .created(URI.create(String.format("http://%s:%s/%s/%s", name, port, "/v1/bootcoin/wallet/", c.getId())))
                        .body(c)))
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteById(@PathVariable String id) {
        log.info("deleteById executed {}", id);
        return bootcoinWalletService.delete(id)
                .doOnSuccess(transaction -> bootcoinTransactionService.findByBootcoinWalletId(id)
                        .map(movement -> bootcoinTransactionService.delete(movement.getId())))
                .map(r -> ResponseEntity.ok().<Void>build()).defaultIfEmpty(ResponseEntity.notFound().build());
    }


    private BootcoinWalletModel sendBootcoinWallet(BootcoinWalletModel bootcoinWalletModel) {
        log.debug("sendBootcoinWallet executed {}", bootcoinWalletModel);
        if (bootcoinWalletModel != null) {
            bootcoinWalletProducer.sendMessage(bootcoinWalletModel);
        }
        return bootcoinWalletModel;
    }
}
