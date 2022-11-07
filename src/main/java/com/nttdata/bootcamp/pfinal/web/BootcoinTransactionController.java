package com.nttdata.bootcamp.pfinal.web;

import com.nttdata.bootcamp.pfinal.domain.BootcoinTransaction;
import com.nttdata.bootcamp.pfinal.repository.BootcoinTransactionRepository;
import com.nttdata.bootcamp.pfinal.web.mapper.BootcoinTransactionMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/bootcoin/transaction")
@RefreshScope
public class BootcoinTransactionController {

    @Autowired
    BootcoinTransactionRepository bootcoinTransactionRepository;

    @Autowired
    BootcoinTransactionMapper bootcoinTransactionMapper;

    public Flux<BootcoinTransaction> findAll(){
        log.debug("findAll executed");
        return bootcoinTransactionRepository.findAll();
    }

    public Mono<BootcoinTransaction> findById(String bootcoinTransactionId){
        log.debug("findById executed {}", bootcoinTransactionId);
        return bootcoinTransactionRepository.findById(bootcoinTransactionId);
    }

    public Mono<BootcoinTransaction> create(BootcoinTransaction bootcoinTransaction){
        log.debug("create executed {}", bootcoinTransaction);

        return bootcoinTransactionRepository.save(bootcoinTransaction);
    }

    public Mono<BootcoinTransaction> update(String transactionId,  BootcoinTransaction bootcoinTransaction){
        log.debug("update executed {}:{}", transactionId, bootcoinTransaction);
        return bootcoinTransactionRepository.findById(transactionId)
                .flatMap(dbTransaction -> {
                    bootcoinTransactionMapper.update(dbTransaction, bootcoinTransaction);
                    return bootcoinTransactionRepository.save(dbTransaction);
                });
    }

    public Mono<BootcoinTransaction> delete(String bootcoinTransactionId){
        log.debug("delete executed {}", bootcoinTransactionId);
        return bootcoinTransactionRepository.findById(bootcoinTransactionId)
                .flatMap(existingTransaction -> bootcoinTransactionRepository.delete(existingTransaction)
                        .then(Mono.just(existingTransaction)));
    }

    public Mono<BootcoinTransaction> findByBootcoinWalletId(String bootcoinWalletId){
        log.debug("findByWalletId executed {}", bootcoinWalletId);
        return bootcoinTransactionRepository.findByBootcoinWalletId(bootcoinWalletId).last();
    }
}
