package com.ridingmate.api.service;

import com.ridingmate.api.entity.BoardEntity;
import com.ridingmate.api.payload.NoticeBoardInsertRequest;
import com.ridingmate.api.payload.TradeBoardInsertRequest;
import com.ridingmate.api.repository.BoardRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Transactional
@ActiveProfiles("local")
class BoardServiceTest {

//    @Autowired
//    BoardService boardService;
//
//    @Autowired
//    BoardRepository boardRepository;
//
//    @Test
//    void 공지사항_새글_등록() {
//        // given
//        NoticeBoardInsertRequest request = new NoticeBoardInsertRequest("공지사항 새글");
//
//        // when
//        boardService.insertNoticeBoardContent(request);
//
//        // then
//        List<BoardEntity> findAll = boardRepository.findAll();
//        Assertions.assertThat(findAll.size()).isEqualTo(1);
//    }
//
//    @Test
//    void 거래글_새글_등록() {
//        // given
//        TradeBoardInsertRequest request = new TradeBoardInsertRequest("거래글 새글", "100,000,000");
//
//        // when
//        boardService.insertTradeBoardContent(request);
//
//        // then
//        List<BoardEntity> findAll = boardRepository.findAll();
//        Assertions.assertThat(findAll.get(0).getTitle()).isEqualTo("거래글 새글");
//    }

}