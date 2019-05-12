package tech.claudioed.shipment.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.Document;

/** @author claudioed on 2019-04-21. Project shipment */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShipmentEvent implements MongoDocument {

  private String id;

  private String action;

  private Movement movement;

  @Override
  public Document toDoc() {
    return new Document()
        .append("_id", this.id)
        .append("action", this.action)
        .append("movement", this.movement.toDoc());
  }

  @Override
  public Object fromDoc(Document doc) {
    return null;
  }
}
