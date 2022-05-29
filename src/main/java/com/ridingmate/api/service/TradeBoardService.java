package com.ridingmate.api.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.core.types.Predicate;
import com.ridingmate.api.consts.ResponseCode;
import com.ridingmate.api.entity.BoardEntity;
import com.ridingmate.api.entity.LocationEntity;
import com.ridingmate.api.entity.TradeBoardEntity;
import com.ridingmate.api.entity.UserEntity;
import com.ridingmate.api.exception.CustomException;
import com.ridingmate.api.payload.user.dto.BoardDto;
import com.ridingmate.api.repository.BoardRepository;
import com.ridingmate.api.repository.TradeBoardRepository;
import com.ridingmate.api.repository.predicate.BoardPredicate;
import com.ridingmate.api.service.common.AuthService;
import com.ridingmate.api.service.common.AwsS3Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TradeBoardService {

    private final BoardRepository boardRepository;
    private final TradeBoardRepository tradeBoardRepository;
    private final AwsS3Service s3Service;
    private final AuthService authService;
    private final LocationService locationService;

    @Transactional
    public void insertTradeBoardContent(BoardDto.Request.TradeInsert dto) {
        // TODO : 내 바이크 조건처리 필요
        UserEntity userEntity = authService.getUserEntityByAuthentication();

        // 거래 지역
        LocationEntity location = null;
        if (dto.getLocationCode() != null) {
            location = locationService.getLocation(dto.getLocationCode());
        }

        TradeBoardEntity tradeBoard = new TradeBoardEntity(
                dto.getTitle(),
                dto.getContent(),
                dto.getCompany(),
                dto.getModelName(),
                dto.getFuelEfficiency(),
                dto.getCc(),
                dto.getYear(),
                dto.getMileage(),
                dto.getPrice(),
                dto.getPhoneNumber(),
                dto.getIsOpenToBuyer(),
                dto.getPurchaseYear(),
                dto.getPurchaseMonth(),
                userEntity,
                location);
        boardRepository.save(tradeBoard);
    }

    @Transactional
    public void updateBoardContent(BoardEntity board) {
        // TODO : 파일 관련 처리 추가
    }

    private Page<TradeBoardEntity> getBoardList(Pageable pageable, Predicate predicate) {
        return tradeBoardRepository.findAll(predicate, pageable);
    }

    public Page<BoardDto.Response.TradeList> getTradeList(Pageable pageable, BoardDto.Request.TradeList dto) {
        return getBoardList(pageable, BoardPredicate.tradeBoardPredicate(dto))
                .map(tradeBoard -> BoardDto.Response.TradeList.builder()
                                                              .id(tradeBoard.getIdx())
                                                              .title(tradeBoard.getTitle())
                                                              .company(tradeBoard.getCompany())
                                                              .modelName(tradeBoard.getModelName())
                                                              .price(tradeBoard.getPrice())
                                                              .cc(tradeBoard.getCc())
                                                              .mileage(tradeBoard.getMileage())
                                                              .year(tradeBoard.getYear())
                                                              .build());
    }

    private TradeBoardEntity getBoardContent(Long boardId) {
        BoardEntity board = boardRepository.findById(boardId).orElseThrow(() ->
                new CustomException(ResponseCode.NOT_FOUND_BOARD));
        board.increaseHitCount();
        return (TradeBoardEntity) board;
    }

    @Transactional
    public BoardDto.Response.TradeContent getTradeBoardContent(Long boardId) {
        TradeBoardEntity board = getBoardContent(boardId);
        return BoardDto.Response.TradeContent.builder()
                                             .title(board.getTitle())
                                             .company(board.getCompany())
                                             .modelName(board.getModelName())
                                             .price(board.getPrice())
                                             .cc(board.getCc())
                                             .mileage(board.getMileage())
                                             .year(board.getYear())
                                             .dateOfPurchase(board.getDateOfPurchase().toString())
                                             .location(board.getLocation() != null ? board.getLocation().getName() : "")
                                             .build();
    }

    @Transactional
    public void deleteBoardContent(Long boardId) {
        // TODO : 파일 삭제 로직 추가
        boardRepository.deleteById(boardId);
    }

}
