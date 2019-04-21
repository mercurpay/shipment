package tech.claudioed.shipment.domain;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import java.util.List;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author claudioed on 2019-04-13.
 * Project shipment
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
public class Shipment extends PanacheEntity {

  @Id
  private String id;

  private String orderId;

  private String customerId;

  @Embedded
  private Destination destination;

  @ElementCollection
  private List<ShipmentEvent> events;

}
