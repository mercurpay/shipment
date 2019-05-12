package tech.claudioed.shipment.domain;

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
public class Destination implements MongoDocument<Destination> {

  private String address;

  private Place place;

  @Override
  public Document toDoc() {
    return new Document().append("address", this.address).append("place", this.place.toDoc());
  }

  @Override
  public Destination fromDoc(Document doc) {
    return null;
  }

}
