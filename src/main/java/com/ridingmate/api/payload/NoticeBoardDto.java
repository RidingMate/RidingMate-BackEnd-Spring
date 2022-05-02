package com.ridingmate.api.payload;

import com.ridingmate.api.entity.BoardEntity;
import com.ridingmate.api.entity.NoticeBoardEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

@Getter
@AllArgsConstructor
public class NoticeBoardDto {

    private Long id;
    private String title;
    private String date;

    public static NoticeBoardDto convertEntityToDto(BoardEntity board) {
        return new NoticeBoardDto(
                board.getIdx(),
                board.getTitle(),
                board.getCreateAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
    }
}
