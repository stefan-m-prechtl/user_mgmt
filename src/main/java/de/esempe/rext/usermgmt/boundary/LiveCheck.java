package de.esempe.rext.usermgmt.boundary;

import java.time.LocalDateTime;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.health.Health;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;

@Health
@ApplicationScoped
public class LiveCheck implements HealthCheck 
{

	@Override
	public HealthCheckResponse call() 
	{
		return HealthCheckResponse.named("LiveCheck").up().withData("now",LocalDateTime.now().toString()).build();
	}

}
