package de.esempe.rext.usermgmt.api;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import de.esempe.rext.usermgmt.domain.User;

@DisplayName("Tests für Json-Konvertierung User")
public class UserJsonTest
{

	@Test
	void convertToJson()
	{
		// prepare
		final Jsonb jsonb = JsonbBuilder.create();
		final User userObj = new User("prs");

		// act
		final String userJson = jsonb.toJson(userObj);

		// assert
		assertThat(userJson).isNotNull();
		assertThat(userJson).isNotEmpty();

	}
	// {"login":"smp2","objid":"373b22b1-fc88-11e8-99be-001ee5a5ba08"}

	@Test
	void convertFromJson()
	{
		// prepare
		final String userJson = "{\"login\":\"smp2\",\"objid\":\"373b22b1-fc88-11e8-99be-001ee5a5ba08\"}";
		final Jsonb jsonb = JsonbBuilder.create();

		// act
		final User userObj = jsonb.fromJson(userJson, User.class);

		// assert
		assertThat(userObj).isNotNull();
		assertThat(userObj.getLogin()).isEqualTo("smp2");
		assertThat(userObj.getObjid()).isEqualTo(UUID.fromString("373b22b1-fc88-11e8-99be-001ee5a5ba08"));
	}
}
