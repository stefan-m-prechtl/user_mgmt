package de.esempe.rext.usermgmt.boundary;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
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

@Stateless(description = "REST-Interface für User")
@Path("/users")
public class UserResource
{
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUsers()
	{
		final List<User> users = this.loadAll();
		return Response.ok(users).build();
	}

	@PersistenceContext(name = "userdb")
	EntityManager em;

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getResourceById(@PathParam("id") final String resourceId) throws Exception
	{
		final UUID objid = UUID.fromString(resourceId);
		final Optional<User> searchResult = this.findByObjId(objid);

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
		final Optional<User> searchResult = this.findByObjId(objid);

		if (searchResult.isPresent())
		{
			this.delete(objid);
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

		final Optional<User> searchResult = this.findByObjId(objid);

		if (!searchResult.isPresent())
		{
			this.save(user);
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

		final Optional<User> searchResult = this.findByObjId(objid);
		if (searchResult.isPresent())
		{
			this.save(user);
			return Response.noContent().build();
		}

		return Response.status(Response.Status.NOT_FOUND).build();

	}

	List<User> loadAll()
	{
		return this.em.createNamedQuery("all", User.class).getResultList();
	}

	void save(final User user)
	{
		final Optional<User> findResult = this.findByObjId(user.getObjid());

		// vorhandene Entität?
		if (findResult.isPresent())
		{
			// --> Update
			this.em.merge(user);
		} else
		{
			// --> Insert
			this.em.persist(user);
		}
		this.em.flush();
	}

	public void delete(final UUID objid)
	{
		final Optional<User> searchResult = this.findByObjId(objid);
		if (searchResult.isPresent())
		{
			this.em.remove(searchResult.get());
			this.em.flush();
		}
	}

	void delete(final User user)
	{
		this.delete(user.getObjid());
	}

	Optional<User> findByObjId(final UUID objid)
	{
		return this.findByNamedQuery("byObjId", "objid", objid);
	}

	Optional<User> findByLoginId(final String login)
	{
		return this.findByNamedQuery("byLogin", "login", login);
	}

	Optional<User> findByNamedQuery(final String nameOfQuery, final String nameOfParameter, final Object valueOfParameter)
	{
		Optional<User> result = Optional.empty();

		try
		{
			final TypedQuery<User> qry = this.em.createNamedQuery(nameOfQuery, User.class);
			qry.setParameter(nameOfParameter, valueOfParameter);
			final User user = qry.getSingleResult();
			result = Optional.of(user);
		}
		// kein Ergebnis
		catch (final NoResultException e)
		{

		}
		// // 2-n Ergebnisse
		// catch (final NonUniqueResultException e)
		// {
		//
		// }

		return result;

	}

}
