package com.ridingmate.api.payload.user.response;

import java.util.List;

import org.springframework.data.domain.Page;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
public class PageResponse<T> {

    Pageable pageable;
    List<T> content;

    public PageResponse(Page<T> page) {
        content = page.getContent();
        pageable = new Pageable(
                page.getPageable().getPageSize(),
                page.getPageable().getPageNumber(),
                page.getTotalPages(),
                page.getTotalElements(),
                page.isFirst(),
                page.isLast());
    }

    @Data
    @RequiredArgsConstructor
    public static class Pageable {
        private final int pageSize;
        private final int pageNumber;
        private final int totalPages;
        private final long totalElements;
        private final boolean isFirst;
        private final boolean isLast;
    }
}
