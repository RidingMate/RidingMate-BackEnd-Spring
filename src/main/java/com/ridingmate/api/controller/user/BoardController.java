package com.ridingmate.api.controller.user;

import javax.validation.Valid;

import org.springframework.data.domain.Pageable;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ridingmate.api.annotation.CurrentUser;
import com.ridingmate.api.consts.ResponseCode;
import com.ridingmate.api.payload.common.ResponseDto;
import com.ridingmate.api.payload.user.dto.BoardDto;
import com.ridingmate.api.payload.user.dto.CommentDto.Request.Comment;
import com.ridingmate.api.payload.user.dto.CommentDto.Request.InsertComment;
import com.ridingmate.api.payload.user.dto.CommentDto.Request.InsertReply;
import com.ridingmate.api.payload.user.dto.CommentDto.Request.Reply;
import com.ridingmate.api.payload.user.dto.CommentDto.Response.Info;
import com.ridingmate.api.payload.user.dto.PageDto;
import com.ridingmate.api.security.UserPrincipal;
import com.ridingmate.api.service.NoticeBoardService;
import com.ridingmate.api.service.TradeBoardService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import springfox.documentation.annotations.ApiIgnore;
/*
    중고장터 관련 컨트롤러
    필터 :   제조사
            모델명
            거래지역
            -> 위 3개만 필터링 인식해서 진행하면 될듯

            주행거리
            가격
            연식
            배기량
            ->이부분은 최소와 최대값이 있기 때문에 null로 들어올경우 0~999999999 이런식으로 해서 항상 검색하면 될듯
    리스트 :  공지사항 페이지네이션 3개씩 보이게 디폴트
            이미지 1, title, 제조사, 모델명, 연식, 현재 주행거리, 배기량, 가격 , 예약/판매완료/판매중 까지 한묶음으로 리스트
        ->필터링 최근 등록순만 있기때문에 이부분 논의해서 뭐 추가할지 생각해보면 좋을듯 합니다.
        ->웹 메인 뷰에서 top 8이 보이기때문에 이부분 리스트도 하나 보이면 될거같습니다 -> 최근 등록순
    댓글 : 대댓까지만 적용해서 작성 -> 프론트에 대댓 알려줄 수 있는 컬럼 있으면 좋을듯 합니다.
        ->fcm기능은 논의 해서 추가해야 할거같습니다.
        ->수정 삭제가 존재해서 컨트롤러 따로 빼는거도 좋을거같습니다.
    디테일 : 이미지, title, content, 거래지역, 연락처, 제조사, 모델, 연식, 구매일자, 누적주행거리, 가격, 댓글 , 주유/정비정보 표출


    판매글작성 : 내 바이크 선택시 또는 직접입력이 존재하는데 이부분은 bikecontroller 쪽에서 처리하겠습니다.
        제조사, 모델명, 연식, 주행거리, 구매일자, title, content, 연락처, 거래지역, 가격, 이미지 (최대 20), 주유/정비정보 공개 동의

   예약중, 판매완료 컬럼 필요함 -> 바이크 글 들어왔을때 글쓴 유저라면 상태 변경 가능하게 해야할것 같습니다.
        -> 판매 완료시 플로우는 조금 추후에 논의 해보면 될거같은데 아마 bike쪽에서 insert로 처리하면 될거같습니당
*/

@RestController
@RequestMapping("/v1/board")
@RequiredArgsConstructor
public class BoardController {

    private final NoticeBoardService noticeBoardService;
    private final TradeBoardService tradeBoardService;

    @GetMapping("/notice/list")
    @ApiOperation("공지사항 리스트 조회")
    public ResponseDto<PageDto<BoardDto.Response.NoticeList>> getNoticeBoardList(
            Pageable pageable
    ) {
        return ResponseDto.<PageDto<BoardDto.Response.NoticeList>>builder()
                          .response(new PageDto<>(noticeBoardService.getNoticeBoardList(pageable)))
                          .build();
    }

    @SneakyThrows
    @GetMapping("/trade/list")
    @ApiOperation("거래글 리스트 조회")
    public ResponseDto<PageDto<BoardDto.Response.TradeList>> getTradeBoardList(
            @Valid BoardDto.Request.TradeList dto,
            Pageable pageable,
            BindingResult result
    ) {
        if (result.hasErrors()) {
            throw new BindException(result);
        }
        return ResponseDto.<PageDto<BoardDto.Response.TradeList>>builder()
                          .response(new PageDto<>(tradeBoardService.getTradeBoardList(pageable, dto)))
                          .build();
    }

    @SneakyThrows
    @PostMapping("/notice")
    @ApiOperation("공지사항 등록")
    public ResponseDto insertNoticeBoard(
            @RequestHeader(value = "Authorization") String token,
            @RequestBody @Valid BoardDto.Request.NoticeInsert request,
            @ApiIgnore @CurrentUser UserPrincipal user,
            BindingResult result
    ) {
        if (result.hasErrors()) {
            throw new BindException(result);
        }
        noticeBoardService.insertNoticeBoard(request, user.getIdx());
        return ResponseDto.builder().build();
    }

    @SneakyThrows
    @PostMapping("/trade")
    @ApiOperation("거래글 등록")
    public ResponseDto insertTradeBoard(
            @RequestHeader(value = "Authorization") String token,
            @RequestBody @Valid BoardDto.Request.TradeInsert dto,
            @ApiIgnore @CurrentUser UserPrincipal user,
            BindingResult result
    ) {
        if (result.hasErrors()) {
            throw new BindException(result);
        }
        tradeBoardService.insertTradeBoardContent(dto, user.getIdx());
        return ResponseDto.builder().build();
    }

    @ApiOperation("공지사항 상세 조회")
    @GetMapping("/notice/{boardId}")
    @ApiImplicitParam(name = "boardId", value = "게시글 ID", required = true)
    public ResponseDto<BoardDto.Response.NoticeContent> getNoticeBoardContent(@PathVariable("boardId") Long boardId) {
        return ResponseDto.<BoardDto.Response.NoticeContent>builder()
                          .response(noticeBoardService.getNoticeBoardContent(boardId))
                          .build();
    }

    @ApiOperation("거래글 상세 조회")
    @GetMapping("/trade/{boardId}")
    @ApiImplicitParam(name = "boardId", value = "게시글 ID", required = true)
    public ResponseDto<BoardDto.Response.TradeContent> getTradeBoardContent(
            @PathVariable("boardId") Long boardId
    ) {
        return ResponseDto.<BoardDto.Response.TradeContent>builder()
                .response(tradeBoardService.getTradeBoardContent(boardId))
                .build();
    }

    @ApiOperation("거래글 댓글 등록")
    @PostMapping("/trade/comment")
    public ResponseDto<?> insertComment(
            @RequestHeader(value = "Authorization") String token,
            @RequestBody InsertComment dto,
            @ApiIgnore @CurrentUser UserPrincipal user
    ) {
        tradeBoardService.insertComment(dto, user.getIdx());
        return ResponseDto.builder()
                          .responseCode(ResponseCode.SUCCESS)
                          .build();
    }

    @ApiOperation("거래글 대댓글 등록")
    @PostMapping("/trade/reply")
    public ResponseDto<?> insertReply(
            @RequestHeader(value = "Authorization") String token,
            @RequestBody InsertReply dto,
            @ApiIgnore @CurrentUser UserPrincipal user
    ) {
        tradeBoardService.insertReply(dto, user.getIdx());
        return ResponseDto.builder()
                          .responseCode(ResponseCode.SUCCESS)
                          .build();
    }

    @ApiOperation("거래글 댓글만 조회")
    @GetMapping("/trade/comment")
    public ResponseDto<PageDto<Info>> getCommentList(
            Comment dto,
            Pageable commentPageable
    ) {
        return ResponseDto.<PageDto<Info>>builder()
                          .response(new PageDto<>(tradeBoardService.getCommentList(dto, commentPageable)))
                          .build();
    }

    @ApiOperation("거래글 대댓글 조회")
    @GetMapping("/trade/reply")
    public ResponseDto<PageDto<Info>> getReplyList(
            Reply dto,
            Pageable commentPageable
    ) {
        return ResponseDto.<PageDto<Info>>builder()
                          .response(new PageDto<>(tradeBoardService.getReplyList(dto, commentPageable)))
                          .build();
    }

    @ApiOperation("거래글 상태 판매완료로 변경")
    @PutMapping("/trade/{boardId}/status/complete")
    public ResponseDto<?> setTradeStatusToComplete(
            @RequestHeader(value = "Authorization") String token,
            @ApiIgnore @CurrentUser UserPrincipal user,
            @PathVariable Long boardId
    ) {
        tradeBoardService.setTradeStatusToComplete(boardId, user.getIdx());
        return ResponseDto.builder()
                          .responseCode(ResponseCode.SUCCESS)
                          .build();
    }
}
