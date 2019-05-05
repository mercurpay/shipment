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
public class HandleStartShipmentService {

  @Inject
  ShipmentService shipmentService;

  @Inject
  Logger logger;

  public void notifyCrmStarted(@Observes @RequestShipment StartShipmentEvent event){
    this.logger.info("Receiving request to start shipment for orderId {}",event.getOrderId());
    this.shipmentService.startShipment(event);
    this.logger.info("Start shipment for orderId {} executed successfully",event.getOrderId());
  }

}
