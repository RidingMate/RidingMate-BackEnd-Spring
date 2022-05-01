package com.ridingmate.api.service;

import com.ridingmate.api.entity.BoardEntity;
import com.ridingmate.api.entity.NoticeBoardEntity;
import com.ridingmate.api.entity.TradeBoardEntity;
import com.ridingmate.api.payload.BoardDto;
import com.ridingmate.api.payload.NoticeBoardInsertRequest;
import com.ridingmate.api.payload.TradeBoardInsertRequest;
import com.ridingmate.api.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    @Transactional
    public void insertNoticeBoardContent(NoticeBoardInsertRequest request) {
        NoticeBoardEntity notice = new NoticeBoardEntity(request.getTitle());
        boardRepository.save(notice);
    }

    @Transactional
    public void insertTradeBoardContent(TradeBoardInsertRequest request) {
        TradeBoardEntity trade = new TradeBoardEntity(request.getTitle(), request.getPrice());
        boardRepository.save(trade);
    }

    public void updateBoardContent() {
        // TODO : 게시글 수정
    }

    public void getNoticeBoardList(int pageNum) {
        // TODO : 게시판 게시글 리스트 조회
    }

    public void getBoardContent() {
        // TODO : 게시글 상세 조회
    }

    public void deleteBoardContent() {
        // TODO : 게시글 삭제
    }
}
