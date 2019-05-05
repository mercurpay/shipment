package tech.claudioed.shipment.domain.service;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import org.slf4j.Logger;
import tech.claudioed.shipment.domain.Shipment;
import tech.claudioed.shipment.domain.repository.ShipmentRepository;
import tech.claudioed.shipment.domain.resources.data.StartShipmentEvent;
import tech.claudioed.shipment.infra.event.ShipmentStarted;

/** @author claudioed on 2019-04-14. Project shipment */
@ApplicationScoped
public class ShipmentService {

  @Inject ShipmentRepository shipmentRepository;

  @Inject @ShipmentStarted
  Event<Shipment> trigger;

  @Inject Logger logger;

  public void startShipment(StartShipmentEvent startShipmentEvent) {
    final Shipment shipment = this.shipmentRepository.create(startShipmentEvent);
    this.logger.info("Triggering shipment started...");
    this.trigger.fire(shipment);
    this.logger.info("Shipment started event triggered successfully!!!");
  }

  public Shipment find(String id) {
    return this.shipmentRepository.find(id);
  }
}
