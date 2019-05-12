package tech.claudioed.shipment.domain;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.Document;

/** @author claudioed on 2019-04-13. Project shipment */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Shipment implements MongoDocument<Shipment> {

  private String id;

  private String orderId;

  private String customerId;

  private Destination destination;

  private List<ShipmentEvent> events;

  public ShipmentEvent firstEvent() {
    return this.events.get(0);
  }

  public Document toDoc() {
    return new Document()
        .append("_id", this.id)
        .append("orderId", this.orderId)
        .append("customerId", this.customerId)
        .append("destination", this.destination.toDoc())
        .append("events", this.events.stream().map(ShipmentEvent::toDoc));
  }

  @Override
  public Shipment fromDoc(Document doc) {


    return null;
  }
}
