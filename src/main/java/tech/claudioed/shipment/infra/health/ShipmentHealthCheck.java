package tech.claudioed.shipment.infra.health;

import io.nats.client.Connection;
import io.nats.client.Connection.Status;
import io.nats.client.Dispatcher;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.eclipse.microprofile.health.Health;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import tech.claudioed.shipment.infra.event.StartShipmentDispatcher;
import tech.claudioed.shipment.infra.nats.NatsConnection;

@Health
@ApplicationScoped
public class ShipmentHealthCheck implements HealthCheck {

  @Inject
  @StartShipmentDispatcher
  Dispatcher dispatcher;

  @Inject
  @NatsConnection
  Connection connection;

  @Override
  public HealthCheckResponse call() {
    if(this.dispatcher.isActive() && Status.CONNECTED.equals(this.connection.getStatus())){
      return HealthCheckResponse.named("Shipment is running").up().build();
    }
    return HealthCheckResponse.named("Shipment is not running").down().build();
  }

}