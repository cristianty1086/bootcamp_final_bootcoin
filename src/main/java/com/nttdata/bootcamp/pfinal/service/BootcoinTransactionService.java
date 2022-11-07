package com.nttdata.bootcamp.pfinal.service;

import com.nttdata.bootcamp.pfinal.domain.BootcoinTransaction;
import com.nttdata.bootcamp.pfinal.repository.BootcoinTransactionRepository;
import com.nttdata.bootcamp.pfinal.web.mapper.BootcoinTransactionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class BootcoinTransactionService {

    @Autowired
    BootcoinTransactionRepository bootcoinTransactionRepository;

    @Autowired
    BootcoinTransactionMapper bootcoinTransactionMapper;

    public Flux<BootcoinTransaction> findAll(){
        log.debug("findAll executed");
        return bootcoinTransactionRepository.findAll();
    }

    public Mono<BootcoinTransaction> findById(String transactionId){
        log.debug("findById executed {}", transactionId);
        return bootcoinTransactionRepository.findById(transactionId);
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

    public Mono<BootcoinTransaction> delete(String transactionId){
        log.debug("delete executed {}", transactionId);
        return bootcoinTransactionRepository.findById(transactionId)
                .flatMap(existingTransaction -> bootcoinTransactionRepository.delete(existingTransaction)
                        .then(Mono.just(existingTransaction)));
    }

    public Mono<BootcoinTransaction> findByBootcoinWalletId(String bootcoinWalletId){
        log.debug("findByWalletId executed {}", bootcoinWalletId);
        return bootcoinTransactionRepository.findByBootcoinWalletId(bootcoinWalletId).last();
    }
}
