package tech.claudioed.shipment.domain;

import java.time.LocalDateTime;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author claudioed on 2019-04-13.
 * Project shipment
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Movement {

  private LocalDateTime at;

  @Embedded
  @AttributeOverrides({
      @AttributeOverride(name = "name", column = @Column(name = "from_name")),
      @AttributeOverride(name = "city", column = @Column(name = "from_city")),
      @AttributeOverride(name = "country", column = @Column(name = "from_country"))
  })
  private Place from;

  @Embedded
  @AttributeOverrides({
      @AttributeOverride(name = "name", column = @Column(name = "to_name")),
      @AttributeOverride(name = "city", column = @Column(name = "to_city")),
      @AttributeOverride(name = "country", column = @Column(name = "to_country"))
  })
  private Place to;

}

