package com.example.security.repository;

import com.example.security.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

//CRUD 함수를 JpaRepository가 들고있음
//@Repositoty라는 어노테이션이 없어도 Ioc가 된다. 이유는 JpaRepository를 상속했기 때문에.
public interface UserRepository extends JpaRepository<User,Long> {

    //findBy규칙 -> Username 문법
    //select * from user where username =1?
     public User findByUsername(String username); //Jpa name 함수
}
