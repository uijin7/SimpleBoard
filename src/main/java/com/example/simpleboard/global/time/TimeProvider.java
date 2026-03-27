package com.example.simpleboard.global.time;

import java.time.LocalDateTime;
import java.time.ZoneId;

public final class TimeProvider {

    public static final ZoneId KOREA_ZONE_ID = ZoneId.of("Asia/Seoul");

    private TimeProvider() {
    }

    public static LocalDateTime nowInKorea() {
        return LocalDateTime.now(KOREA_ZONE_ID);
    }
}
