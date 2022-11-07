package com.nttdata.bootcamp.pfinal.web.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.math.BigInteger;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestExchangeModel {

    @JsonIgnore
    String id;

    @NotBlank(message="amount cannot be null or empty")
    double amount;

    @NotBlank(message="paymentType cannot be null or empty")
    String buyerPaymentType;

    @NotBlank(message="buyerBootcoinWalletId cannot be null or empty")
    String buyerBootcoinWalletId;

    String sellerBootcoinWalletId;

    String sellerPaymentType;

}
