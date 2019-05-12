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
public class Place implements MongoDocument<Place> {

  private String name;

  private String city;

  private String country;

  public Document toDoc() {
    return new Document()
        .append("name", this.name)
        .append("city", this.city)
        .append("country", this.country);
  }

  @Override
  public Place fromDoc(Document doc) {
    return null;
  }
}
