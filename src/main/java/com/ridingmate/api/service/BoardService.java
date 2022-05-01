package com.ridingmate.api.service;

import com.ridingmate.api.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    public void registerBoardContent() {
        // TODO : 새 게시글 등록
    }

    public void updateBoardContent() {
        // TODO : 게시글 수정
    }

    public void getBoardList() {
        // TODO : 게시판 게시글 리스트 조회
    }

    public void getBoardContent() {
        // TODO : 게시글 상세 조회
    }

    public void deleteBoardContent() {
        // TODO : 게시글 삭제
    }
}
