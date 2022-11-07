package com.nttdata.bootcamp.pfinal.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;



@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Document(value = "bootcoin_transaction")
public class BootcoinTransaction {

    @Id
    String id;

    @NotNull
    String bootcoinWalletId;

    @NotNull
    double amount;

    @NotNull
    String transactionType;

    @NotNull
    String requestExchangueId;

}
