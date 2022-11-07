package com.nttdata.bootcamp.pfinal.producer;

import com.nttdata.bootcamp.pfinal.web.model.BootcoinWalletModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Component
@RequiredArgsConstructor
@Slf4j
public class BootcoinWalletProducer {
    private final KafkaTemplate<String, BootcoinWalletModel> kafkaTemplate;

    @Value(value = "${kafka.topic.bootcoin.wallet.name}")
    private String topic;

    public void sendMessage(BootcoinWalletModel bootcoinWalletModel) {

        ListenableFuture<SendResult<String, BootcoinWalletModel>> future = kafkaTemplate.send(this.topic, bootcoinWalletModel);

        future.addCallback(new ListenableFutureCallback<SendResult<String, BootcoinWalletModel>>() {
            @Override
            public void onSuccess(SendResult<String, BootcoinWalletModel> result) {
                log.info("Message {} has been sent ", bootcoinWalletModel);
            }

            @Override
            public void onFailure(Throwable ex) {
                log.error("Something went wrong with the bootcoinWalletModel {} ", bootcoinWalletModel);
            }
        });
    }
}
