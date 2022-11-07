package com.nttdata.bootcamp.pfinal.service;

import com.nttdata.bootcamp.pfinal.domain.BootcoinWallet;
import com.nttdata.bootcamp.pfinal.repository.BootcoinWalletRepository;
import com.nttdata.bootcamp.pfinal.web.mapper.BootcoinWalletMapper;
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
public class BootcoinWalletService {

    @Autowired
    BootcoinWalletRepository bootcoinWalletReporitory;

    @Autowired
    BootcoinWalletMapper bootcoinWalletMapper;

    public Flux<BootcoinWallet> findAll(){
        log.debug("findAll executed");
        return bootcoinWalletReporitory.findAll();
    }

    public Mono<BootcoinWallet> findById(String transactionId){
        log.debug("findById executed {}", transactionId);
        return bootcoinWalletReporitory.findById(transactionId);
    }

    public Mono<BootcoinWallet> create(BootcoinWallet bootcoinWallet){
        log.debug("create executed {}", bootcoinWallet);

        return bootcoinWalletReporitory.save(bootcoinWallet);
    }

    public Mono<BootcoinWallet> update(String transactionId,  BootcoinWallet bootcoinWallet){
        log.debug("update executed {}:{}", transactionId, bootcoinWallet);
        return bootcoinWalletReporitory.findById(transactionId)
                .flatMap(dbTransaction -> {
                    bootcoinWalletMapper.update(dbTransaction, bootcoinWallet);
                    return bootcoinWalletReporitory.save(dbTransaction);
                });
    }

    public Mono<BootcoinWallet> delete(String transactionId){
        log.debug("delete executed {}", transactionId);
        return bootcoinWalletReporitory.findById(transactionId)
                .flatMap(existingTransaction -> bootcoinWalletReporitory.delete(existingTransaction)
                        .then(Mono.just(existingTransaction)));
    }

}
