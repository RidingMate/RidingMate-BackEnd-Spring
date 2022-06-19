package com.ridingmate.api.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.core.types.Predicate;
import com.ridingmate.api.consts.ResponseCode;
import com.ridingmate.api.entity.BoardEntity;
import com.ridingmate.api.entity.NoticeBoardEntity;
import com.ridingmate.api.entity.UserEntity;
import com.ridingmate.api.exception.CustomException;
import com.ridingmate.api.payload.user.dto.BoardDto;
import com.ridingmate.api.payload.user.dto.BoardDto.Response.NoticeInfo;
import com.ridingmate.api.payload.user.dto.BoardDto.Response.NoticeList;
import com.ridingmate.api.repository.NoticeBoardRepository;
import com.ridingmate.api.repository.predicate.BoardPredicate;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NoticeBoardService {

    private final NoticeBoardRepository noticeBoardRepository;

    @Transactional
    public void updateBoardContent(BoardEntity board) {
    }

    private Page<NoticeBoardEntity> getBoardList(Predicate predicate, Pageable page) {
        return noticeBoardRepository.findAll(predicate, page);
    }

    public Page<BoardDto.Response.NoticeList> getNoticeBoardList(Pageable pageable) {
        return getBoardList(BoardPredicate.noticeBoardPredicate(), pageable)
                .map(NoticeList::of);
    }

    @Transactional
    public void insertNoticeBoard(BoardDto.Request.NoticeInsert dto, UserEntity user) {
        NoticeBoardEntity noticeBoard = new NoticeBoardEntity(dto.getTitle(), user);
        noticeBoardRepository.save(noticeBoard);
    }

    private NoticeBoardEntity getBoardContent(Long boardId) {
        BoardEntity board = noticeBoardRepository.findById(boardId).orElseThrow(() ->
                new CustomException(ResponseCode.NOT_FOUND_BOARD));
        board.increaseHitCount();
        return (NoticeBoardEntity) board;
    }

    @Transactional
    public NoticeInfo getNoticeBoardContent(Long boardId) {
        NoticeBoardEntity board = getBoardContent(boardId);
        return NoticeInfo.of(board);
    }

    @Transactional
    public void deleteBoardContent(Long boardId) {
        noticeBoardRepository.deleteById(boardId);
    }

}
