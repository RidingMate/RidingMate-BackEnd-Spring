package com.ridingmate.api.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@Getter
@DiscriminatorValue("NOTICE")
@NoArgsConstructor
public class NoticeBoardEntity extends BoardEntity {

    /**
     * 공지사항 게시판 엔티티
     * 공지사항에 필요한 데이터나 기능이 있다면 추가
     */

    public NoticeBoardEntity(String title) {
        createBoardEntity(title);
    }

}
