package com.ridingmate.api.controller;

import com.ridingmate.api.entity.NoticeBoardEntity;
import com.ridingmate.api.entity.TradeBoardEntity;
import com.ridingmate.api.payload.*;
import com.ridingmate.api.service.NoticeBoardService;
import com.ridingmate.api.service.TradeBoardService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/board")
@RequiredArgsConstructor
public class BoardController {

    private final NoticeBoardService noticeBoardService;
    private final TradeBoardService tradeBoardService;

    @GetMapping("/notice/list")
    @ApiOperation("공지사항 리스트 조회")
    public Page<NoticeBoardDto> getNoticeBoardList(
            @RequestParam(value = "pageNum") int pageNum
    ) {
        Sort sort = Sort.by("createAt").descending();
        PageRequest page = PageRequest.of(pageNum - 1, 10, sort);
        return noticeBoardService.getBoardList(page).map(NoticeBoardDto::convertEntityToDto);
    }

    @GetMapping("/trade/list")
    @ApiOperation("거래글 리스트 조회")
    public Page<TradeBoardDto> getTradeBoardList(
            @RequestParam(value = "pageNum") int pageNum
    ) {
        Sort sort = Sort.by("createAt").descending();
        PageRequest page = PageRequest.of(pageNum - 1, 10, sort);
        return tradeBoardService.getBoardList(page).map(TradeBoardDto::convertEntityToDto);
    }


    @PostMapping("/notice")
    @ApiOperation("공지사항 등록")
    public String insertNoticeBoard(
            @RequestBody NoticeBoardRequest request
    ) {
        NoticeBoardEntity noticeBoard = new NoticeBoardEntity(request.getTitle());
        noticeBoardService.insertBoardContent(noticeBoard);
        return "success";
    }

    @PostMapping("/trade")
    @ApiOperation("거래글 등록")
    public String insertTradeBoard(
            @RequestBody TradeBoardRequest request
    ) {
        // TODO : 내 바이크 조건처리 필요
        // TODO : 작성자 필요
        TradeBoardEntity tradeBoard = new TradeBoardEntity(
                request.getTitle(),
                request.getCompany(),
                request.getModelName(),
                request.getFuelEfficiency(),
                request.getCc(),
                request.getYear(),
                request.getMileage(),
                request.getPrice());
        tradeBoardService.insertBoardContent(tradeBoard);
        return "success";
    }

    @GetMapping("/notice/{boardId}")
    @ApiOperation("공지사항 상세 조회")
    public NoticeBoardContentDto getNoticeBoardContent(@PathVariable("boardId") Long boardId) {
        return NoticeBoardContentDto.convertEntityToDto(noticeBoardService.getBoardContent(boardId));
    }

    @GetMapping("/trade/{boardId}")
    @ApiOperation("거래글 상세 조회")
    public TradeBoardContentDto getTradeBoardContent(@PathVariable("boardId") Long boardId) {
        return TradeBoardContentDto.convertEntityToDto(tradeBoardService.getBoardContent(boardId));
    }
}
