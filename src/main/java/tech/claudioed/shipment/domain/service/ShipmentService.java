package tech.claudioed.shipment.domain.service;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import tech.claudioed.shipment.domain.Shipment;
import tech.claudioed.shipment.domain.repository.ShipmentRepository;
import tech.claudioed.shipment.domain.resources.data.StartShipmentEvent;

/** @author claudioed on 2019-04-14. Project shipment */
@ApplicationScoped
public class ShipmentService {

  @Inject private ShipmentRepository shipmentRepository;

  public void startShipment(StartShipmentEvent startShipmentEvent) {
    this.shipmentRepository.create(startShipmentEvent);
  }

  public Shipment find(String id){
    return this.shipmentRepository.find(id);
  }

}
