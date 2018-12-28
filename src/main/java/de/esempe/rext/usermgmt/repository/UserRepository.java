package de.esempe.rext.usermgmt.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import de.esempe.rext.usermgmt.domain.User;

@Stateless(description = "Repository für Domänenklasse User")
public class UserRepository
{
	@PersistenceContext(name = "userdb")
	EntityManager em;

	public UserRepository()
	{

	}

	// Für JPA-Integrationstest
	UserRepository(final EntityManager em)
	{
		this.em = em;
	}

	public List<User> loadAll()
	{
		return this.em.createNamedQuery("all", User.class).getResultList();
	}

	public void save(final User user)
	{
		final Optional<User> findResult = findByObjId(user.getObjid());

		// vorhandene Entität?
		if (findResult.isPresent())
		{
			// --> Update
			this.em.merge(user);
		}
		else
		{
			// --> Insert
			this.em.persist(user);
		}
		this.em.flush();
	}

	public void delete(final UUID objid)
	{
		final Optional<User> searchResult = findByObjId(objid);
		if (searchResult.isPresent())
		{
			this.em.remove(searchResult.get());
			this.em.flush();
		}
	}

	public void delete(final User user)
	{
		delete(user.getObjid());
	}

	public Optional<User> findByObjId(final UUID objid)
	{
		return findByNamedQuery("byObjId", "objid", objid);
	}

	public Optional<User> findByLoginId(final String login)
	{
		return findByNamedQuery("byLogin", "login", login);
	}

	private Optional<User> findByNamedQuery(final String nameOfQuery, final String nameOfParameter, final Object valueOfParameter)
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
//		// 2-n Ergebnisse
//		catch (final NonUniqueResultException e)
//		{
//
//		}

		return result;

	}


}
