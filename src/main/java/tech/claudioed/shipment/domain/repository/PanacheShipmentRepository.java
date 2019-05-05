package tech.claudioed.shipment.domain.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import javax.enterprise.context.ApplicationScoped;
import tech.claudioed.shipment.domain.Shipment;

/** @author claudioed on 2019-05-05. Project shipment */
@ApplicationScoped
public class PanacheShipmentRepository implements PanacheRepositoryBase<Shipment, String> {}
