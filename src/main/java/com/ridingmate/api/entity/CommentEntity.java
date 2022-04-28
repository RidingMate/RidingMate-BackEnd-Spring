package com.ridingmate.api.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Builder
@Table(name = "RMC_COMMENT")
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@DynamicUpdate
public class CommentEntity extends BaseTime {

    /*
        중고거래 댓글을 저장할 테이블
        Board와 연관관계 필요
        대댓글을 위한 연관관계 고민해봐야함 or 대댓글에 대한 상태값을 저장
            -> 대댓글까지만 허용할 예정
     */

    @Id
    @Column(name = "idx")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    //댓글 내용
    @Column(name = "hit")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private BoardEntity board;
}
