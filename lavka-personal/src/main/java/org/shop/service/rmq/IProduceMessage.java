package org.shop.service.rmq;

public interface IProduceMessage {

   <T extends RMQModelMessage> void send(T message);

}
