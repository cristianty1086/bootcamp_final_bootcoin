package com.nttdata.bootcamp.pfinal.repository;

import com.nttdata.bootcamp.pfinal.domain.BootcoinWallet;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface BootcoinWalletRepository extends ReactiveMongoRepository<BootcoinWallet, String> {

}
