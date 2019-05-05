package tech.claudioed.shipment.domain.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import tech.claudioed.shipment.domain.Shipment;
import tech.claudioed.shipment.infra.event.EventRequest;
import tech.claudioed.shipment.infra.event.EventRequest.EventRequestBuilder;
import tech.claudioed.shipment.infra.event.ShipmentStarted;

/**
 * @author claudioed on 2019-05-04.
 * Project shipment
 */
@ApplicationScoped
public class NotifyCrmShipmentStartedService {

  @Inject
  @ConfigProperty(name = "crm.events.url")
  String crmEventsUrl;

  @Inject
  ObjectMapper mapper;

  @Inject
  Logger logger;

  public void notifyCrmStarted(@Observes @ShipmentStarted Shipment shipment){
    this.logger.info("Starting notify CRM for orderId {}",shipment.getOrderId());
    final Map data = this.mapper.convertValue(shipment.firstEvent(), Map.class);
    final EventRequestBuilder eventStarted = EventRequest.builder()
        .type("START_SHIPMENT_STARTED").data(data);
    final String address = this.crmEventsUrl + "/api/orders" + "/" + "/" + shipment.getOrderId() + "/event";
    final Response response = ClientBuilder.newClient().target(address)
        .request(MediaType.APPLICATION_JSON).post(
            Entity.entity(eventStarted, MediaType.APPLICATION_JSON));
    this.logger.info("CRM notified with result {}",response.getStatus());
  }

}
