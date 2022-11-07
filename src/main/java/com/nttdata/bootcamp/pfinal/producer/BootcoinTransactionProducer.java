package com.nttdata.bootcamp.pfinal.producer;

import com.nttdata.bootcamp.pfinal.web.model.BootcoinTransactionModel;
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
public class BootcoinTransactionProducer {
    private final KafkaTemplate<String, BootcoinTransactionModel> kafkaTemplate;

    @Value(value = "${kafka.topic.bootcoin.transaction.name}")
    private String topic;

    public void sendMessage(BootcoinTransactionModel bootcoinTransactionModel) {

        ListenableFuture<SendResult<String, BootcoinTransactionModel>> future = kafkaTemplate.send(this.topic, bootcoinTransactionModel);

        future.addCallback(new ListenableFutureCallback<SendResult<String, BootcoinTransactionModel>>() {
            @Override
            public void onSuccess(SendResult<String, BootcoinTransactionModel> result) {
                log.info("Message {} has been sent ", bootcoinTransactionModel);
            }

            @Override
            public void onFailure(Throwable ex) {
                log.error("Something went wrong with the bootcoinTransactionModel {} ", bootcoinTransactionModel);
            }
        });
    }
}
