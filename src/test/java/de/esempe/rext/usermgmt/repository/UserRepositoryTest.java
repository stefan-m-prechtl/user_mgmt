package de.esempe.rext.usermgmt.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import de.esempe.rext.usermgmt.domain.User;

@DisplayName("Integrationstests UserRepository/MySQL-Datenbank")
class UserRepositoryTest
{

	private EntityManager em;
	private EntityTransaction tx;
	private final String jpaContext = "dbtest";

	private UserRepository objUnderTest;

	@BeforeEach
	void setUp() throws Exception
	{
		// JPA-Umgebung initialisieren
		final EntityManagerFactory factory = Persistence.createEntityManagerFactory(this.jpaContext);
		this.em = factory.createEntityManager();
		this.tx = this.em.getTransaction();

		// Alle Daten in DB löschen
		DbHelper.deleteTableData(this.jpaContext);

		// Testobjekt erzeugen
		this.objUnderTest = new UserRepository(this.em);

	}

	@AfterEach
	void tearDown() throws Exception
	{
		this.em.clear();
		this.em.close();
	}

	@Test
	void testCRUD()
	{
		// Create
		final User user = create("u4711");

		// Read
		read(user);

		// Update
		user.setLogin(user.getLogin().toUpperCase());
		update(user);

		// Delete
		delete(user);

	}



	private User create(final String login)
	{
		// prepare
		final User result = new User(login);

		// act
		this.tx.begin();
		this.objUnderTest.save(result);
		this.tx.commit();

		// assert
		assertThat(result).isNotNull();
		assertThat(result.getId()).isGreaterThan(0);
		assertThat(result.getLogin()).isEqualTo("u4711");

		// für die Weiterverwendung in den Folgetests
		return result;
	}

	private void read(final User example)
	{
		// act
		final Optional<User> searchResult = this.objUnderTest.findByObjId(example.getObjid());

		// assert
		assertThat(searchResult).isNotNull();
		assertThat(searchResult.get()).isNotNull();
		assertThat(searchResult.get().getLogin()).isEqualTo(example.getLogin());

	}


	private void update(final User user)
	{
		// prepare
		final long id = user.getId();
		final UUID objid = user.getObjid();
		final String login = user.getLogin();

		// act
		this.tx.begin();
		this.objUnderTest.save(user);
		this.tx.commit();

		// assert
		assertThat(user).isNotNull();
		assertThat(user.getId()).isEqualTo(id);
		assertThat(user.getObjid()).isEqualTo(objid);
		assertThat(user.getLogin()).isEqualTo(login);
	}

	private void delete(final User user)
	{
		// prepare
		final UUID objid = user.getObjid();

		// act
		this.tx.begin();
		this.objUnderTest.delete(user);
		this.tx.commit();

		final Optional<User> searchResult = this.objUnderTest.findByObjId(objid);

		// assert
		assertThat(searchResult.isPresent()).isFalse();
	}



}
