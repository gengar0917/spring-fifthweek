# Spring Lv.4

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
⚠️ **아래의 요구사항에 맞게 프로젝트 수정과, 기능 추가를 해 보세요!**

<aside>
☝ **Lv.3 프로젝트에 Spring Security 기능을 추가하고 해당 기능을 활용하여 전체 코드를 수정해 보세요.
추가 및 변경된 요구사항은 하이라이트로 표시되었습니다.**

</aside>

</aside>

<aside>
☝ **새로운 요구사항을 구현해 보세요!**

</aside>

1. Lv.3 프로젝트에 Spring Security 적용하기

<aside>
✌️ **요구사항에 맞게 수정해 보세요!**

</aside>

1. 회원 가입 API
    - username, password를 Client에서 전달받기
    - username은  `최소 4자 이상, 10자 이하이며 알파벳 소문자(a~z), 숫자(0~9)`로 구성되어야 한다.
    - password는  `최소 8자 이상, 15자 이하이며 알파벳 대소문자(a~z, A~Z), 숫자(0~9)`로 구성되어야 한다.
    - DB에 중복된 username이 없다면 회원을 저장하고 Client 로 성공했다는 메시지, 상태코드 반환하기
    - 회원 권한 부여하기 (ADMIN, USER) - ADMIN 회원은 모든 게시글 수정 / 삭제 가능
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
4. 게시글 작성 API
    - ~~토큰을 검사하여, 유효한 토큰일 경우에만 게시글 작성 가능~~  ⇒ Spring Security를 사용하여 토큰 검사 및 인증하기!
    - 제목, 작성자명(username), 작성 내용을 저장하고
    - 저장된 게시글을 Client 로 반환하기
5. 선택한 게시글 조회 API
    - 선택한 게시글의 제목, 작성자명(username), 작성 날짜, 작성 내용을 조회하기 
    (검색 기능이 아닙니다. 간단한 게시글 조회만 구현해주세요.)
6. 선택한 게시글 수정 API
    - ~~토큰을 검사한 후, 유효한 토큰이면서 해당 사용자가 작성한 게시글만 수정 가능~~ ⇒ Spring Security를 사용하여 토큰 검사 및 인증하기!
    - 제목, 작성 내용을 수정하고 수정된 게시글을 Client 로 반환하기
7. 선택한 게시글 삭제 API  
    - ~~토큰을 검사한 후, 유효한 토큰이면서 해당 사용자가 작성한 게시글만 삭제 가능~~ ⇒ Spring Security를 사용하여 토큰 검사 및 인증하기!
    - 선택한 게시글을 삭제하고 Client 로 성공했다는 메시지, 상태코드 반환하기

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
❓ **Why: 과제 제출시에는 아래 질문을 고민해보고 답변을 함께 제출해주세요.**

</aside>

1. Spring Security를 적용했을 때 어떤 점이 도움이 되셨나요?
2. IoC / DI 에 대해 간략하게 설명해 주세요!  - Lv.2의 답변을 Upgrade 해 주세요!
3. JWT를 사용하여 인증/인가를 구현 했을 때의 장점은 무엇일까요? - Lv.2의 답변을 Upgrade 해 주세요!
4. 반대로 JWT를 사용한 인증/인가의 한계점은 무엇일까요? - Lv.2의 답변을 Upgrade 해 주세요!

<aside>
⚠️ **Warning : 꼭 지켜야 할 것!**

</aside>

- 프로젝트 Github 링크를 제출해주세요.
- 프로젝트를 배포까지 하신 분은 배포한 주소를 프로젝트 Github 링크와 함께 제출해주세요.
- 이것은 꼭 지켜주세요(Do's)
    - 과제 요구 사항은 모두 지켜주세요. 특정 기능을 임의로 배제하면 안 됩니다!
    - 배포 시 AWS의 RDS를 사용하셨다면 Github에 절대 RDS에 대한 정보를 올리시면 안 됩니다!
    

<aside>
📌 제출하기 : [https://forms.gle/pnW2Fp4SS6xLQu957](https://forms.gle/pnW2Fp4SS6xLQu957)

</aside>
