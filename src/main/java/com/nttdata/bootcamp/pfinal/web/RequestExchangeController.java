package com.nttdata.bootcamp.pfinal.web;

import com.nttdata.bootcamp.pfinal.domain.BootcoinTransaction;
import com.nttdata.bootcamp.pfinal.domain.Parameter;
import com.nttdata.bootcamp.pfinal.domain.RequestExchange;
import com.nttdata.bootcamp.pfinal.repository.RequestExchangeRepository;
import com.nttdata.bootcamp.pfinal.utilities.BuilderUrl;
import com.nttdata.bootcamp.pfinal.web.mapper.RequestExchangeMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/bootcoin/exchange")
@RefreshScope
public class RequestExchangeController {
    private static final Logger LOGGER = LoggerFactory.getLogger(RequestExchangeController.class);

    @Autowired
    RequestExchangeRepository requestExchangeRepository;

    @Autowired
    RequestExchangeMapper requestExchangeMapper;

    @Autowired
    private RestTemplate restTemplate;

    public Flux<RequestExchange> findAll(){
        log.debug("findAll executed");
        return requestExchangeRepository.findAll();
    }

    public Mono<RequestExchange> findById(String requestExchangeId){
        log.debug("findById executed {}", requestExchangeId);
        return requestExchangeRepository.findById(requestExchangeId);
    }

    public Mono<RequestExchange> create(RequestExchange requestExchange){
        log.debug("create executed {}", requestExchange);

        return requestExchangeRepository.save(requestExchange);
    }

    public Mono<RequestExchange> update(String transactionId,  RequestExchange requestExchange){
        log.debug("update executed {}:{}", transactionId, requestExchange);
        return requestExchangeRepository.findById(transactionId)
                .flatMap(dbTransaction -> {
                    // crear las transacciones bootcoin si existe el vendedor
                    if( requestExchange.getSellerBootcoinWalletId() != null &&
                            requestExchange.getSellerPaymentType() != null ) {
                        String url1 = BuilderUrl.buildGetCompraBootcoin();
                        Parameter parameterCompraBootcoin = restTemplate.getForObject(url1, Parameter.class);

                        // orden de compra de usuario 1
                        String url2 = BuilderUrl.buildPostBootcoinTransaction();

                        // orden de venta de usuario 2
                        String url3 = BuilderUrl.buildPostBootcoinTransaction();
                        try{
                            BootcoinTransaction bt = new BootcoinTransaction();
                            bt.setAmount(requestExchange.getAmount());
                            bt.setRequestExchangueId(requestExchange.getId());
                            bt.setBootcoinWalletId(requestExchange.getBuyerBootcoinWalletId());
                            bt.setTransactionType("BUY");
                            restTemplate.postForObject(url2, bt, BootcoinTransaction.class);

                            BootcoinTransaction bt2 = new BootcoinTransaction();
                            bt.setAmount(requestExchange.getAmount());
                            bt.setRequestExchangueId(requestExchange.getId());
                            bt.setBootcoinWalletId(requestExchange.getSellerBootcoinWalletId());
                            bt.setTransactionType("SELL");
                            restTemplate.postForObject(url3, bt2, BootcoinTransaction.class);
                        } catch (ResponseStatusException ex) {
                            LOGGER.info(ex.getMessage());
                            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No se pudo registrar el pago del comprador", null);
                        }

                        // TODO modificar cuenta bancaria


                    }
                    requestExchangeMapper.update(dbTransaction, requestExchange);
                    return requestExchangeRepository.save(dbTransaction);
                });
    }

    public Mono<RequestExchange> delete(String requestExchangeId){
        log.debug("delete executed {}", requestExchangeId);
        return requestExchangeRepository.findById(requestExchangeId)
                .flatMap(existingTransaction -> requestExchangeRepository.delete(existingTransaction)
                        .then(Mono.just(existingTransaction)));
    }

    public Mono<RequestExchange> findByBuyerBootcoinWalletI(String requestExchangeId){
        log.debug("findByWalletId executed {}", requestExchangeId);
        return requestExchangeRepository.findByBuyerBootcoinWalletId(requestExchangeId).last();
    }

    public Mono<RequestExchange> findBySellerBootcoinWalletI(String requestExchangeId){
        log.debug("findByWalletId executed {}", requestExchangeId);
        return requestExchangeRepository.findBySellerBootcoinWalletId(requestExchangeId).last();
    }
}
