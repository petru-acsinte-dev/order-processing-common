# Order Processing: Common Library

Shared library used by all microservices in the 
[Order Processing](https://github.com/petru-acsinte-dev) portfolio project.

Published to GitHub Packages and consumed as a Maven dependency. 

Contains cross-cutting concerns shared across all three microservices — security, messaging, observability, and error handling.

Current version: `0.4.4-SNAPSHOT`

### Usage
```xml
<dependency>
    <groupId>com.orderprocessing</groupId>
    <artifactId>order-processing-common</artifactId>
    <version>0.4.4-SNAPSHOT</version>
</dependency>
```

### Contents
- JWT validation (`JWTValidator`, `JWTValidatorCommon`)
- Security filter chain components (`JWTFilter`, `RequestIdFilter`)
- Correlation ID propagation (`X-Request-ID` via MDC, carried in message events)
- RabbitMQ topology configuration (topic exchange, queues, DLQs, bindings)
- Message events (`OrderConfirmedEvent`, `OrderShippedEvent`)
- Base exception hierarchy and global error handling
- Shared DTOs and `PagedResponse<T>`
- OpenAPI/Swagger configuration
- Shared test infrastructure (`AbstractIntegrationTestBase`, `SharedPostgresContainer`, `SharedRabbitMQContainer`, `TestJwtTokenGenerator`)
- Nginx reverse proxy configuration (`src/main/resources/other/nginx.conf`)

### Related
- [order-processing-users](https://github.com/petru-acsinte-dev/order-processing-users)
- [order-processing-orders](https://github.com/petru-acsinte-dev/order-processing-orders)
- [order-processing-shipments](https://github.com/petru-acsinte-dev/order-processing-shipments)
- [Development journal](docs/journal/daily-journal.md)
