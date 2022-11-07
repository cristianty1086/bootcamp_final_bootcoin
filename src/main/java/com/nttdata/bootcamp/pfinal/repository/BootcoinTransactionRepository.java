package com.nttdata.bootcamp.pfinal.repository;

import com.nttdata.bootcamp.pfinal.domain.BootcoinTransaction;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface BootcoinTransactionRepository extends ReactiveMongoRepository<BootcoinTransaction, String> {

    Flux<BootcoinTransaction> findByBootcoinWalletId(String bootcoinWalletId);

}
