package com.nttdata.bootcamp.pfinal.utilities;

import java.math.BigInteger;

public class BuilderUrl {
    public static String buildGetCompraBootcoin() {

        StringBuilder ss = new StringBuilder();
        ss.append(AppConstants.baseUrlParameters);
        ss.append("/v1/parameter/name/CompraBootcoin");

        return ss.toString();
    }
    public static String buildGetVentaBootcoin() {

        StringBuilder ss = new StringBuilder();
        ss.append(AppConstants.baseUrlParameters);
        ss.append("/v1/parameter/name/VentaBootcoin");

        return ss.toString();
    }
    public static String buildPostBootcoinTransaction() {
        StringBuilder ss = new StringBuilder();
        ss.append(AppConstants.baseUrlBootcoin);
        ss.append("/v1/bootcoin/transaction");

        return ss.toString();
    }
    public static String buildCountCreditCards(BigInteger costumerId) {
        if(costumerId == null) {
            return null;
        }

        StringBuilder ss = new StringBuilder();
        ss.append(AppConstants.baseUrlCredits);
        ss.append("/credit/count_credits_cards/");
        ss.append(costumerId);

        return ss.toString();
    }
}
