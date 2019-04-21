package tech.claudioed.shipment.domain;

import java.time.LocalDateTime;
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
public class Movement {

  private LocalDateTime at;

  private Place from;

  private Place to;

}

