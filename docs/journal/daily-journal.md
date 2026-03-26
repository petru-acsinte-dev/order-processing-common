OrderProcessor - common library - Daily journal
=

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
