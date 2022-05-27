package com.ridingmate.api.service;

import com.querydsl.core.types.Predicate;
import com.ridingmate.api.entity.BoardEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface BoardService {

    void insertBoardContent(BoardEntity board);

    void updateBoardContent(BoardEntity board);

    void deleteBoardContent(Long boardId);

    BoardEntity getBoardContent(Long boardId);

}
