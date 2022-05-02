package com.ridingmate.api.service;

import com.ridingmate.api.entity.BoardEntity;
import com.ridingmate.api.entity.TradeBoardEntity;
import com.ridingmate.api.repository.BoardRepository;
import com.ridingmate.api.repository.TradeBoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TradeBoardService implements BoardService {

    private final BoardRepository boardRepository;
    private final TradeBoardRepository tradeBoardRepository;

    @Transactional
    public void insertBoardContent(BoardEntity board) {
        boardRepository.save(board);
    }

    @Transactional
    public void updateBoardContent(BoardEntity board) {
    }

    public Page<TradeBoardEntity> getBoardList(PageRequest page) {
        return tradeBoardRepository.findAll(page);
    }

    @Transactional
    public BoardEntity getBoardContent(Long boardId) {
        BoardEntity board = boardRepository.findById(boardId).orElseThrow(() ->
                new NullPointerException("id와 일치하는 게시글이 존재하지 않습니다."));
        board.increaseHitCount();
        return board;
    }

    @Transactional
    public void deleteBoardContent(Long boardId) {
        boardRepository.deleteById(boardId);
    }

}
