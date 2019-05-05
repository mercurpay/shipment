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

//  @Inject @RequestShipment
//  Event<StartShipmentEvent> trigger;

//  @Inject
//  ObjectMapper mapper;

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

//  @Produces
//  @SneakyThrows
//  @ApplicationScoped
//  @StartShipmentDispatcher
//  public Dispatcher startShipmentDispatcher(){
//    this.logger.info("Setting start shipment dispatcher....");
//    final Dispatcher startShipmentDispatcher = this.connection.createDispatcher((message) -> {
//      try {
//        this.logger.info("Receiving message...");
//        final String messageData = new String(message.getData(), StandardCharsets.UTF_8);
//        final StartShipmentEvent startShipmentEvent = this.mapper
//            .readValue(messageData, StartShipmentEvent.class);
//        this.logger.info("Start shipment data {}",startShipmentEvent);
//        //this.trigger.fire(startShipmentEvent);
//      } catch (IOException e) {
//        this.logger.error(e.getMessage());
//      }
//    });
//    startShipmentDispatcher.subscribe("start-shipment");
//    this.logger.info("Shipment dispatcher configured successfully!!!");
//    return startShipmentDispatcher;
//  }

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
