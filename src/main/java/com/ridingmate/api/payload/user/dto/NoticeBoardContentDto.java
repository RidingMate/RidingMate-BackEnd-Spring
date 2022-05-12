package com.ridingmate.api.payload.user.dto;

import com.ridingmate.api.entity.NoticeBoardEntity;
import lombok.Getter;

@Getter
public class NoticeBoardContentDto {

    private String title;

    public NoticeBoardContentDto(NoticeBoardEntity noticeBoard) {
        title = noticeBoard.getTitle();
    }
}
