package tech.claudioed.shipment.domain;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author claudioed on 2019-04-21.
 * Project shipment
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ShipmentEvent {

  @Id
  private String id;

  @Embedded
  private Movement movement;

  private String action;

}
