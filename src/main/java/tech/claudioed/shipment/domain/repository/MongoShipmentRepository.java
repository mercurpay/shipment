package tech.claudioed.shipment.domain.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import lombok.SneakyThrows;
import org.bson.Document;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import tech.claudioed.shipment.domain.Destination;
import tech.claudioed.shipment.domain.Movement;
import tech.claudioed.shipment.domain.Place;
import tech.claudioed.shipment.domain.Shipment;
import tech.claudioed.shipment.domain.ShipmentEvent;
import tech.claudioed.shipment.domain.resources.data.Customer;
import tech.claudioed.shipment.domain.resources.data.StartShipmentEvent;

/** @author claudioed on 2019-04-14. Project shipment */
@ApplicationScoped
public class MongoShipmentRepository implements ShipmentRepository {

  private static final String FROM_CITY = "New York";

  private static final String TO_CITY = "Las Vegas";

  @Inject
  ObjectMapper objectMapper;

  @Inject
  Logger logger;

  @Inject
  @ConfigProperty(name = "mongo.database",defaultValue = "SHIPMENT")
  String database;

  @Inject
  MongoClient mongoClient;

  @SneakyThrows
  public Shipment create(StartShipmentEvent startShipmentEvent){
    this.logger.info("Starting shipment for order {} ",startShipmentEvent);
    this.logger.info("Customer data {}",startShipmentEvent.getData());
    final Customer customerData = startShipmentEvent.customerData(this.objectMapper);
    this.logger.info("Customer loaded successfully {}",customerData);
    final Destination destination = Destination.builder().address(customerData.getAddress())
        .place(Place.builder().city(customerData.getCity()).country(customerData.getCountry())
            .name("Home")
            .build()).build();
    final Shipment shipment = Shipment.builder().id(UUID.randomUUID().toString())
        .destination(destination).customerId(customerData.getId())
        .orderId(startShipmentEvent.getOrderId()).events(startEvent(customerData.getCountry())).build();
    try{
      final MongoDatabase db = this.mongoClient.getDatabase(this.database);
      final MongoCollection<Document> dbCollection = db.getCollection("shipments");
      dbCollection.insertOne(shipment.toDoc());
    }catch (Exception e){
      logger.error("Error to persist shipment",e);
    }
    this.logger.info("Shipment event processed successfully for order id {} shipment id {}",
        startShipmentEvent.getOrderId(),shipment.getId());
    return shipment;
  }

  @Override
  public Shipment find(String id) {
    this.logger.info("Finding shipment id {}",id);
    final MongoDatabase db = this.mongoClient.getDatabase(this.database);
    final MongoCollection<Document> dbCollection = db.getCollection("shipments");
    final FindIterable<Document> documents = dbCollection.find(new Document("_id", id));
    final Document first = documents.first();



    return null;
  }

  private List<ShipmentEvent> startEvent(String country){
    final Place from = Place.builder().country(country).city(FROM_CITY).name("Store")
        .build();
    final Place to = Place.builder().country(country).city(TO_CITY).name("Distribution Center")
        .build();
    return Arrays.asList(ShipmentEvent.builder().id(UUID.randomUUID().toString()).action("start")
        .movement(Movement.builder().at(LocalDateTime.now()).from(from).to(to).build()).build());
  }

}
