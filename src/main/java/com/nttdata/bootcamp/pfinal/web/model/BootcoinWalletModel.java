package com.nttdata.bootcamp.pfinal.web.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BootcoinWalletModel {

    @JsonIgnore
    private String id;

    @NotBlank(message="documentType cannot be null or empty")
    String documentType;

    @NotBlank(message="documentNumber cannot be null or empty")
    String documentNumber;

    @NotBlank(message="cellphone cannot be null or empty")
    String cellphone;

    @NotBlank(message="email cannot be null or empty")
    String email;

    String bankAccountId;
    String walletId;

}
