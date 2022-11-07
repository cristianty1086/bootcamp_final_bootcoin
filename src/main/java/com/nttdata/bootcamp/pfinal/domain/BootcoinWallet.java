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
@Document(value = "bootcoin_wallet")
public class BootcoinWallet {

    @Id
    private String id;

    @NotNull
    String documentType;

    @NotNull
    String documentNumber;

    @NotNull
    String cellphone;

    @NotNull
    String email;

    String bankAccountId;
    String walletId;

}
