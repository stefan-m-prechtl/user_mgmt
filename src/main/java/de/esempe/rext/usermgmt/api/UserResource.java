package de.esempe.rext.usermgmt.api;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import de.esempe.rext.usermgmt.domain.User;
import de.esempe.rext.usermgmt.repository.UserRepository;

@Stateless(description = "REST-Interface für User")
@Path("/users")
public class UserResource
{

	@Inject
	UserRepository repository;

	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUsers()
	{
		final List<User> users = this.repository.loadAll();
		return Response.ok(users).build();
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getResourceById(@PathParam("id") final String resourceId) throws Exception
	{
		final UUID objid = UUID.fromString(resourceId);
		final Optional<User> searchResult = this.repository.findByObjId(objid);

		if (searchResult.isPresent())
		{
			// Link im HTTP-Header: HTTPreturn Response.ok(result).links(link).build();
			return Response.ok(searchResult.get()).build();
		}

		return Response.status(Response.Status.NOT_FOUND).build();
	}

	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteResourceById(@PathParam("id") final String resourceId)
	{
		final UUID objid = UUID.fromString(resourceId);
		final Optional<User> searchResult = this.repository.findByObjId(objid);

		if (searchResult.isPresent())
		{
			this.repository.delete(objid);
			return Response.noContent().build();
		}

		return Response.status(Response.Status.NOT_FOUND).build();
	}

	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createResource(final User user)
	{
		final UUID objid = user.getObjid();

		final Optional<User> searchResult = this.repository.findByObjId(objid);

		if (!searchResult.isPresent())
		{
			this.repository.save(user);
			return Response.noContent().build();
		}

		return Response.status(Response.Status.CONFLICT).build();
	}

	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateResource(@PathParam("id") final String resourceId, final User user)
	{
		final UUID objid = UUID.fromString(resourceId);

		// REST-Pfad-ID muss mit ID im Objekt übereinstimmen
		if (false == objid.equals(user.getObjid()))
		{
			final String reason = String.format("Request-Id %s ungleich der Object-Id %s", objid, user.getObjid());
			return Response.status(Response.Status.BAD_REQUEST).entity(reason).build();
		}

		final Optional<User> searchResult = this.repository.findByObjId(objid);
		if (searchResult.isPresent())
		{
			this.repository.save(user);
			return Response.noContent().build();
		}

		return Response.status(Response.Status.NOT_FOUND).build();

	}





}
