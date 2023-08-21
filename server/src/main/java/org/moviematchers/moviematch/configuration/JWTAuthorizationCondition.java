package org.moviematchers.moviematch.configuration;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.lang.NonNull;

public class JWTAuthorizationCondition implements Condition {
	@Override
	public boolean matches(@NonNull ConditionContext context, @NonNull AnnotatedTypeMetadata metadata) {
		Environment environment = context.getEnvironment();
		return environment.containsProperty("movie-match.authorization.jwt.public-key") &&
			environment.containsProperty("movie-match.authorization.jwt.private-key");
	}
}
