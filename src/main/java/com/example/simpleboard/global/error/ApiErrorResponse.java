package com.example.simpleboard.global.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApiErrorResponse {

    private final String message;
}
