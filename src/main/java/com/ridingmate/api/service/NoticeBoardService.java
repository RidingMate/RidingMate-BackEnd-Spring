package com.ridingmate.api.service;

import com.querydsl.core.types.Predicate;
import com.ridingmate.api.consts.ResponseCode;
import com.ridingmate.api.entity.BoardEntity;
import com.ridingmate.api.entity.NoticeBoardEntity;
import com.ridingmate.api.exception.CustomException;
import com.ridingmate.api.payload.user.dto.NoticeBoardDto;
import com.ridingmate.api.repository.BoardRepository;
import com.ridingmate.api.repository.NoticeBoardRepository;
import com.ridingmate.api.repository.predicate.BoardPredicate;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class NoticeBoardService implements BoardService {

    private final BoardRepository boardRepository;
    private final NoticeBoardRepository noticeBoardRepository;

    @Transactional
    public void insertBoardContent(BoardEntity board) {
        boardRepository.save(board);
    }

    @Transactional
    public void updateBoardContent(BoardEntity board) {
    }

    private Page<NoticeBoardEntity> getBoardList(Predicate predicate, Pageable page) {
        return noticeBoardRepository.findAll(predicate, page);
    }

    public Page<NoticeBoardDto> getNoticeBoardList(Pageable pageable) {
        return getBoardList(BoardPredicate.noticeBoardPredicate(), pageable)
                .map(noticeBoard -> new NoticeBoardDto(noticeBoard));
    }

    @Transactional
    public NoticeBoardEntity getBoardContent(Long boardId) {
        BoardEntity board = boardRepository.findById(boardId).orElseThrow(() ->
                new CustomException(ResponseCode.NOT_FOUND_BOARD));
        board.increaseHitCount();
        return (NoticeBoardEntity) board;
    }

    @Transactional
    public void deleteBoardContent(Long boardId) {
        boardRepository.deleteById(boardId);
    }

}
