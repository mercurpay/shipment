package tech.claudioed.shipment.infra.nats;

import io.nats.client.Connection;
import io.nats.client.Nats;
import io.nats.client.Options;
import java.time.Duration;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Produces;
import lombok.SneakyThrows;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class NatsProducer {

  @Inject
  @ConfigProperty(name="nats.user")
  String natsUser;

  @Inject
  @ConfigProperty(name="nats.pass")
  String natsPass;

  @Inject
  @ConfigProperty(name = "nats.host")
  String natsHost;

  @Produces
  @SneakyThrows
  @ApplicationScoped
  public Connection natsConnection() {
    return Nats.connect(new Options.Builder()
        .connectionTimeout(Duration.ofSeconds(2))
        .pingInterval(Duration.ofSeconds(10))
        .reconnectWait(Duration.ofSeconds(1))
        .userInfo(natsUser,natsPass)
        .maxReconnects(-1)
        .reconnectBufferSize(-1)
        .connectionName(System.getenv("HOSTNAME"))
        .server(natsHost)
        .build());
  }

}