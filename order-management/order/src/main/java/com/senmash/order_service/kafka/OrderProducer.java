package com.senmash.order_service.kafka;

import com.base.base.dto.OrderEventDTO;
import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class OrderProducer {

    private static final Logger LOGGER= LoggerFactory.getLogger(OrderEventDTO.class);

    @Autowired
    private NewTopic orderTopic;

    @Autowired
    private KafkaTemplate<String, OrderEventDTO> kafkaTemplate;


    public void sendMessage(OrderEventDTO orderEventDTO) {
        LOGGER.info(String.format("Sending order event to topic %s", orderEventDTO.toString()));

        Message<OrderEventDTO> message = MessageBuilder
                .withPayload(orderEventDTO)
                .setHeader(KafkaHeaders.TOPIC, orderTopic.name())
                .build();

        kafkaTemplate.send(message);
    }


}
