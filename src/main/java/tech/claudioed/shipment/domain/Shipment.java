package tech.claudioed.shipment.domain;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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
public class Shipment extends PanacheEntityBase {

  @Id
  private String id;

  private String orderId;

  private String customerId;

  @Embedded
  private Destination destination;

  @OneToMany(cascade= CascadeType.ALL, fetch = FetchType.LAZY)
  private List<ShipmentEvent> events;

  public ShipmentEvent firstEvent(){
    return this.events.get(0);
  }

}
