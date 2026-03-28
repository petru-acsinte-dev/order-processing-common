package com.orderprocessing.common.tests;

import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@Testcontainers
public class SharedRabbitMQContainer {

	@SuppressWarnings("resource")
    public static final RabbitMQContainer RABBITMQ =
            new RabbitMQContainer(DockerImageName.parse("rabbitmq:3-management")) //$NON-NLS-1$
                    .withReuse(true);

    static {
        RABBITMQ.start();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if (RABBITMQ.isRunning()) {
                RABBITMQ.stop();
                RABBITMQ.close();
            }
        }));
    }

    private SharedRabbitMQContainer() {}

}
