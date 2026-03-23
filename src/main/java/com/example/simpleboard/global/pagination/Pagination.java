package com.example.simpleboard.common;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Pagination {

    private Integer page;

    private Integer size;

    private Integer currentElement;

    private Integer totalPage;

    private Long totalElement;
}
