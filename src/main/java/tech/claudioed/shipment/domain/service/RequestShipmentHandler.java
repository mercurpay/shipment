package tech.claudioed.shipment.domain.service;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import org.slf4j.Logger;
import tech.claudioed.shipment.domain.resources.data.StartShipmentEvent;
import tech.claudioed.shipment.infra.event.RequestShipment;

/**
 * @author claudioed on 2019-05-04.
 * Project shipment
 */
@ApplicationScoped
public class RequestShipmentHandler {

  @Inject
  ShipmentService shipmentService;

  @Inject
  Logger logger;

  public void handleShipmentStarted(@Observes @RequestShipment StartShipmentEvent startShipmentEvent){
    this.logger.info("Receiving event to start shipment for orderId {}",startShipmentEvent.getOrderId());
    this.shipmentService.startShipment(startShipmentEvent);
    this.logger.info("Start shipment executed successfully for orderId {}",startShipmentEvent.getOrderId());
  }

}
