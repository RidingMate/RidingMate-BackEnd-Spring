package com.ridingmate.api.payload.user.dto;

import com.ridingmate.api.entity.NoticeBoardEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NoticeBoardContentDto {

    private String title;

    public static NoticeBoardContentDto convertEntityToDto(NoticeBoardEntity noticeBoard) {
        return new NoticeBoardContentDto(
                noticeBoard.getTitle());
    }
}
