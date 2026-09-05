package org.example;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;

 public class ProjectClock {
    private static final Instant START_TIME = Instant.parse("2026-09-05T12:00:00Z");

    public static final Clock clock = Clock.offset(Clock.systemUTC(), Duration.between(Instant.now(), START_TIME));

    public static Instant now() {
        return clock.instant();
    }
}
