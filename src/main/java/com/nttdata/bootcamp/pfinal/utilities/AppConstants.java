package com.nttdata.bootcamp.pfinal.utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class AppConstants {

    public static String baseUrl = "http://localhost";
    public static String baseUrlCredits = "http://localhost:8085";
    public static String baseUrlBankAccounts = "http://localhost:8084";
    public static String baseUrlYanki = "http://localhost:9045";
    public static String baseUrlBootcoin = "http://localhost:9046";
    public static String getCurrentUrl() {
        String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        String appConfigPath = rootPath + "application.properties";

        String currentPort = "80";
        StringBuilder url  = new StringBuilder();
        url.append(AppConstants.baseUrl);
        url.append(":");
        Properties appProps = new Properties();
        try {
            appProps.load(new FileInputStream(appConfigPath));
            currentPort = appProps.getProperty("server.port");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            url.append(currentPort);
        }
        return url.toString();
    }
}
