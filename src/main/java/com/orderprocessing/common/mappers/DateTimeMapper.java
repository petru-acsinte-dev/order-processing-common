package com.orderprocessing.common.mappers;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public interface DateTimeMapper {

	default OffsetDateTime toOffsetDateTime(LocalDateTime localDateTime) {
        if (localDateTime == null) {
			return null;
		}
        return localDateTime.atOffset(ZoneOffset.UTC);
    }

}
