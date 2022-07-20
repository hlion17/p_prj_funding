## 프로젝트 개요

### 목적
- 스프링 프레임워크를 활용한 회원제 펀딩 사이트 구현
- 기본적인 회원 기능, 결제 기능 개발
- Junit 5, Mockito를 활용한 테스트 코드 작성
<br>

### 개발환경

- FrontEnd: HTML, CSS, Javascript, jQuery, Bootstrap
- BackEnd: Tomcat Server 9, Java 1.8, SpringBoot 2.6.7, Mybatis
- Database: Oracle
- Test: Junit5, Mockito  
<br>

### 요구사항
- 세션을 활용한 회원 인증
- 작성중, 심사중, 진행중, 종료 상태로 프로젝트 관리
- 프로젝트 좋아요를 눌러 따로 조회 가능
- 마이페이지
  - 프로젝트 상태별 관리
  - 좋아요 한 프로젝트 조회
- 펀딩 프로젝트 작성 및 조회
    - 프로젝트 작성은 회원 전용 기능
    - 검색, 필터 기능을 활용하여 다양한 방식으로 프로젝트 조회
- 메인페이지
  - 좋아요 많은 순 프로젝트 나열
- 결제
  - 원하는 리워드를 선택하여 결제 가능
  - 클라이언트 결제 요청 금액과 서버 측 금액 검증
  - 검증을 통과하지 못하거나 서버측 오류 발생시 결제 취소
<br>

### ERD
![ERD](https://user-images.githubusercontent.com/89788111/173987838-8c2e0c2a-c983-42b7-85c2-d5f98eed02fc.png)
<br>

## 개발 결과
### 메인페이지
- 좋아요를 많이 받은 프로젝트 순서대로 출력
![main](https://user-images.githubusercontent.com/89788111/174217397-ff6e050c-25ce-473b-a5cc-2aa31610d728.jpg)
<br>

### 프로젝트 리스트
- 프로젝트 제목으로 검색
- 종료일, 등록일 순 정렬
- 진행중, 마감된 프로젝트 필터링
- 카테고리별 프로젝트 필터링
![project-list](https://user-images.githubusercontent.com/89788111/174217458-ea650b0f-4e41-4c46-adbf-355bea10f95f.jpg)
<br>

### 프로젝트 상세 페이지
- 프로젝트 내용 조회
- 리워드 결제
- 커뮤니티에서 후기 작성 및 채팅
![project-detail1](https://user-images.githubusercontent.com/89788111/174217842-3d081da0-7f1c-4611-b47a-a22f7ad3024c.jpg)
<br>

![project-detail2](https://user-images.githubusercontent.com/89788111/174218493-b85fb099-edbb-4c8e-ae56-623a9068ce22.jpg)
<br>

![project-chat](https://user-images.githubusercontent.com/89788111/174218527-a3d68ac6-49ec-4e67-be19-d2b5e7895a2b.jpg)
<br>

### 결제 페이지
- 선택한 리워드를 결제
![payment-page](https://user-images.githubusercontent.com/89788111/174217919-dea401e9-511e-4951-b316-d7bcfac034a7.jpg)
<br>

### 프로젝트 작성
![write-project](https://user-images.githubusercontent.com/89788111/174217961-1fe8dfc6-1ffd-44bd-87f4-c1ae1551eb6b.jpg)
<br>
![project-write2](https://user-images.githubusercontent.com/89788111/174218081-7fb84553-4487-43a4-a24a-2bfb64c324c1.jpg)
<br>
![project-write3](https://user-images.githubusercontent.com/89788111/174218100-8c5952b0-f103-49af-9463-0af93db958cb.jpg)
<br>

### 마이페이지
- 좋아요 한 프로젝트 목록
![like-projects](https://user-images.githubusercontent.com/89788111/174218213-c90aff3b-ff6a-4c92-becf-5fbc373968f3.jpg)

- 결제한 프로젝트 목록
![paid-project](https://user-images.githubusercontent.com/89788111/174218316-6ef5b63c-c98a-4c96-9d32-89ff25bdbcc1.jpg)

- 내가 작성한 프로젝트 목록
![my projects](https://user-images.githubusercontent.com/89788111/174218227-83f13d5d-40ad-49d1-b312-67da47e76043.jpg)

<br>

