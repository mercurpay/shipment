package tech.claudioed.shipment.domain;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.Document;

/** @author claudioed on 2019-04-13. Project shipment */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Movement implements MongoDocument<Movement> {

  private LocalDateTime at;

  private Place from;

  private Place to;

  @Override
  public Document toDoc() {
    return new Document()
        .append("at", this.at.toString())
        .append("from", this.from.toDoc())
        .append("to", this.to.toDoc());
  }

  @Override
  public Movement fromDoc(Document doc) {
    return null;
  }
}
