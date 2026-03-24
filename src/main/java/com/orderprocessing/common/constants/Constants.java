package com.orderprocessing.common.constants;

public final class Constants {

	// This class should not be instantiated
	private Constants() {}

	public static final String ADMIN_ROLE = "ROLE_ADMIN"; //$NON-NLS-1$

	public static final String PARAM_EXTERNAL_ID = "externalId"; //$NON-NLS-1$

	public static final String BEARER = "Bearer "; //$NON-NLS-1$

    public static final String V3_API_DOCS = "/v3/api-docs/**"; //$NON-NLS-1$
	public static final String SWAGGER_UI = "/swagger-ui/**"; //$NON-NLS-1$
	public static final String ACTUATOR_INFO = "/actuator/info"; //$NON-NLS-1$
	public static final String ACTUATOR_HEALTH = "/actuator/health"; //$NON-NLS-1$

	// response paging
	public static final int PAGE_SIZE_HARD_LIMIT = 10_000;
	public static final String LINK_RESPONSE_HEADER = "Link"; //$NON-NLS-1$
	public static final String LINK_NEXT_TEMPLATE = "<%s>; rel=\"next\""; //$NON-NLS-1$
	public static final String LINK_PREV_TEMPLATE = "<%s>; rel=\"prev\""; //$NON-NLS-1$
	public static final String PAGE_LINK_TEMPLATE = "%s?page=%d&size=%d"; //$NON-NLS-1$
	public static final String PAGE_LINK_SORT_TEMPLATE = "%s?page=%d&size=%d&%s"; //$NON-NLS-1$
	public static final String PAGE_CONTENT_ATTR = "content"; //$NON-NLS-1$

}
