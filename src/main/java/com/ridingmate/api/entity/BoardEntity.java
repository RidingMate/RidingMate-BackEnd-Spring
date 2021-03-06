package com.ridingmate.api.entity;

import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "RMC_BOARD")
@DiscriminatorColumn(name = "board_type")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class BoardEntity extends BaseTime {

    /**
     * 게시판 엔티티
     * 모든 게시판에 공통적으로 들어가는 데이터나 기능 구현
     */

    @Id
    @Column(name = "idx")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    //제목
    @Column(name = "title")
    private String title;

    // 내용
    @Column(name = "content")
    private String content;

    //조회수
    @Column(name = "hit")
    private int hit;

    // 댓글
    @OneToMany(mappedBy = "board")
    private List<CommentEntity> comments = new ArrayList<>();

    // 게시글 작성자
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    public void setBoardInfo(String title, UserEntity user) {
        this.title = title;
        this.user = user;
    }

    /**
     * 게시글 정보 등록
     * @param title     제목
     * @param content   내용
     * @param user      작성자
     */
    public void setBoardInfo(String title, String content, UserEntity user) {
        this.title = title;
        this.content = content;
        this.user = user;
    }

    public void updateBoardEntity(String title) {
        this.title = title;
    }

    // 조회수 증가
    public void increaseHitCount() {
        hit += 1;
    }

    // 댓글 추가
    public void addComment(CommentEntity comment) {
        comments.add(comment);
        comment.setBoard(this);
    }

    /**
     * 게시글 정보만 수정
     * @param title 게시글 제목
     */
    public void updateBoard(String title) {
        this.title = title;
    }
}
