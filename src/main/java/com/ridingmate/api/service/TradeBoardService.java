package com.ridingmate.api.service;

import com.querydsl.core.types.Predicate;
import com.ridingmate.api.consts.ResponseCode;
import com.ridingmate.api.entity.BoardEntity;
import com.ridingmate.api.entity.TradeBoardEntity;
import com.ridingmate.api.exception.CustomException;
import com.ridingmate.api.payload.user.dto.TradeBoardDto;
import com.ridingmate.api.payload.user.request.TradeSearchRequest;
import com.ridingmate.api.repository.BoardRepository;
import com.ridingmate.api.repository.TradeBoardRepository;
import com.ridingmate.api.service.common.AwsS3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TradeBoardService implements BoardService {

    private final BoardRepository boardRepository;
    private final TradeBoardRepository tradeBoardRepository;
    private final AwsS3Service s3Service;

    @Transactional
    public void insertBoardContent(BoardEntity board) {
        // TODO : 파일 등록 로직 추가
        boardRepository.save(board);
    }

    @Transactional
    public void updateBoardContent(BoardEntity board) {
        // TODO : 파일 관련 처리 추가
    }

    public Page<TradeBoardEntity> getBoardList(PageRequest page) {
        return tradeBoardRepository.findAll(page);
    }

    public Page<TradeBoardEntity> getBoardList(Predicate predicate, PageRequest page) {
        return tradeBoardRepository.findAll(predicate, page);
    }

    private Page<TradeBoardEntity> getBoardList(Pageable pageable, Predicate predicate) {
        return tradeBoardRepository.findAll(predicate, pageable);
    }

    public Page<TradeBoardDto> getTradeBoardList(Pageable pageable, TradeSearchRequest request) {
        return getBoardList(pageable, null).map(tradeBoard -> new TradeBoardDto(tradeBoard));
    }

    @Transactional
    public TradeBoardEntity getBoardContent(Long boardId) {
        BoardEntity board = boardRepository.findById(boardId).orElseThrow(() ->
                new CustomException(ResponseCode.NOT_FOUND_BOARD));
        board.increaseHitCount();
        return (TradeBoardEntity) board;
    }

    @Transactional
    public void deleteBoardContent(Long boardId) {
        // TODO : 파일 삭제 로직 추가
        boardRepository.deleteById(boardId);
    }

}
