package com.nttdata.bootcamp.pfinal.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.math.BigInteger;


@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Document(value = "request_exhange")
public class RequestExchange {

    @Id
    String id;

    @NotNull
    double amount;

    @NotNull
    String buyerPaymentType;

    @NotNull
    String buyerBootcoinWalletId;

    String sellerBootcoinWalletId;

    String sellerPaymentType;
}
