package com.ridingmate.api.service;

import java.util.List;
import java.util.Objects;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
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
import lombok.extern.slf4j.Slf4j;

@Slf4j
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

    private Page<TradeBoardEntity> getBoardListPage(Pageable pageable, Predicate predicate) {
        return tradeBoardRepository.findAll(predicate, pageable);
    }

    private Slice<TradeBoardEntity> getBoardListSlice(Pageable pageable, Predicate predicate) {
        return tradeBoardRepository.findAll(predicate, pageable);
    }

    private TradeBoardEntity getBoardContent(Long boardId) {
        TradeBoardEntity board = tradeBoardRepository.findById(boardId).orElseThrow(() -> new CustomException(ResponseCode.NOT_FOUND_BOARD));
        board.increaseHitCount();
        return board;
    }

    /**
     * ????????? ??????
     * @param dto ?????? ??????
     * @param user ?????? ???????????? ?????????
     */
    @Transactional
    public void insertTradeBoardContent(BoardDto.Request.TradeInsert dto, UserEntity user) {
        TradeBoardEntity tradeBoard = TradeInsert.of(dto);
        tradeBoard.setBoardInfo(dto.getTitle(), dto.getContent(), user);
        tradeBoard.setDateOfPurchase(dto.getPurchaseYear(), dto.getPurchaseMonth());
        tradeBoard.setForSaleStatus();

        // ??? ?????????
        if (dto.getBikeIdx() != null) {
            BikeEntity myBike = bikeRepository.findById(dto.getBikeIdx()).orElseThrow(
                    () -> new CustomException(ResponseCode.NOT_FOUND_BIKE));
            tradeBoard.setMyBike(myBike);
        }
        // ?????? ??????
        if (StringUtils.hasText(dto.getLocationCode())) {
            LocationEntity location = locationService.getLocation(dto.getLocationCode());
            tradeBoard.setLocation(location);
        }
        TradeBoardEntity saveTradeBoard = tradeBoardRepository.save(tradeBoard);

        // ?????? ??????
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
     * ????????? ??????
     * @param dto ?????? ??????
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
     * ????????? ????????? ??????
     * @param pageable ????????? ??????
     * @param dto ?????? ??????
     * @return ????????? ?????????
     */
    public Page<TradeList> getTradeBoardList(Pageable pageable, BoardDto.Request.TradeList dto) {
        return getBoardListPage(pageable, BoardPredicate.tradeBoardPredicate(dto)).map(TradeList::of);
    }

    public Slice<TradeList> getTradeBoardSlice(Pageable pageable, BoardDto.Request.TradeList dto) {
        return getBoardListSlice(pageable, BoardPredicate.tradeBoardPredicate(dto)).map(TradeList::of);
    }

    /**
     * ????????? ?????? ??????
     * @param boardId   ????????? ID
     * @param user      ?????? ???????????? ??????
     * @return          ????????? ??????
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
     * ????????? ??????
     * @param boardId ????????? ????????? ID
     */
    @Transactional
    public void deleteBoardContent(Long boardId, Long userId) {
        TradeBoardEntity tradeBoard = tradeBoardRepository.findById(boardId).orElseThrow(
                () -> new CustomException(ResponseCode.NOT_FOUND_BOARD));
        if (!Objects.equals(tradeBoard.getUser().getIdx(), userId)) {
            throw new CustomException(ResponseCode.NOT_WRITER_OF_BOARD);
        }
        fileService.deleteMultipleFile(tradeBoard.getFiles());
        tradeBoardRepository.delete(tradeBoard);
    }

    /**
     * ?????? ??????
     * @param dto ?????? ??????
     * @param user ?????? ?????????
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
     * ????????? ??????
     * @param dto ????????? ??????
     * @param user ????????? ?????????
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
     * ?????? ????????? ??????
     * @param dto ?????? ??????
     * @param commentPageable ?????? ????????? ??????
     * @return ?????? ?????????
     */
    public Page<Response.Info> getCommentList(Comment dto, Pageable commentPageable) {
        TradeBoardEntity tradeBoard = tradeBoardRepository.findById(dto.getBoardId()).orElseThrow(
                () -> new CustomException(ResponseCode.NOT_FOUND_BOARD));
        return commentRepository.findAll(CommentPredicate.getComment(tradeBoard, null), commentPageable)
                                .map(Response.Info::of);
    }

    /**
     * ????????? ????????? ??????
     * @param dto ????????? ??????
     * @param commentPageable ????????? ????????? ??????
     * @return ????????? ?????????
     */
    public Page<Response.Info> getReplyList(Reply dto, Pageable commentPageable) {
        TradeBoardEntity tradeBoard = tradeBoardRepository.findById(dto.getBoardId()).orElseThrow(
                () -> new CustomException(ResponseCode.NOT_FOUND_BOARD));
        return commentRepository.findAll(CommentPredicate.getComment(tradeBoard, dto.getCommentId()), commentPageable)
                .map(Response.Info::of);
    }

    /**
     * ????????? ???????????? ??????
     * @param boardId ????????? ID
     */
    @Transactional
    public void setTradeStatusToComplete(Long boardId, UserEntity user) {
        TradeBoardEntity tradeBoard = tradeBoardRepository.findById(boardId).orElseThrow(
                () -> new CustomException(ResponseCode.NOT_FOUND_BOARD));
        if (!tradeBoard.getUser().getIdx().equals(user.getIdx())) {
            throw new CustomException(ResponseCode.NOT_WRITER_OF_BOARD);
        }
        tradeBoard.setCompletedStatus();
    }

    /**
     * ?????? ????????? ????????? ?????????
     * @param user      ?????? ???????????? ??????
     * @param pageable  page
     * @return          TradeList
     */
    public Page<TradeList> getMyTradeBoardList(UserEntity user, Pageable pageable) {
        return tradeBoardRepository.findAll(BoardPredicate.myTradeBoardPredicate(user), pageable)
                .map(TradeList::of);
    }

    /**
     * ?????? ?????? ?????? ????????? ?????????
     * @param user      ?????? ???????????? ??????
     * @param pageable  page
     * @return          TradeList
     */
    public Page<TradeList> getMyCommentBoardList(UserEntity user, Pageable pageable) {
        return boardCustomRepository.getMyCommentBoardList(user, pageable)
                                    .map(board -> TradeList.of((TradeBoardEntity) board));
    }

    /**
     * ????????? ??????, ??????<br>
     * ????????? ????????? ??????, ????????? ??????
     * @param boardId   ????????? ????????? ID
     * @param user      ?????? ???????????? ?????????
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

    /**
     * ????????? ??????
     * @param boardId   ????????? ID
     */
    public void reportContent(Long boardId) {
        tradeBoardRepository.findById(boardId).orElseThrow(
                () -> new CustomException(ResponseCode.NOT_FOUND_BOARD));
    }

}
