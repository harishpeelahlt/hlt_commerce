# hltcommerce Platform (Mono-repo)

Java 21, Spring Boot 3.5.7, Spring Cloud 2024.x, multi-module Maven. Opaque tokens via Spring Authorization Server with `/oauth/check_token` compatibility, multi-tenancy support, Redis integration.

## Modules
- common-orm
- auth-service
- user-service
- organisation-service
- placement-service
- wallet-service
- gateway-service
- config-service
- discovery-service
- infra

## Quick start
- Build: `mvn -q -DskipTests package`
- Run individual service: `mvn -pl :auth-service spring-boot:run`

Detailed README and docker-compose are in `infra/` (to be expanded).

