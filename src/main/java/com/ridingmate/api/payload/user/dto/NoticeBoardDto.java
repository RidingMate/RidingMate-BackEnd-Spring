package com.ridingmate.api.payload.user.dto;

import com.ridingmate.api.entity.BoardEntity;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

@Getter
public class NoticeBoardDto {

    private Long id;
    private String title;
    private String date;

    public NoticeBoardDto(BoardEntity board) {
        id = board.getIdx();
        title = board.getTitle();
        date = board.getCreateAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
}
