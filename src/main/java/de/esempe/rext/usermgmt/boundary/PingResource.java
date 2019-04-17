package de.esempe.rext.usermgmt.boundary;

import java.time.LocalDateTime;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.RegistryType;
import org.eclipse.microprofile.metrics.MetricRegistry;

@Stateless(description = "REST-Interface für Ping")
@Path("/ping")
public class PingResource
{
	/*
	@Inject
	@RegistryType(type = MetricRegistry.Type.APPLICATION)
	MetricRegistry registry;
	
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public Response ping()
	{
		final LocalDateTime now = LocalDateTime.now();
		final String result = "Got ping from User-Management at: " + now.toString();
		
		registry.counter("pingCounter").inc();

		return Response.ok(result).build();
	}
	*/
	
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	@Counted(monotonic = true, displayName ="PingCounter")
	public Response ping()
	{
		final LocalDateTime now = LocalDateTime.now();
		final String result = "Got ping: " + now.toString();
		
		
		return Response.ok(result).build();
	}

	
	
}
