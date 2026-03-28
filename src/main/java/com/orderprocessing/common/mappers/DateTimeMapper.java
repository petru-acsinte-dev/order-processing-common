package com.orderprocessing.common.mappers;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import org.mapstruct.Mapper;

@Mapper(config = GlobalMapperConfig.class)
public interface DateTimeMapper extends GlobalMapperConfig {

	default OffsetDateTime toOffsetDateTime(LocalDateTime localDateTime) {
        if (localDateTime == null) {
			return null;
		}
        return localDateTime.atOffset(ZoneOffset.UTC);
    }

}
