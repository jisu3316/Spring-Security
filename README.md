# Spring-Security

JPA

Security

oauth2-client를 사용하여 소셜로그인과 시큐리티를 DefaultOAuth2UserService 상속받아 loadUser 메소드로 유저정보를 처리.

프로젝트 구조
\---src
+---main
|   +---java
|   |   \---com
|   |       \---example
|   |           \---security
|   |               |   SecurityApplication.java
|   |               |
|   |               +---config
|   |               |   |--SecurityConfig.java
|   |               |   |--WebMvcConfig.java
|   |               |   +---auth
|   |               |   |     PricipalDetails.java
|   |               |   |     PricipalDetailsService.java
|   |               |   +---oauth
|   |               |        | PrincipalOauth2UserService.java
|   |               |        +---procider
|   |               |             FaceBookUserInfo.java
|   |               |             GoogleUserInfo.java
|   |               |             NaverUserInfo.java         
|   |               +---controller
|   |               |       IndexController.java
|   |               |
|   |               |---model
|   |               |     User.java
|   |               |
|   |               \---repository
|   |                       UserRepository.java
|   |
|   \---resources
|       |   application.yml
|       |
|       +---static
|       \---templates
|               Index.html
|               joinForm.html
|               loginForm.html
