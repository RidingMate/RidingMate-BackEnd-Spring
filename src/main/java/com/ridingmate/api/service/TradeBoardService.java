package com.ridingmate.api.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.querydsl.core.types.Predicate;
import com.ridingmate.api.consts.ResponseCode;
import com.ridingmate.api.entity.BikeEntity;
import com.ridingmate.api.entity.BookmarkEntity;
import com.ridingmate.api.entity.CommentEntity;
import com.ridingmate.api.entity.FileEntity;
import com.ridingmate.api.entity.LocationEntity;
import com.ridingmate.api.entity.TradeBoardEntity;
import com.ridingmate.api.entity.UserEntity;
import com.ridingmate.api.exception.CustomException;
import com.ridingmate.api.payload.user.dto.BoardDto;
import com.ridingmate.api.payload.user.dto.BoardDto.Request.TradeInsert;
import com.ridingmate.api.payload.user.dto.BoardDto.Response.TradeInfo;
import com.ridingmate.api.payload.user.dto.BoardDto.Response.TradeList;
import com.ridingmate.api.payload.user.dto.CommentDto.Request.Comment;
import com.ridingmate.api.payload.user.dto.CommentDto.Request.InsertComment;
import com.ridingmate.api.payload.user.dto.CommentDto.Request.InsertReply;
import com.ridingmate.api.payload.user.dto.CommentDto.Request.Reply;
import com.ridingmate.api.payload.user.dto.CommentDto.Response;
import com.ridingmate.api.payload.user.dto.CommentDto.Response.Info;
import com.ridingmate.api.repository.BikeRepository;
import com.ridingmate.api.repository.BoardCustomRepository;
import com.ridingmate.api.repository.BookmarkRepository;
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
    private final BikeRepository bikeRepository;
    private final BoardCustomRepository boardCustomRepository;
    private final BookmarkRepository bookmarkRepository;

    private Page<TradeBoardEntity> getBoardList(Pageable pageable, Predicate predicate) {
        return tradeBoardRepository.findAll(predicate, pageable);
    }

    private TradeBoardEntity getBoardContent(Long boardId) {
        TradeBoardEntity board = tradeBoardRepository.findById(boardId).orElseThrow(() -> new CustomException(ResponseCode.NOT_FOUND_BOARD));
        board.increaseHitCount();
        return board;
    }

    /**
     * 거래글 등록
     * @param dto 등록 정보
     * @param user 현재 로그인된 사용자
     */
    @Transactional
    public void insertTradeBoardContent(BoardDto.Request.TradeInsert dto, UserEntity user) {
        TradeBoardEntity tradeBoard = TradeInsert.of(dto);
        tradeBoard.setBoardInfo(dto.getTitle(), dto.getContent(), user);
        tradeBoard.setDateOfPurchase(dto.getPurchaseYear(), dto.getPurchaseMonth());
        tradeBoard.setForSaleStatus();

        // 내 바이크
        if (dto.getBikeIdx() != null) {
            BikeEntity myBike = bikeRepository.findById(dto.getBikeIdx()).orElseThrow(
                    () -> new CustomException(ResponseCode.NOT_FOUND_BIKE));
            tradeBoard.setMyBike(myBike);
        }
        // 거래 지역
        if (StringUtils.hasText(dto.getLocationCode())) {
            LocationEntity location = locationService.getLocation(dto.getLocationCode());
            tradeBoard.setLocation(location);
        }
        TradeBoardEntity saveTradeBoard = tradeBoardRepository.save(tradeBoard);

        // 파일 저장
        if (!dto.getFiles().isEmpty()) {
            try {
                List<FileEntity> files = fileService.uploadMultipleFile(dto.getFiles(), user);
                for (FileEntity file : files) {
                    file.connectBoard(saveTradeBoard);
                }
            } catch (Exception e) {
                throw new CustomException(ResponseCode.DONT_SAVE_S3_FILE);
            }
        }
    }

    /**
     * 거래글 수정
     * @param dto 수정 정보
     */
    @Transactional
    public void updateBoardContent(BoardDto.Request.TradeUpdate dto, UserEntity user) {
        if (!dto.getFiles().isEmpty()) {
            try {
                fileService.uploadMultipleFile(dto.getFiles(), user);
            } catch (Exception e) {
                throw new CustomException(ResponseCode.DONT_SAVE_S3_FILE);
            }
        }
    }

    /**
     * 거래글 리스트 조회
     * @param pageable 페이징 정보
     * @param dto 검색 정보
     * @return 거래글 리스트
     */
    public Page<TradeList> getTradeBoardList(Pageable pageable, BoardDto.Request.TradeList dto) {
        return getBoardList(pageable, BoardPredicate.tradeBoardPredicate(dto)).map(TradeList::of);
    }

    /**
     * 거래글 상세 조회
     * @param boardId   거래글 ID
     * @param user      현재 로그인된 유저
     * @return          거래글 상세
     */
    @Transactional
    public TradeInfo getTradeBoardContent(Long boardId, UserEntity user) {
        TradeBoardEntity board = getBoardContent(boardId);
        Page<Response.Info> comments = commentRepository.findAll(CommentPredicate.getComment(board, null),
                                                                 PageRequest.of(0, 7, Sort.by("createAt")
                                                                                          .descending())).map(Info::of);
        return TradeInfo.of(board, comments, user.getIdx(), bookmarkRepository.existsByBoardAndUser(board, user));
    }

    /**
     * 거래글 삭제
     * @param boardId 삭제할 게시글 ID
     */
    @Transactional
    public void deleteBoardContent(Long boardId) {
        TradeBoardEntity tradeBoard = tradeBoardRepository.findById(boardId).orElseThrow(
                () -> new CustomException(ResponseCode.NOT_FOUND_BOARD));
        fileService.deleteMultipleFile(tradeBoard.getFiles());
        tradeBoardRepository.delete(tradeBoard);
    }

    /**
     * 댓글 등록
     * @param dto 댓글 정보
     * @param user 댓글 작성자
     */
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

    /**
     * 대댓글 등록
     * @param dto 대댓글 정보
     * @param user 대댓글 작성자
     */
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

    /**
     * 댓글 리스트 조회
     * @param dto 댓글 정보
     * @param commentPageable 댓글 페이징 정보
     * @return 댓글 리스트
     */
    public Page<Response.Info> getCommentList(Comment dto, Pageable commentPageable) {
        TradeBoardEntity tradeBoard = tradeBoardRepository.findById(dto.getBoardId()).orElseThrow(
                () -> new CustomException(ResponseCode.NOT_FOUND_BOARD));
        return commentRepository.findAll(CommentPredicate.getComment(tradeBoard, null), commentPageable)
                                .map(Response.Info::of);
    }

    /**
     * 대댓글 리스트 조회
     * @param dto 대댓글 정보
     * @param commentPageable 대댓글 페이징 정보
     * @return 대댓글 리스트
     */
    public Page<Response.Info> getReplyList(Reply dto, Pageable commentPageable) {
        TradeBoardEntity tradeBoard = tradeBoardRepository.findById(dto.getBoardId()).orElseThrow(
                () -> new CustomException(ResponseCode.NOT_FOUND_BOARD));
        return commentRepository.findAll(CommentPredicate.getComment(tradeBoard, dto.getCommentId()), commentPageable)
                .map(Response.Info::of);
    }

    /**
     * 거래글 판매완료 처리
     * @param boardId 게시글 ID
     */
    @Transactional
    public void setTradeStatusToComplete(Long boardId, Long userIdx) {
        TradeBoardEntity tradeBoard = tradeBoardRepository.findById(boardId).orElseThrow(
                () -> new CustomException(ResponseCode.NOT_FOUND_BOARD));
        if (!tradeBoard.getUser().getIdx().equals(userIdx)) {
            throw new CustomException(ResponseCode.NOT_WRITER_OF_BOARD);
        }
        tradeBoard.setCompletedStatus();
    }

    /**
     * 내가 작성한 거래글 리스트
     * @param user      현재 로그인된 유저
     * @param pageable  page
     * @return          TradeList
     */
    public Page<TradeList> getMyTradeBoardList(UserEntity user, Pageable pageable) {
        return tradeBoardRepository.findAll(BoardPredicate.myTradeBoardPredicate(user), pageable)
                .map(TradeList::of);
    }

    /**
     * 내가 댓글 남긴 거래글 리스트
     * @param user      현재 로그인된 유저
     * @param pageable  page
     * @return          TradeList
     */
    public Page<TradeList> getMyCommentBoardList(UserEntity user, Pageable pageable) {
        return boardCustomRepository.getMyCommentBoardList(user, pageable)
                                    .map(board -> TradeList.of((TradeBoardEntity) board));
    }

    /**
     * 북마크 등록, 제거<br>
     * 북마크 있으면 해제, 없으면 등록
     * @param boardId   선택한 거래글 ID
     * @param user      현재 로그인된 사용자
     */
    @Transactional
    public void switchBookmark(Long boardId, UserEntity user) {
        TradeBoardEntity board = tradeBoardRepository.findById(boardId).orElseThrow(
                () -> new CustomException(ResponseCode.NOT_FOUND_BOARD));
        bookmarkRepository.findByBoardAndUser(board, user).ifPresentOrElse(
                bookmarkRepository::delete,
                () -> bookmarkRepository.save(BookmarkEntity.builder()
                                                            .board(board)
                                                            .user(user)
                                                            .build()));
    }

}
