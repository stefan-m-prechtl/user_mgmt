package de.esempe.rext.usermgmt.boundary;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

@Provider
public class JsonbContextResolver implements ContextResolver<Jsonb>
{
	@Override
	public Jsonb getContext(Class<?> type)
	{
		final JsonbConfig config = new JsonbConfig().withAdapters(new UserJsonAdapter());
		final Jsonb jsonb = JsonbBuilder.create(config);

		return jsonb;
	}

}
