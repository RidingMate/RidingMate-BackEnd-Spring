package com.ridingmate.api.controller.user;

import com.ridingmate.api.consts.ResponseCode;
import com.ridingmate.api.entity.LocationEntity;
import com.ridingmate.api.entity.NoticeBoardEntity;
import com.ridingmate.api.entity.TradeBoardEntity;
import com.ridingmate.api.entity.UserEntity;
import com.ridingmate.api.exception.ParameterException;
import com.ridingmate.api.payload.common.ApiResponse;
import com.ridingmate.api.payload.user.dto.NoticeBoardContentDto;
import com.ridingmate.api.payload.user.dto.NoticeBoardDto;
import com.ridingmate.api.payload.user.dto.TradeBoardContentDto;
import com.ridingmate.api.payload.user.dto.TradeBoardDto;
import com.ridingmate.api.payload.user.request.NoticeBoardRequest;
import com.ridingmate.api.payload.user.request.TradeBoardRequest;
import com.ridingmate.api.payload.user.request.TradeSearchRequest;
import com.ridingmate.api.service.LocationService;
import com.ridingmate.api.service.NoticeBoardService;
import com.ridingmate.api.service.TradeBoardService;
import com.ridingmate.api.service.common.AuthService;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
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
    private final AuthService authService;
    private final LocationService locationService;

    @GetMapping("/notice/list")
    @ApiOperation("공지사항 리스트 조회")
    public Page<NoticeBoardDto> getNoticeBoardList(
            @RequestParam(value = "pageNum") @Min(value = 1) int pageNum
    ) {
        Sort sort = Sort.by("createAt").descending();
        PageRequest page = PageRequest.of(pageNum - 1, 3, sort);
        return noticeBoardService.getBoardList(page).map(noticeBoard -> new NoticeBoardDto(noticeBoard));
    }

    @GetMapping("/trade/list")
    @ApiOperation("거래글 리스트 조회")
    public Page<TradeBoardDto> getTradeBoardList(
            @RequestParam(value = "pageNum") @Min(value = 1) int pageNum,
            @ModelAttribute @Valid TradeSearchRequest search,
            BindingResult result
    ) {

        if (result.hasErrors()) {
            throw new ParameterException(result.getFieldErrors().get(0).getDefaultMessage());
        }

        Sort sort = Sort.by("createAt").descending();
        PageRequest page = PageRequest.of(pageNum - 1, 10, sort);
        return tradeBoardService.getBoardList(page).map(tradeBoard -> new TradeBoardDto(tradeBoard));
    }


    @PostMapping("/notice")
    @ApiOperation("공지사항 등록")
    public ResponseEntity insertNoticeBoard(
            @RequestBody @Valid NoticeBoardRequest request,
            BindingResult result
    ) {
        if (result.hasErrors()) {
            throw new ParameterException(result.getFieldErrors().get(0).getDefaultMessage());
        }
        NoticeBoardEntity noticeBoard = new NoticeBoardEntity(request.getTitle());
        noticeBoardService.insertBoardContent(noticeBoard);
        return ResponseEntity.ok(new ApiResponse(ResponseCode.SUCCESS));
    }

    @PostMapping("/trade")
    @ApiOperation("거래글 등록")
    public ResponseEntity insertTradeBoard(
            @RequestHeader(value = "Authorization") String token,
            @RequestBody @Valid TradeBoardRequest request,
            BindingResult result
    ) {

        if (result.hasErrors()) {
            throw new ParameterException(result.getFieldErrors().get(0).getDefaultMessage());
        }

        // TODO : 내 바이크 조건처리 필요
        UserEntity userEntity = authService.getUserEntityByAuthentication();

        // 거래 지역
        LocationEntity location = null;
        if (request.getLocationCode() != null) {
            location = locationService.getLocation(request.getLocationCode());
        }

        TradeBoardEntity tradeBoard = new TradeBoardEntity(
                request.getTitle(),
                request.getContent(),
                request.getCompany(),
                request.getModelName(),
                request.getFuelEfficiency(),
                request.getCc(),
                request.getYear(),
                request.getMileage(),
                request.getPrice(),
                request.getPhoneNumber(),
                request.getIsOpenToBuyer(),
                request.getPurchaseYear(),
                request.getPurchaseMonth(),
                userEntity,
                location);
        tradeBoardService.insertBoardContent(tradeBoard);
        return ResponseEntity.ok(new ApiResponse(ResponseCode.SUCCESS));
    }

    @GetMapping("/notice/{boardId}")
    @ApiOperation("공지사항 상세 조회")
    public ResponseEntity<NoticeBoardContentDto> getNoticeBoardContent(@PathVariable("boardId") Long boardId) {
        return ResponseEntity.ok(new NoticeBoardContentDto(noticeBoardService.getBoardContent(boardId)));
    }

    @GetMapping("/trade/{boardId}")
    @ApiOperation("거래글 상세 조회")
    public ResponseEntity<TradeBoardContentDto> getTradeBoardContent(@PathVariable("boardId") Long boardId) {
        return ResponseEntity.ok(new TradeBoardContentDto(tradeBoardService.getBoardContent(boardId)));
    }
}
