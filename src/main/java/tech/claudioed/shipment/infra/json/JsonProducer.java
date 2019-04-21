package tech.claudioed.shipment.infra.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.Produces;

/**
 * @author claudioed on 2019-04-14.
 * Project shipment
 */
@ApplicationScoped
public class JsonProducer {

  @Produces
  @ApplicationScoped
  public ObjectMapper objectMapper(){
    return new ObjectMapper();
  }

}
