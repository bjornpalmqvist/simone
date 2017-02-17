package se.uhr.nya.integration.sim.server.boundary;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import se.uhr.nya.integration.sim.server.boundary.representation.ResourcesRepresentation;

@Path("/")
public class RootResource {

	@Inject
	private ResourcesRepresentation root;

	@GET
	public Response getRoot() {
		return Response.ok(root).build();
	}
}
