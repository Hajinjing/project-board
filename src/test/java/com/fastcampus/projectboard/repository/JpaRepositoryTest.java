package com.fastcampus.projectboard.repository;

import com.fastcampus.projectboard.config.JpaConfig;
import com.fastcampus.projectboard.domain.Article;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

// 슬라이스테스트..?
@DisplayName("JPA 연결 테스트")
@Import(JpaConfig.class)
@DataJpaTest //이거에 의해서 테스트 내에서도 생성자 주입이 가능해
class JpaRepositoryTest {

    // 테스트 할 대상
    private final ArticleRepository articleRepository;
    private final ArticleCommentRepository articleCommentRepository;

    public JpaRepositoryTest(
            @Autowired ArticleRepository articleRepository,
            @Autowired ArticleCommentRepository articleCommentRepository
    ) {
        this.articleRepository = articleRepository;
        this.articleCommentRepository = articleCommentRepository;
    }

    @DisplayName("select 테스트")
    @Test
    void givenTestData_whenSelecting_thenWorksFine() {

        //given

        //when
        List<Article> articles = articleRepository.findAll();

        //then
        assertThat(articles)
                .isNotNull()
                .hasSize(123);

    }

//    @DisplayName("insert 테스트")
//    @Test
//    void givenTestData_whenInserting_thenWorksFine() {
//
//        //given
//        long previousCount = articleRepository.count();
//        Article article = Article.of("new article","new content","#spring");
//
//        //when
//        List<Article> articles = articleRepository.findAll();
//
//        //then
//        assertThat(articles)
//                .isNotNull()
//                .hasSize(0);
//
//    }
}