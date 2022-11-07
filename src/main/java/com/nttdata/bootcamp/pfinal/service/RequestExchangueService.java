package com.nttdata.bootcamp.pfinal.service;

import com.nttdata.bootcamp.pfinal.domain.RequestExchange;
import com.nttdata.bootcamp.pfinal.repository.RequestExchangeRepository;
import com.nttdata.bootcamp.pfinal.web.mapper.RequestExchangeMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class RequestExchangueService {

    @Autowired
    RequestExchangeRepository requestExchangeRepository;

    @Autowired
    RequestExchangeMapper requestExchangeMapper;

    public Flux<RequestExchange> findAll(){
        log.debug("findAll executed");
        return requestExchangeRepository.findAll();
    }

    public Mono<RequestExchange> findById(String transactionId){
        log.debug("findById executed {}", transactionId);
        return requestExchangeRepository.findById(transactionId);
    }

    public Mono<RequestExchange> create(RequestExchange requestExchange){
        log.debug("create executed {}", requestExchange);

        return requestExchangeRepository.save(requestExchange);
    }

    public Mono<RequestExchange> update(String transactionId,  RequestExchange requestExchange){
        log.debug("update executed {}:{}", transactionId, requestExchange);
        return requestExchangeRepository.findById(transactionId)
                .flatMap(dbTransaction -> {
                    requestExchangeMapper.update(dbTransaction, requestExchange);
                    return requestExchangeRepository.save(dbTransaction);
                });
    }

    public Mono<RequestExchange> delete(String transactionId){
        log.debug("delete executed {}", transactionId);
        return requestExchangeRepository.findById(transactionId)
                .flatMap(existingTransaction -> requestExchangeRepository.delete(existingTransaction)
                        .then(Mono.just(existingTransaction)));
    }

    public Mono<RequestExchange> findByBuyerBootcoinWalletI(String bootcoinWalletId){
        log.debug("findByWalletId executed {}", bootcoinWalletId);
        return requestExchangeRepository.findByBuyerBootcoinWalletId(bootcoinWalletId).last();
    }

    public Mono<RequestExchange> findBySellerBootcoinWalletId(String bootcoinWalletId){
        log.debug("findByWalletId executed {}", bootcoinWalletId);
        return requestExchangeRepository.findBySellerBootcoinWalletId(bootcoinWalletId).last();
    }
}
