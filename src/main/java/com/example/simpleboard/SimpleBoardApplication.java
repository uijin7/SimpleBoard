package com.example.simpleboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.TimeZone;

import static com.example.simpleboard.global.time.TimeProvider.KOREA_ZONE_ID;

@SpringBootApplication
public class SimpleBoardApplication {

    public static void main(String[] args) {
        TimeZone.setDefault(TimeZone.getTimeZone(KOREA_ZONE_ID));
        SpringApplication.run(SimpleBoardApplication.class, args);
    }

}
