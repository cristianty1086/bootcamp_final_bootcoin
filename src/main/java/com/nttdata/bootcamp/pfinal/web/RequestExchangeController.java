package com.nttdata.bootcamp.pfinal.web;

import com.nttdata.bootcamp.pfinal.domain.RequestExchange;
import com.nttdata.bootcamp.pfinal.repository.RequestExchangeRepository;
import com.nttdata.bootcamp.pfinal.utilities.BuilderUrl;
import com.nttdata.bootcamp.pfinal.web.mapper.RequestExchangeMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/bootcoin/exchange")
@RefreshScope
public class RequestExchangeController {

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
                        //String url1 = BuilderUrl.buildCountCreditCards(costumer.getId());
                        //restTemplate.getForObject()
                        //restTemplate.postForObject();
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
