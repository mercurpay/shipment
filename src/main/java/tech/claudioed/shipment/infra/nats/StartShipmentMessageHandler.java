package tech.claudioed.shipment.infra.nats;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.nats.client.Message;
import io.nats.client.MessageHandler;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import tech.claudioed.shipment.domain.resources.data.StartShipmentEvent;
import tech.claudioed.shipment.domain.service.ShipmentService;
import tech.claudioed.shipment.infra.event.RequestShipment;

/**
 * @author claudioed on 2019-05-05.
 * Project shipment
 */
@ApplicationScoped
public class StartShipmentMessageHandler implements MessageHandler {

  @Inject
  ShipmentService shipmentService;

  @Inject
  Logger logger;

  @Inject
  ObjectMapper mapper;

  @Inject @RequestShipment
  Event<StartShipmentEvent> trigger;

  @Override
  @SneakyThrows
  public void onMessage(Message message) throws InterruptedException {
    try {
      this.logger.info("Receiving message...");
      final String messageData = new String(message.getData(), StandardCharsets.UTF_8);
      final StartShipmentEvent startShipmentEvent = this.mapper
          .readValue(messageData, StartShipmentEvent.class);
      this.logger.info("Start shipment data {}",startShipmentEvent);
      this.trigger.fire(startShipmentEvent);
    } catch (IOException e) {
      this.logger.error(e.getMessage());
    }

  }
}
