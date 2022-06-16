package com.ridingmate.api.service;

import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.core.types.Predicate;
import com.ridingmate.api.consts.ResponseCode;
import com.ridingmate.api.entity.BoardEntity;
import com.ridingmate.api.entity.CommentEntity;
import com.ridingmate.api.entity.FileEntity;
import com.ridingmate.api.entity.LocationEntity;
import com.ridingmate.api.entity.TradeBoardEntity;
import com.ridingmate.api.entity.UserEntity;
import com.ridingmate.api.exception.CustomException;
import com.ridingmate.api.payload.user.dto.BoardDto;
import com.ridingmate.api.payload.user.dto.CommentDto.Request.Comment;
import com.ridingmate.api.payload.user.dto.CommentDto.Request.InsertComment;
import com.ridingmate.api.payload.user.dto.CommentDto.Request.InsertReply;
import com.ridingmate.api.payload.user.dto.CommentDto.Request.Reply;
import com.ridingmate.api.payload.user.dto.CommentDto.Response.Info;
import com.ridingmate.api.payload.user.dto.PageDto;
import com.ridingmate.api.repository.CommentRepository;
import com.ridingmate.api.repository.TradeBoardRepository;
import com.ridingmate.api.repository.predicate.BoardPredicate;
import com.ridingmate.api.repository.predicate.CommentPredicate;
import com.ridingmate.api.service.common.FileService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TradeBoardService {

    private final TradeBoardRepository tradeBoardRepository;
    private final LocationService locationService;
    private final CommentRepository commentRepository;
    private final FileService fileService;

    @Transactional
    public void insertTradeBoardContent(BoardDto.Request.TradeInsert dto, UserEntity user) {
        // TODO : 내 바이크 조건처리 필요

        // 거래 지역
        LocationEntity location = null;
        if (dto.getLocationCode() != null) {
            location = locationService.getLocation(dto.getLocationCode());
        }

        // 파일 저장
        if (!dto.getFiles().isEmpty()) {
            try {
                List<FileEntity> files = fileService.uploadMultipleFile(dto.getFiles(), user);
            } catch (Exception e) {
                throw new CustomException(ResponseCode.DONT_SAVE_S3_FILE);
            }
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
                user,
                location);
        tradeBoardRepository.save(tradeBoard);
    }

    @Transactional
    public void updateBoardContent(BoardDto.Request.TradeUpdate dto) {
        // TODO : 파일 관련 처리 추가
        if (!dto.getFiles().isEmpty()) {

        }
    }

    private Page<TradeBoardEntity> getBoardList(Pageable pageable, Predicate predicate) {
        return tradeBoardRepository.findAll(predicate, pageable);
    }

    public Page<BoardDto.Response.TradeList> getTradeBoardList(Pageable pageable, BoardDto.Request.TradeList dto) {
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
        BoardEntity board = tradeBoardRepository.findById(boardId).orElseThrow(() ->
                new CustomException(ResponseCode.NOT_FOUND_BOARD));
        board.increaseHitCount();
        return (TradeBoardEntity) board;
    }

    @Transactional
    public BoardDto.Response.TradeContent getTradeBoardContent(Long boardId, Long userIdx) {
        TradeBoardEntity board = getBoardContent(boardId);
        Page<Info> comments = commentRepository.findAll(CommentPredicate.getComment(board, null), PageRequest.of(0, 7, Sort.by("createAt").descending()))
                .map(comment -> Info.builder()
                                    .commentId(comment.getIdx())
                                    .content(comment.getContent())
                                    .date(comment.getCreateAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                                    .username(comment.getUser().getNickname())
                                    .build());
        return BoardDto.Response.TradeContent.builder()
                                             .boardId(board.getIdx())
                                             .title(board.getTitle())
                                             .company(board.getCompany())
                                             .modelName(board.getModelName())
                                             .price(board.getPrice())
                                             .cc(board.getCc())
                                             .mileage(board.getMileage())
                                             .year(board.getYear())
                                             .dateOfPurchase(board.getDateOfPurchase() != null ? board.getDateOfPurchase().toString() : null)
                                             .location(board.getLocation() != null ?
                                                       board.getLocation().getName() : "")
                                             .comments(new PageDto<>(comments))
                                             .isMyPost(board.getUser() != null && board.getUser().getIdx() == userIdx)
                                             .build();
    }

    @Transactional
    public void deleteBoardContent(Long boardId) {
        // TODO : 파일 삭제 로직 추가
        tradeBoardRepository.deleteById(boardId);
    }

    @Transactional
    public void insertComment(InsertComment dto, UserEntity user) {
        TradeBoardEntity tradeBoard = tradeBoardRepository.findById(dto.getBoardId()).orElseThrow(
                () -> new CustomException(ResponseCode.NOT_FOUND_BOARD));
        CommentEntity comment = CommentEntity.builder()
                                             .board(tradeBoard)
                                             .content(dto.getContent())
                                             .user(user)
                                             .build();
        commentRepository.save(comment);
    }

    @Transactional
    public void insertReply(InsertReply dto, UserEntity user) {
        TradeBoardEntity tradeBoard = tradeBoardRepository.findById(dto.getBoardId()).orElseThrow(
                () -> new CustomException(ResponseCode.NOT_FOUND_BOARD));
        CommentEntity comment = CommentEntity.builder()
                                             .parentCommentIdx(dto.getCommentId())
                                             .board(tradeBoard)
                                             .content(dto.getContent())
                                             .user(user)
                                             .build();
        commentRepository.save(comment);
    }

    public Page<Info> getCommentList(Comment dto, Pageable commentPageable) {
        TradeBoardEntity tradeBoard = tradeBoardRepository.findById(dto.getBoardId()).orElseThrow(
                () -> new CustomException(ResponseCode.NOT_FOUND_BOARD));
        return commentRepository.findAll(CommentPredicate.getComment(tradeBoard, null), commentPageable)
                                .map(comment -> Info.builder()
                                                    .commentId(comment.getIdx())
                                                    .content(comment.getContent())
                                                    .date(comment.getCreateAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                                                    .username(comment.getUser().getNickname())
                                                    .build());
    }

    public Page<Info> getReplyList(Reply dto, Pageable commentPageable) {
        TradeBoardEntity tradeBoard = tradeBoardRepository.findById(dto.getBoardId()).orElseThrow(
                () -> new CustomException(ResponseCode.NOT_FOUND_BOARD));
        return commentRepository.findAll(CommentPredicate.getComment(tradeBoard, dto.getCommentId()), commentPageable)
                .map(comment -> Info.builder()
                                    .commentId(comment.getIdx())
                                    .content(comment.getContent())
                                    .date(comment.getCreateAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                                    .username(comment.getUser().getNickname())
                                    .build());
    }

    /**
     * 거래글 판매완료 처리
     * @param boardId 게시글 ID
     */
    @Transactional
    public void setTradeStatusToComplete(Long boardId, Long userIdx) {
        TradeBoardEntity tradeBoard = tradeBoardRepository.findById(boardId).orElseThrow(
                () -> new CustomException(ResponseCode.NOT_FOUND_BOARD));
        if (tradeBoard.getUser().getIdx() != userIdx) {
            throw new CustomException(ResponseCode.NOT_WRITER_OF_BOARD);
        }
        tradeBoard.setCompletedStatus();
    }

}
