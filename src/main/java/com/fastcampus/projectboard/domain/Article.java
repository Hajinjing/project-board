package com.fastcampus.projectboard.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@ToString
@Table(indexes = {
        @Index(columnList = "title"),
        @Index(columnList = "hashtag"),
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy")
})
@Entity
public class Article {
    // entity의 primary key를 지정해줘야함.

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto increment를 지정, mysql의 경우 identity방식으로 autoincrement
    private Long id; //id는 자동으로 부여되는 고유 번호기 때문에 setter에 의해서 수정되게 하면 안돼서 전체 클래스에 세터를 사용하지 않았음(이 경우엔)

    @Setter @Column(nullable = false) private String title; // 제목
    @Setter @Column(nullable = false, length = 10000) private String content; // 내용

    @Setter private String hashtag; // 해시태그, optional하기때문에 Null값 가능


    // 양방향 바인딩 OneToMany
    @ToString.Exclude // 순환참조를 끊어줌
    @OrderBy("id")
    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
    private final Set<ArticleComment> articleComments = new LinkedHashSet<>();


    // 메타 데이터
    // autoauditing을 통해 자동 변경
    @CreatedDate @Column(nullable = false) private LocalDateTime createdAt; // 생성일시
    @CreatedBy @Column(nullable = false, length = 100) private String createdBy; // 생성자 , 이 생성자 부분을 JpaConfig에 설정해줘야음
    @LastModifiedDate @Column(nullable = false) private LocalDateTime modifiedAt; // 수정일시
    @LastModifiedBy @Column(nullable = false, length = 100) private String modifiedBy; // 수정자

    // Hibernate 구현체를 사용하는 모든 jpa entity 경우 기본생성자를 가지고 있어야 한다


    protected Article() {} // 평소에는 오픈하지 않을거라 protected로 설정

    // private로 막아두고
    private Article(String title, String content, String hashtag) {
        this.title = title;
        this.content = content;
        this.hashtag = hashtag;
    }
    // 사용의 편의성을 위해
    public static Article of(String title, String content, String hashtag) {
        return new Article(title, content, hashtag);

    }

    // 데이터를 리스트로 받고, 중복요소 제거, 정렬 등이 필요할 수 있어서
    // 동일성 동등성 검사가 필요 equals, hashcode


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Article article)) return false;
        // db에 데이터를 아직 연결하지 않은 경우(영속화하지 않은 경우) id를 부여받지 못해서 notnull
        return id != null && id.equals(article.id); // 지금 막 만든, 아직 영속화되지 않은 entity는 모두 동등성 검사에서 탈락
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}