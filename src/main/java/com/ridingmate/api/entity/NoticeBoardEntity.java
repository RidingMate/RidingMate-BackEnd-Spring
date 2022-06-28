package com.ridingmate.api.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@DiscriminatorValue("NOTICE")
@NoArgsConstructor
public class NoticeBoardEntity extends BoardEntity {

    /**
     * 공지사항 게시판 엔티티
     * 공지사항에 필요한 데이터나 기능이 있다면 추가
     */

    public NoticeBoardEntity(String title, UserEntity user) {
        setBoardInfo(title, user);
    }

    public void updateInfo(String title) {
        updateBoard(title);
    }
}
