package tech.claudioed.shipment.domain.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.UUID;
import javax.inject.Inject;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import tech.claudioed.shipment.domain.Destination;
import tech.claudioed.shipment.domain.Place;
import tech.claudioed.shipment.domain.Shipment;
import tech.claudioed.shipment.domain.resources.data.Customer;
import tech.claudioed.shipment.domain.resources.data.StartShipmentEvent;

/** @author claudioed on 2019-04-14. Project shipment */
public class JdbcShipmentRepository implements ShipmentRepository {

  @Inject
  ObjectMapper objectMapper;

  @Inject
  Logger logger;

  @SneakyThrows
  public Shipment create(StartShipmentEvent startShipmentEvent){
    this.logger.info("Starting shipment for order id {}",startShipmentEvent.getOrderId());
    final Customer customerData = this.objectMapper.readValue(
        this.objectMapper.writerWithDefaultPrettyPrinter()
            .writeValueAsString(startShipmentEvent.getData()), Customer.class);
    final Destination destination = Destination.builder().address(customerData.getAddress())
        .place(Place.builder().city(customerData.getCity()).country(customerData.getCountry())
            .name("Home")
            .build()).build();
    final Shipment shipment = Shipment.builder().id(UUID.randomUUID().toString())
        .destination(destination).customerId(customerData.getId())
        .orderId(startShipmentEvent.getOrderId()).build();
    shipment.persist();
    this.logger.info("Shipment event processed successfully for order id {} shipment id {}",
        startShipmentEvent.getOrderId(),shipment.getId());
    return shipment;
  }

  @Override
  public Shipment find(String id) {
    this.logger.info("Finding shipment id {}",id);
    final Shipment shipment = Shipment.findById(id);
    this.logger.info("Loaded shipment data {}",shipment);
    return shipment;
  }

}
