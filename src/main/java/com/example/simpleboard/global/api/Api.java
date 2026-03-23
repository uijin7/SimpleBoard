package com.example.simpleboard.global.api;

import com.example.simpleboard.global.pagination.Pagination;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Api<T> {

    private T body;

    private Pagination pagination;
}
