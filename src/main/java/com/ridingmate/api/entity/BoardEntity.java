package com.ridingmate.api.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

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

    // TODO : 내용 컬럼 추가
    // TODO : 썸네일 컬럼 추가

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

    //TODO : 썸네일 저장을 위한 location 저장 컬럼
    //        File에 대한 entity 필요할거같음 생성해서 연관관계 연결
    //TODO : 내용과 사진을 같이 저장할 BLOB와 같은 컬럼


}
