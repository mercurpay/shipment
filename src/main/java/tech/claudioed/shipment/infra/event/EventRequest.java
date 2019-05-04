package tech.claudioed.shipment.infra.event;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author claudioed on 2019-03-05.
 * Project crm
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventRequest {

  private String type;

  private Map<String, Object> data;

}
