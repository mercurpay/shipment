package tech.claudioed.shipment.domain.resources;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import tech.claudioed.shipment.domain.service.ShipmentService;

@Path("/shipments")
public class ShipmentResources {

  @Inject
  ShipmentService shipmentService;

  @GET
  @Path("/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response hello(@PathParam("id")String id) {
    return Response.ok(this.shipmentService.find(id)).build();
  }
}
