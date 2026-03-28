package com.orderprocessing.common.tests;

import static com.orderprocessing.common.tests.SharedPostgresContainer.POSTGRES;
import static com.orderprocessing.common.tests.SharedRabbitMQContainer.RABBITMQ;

import java.util.UUID;

import org.junit.jupiter.api.Tag;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.orderprocessing.common.constants.Constants;

@Tag("integration")
@Testcontainers
public abstract class AbstractIntegrationTestBase {

	protected static final String UUID_REGEX = "[a-fA-F0-9]{8}-([a-fA-F0-9]{4}-){3}[a-fA-F0-9]{12}"; //$NON-NLS-1$

	protected static final String JSON_PATH_EXTERNAL_ID = "$.externalId"; //$NON-NLS-1$
	protected static final String JSON_PATH_ROLE = "$.role"; //$NON-NLS-1$
	protected static final String JSON_PATH_STATUS = "$.status"; //$NON-NLS-1$
	protected static final String JSON_PATH_ADDRESS_LINE1 = "$.address.addressLine1"; //$NON-NLS-1$
	protected static final String JSON_PATH_EMAIL = "$.email"; //$NON-NLS-1$
	protected static final String JSON_PATH_USERNAME = "$.username"; //$NON-NLS-1$

	protected static final String TEST_ADDRESS = "nowhere"; //$NON-NLS-1$
	protected static final String USER_EMAIL = "user@dev.com"; //$NON-NLS-1$
	protected static final String TEST_PSWD = "User1234"; //$NON-NLS-1$
	protected static final String TEST_USER = "user1234"; //$NON-NLS-1$

	@Autowired
	protected MockMvc mockMvc;

	@Autowired
	protected ObjectMapper objectMapper;

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", POSTGRES::getJdbcUrl); //$NON-NLS-1$
        registry.add("spring.datasource.username", POSTGRES::getUsername); //$NON-NLS-1$
        registry.add("spring.datasource.password", POSTGRES::getPassword); //$NON-NLS-1$

        registry.add("spring.rabbitmq.host", RABBITMQ::getHost); //$NON-NLS-1$
        registry.add("spring.rabbitmq.port", RABBITMQ::getAmqpPort); //$NON-NLS-1$
        registry.add("spring.rabbitmq.username", RABBITMQ::getAdminUsername); //$NON-NLS-1$
        registry.add("spring.rabbitmq.password", RABBITMQ::getAdminPassword); //$NON-NLS-1$
    }

	protected abstract Logger getLog();

	private String bearer;

	public String getBearer() {
		return Constants.BEARER + bearer;
	}

	protected void mockLogin(String username, UUID externalId, String role, String secret) {
		bearer = TestJwtTokenGenerator.generateToken(username, externalId, role, secret);
	}

}
