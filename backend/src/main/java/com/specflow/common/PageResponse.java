package com.specflow.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageResponse<T> {
    private List<T> rows;
    private long total;
    private int page;
    private int pageSize;

    public static <T> PageResponse<T> of(List<T> rows, long total, int page, int pageSize) {
        return new PageResponse<>(rows, total, page, pageSize);
    }
}
