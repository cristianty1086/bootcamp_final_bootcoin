package com.nttdata.bootcamp.pfinal.repository;


import com.nttdata.bootcamp.pfinal.domain.RequestExchange;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface RequestExchangeRepository extends ReactiveMongoRepository<RequestExchange, String> {

    Flux<RequestExchange> findByBuyerBootcoinWalletId(String buyerBootcoinWalletId);

    Flux<RequestExchange> findBySellerBootcoinWalletId(String sellerBootcoinWalletId);

}
