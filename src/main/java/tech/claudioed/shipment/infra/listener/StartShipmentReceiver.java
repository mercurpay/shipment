package tech.claudioed.shipment.infra.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.nats.client.Connection;
import io.nats.client.Dispatcher;
import io.nats.client.Subscription;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import tech.claudioed.shipment.domain.resources.data.StartShipmentEvent;
import tech.claudioed.shipment.domain.service.ShipmentService;
import tech.claudioed.shipment.infra.nats.NatsConnection;

/**
 * @author claudioed on 2019-04-21.
 * Project shipment
 */
@ApplicationScoped
public class StartShipmentReceiver {

  @Inject
  ShipmentService shipmentService;

  @Inject @NatsConnection
  Connection connection;

  @Inject
  ObjectMapper mapper;

  @Inject
  Logger logger;

  @PostConstruct
  public void receiveStartShipment(){
    this.logger.info("Starting receiver...");
    final Dispatcher startShipmentDispatcher = this.connection.createDispatcher((message) -> {
      try {
        this.logger.info("Receiving message...");
        final String messageData = new String(message.getData(), StandardCharsets.UTF_8);
        final StartShipmentEvent startShipmentEvent = this.mapper
            .readValue(messageData, StartShipmentEvent.class);
        this.logger.info("Start shipment data {}",startShipmentEvent);
        this.shipmentService.startShipment(startShipmentEvent);
      } catch (IOException e) {
        this.logger.error(e.getMessage());
      }
    });
    startShipmentDispatcher.subscribe("start-shipment");
    this.logger.info("Receiver started successfully");
  }

}
