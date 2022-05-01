package com.ridingmate.api.payload;

import com.ridingmate.api.entity.BoardEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BoardDto {

    private String title;

    public static BoardDto convertEntityToDto(BoardEntity board) {
        return new BoardDto(board.getTitle());
    }
}
