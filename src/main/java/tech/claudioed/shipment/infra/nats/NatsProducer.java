package tech.claudioed.shipment.infra.nats;

import io.nats.client.Connection;
import io.nats.client.Nats;
import io.nats.client.Options;
import java.time.Duration;
import java.util.Objects;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import lombok.SneakyThrows;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;

@ApplicationScoped
public class NatsProducer {

  @Inject
  Logger logger;

  @Inject
  @ConfigProperty(name = "nats.user")
  String natsUser;

  @Inject
  @ConfigProperty(name = "nats.pass")
  String natsPass;

  @Inject
  @ConfigProperty(name = "nats.host")
  String natsHost;

  private Connection connection;

  @Produces
  @SneakyThrows
  @ApplicationScoped
  @NatsConnection
  public Connection natsConnection() {
    if (Objects.isNull(this.connection)) {
      logger.info("Creating nats connection...");
      this.connection =
          Nats.connect(
              new Options.Builder()
                  .connectionTimeout(Duration.ofSeconds(2))
                  .pingInterval(Duration.ofSeconds(10))
                  .reconnectWait(Duration.ofSeconds(1))
                  .userInfo(natsUser, natsPass)
                  .maxReconnects(-1)
                  .reconnectBufferSize(-1)
                  .connectionName(System.getenv("HOSTNAME"))
                  .server(natsHost)
                  .build());
      logger.info("Connected to Nats server on {} with user {}", this.natsHost, this.natsHost);
    }
    return this.connection;
  }

  public void executeNats(@Observes @Initialized(ApplicationScoped.class) Object init){
    natsConnection();
  }

  @PreDestroy
  @SneakyThrows
  void cleanUp(){
    if(Objects.nonNull(this.connection)){
      logger.info("Closing nats connection...");
      this.connection.close();
      logger.info("Nats connection closed successfully");
    }
  }


}
