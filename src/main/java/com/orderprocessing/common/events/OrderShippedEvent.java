package com.orderprocessing.common.events;

import java.time.Instant;
import java.util.UUID;

/**
 * Event used to announce the confirmation of an order.
 */
public record OrderShippedEvent(UUID orderExternalId, Instant occuredOn, String correlationId) {}