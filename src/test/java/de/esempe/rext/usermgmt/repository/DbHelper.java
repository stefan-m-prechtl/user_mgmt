package de.esempe.rext.usermgmt.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class DbHelper
{
	public static void deleteTableData(final String jpaContext)
	{
		final List<String> deleteQueries = new ArrayList<String>();
		deleteQueries.add("DELETE FROM testdb.t_user");

		final EntityManagerFactory factory = Persistence.createEntityManagerFactory(jpaContext);
		final EntityManager em = factory.createEntityManager();
		final EntityTransaction tx = em.getTransaction();

		for (final String deleteQuery : deleteQueries)
		{
			tx.begin();
			final Query qry = em.createNativeQuery(deleteQuery);
			qry.executeUpdate();
			tx.commit();
		}
	}
}
