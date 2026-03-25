package com.orderprocessing.common.security;

import java.util.UUID;

/**
 * This represents the principal used in the order processing security.
 */
public record OrdersPrincipal(String username, UUID externalId) {}
