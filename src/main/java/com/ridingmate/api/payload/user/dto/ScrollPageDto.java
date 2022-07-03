package com.ridingmate.api.payload.user.dto;

import java.util.List;

import org.springframework.data.domain.Slice;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@ApiModel(description = "무한 스크롤 페이징 DTO")
public class ScrollPageDto<T> {

    @ApiModelProperty("페이징 정보")
    Pageable pageable;

    @ApiModelProperty("페이지 리스트")
    List<T> content;

    public ScrollPageDto(Slice<T> slice) {
        content = slice.getContent();
        pageable = new Pageable(
                slice.isFirst(),
                slice.isLast(),
                slice.getSize(),
                slice.getNumberOfElements(),
                slice.getPageable().getPageNumber());
    }

    @Data
    @RequiredArgsConstructor
    @ApiModel(value = "Infinity Scroll Pageable", description = "무한스크롤 페이징 정보")
    private static class Pageable {

        @ApiModelProperty("첫페이지 여부")
        private final Boolean isFirst;

        @ApiModelProperty("마지막 페이지 여부")
        private final Boolean isLast;

        @ApiModelProperty("요청한 페이지 사이즈")
        private final int size;

        @ApiModelProperty("조회된 페이지 수")
        private final int count;

        @ApiModelProperty("요청한 페이지 번호")
        private final int pageNumber;

    }

}
