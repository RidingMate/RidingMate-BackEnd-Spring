package com.ridingmate.api.payload.user.dto;

import java.util.List;

import org.springframework.data.domain.Page;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@ApiModel(description = "페이지 응답 dto")
public class PageDto<T> {

    @ApiModelProperty(value = "페이지 정보")
    Pageable pageable;

    @ApiModelProperty(value = "페이지 리스트")
    List<T> content;

    public PageDto(Page<T> page) {
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
    @ApiModel(description = "페이지 정보")
    public static class Pageable {
        @ApiModelProperty(value = "페이지 수")
        private final int pageSize;

        @ApiModelProperty(value = "현재 페이지 번호")
        private final int pageNumber;

        @ApiModelProperty(value = "전체 페이지 수")
        private final int totalPages;

        @ApiModelProperty(value = "전체 게시글 수")
        private final long totalElements;

        @ApiModelProperty(value = "첫페이지 여부")
        private final Boolean isFirst;

        @ApiModelProperty(value = "마지막 페이지 여부")
        private final Boolean isLast;
    }
}
