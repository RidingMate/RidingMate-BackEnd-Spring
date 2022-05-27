package com.ridingmate.api.service;

import com.ridingmate.api.entity.BoardEntity;

public interface BoardService {

    void insertBoardContent(BoardEntity board);

    void updateBoardContent(BoardEntity board);

    void deleteBoardContent(Long boardId);

    BoardEntity getBoardContent(Long boardId);

}
