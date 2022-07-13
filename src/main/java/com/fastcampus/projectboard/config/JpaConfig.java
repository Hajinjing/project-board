package com.fastcampus.projectboard.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

@EnableJpaAuditing // jpa auditing기능을 활성화
@Configuration  //JpaConfig 는 Configuration bin이 됨, 각종 설정을 잡을때 JpaConfig를 쓰게됨
public class JpaConfig {


    // auditing이 될때마다 밑에 설정한 이름으로 입력됨, 따라서 나중에 이름 부분을 수정해줘야한다.
    // 로그인 후 인증이 되어야 이름을 가져올 수 있음
    @Bean
    public AuditorAware<String> auditorAware() {
        return () -> Optional.of("uno"); // uno는 임의의 데이터. 스프링 시큐리티로 인증 기능을 붙이게 될 때, 수정필요
    }
}

