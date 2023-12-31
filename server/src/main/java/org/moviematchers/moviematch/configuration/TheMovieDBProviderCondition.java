package org.moviematchers.moviematch.configuration;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.lang.NonNull;

public class TheMovieDBProviderCondition implements Condition {
	@Override
	public boolean matches(@NonNull ConditionContext context, @NonNull AnnotatedTypeMetadata metadata) {
		Environment environment = context.getEnvironment();
		return environment.containsProperty("movie-match.provider.the-movie-db.api-key") &&
			environment.containsProperty("movie-match.provider.the-movie-db.api-token");
	}
}
