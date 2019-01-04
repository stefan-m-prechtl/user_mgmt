package de.esempe.rext.usermgmt.api;

import java.time.LocalDateTime;

import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Stateless(description = "REST-Interface für Ping")
@Path("/ping")
public class PingResource
{
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public Response ping()
	{
		final LocalDateTime now = LocalDateTime.now();
		final String result = "ping from: " + now.toString();

		return Response.ok(result).build();
	}
}
