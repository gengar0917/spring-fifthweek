# Spring Lv.5

<aside>
🏁 **Goal:  "Spring Security를 적용한 나만의 항해 블로그 백엔드 서버 만들기"**

</aside>

- 학습 과제를 끝내고 나면 할 수 있어요!
    1. Git 원격 repo를 사용할 수 있고, 프로젝트 Version을 관리하며 개발할 수 있어요.
        
        cf. [git branch 전략](https://velog.io/@kw2577/Git-branch-%EC%A0%84%EB%9E%B5)
        
    2. 복잡한 비즈니스 요구사항을 보고 연관관계를 정하고 구현할 수 있어요.
    3. Spring Security를 사용하여 인증/인가를 구현할 수 있어요.

<aside>
🚩 **Requirement:  과제에 요구되는 사항이에요**

</aside>

<aside>
⚠️ **아래의 요구사항에 맞게 API 명세서를 수정해 보고 프로젝트를 수정 및 기능을 추가해 보세요!**

<aside>
☝ **요구사항에 맞게 추가되어야 하는 Entity를 설계하고 ERD를 만들어보세요!**

- [ERD 개념 및 만드는 법](https://inpa.tistory.com/entry/DB-%F0%9F%93%9A-%EB%8D%B0%EC%9D%B4%ED%84%B0-%EB%AA%A8%EB%8D%B8%EB%A7%81-1N-%EA%B4%80%EA%B3%84-%F0%9F%93%88-ERD-%EB%8B%A4%EC%9D%B4%EC%96%B4%EA%B7%B8%EB%9E%A8)
</aside>

<aside>
✌️ **LV4** **에서 만든 프로젝트에 Spring Security, 좋아요 기능을 추가하고 기존 요구사항의 일부를 변경하세요!
추가 및 변경된 요구사항은 하이라이트로 표시되었습니다.**

</aside>

</aside>

<aside>
☝ **추가된 요구사항을 구현해 보세요!**

</aside>

1. 게시글 좋아요 API
    - 사용자는 선택한 게시글에 ‘좋아요’를 할 수 있습니다.
    - 사용자가 이미 ‘좋아요’한 게시글에 다시 ‘좋아요’ 요청을 하면 ‘좋아요’를 했던 기록이 취소됩니다.
    - 요청이 성공하면 Client 로 성공했다는 메시지, 상태코드 반환하기
2. 댓글 좋아요 API
    - 사용자는 선택한 댓글에 ‘좋아요’를 할 수 있습니다.
    - 사용자가 이미 ‘좋아요’한 댓글에 다시 ‘좋아요’ 요청을 하면 ‘좋아요’를 했던 기록이 취소됩니다.
    - 요청이 성공하면 Client 로 성공했다는 메시지, 상태코드 반환하기
3. 예외처리
    - 아래 예외처리를 AOP 를 활용하여 구현하기

<aside>
✌️ **요구사항에 맞게 수정해 보세요!**

</aside>

1. 회원 가입 API
    - username, password를 Client에서 전달받기
    - username은  `최소 4자 이상, 10자 이하이며 알파벳 소문자(a~z), 숫자(0~9)`로 구성되어야 한다.
    - password는  `최소 8자 이상, 15자 이하이며 알파벳 대소문자(a~z, A~Z), 숫자(0~9), 특수문자`로 구성되어야 한다.
    - DB에 중복된 username이 없다면 회원을 저장하고 Client 로 성공했다는 메시지, 상태코드 반환하기
    - 회원 권한 부여하기 (ADMIN, USER) - ADMIN 회원은 모든 게시글, 댓글 수정 / 삭제 가능
    - 참고자료
        1. [https://mangkyu.tistory.com/174](https://mangkyu.tistory.com/174)
        2. [https://ko.wikipedia.org/wiki/정규_표현식](https://ko.wikipedia.org/wiki/%EC%A0%95%EA%B7%9C_%ED%91%9C%ED%98%84%EC%8B%9D)
        3. [https://bamdule.tistory.com/35](https://bamdule.tistory.com/35)
        
2. 로그인 API
    - username, password를 Client에서 전달받기
    - DB에서 username을 사용하여 저장된 회원의 유무를 확인하고 있다면 password 비교하기
    - 로그인 성공 시, 로그인에 성공한 유저의 정보와 JWT를 활용하여 토큰을 발급하고, 
    발급한 토큰을 Header에 추가하고 성공했다는 메시지, 상태코드 와 함께 Client에 반환하기
3. 전체 게시글 목록 조회 API
    - 제목, 작성자명(username), 작성 내용, 작성 날짜를 조회하기
    - 작성 날짜 기준 내림차순으로 정렬하기
    - 각각의 게시글에 등록된 모든 댓글을 게시글과 같이 Client에 반환하기
    - 댓글은 작성 날짜 기준 내림차순으로 정렬하기
    - 게시글/댓글에 ‘좋아요’ 개수도 함께 반환하기
4. 게시글 작성 API
    - ~~토큰을 검사하여, 유효한 토큰일 경우에만 게시글 작성 가능~~  ⇒ Spring Security 를 사용하여 토큰 검사 및 인증하기!
    - 제목, 작성자명(username), 작성 내용을 저장하고
    - 저장된 게시글을 Client 로 반환하기
5. 선택한 게시글 조회 API
    - 선택한 게시글의 제목, 작성자명(username), 작성 날짜, 작성 내용을 조회하기 
    (검색 기능이 아닙니다. 간단한 게시글 조회만 구현해주세요.)
    - 선택한 게시글에 등록된 모든 댓글을 선택한 게시글과 같이 Client에 반환하기
    - 댓글은 작성 날짜 기준 내림차순으로 정렬하기
    - 게시글/댓글에 ‘좋아요’ 개수도 함께 반환하기
6. 선택한 게시글 수정 API
    - ~~토큰을 검사한 후, 유효한 토큰이면서 해당 사용자가 작성한 게시글만 수정 가능~~  ⇒ Spring Security 를 사용하여 토큰 검사 및 인증하기!
    - 제목, 작성 내용을 수정하고 수정된 게시글을 Client 로 반환하기
    - 게시글에 ‘좋아요’ 개수도 함께 반환하기
7. 선택한 게시글 삭제 API
    - ~~토큰을 검사한 후, 유효한 토큰이면서 해당 사용자가 작성한 게시글만 삭제 가능~~  ⇒ Spring Security 를 사용하여 토큰 검사 및 인증하기!
    - 선택한 게시글을 삭제하고 Client 로 성공했다는 메시지, 상태코드 반환하기
8. 댓글 작성 API
    - ~~토큰을 검사하여, 유효한 토큰일 경우에만 댓글 작성 가능~~  ⇒ Spring Security 를 사용하여 토큰 검사 및 인증하기!
    - 선택한 게시글의 DB 저장 유무를 확인하기
    - 선택한 게시글이 있다면 댓글을 등록하고 등록된 댓글 반환하기
9. 댓글 수정 API
    - ~~토큰을 검사한 후, 유효한 토큰이면서 해당 사용자가 작성한 댓글만 수정 가능~~  ⇒ Spring Security 를 사용하여 토큰 검사 및 인증하기!
    - 선택한 댓글의 DB 저장 유무를 확인하기
    - 선택한 댓글이 있다면 댓글 수정하고 수정된 댓글 반환하기
    - 댓글에 ‘좋아요’ 개수도 함께 반환하기
10. 댓글 삭제 API
    - ~~토큰을 검사한 후, 유효한 토큰이면서 해당 사용자가 작성한 댓글만 삭제 가능~~  ⇒ Spring Security 를 사용하여 토큰 검사 및 인증하기!
    - 선택한 댓글의 DB 저장 유무를 확인하기
    - 선택한 댓글이 있다면 댓글 삭제하고 Client 로 성공했다는 메시지, 상태코드 반환하기
11. 예외 처리
    - 토큰이 필요한 API 요청에서 토큰을 전달하지 않았거나 정상 토큰이 아닐 때는 "토큰이 유효하지 않습니다." 라는 에러메시지와 statusCode: 400을 Client에 반환하기
    - 토큰이 있고, 유효한 토큰이지만 해당 사용자가 작성한 게시글/댓글이 아닌 경우에는 “작성자만 삭제/수정할 수 있습니다.”라는 에러메시지와 statusCode: 400을 Client에 반환하기
    - DB에 이미 존재하는 username으로 회원가입을 요청한 경우 "중복된 username 입니다." 라는 에러메시지와 statusCode: 400을 Client에 반환하기
    - 로그인 시, 전달된 username과 password 중 맞지 않는 정보가 있다면 "회원을 찾을 수 없습니다."라는 에러메시지와 statusCode: 400을 Client에 반환하기
    - 회원가입 시 username과 password의 구성이 알맞지 않으면 에러메시지와 statusCode: 400을 Client에 반환하기

<aside>
📌 개발한 API 테스트 해보기!

</aside>

- Postman을 이용해 HTTP 메서드 요청을 시도해 보세요!
- API 명세서와 ERD 설계 TIP
    - **ERD 설계 →** [https://www.erdcloud.com/](https://www.erdcloud.com/)
    - **API 명세서 작성 툴 →** [https://learnote-dev.com/java/Spring-A-문서-작성하기/](https://learnote-dev.com/java/Spring-A-%EB%AC%B8%EC%84%9C-%EC%9E%91%EC%84%B1%ED%95%98%EA%B8%B0/)

<aside>
🔥 꼭 직접 API 명세서를  수정해 본 다음에 확인하세요!!

</aside>

- API 명세서 예시

<aside>
⚠️ 아래 체크리스트를 확인해 보고 프로젝트를 리팩터링 해보세요!

</aside>

- 체크리스트
    
    <aside>
    ✅ **Spring / Api**
    
    - [ ]  controller, service, repository를 잘 분리하였는지
    - [ ]  dto를 잘 사용하고 있는지
    - [ ]  api의 request와 response는 적절한지
    - [ ]  query param, path param, body를 잘 사용하고 잇는지
    - [ ]  restful api 설계규칙에 부합하는지
    </aside>
    
    <aside>
    ✅ **JPA**
    
    - [ ]  엔티티 Column들의 제약조건을 잘 설정했는지(nullable, unique 등)
    - [ ]  엔티티 사이에 적절한 연관관계를 설정했는지(1:N/1:1/N:N, 양방향/단방향 등)
    - [ ]  트랜젝션 단위를 잘 설정했는지, @Transactional이 필요한 곳에 사용되었는지
    </aside>
    
    <aside>
    ✅ **Java**
    
    - [ ]  변수와 메서드의 네이밍은 적절한지
    - [ ]  변수와 상수를 적절하게 사용하였는지, 하드코딩은 없는지
    - [ ]  인스턴스를 생성하거나 수정하는 방법은 적절한지(키워드 : 생성자, setter, builder 등)
    - [ ]  동일한 코드가 반복되는 부분은 없는지
    - [ ]  접근제어자를 잘 사용하고 있는지
    - [ ]  Lombok 어노테이션으로 처리한 메서드가 있다면 어떤 기능을 하고 있는지 잘 이해하고 있는지, 적절하게 사용하였는지(@Getter, @Setter, @NoArgsConstructor, @AllArgsConstructor, @RequiredArgsConstructor 등)
        - 어노테이션을 사용하지 않고 자바코드로 구현할 수 없다면, 먼저 코드로 구현해보세요!
    </aside>
    

<aside>
❓ **Why: 과제 제출시에는 아래 질문을 고민해보고 답변을 함께 제출해주세요.**

</aside>

1. Spring Security를 적용했을 때 어떤 점이 도움이 되셨나요?
2. Spring Security를 사용하지 않는다면 어떻게 인증/인가를 효율적으로 처리할 수 있을까요?
3. AOP에 대해 설명해 주세요!
4. RefreshToken 적용에 대한 장/단점을 작성해 주세요! 적용해 보지 않으셨다면 JWT를 사용하여 인증/인가를 구현 했을 때의 장/단점에 대해 숙련주차의 답변을 Upgrade 하여 작성해 주세요!
5. 즉시로딩 / 지연로딩에 대해 설명해 주세요!

<aside>
⚠️ **Warning : 꼭 지켜야 할 것!**

</aside>

- 프로젝트 Github 링크와 API명세서, ERD 를 제출해 주세요.
- 프로젝트를 배포까지 하신 분은 배포한 주소를 프로젝트 Github 링크와 API명세서, ERD 와 함께 제출해 주세요.
- 이것은 꼭 지켜주세요(Do's)
    - 과제 요구 사항은 모두 지켜주세요. 특정 기능을 임의로 배제하면 안 됩니다!
    - 배포 시 AWS의 RDS를 사용하셨다면 Github에 절대 RDS에 대한 정보를 올리시면 안 됩니다!

<aside>
💡 **더 나아가기: 과제가 일찍 마무리 되었다면 아래의 내용도 진행해보세요.**

</aside>

- 회원탈퇴(기능추가), 게시글 삭제, 댓글 삭제 시 연관된 데이터 모두 삭제될 수 있도록 구현해 보세요!
- 대댓글 기능을 만들어 보세요!
    - 대댓글 작성하기
    - 댓글 조회 시 대댓글도 함께 조회하기
- 게시글과 댓글 조회할 때 페이징, 정렬 기능을 추가해 보세요!
- 게시글 카테고리 만들어 보세요!
    - 게시글을 분류하는 카테고리를 만들어서 게시글을 작성할 때 카테고리 정보도 함께 저장하기
    - 카테고리 별로 게시글을 조회하는 기능 추가하기
- AccessToken, RefreshToken에 대해 구글링해 보고 RefreshToken을 적용해 보세요!
- 프로젝트에 swagger 를 구글링해 보고 적용해 보세요!
    - swagger란? Open Api Specification(OAS)를 위한 프레임워크 입니다. API들이 가지고 있는 스펙(spec)을 명세, 관리할 수 있으며, 백엔드와 프론트엔드가 협업할 때 사용할 수 있습니다!
    

<aside>
📌 제출하기 : [https://forms.gle/tx7zNodaBi57q3r79](https://forms.gle/tx7zNodaBi57q3r79)

</aside>
