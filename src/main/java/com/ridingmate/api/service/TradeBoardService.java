package com.ridingmate.api.service;

import com.ridingmate.api.entity.BoardEntity;
import com.ridingmate.api.entity.NoticeBoardEntity;
import com.ridingmate.api.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TradeBoardService implements BoardService {

    private final BoardRepository boardRepository;

    @Transactional
    public void insertBoardContent(BoardEntity board) {
        boardRepository.save(board);
    }

    @Transactional
    public void updateBoardContent(BoardEntity board) {
        boardRepository.save(board);
    }

    public Page<BoardEntity> getBoardList(PageRequest page) {
        return null;
    }

    public BoardEntity getBoardContent(Long boardId) {
        return new NoticeBoardEntity();
    }

    @Transactional
    public void deleteBoardContent(Long boardId) {

    }

    @Transactional
    public void deleteBoardContent() {

    }
}
