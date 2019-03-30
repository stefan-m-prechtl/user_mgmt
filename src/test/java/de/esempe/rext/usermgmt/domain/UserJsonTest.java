package de.esempe.rext.usermgmt.domain;

import static org.assertj.core.api.Assertions.assertThat;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

@DisplayName("Tests für Json-Konvertierung User")
public class UserJsonTest
{
	private User generateUser()
	{
		final User userObj = new User("prs");
		userObj.setFirstname("Stefan");
		userObj.setLastname("Prechtl");

		return userObj;
	}

	@Test
	@Order(1)
	void convertToJson()
	{
		// prepare
		final Jsonb jsonb = JsonbBuilder.create();
		final User userObj = this.generateUser();

		// act
		final String userJson = jsonb.toJson(userObj);
		System.out.println(userJson);

		// assert
		assertThat(userJson).isNotNull();
		assertThat(userJson).isNotEmpty();
		assertThat(userJson).contains("firstname");
		assertThat(userJson).contains("lastname");
		assertThat(userJson).contains("login");
		assertThat(userJson).contains("objid");

	}

	@Test
	@Order(2)
	void convertFromJson()
	{
		// prepare
		final Jsonb jsonb = JsonbBuilder.create();
		final User userObj = this.generateUser();
		final String userJson = jsonb.toJson(userObj);

		// act
		final User userObjFromJson = jsonb.fromJson(userJson, User.class);

		// assert
		assertThat(userObjFromJson).isNotNull();
		assertThat(userObjFromJson).isEqualTo(userObj);
		assertThat(userObjFromJson.getFirstname()).isEqualTo(userObj.getFirstname());
		assertThat(userObjFromJson.getLastname()).isEqualTo(userObj.getLastname());

	}
}
