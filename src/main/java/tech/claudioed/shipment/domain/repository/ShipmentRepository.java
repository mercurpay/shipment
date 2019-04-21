package tech.claudioed.shipment.domain.repository;

import tech.claudioed.shipment.domain.Shipment;
import tech.claudioed.shipment.domain.resources.data.StartShipmentEvent;

/**
 * @author claudioed on 2019-04-14.
 * Project shipment
 */
public interface ShipmentRepository {

  Shipment create(StartShipmentEvent startShipmentEvent);

  Shipment find(String id);

}
