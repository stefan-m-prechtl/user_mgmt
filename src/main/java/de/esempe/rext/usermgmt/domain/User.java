package de.esempe.rext.usermgmt.domain;

import java.io.Serializable;
import java.util.UUID;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.google.common.base.MoreObjects;

@Entity
@Table(name = "t_user", schema = "testdb")
//@formatter:off
@NamedQueries({
	@NamedQuery(name = "all", query = "SELECT u FROM User u"),
	@NamedQuery(name = "byObjId", query = "SELECT u FROM User u WHERE u.objid= :objid"),
	@NamedQuery(name = "byLogin", query = "SELECT u FROM User u WHERE u.login= :login")
})
//@formatter:on

public class User implements Serializable
{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonbTransient
	private long id;

	@Convert(converter = de.esempe.rext.usermgmt.repository.UuidConverter.class)
	private UUID objid;

	private String login;

	User()
	{
		// wegen JPA
	}


	public User(final String login)
	{
		this(login, UUID.randomUUID());
	}

	// Für Konvertierung von Json-String zu Java-Objekct
	@JsonbCreator
	public User(@JsonbProperty("login") final String login, @JsonbProperty("objid") final UUID objid)
	{
		this.id = -1L;
		this.objid = objid;
		this.login = login;
	}


	// Getter/Setter
	public String getLogin()
	{
		return this.login;
	}

	public void setLogin(final String login)
	{
		this.login = login;
	}

	public long getId()
	{
		return this.id;
	}

	public UUID getObjid()
	{
		return this.objid;
	}


	// Standardmethoden

	@Override
	public String toString()
	{
		//@formatter:off
		final String result = MoreObjects.toStringHelper(this)
							.add("id",this.id)
							.add("obiId",this.objid)
							.add("login",this.login)
							.toString();
		
		return result;
		//@formatter:on
	}





}
