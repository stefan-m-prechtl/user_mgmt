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

	@Test
	@Order(1)
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


	@Test
	@Order(2)
	void convertFromJson()
	{
		// prepare
		final Jsonb jsonb = JsonbBuilder.create();
		final User userObj = new User("prs");
		final String userJson = jsonb.toJson(userObj);


		// act
		final User userObjFromJson = jsonb.fromJson(userJson, User.class);

		// assert
		assertThat(userObjFromJson).isNotNull();
		assertThat(userObjFromJson).isEqualTo(userObj);

	}
}
