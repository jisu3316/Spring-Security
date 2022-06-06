package com.example.security.config;

import com.example.security.config.oauth.PrincipalOauth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

// 구글 로그인이 완료된 뒤의 후처리가 필요함.
// 1. 코드받기(인증을받음,구글에 로그인이 됨), 2.엑세스토큰을받음(사용자정보에 접근 권한가능), 3.사용자 프로필 정보를 가져오고, 4-1. 그 정보를 토대로 회원가입을 자동으로 진행시키도록 함
// 4-2(이메일,전화번호,이름,아이디) 쇼핑몰 -> (집주소),백화점몰 ->(vip등급,일반등급)

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true,prePostEnabled = true)  //secured 어노테이션 활성화, preAuthorize,postAuthorize 어노테이션 활성화
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final PrincipalOauth2UserService principalOauth2UserService;

    public SecurityConfig(PrincipalOauth2UserService principalOauth2UserService) {
        this.principalOauth2UserService = principalOauth2UserService;
    }


    //해당 메서드의 리턴되는 오브젝트를 IoC로 등록해준다.
    @Bean
    public BCryptPasswordEncoder encodePwd() {
        return new BCryptPasswordEncoder();
    }



    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers("/user/**").authenticated() // /user/** 밑으로 들어오는 url는 인증이 필요하다.
                .antMatchers("/manager/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')") //hasRole 권한이 있어야한다.
                .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
                .anyRequest().permitAll()          //나머지 주소는 권한이 허용이된다.
                .and()
                .formLogin()
                .loginPage("/loginForm")
                //.usernameParameter("username2")  form에서 name값을 바꿔주려면 parameter 바꿔줘야함
                .loginProcessingUrl("/login")       //login 조수가 호출이되면 시큐리가 낚아채서 대신 로그인을 진행해줍니다.
                .defaultSuccessUrl("/")
                .and()
                .oauth2Login()
                .loginPage("/loginForm")  // 구글 로그인이 완료된 뒤의 후처리가 필요함. Tip.코드X, (엑세스토큰+ 사용자 프로필정보O)
                .userInfoEndpoint()
                .userService(principalOauth2UserService);

    }
}
