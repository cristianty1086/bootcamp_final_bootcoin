package com.nttdata.bootcamp.pfinal.producer;

import com.nttdata.bootcamp.pfinal.web.model.BootcoinTransactionModel;
import com.nttdata.bootcamp.pfinal.web.model.RequestExchangeModel;
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
public class RequestExchangeProducer {
    private final KafkaTemplate<String, RequestExchangeModel> kafkaTemplate;

    @Value(value = "${kafka.topic.bootcoin.exchange.name}")
    private String topic;

    public void sendMessage(RequestExchangeModel requestExchangeModel) {

        ListenableFuture<SendResult<String, RequestExchangeModel>> future = kafkaTemplate.send(this.topic, requestExchangeModel);

        future.addCallback(new ListenableFutureCallback<SendResult<String, RequestExchangeModel>>() {
            @Override
            public void onSuccess(SendResult<String, RequestExchangeModel> result) {
                log.info("Message {} has been sent ", requestExchangeModel);
            }

            @Override
            public void onFailure(Throwable ex) {
                log.error("Something went wrong with the requestExchangeModel {} ", requestExchangeModel);
            }
        });
    }
}
