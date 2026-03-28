package com.orderprocessing.common.events;

import java.util.UUID;

/**
 * Event used to announce the confirmation of an order.
 */
public record OrderConfirmedEvent(UUID orderExternalId) {}