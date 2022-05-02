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
public class NoticeBoardService implements BoardService {

    private final BoardRepository boardRepository;

    @Transactional
    public void insertBoardContent(BoardEntity board) {
        boardRepository.save(board);
    }

    @Transactional
    public void updateBoardContent(BoardEntity board) {
    }

    public Page<BoardEntity> getBoardList(PageRequest page) {
        return boardRepository.findAll(page);
    }

    public BoardEntity getBoardContent(Long boardId) {
        return boardRepository.findById(boardId).orElseThrow(() ->
                new NullPointerException("id와 일치하는 게시글이 존재하지 않습니다."));
    }

    @Transactional
    public void deleteBoardContent(Long boardId) {
        boardRepository.deleteById(boardId);
    }
}
