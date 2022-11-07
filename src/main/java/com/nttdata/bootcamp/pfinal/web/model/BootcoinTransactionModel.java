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
public class BootcoinTransactionModel {

    @JsonIgnore
    String id;

    @NotBlank(message="bootcoinWalletId cannot be null or empty")
    String bootcoinWalletId;

    @NotBlank(message="amount cannot be null or empty")
    double amount;

    @NotBlank(message="transactionType cannot be null or empty")
    String transactionType;

    @NotBlank(message="requestExchangueId cannot be null or empty")
    String requestExchangueId;

}
