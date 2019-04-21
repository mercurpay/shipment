package tech.claudioed.shipment.domain.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import javax.inject.Inject;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import tech.claudioed.shipment.domain.Destination;
import tech.claudioed.shipment.domain.Movement;
import tech.claudioed.shipment.domain.Place;
import tech.claudioed.shipment.domain.Shipment;
import tech.claudioed.shipment.domain.ShipmentEvent;
import tech.claudioed.shipment.domain.ShipmentEvent.ShipmentEventBuilder;
import tech.claudioed.shipment.domain.resources.data.Customer;
import tech.claudioed.shipment.domain.resources.data.StartShipmentEvent;

/** @author claudioed on 2019-04-14. Project shipment */
public class JdbcShipmentRepository implements ShipmentRepository {

  private static final String FROM_CITY = "New York";

  private static final String TO_CITY = "Las Vegas";

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
        .orderId(startShipmentEvent.getOrderId()).events(startEvent(customerData.getCountry())).build();
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

  private List<ShipmentEvent> startEvent(String country){
    final Place from = Place.builder().country(country).city(FROM_CITY).name("Store")
        .build();
    final Place to = Place.builder().country(country).city(TO_CITY).name("Distribution Center")
        .build();
    return Arrays.asList(ShipmentEvent.builder().action("start")
        .movement(Movement.builder().at(LocalDateTime.now()).from(from).to(to).build()).build());
  }

}
