OrderProcessor - common library - Daily journal
=

> Most functionality in this library originated in the monolith phase. 
> See the [monolith journal](https://github.com/petru-acsinte-dev/order-processing-monolith/blob/master/OrdersProcessor/docs/journal/daily-journal.md) for the full design history.

2026-03-23
-
- defined a new GitHub repository associated with a new common Maven project
- moved and adapted to the new common project the classes for the common library (including but not limited to common exceptions, paged responses, OpenAPI configuration, request identification, JWT filter, predefined constants)
- setup a CI to publish the common package

2026-03-24
-
- added global config mapper (common parent class for all order processing mappers)
- added common test classes and test constants
- security utilities

2026-03-25
-
- made JWT self-contained to include user external id; added custom principal to the security context
- moved JWT validation into common and added mock JWT for orders and shipments ITs
- added a global controller advice to handle bad requests (with invalid request body)

2026-03-26
-
- added an interceptor for Feign calls to propagate X-Request-ID across microservices

2026-03-27
-
- updated readme with more details about the common project
- revisited serviceability; introduced a request logging filter with durations

2026-03-28
-
- moved events used for inter-service communication to common
- added support for RabbitMQ (including testcontainers); configured one exchange and 2 queues for order confirmation and shipment
- Feign client cleanup; deleted FeignCorrelationInterceptor, Spring Cloud BOM, and all Feign dependencies; inter-service communication fully replaced by RabbitMQ async messaging
- expanded events to include timestamp and correlationId (requires adoption)
- Replaced LocalDateTime with OffsetDateTime in API responses

2026-04-06
-
- found and fixed an issue (#1) — OpenAPI title was hardcoded in common but each service needs its own title; made configurable via `openapi.title` and `openapi.version` properties with sensible defaults
