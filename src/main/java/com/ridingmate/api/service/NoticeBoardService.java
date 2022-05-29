package com.ridingmate.api.service;

import java.time.format.DateTimeFormatter;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.core.types.Predicate;
import com.ridingmate.api.consts.ResponseCode;
import com.ridingmate.api.entity.BoardEntity;
import com.ridingmate.api.entity.NoticeBoardEntity;
import com.ridingmate.api.exception.CustomException;
import com.ridingmate.api.payload.user.dto.BoardDto;
import com.ridingmate.api.repository.BoardRepository;
import com.ridingmate.api.repository.NoticeBoardRepository;
import com.ridingmate.api.repository.predicate.BoardPredicate;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NoticeBoardService {

    private final BoardRepository boardRepository;
    private final NoticeBoardRepository noticeBoardRepository;

    @Transactional
    public void updateBoardContent(BoardEntity board) {
    }

    private Page<NoticeBoardEntity> getBoardList(Predicate predicate, Pageable page) {
        return noticeBoardRepository.findAll(predicate, page);
    }

    public Page<BoardDto.Response.NoticeList> getNoticeList(Pageable pageable) {
        return getBoardList(BoardPredicate.noticeBoardPredicate(), pageable)
                .map(noticeBoard -> BoardDto.Response.NoticeList.builder()
                                                                .id(noticeBoard.getIdx())
                                                                .title(noticeBoard.getTitle())
                                                                .date(noticeBoard.getCreateAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                                                                .build());
    }

    @Transactional
    public void insertNoticeBoard(BoardDto.Request.NoticeInsert dto) {
        NoticeBoardEntity noticeBoard = new NoticeBoardEntity(dto.getTitle());
        boardRepository.save(noticeBoard);
    }

    private NoticeBoardEntity getBoardContent(Long boardId) {
        BoardEntity board = boardRepository.findById(boardId).orElseThrow(() ->
                new CustomException(ResponseCode.NOT_FOUND_BOARD));
        board.increaseHitCount();
        return (NoticeBoardEntity) board;
    }

    @Transactional
    public BoardDto.Response.NoticeContent getNoticeBoardContent(Long boardId) {
        NoticeBoardEntity board = getBoardContent(boardId);
        return BoardDto.Response.NoticeContent.builder()
                                              .title(board.getTitle())
                                              .build();
    }

    @Transactional
    public void deleteBoardContent(Long boardId) {
        boardRepository.deleteById(boardId);
    }

}
