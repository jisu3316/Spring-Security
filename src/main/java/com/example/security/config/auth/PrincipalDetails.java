package com.example.security.config.auth;

// 시큐리티가 /login 주소 요청이 오면 낚아채서 로그인을 진행시킨다.
// 로그인을 진행이 완료가 되면 시큐리티 session을 만들어 줍니다. (Security ContextHolder)
// 시큐리티에 들어갈수있는 정보가 정해져있다.  오브젝트 => Authentication 타입 객체
// Authentication 안에 User 정보가 있어야 됨.
// User오브젝트 타입 => UserDetails 타입 객체

//Security Session 영역이 있음. => Authentication => UserDetails(PrincipalDetails) 타입이여야함.
//Security Session을 꺼내면 Authentication 객체가 나옴. UserDetails을 꺼내면 User정보에 접근가능함.

import com.example.security.model.User;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@Data
public class PrincipalDetails implements UserDetails, OAuth2User {

    private User user;
    private Map<String,Object> attributes;


    //일반 로그인
    public PrincipalDetails(User user) {
        this.user = user;
    }
    //OAuth 로그인
    public PrincipalDetails(User user, Map<String, Object> attributes) {
        this.user = user;
        this.attributes = attributes;
    }


    //해당 유저의 권한을 리턴하는 곳!!
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collect = new ArrayList<>();
        collect.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return user.getRole();
            }
        });
        return collect;
    }

    //패스워드 리턴
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    //유저이름 리턴턴
    @Override
    public String getUsername() {
        return user.getUsername();
    }

    //계정 만료되었는지 true ==만료되지 않았다.
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    //계정 잠겼니  true ==안잠겼다
   @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    //계정의 비밀번호 1년이 지났니 true = 아니요
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    //계정이 활성화 되었니 true = 아니요
    @Override
    public boolean isEnabled() {

        //우리 사이트에서 1년동안 회원이 로그인을 안하면!! 휴면계정으로 하기로 함.
        //현재시간 - 로그인 시간 => 1년을 초가화면 return false; 하면됨.
        return true;
    }

    //OAuth2User 구현 메소드
    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public String getName() {
        return (String) attributes.get("sub");
    }
}
