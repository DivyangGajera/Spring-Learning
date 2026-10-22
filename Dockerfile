# syntax=docker/dockerfile:1

# ---------------------------------------------------------------------------
# Stage 1: build the Spring Boot fat JAR with Maven
# ---------------------------------------------------------------------------
FROM maven:3.10-eclipse-temurin-17 AS build

WORKDIR /workspace

# Copy the POM first so the dependency download layer is cached and only
# invalidated when pom.xml changes.
COPY pom.xml .
RUN mvn -B -ntp dependency:go-offline

# Now copy the sources and package (tests are run by CI / locally).
COPY src ./src
RUN mvn -B -ntp clean package -DskipTests

# ---------------------------------------------------------------------------
# Stage 2: minimal JRE runtime image
# ---------------------------------------------------------------------------
FROM eclipse-temurin:17-jre

# curl is only needed for container healthchecks, ca-certificates for HTTPS.
RUN apt-get update \
    && apt-get install -y --no-install-recommends curl ca-certificates \
    && rm -rf /var/lib/apt/lists/*

WORKDIR /app

# Run as a non-root user.
RUN groupadd --system app && useradd --system --gid app --home /app app

COPY --from=build /workspace/target/demo-0.0.1-SNAPSHOT.jar /app/app.jar
RUN chown app:app /app/app.jar

USER app

EXPOSE 8080

# Extra JVM flags can be provided at runtime, e.g. JAVA_OPTS="-Xmx512m"
ENV JAVA_OPTS=""

HEALTHCHECK --interval=15s --timeout=5s --start-period=30s --retries=5 \
    CMD curl -fsS http://localhost:8080/api/tasks || exit 1

ENTRYPOINT ["sh", "-c", "exec java $JAVA_OPTS -jar /app/app.jar"]
