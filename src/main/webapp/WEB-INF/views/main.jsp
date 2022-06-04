<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@include file="component/header.jsp"%>

<%--<div class="container my-5">
<h1>메인화면</h1>
<hr>

로그인 아이디: ${sessionScope.loginId}

    <button id="test-button" onclick="test()">test</button>
</div>--%>

<script>
    // function test() {
    //     $.ajax({
    //         type: "get"
    //         , url: "/test"
    //         , dataType: "json"
    //         , data: {}
    //         , success: function(res) {
    //             console.log("성공")
    //         }
    //         , error: function() {
    //             console.log("ajax 실패")
    //         }
    //     })
    // }

</script>

<%-- 카로우셀 --%>

<style>
    main {
        width: 1280px;
        margin: 0 auto;
        display: flex;
        padding: 20px;
    }
    section {
        border: 1px solid black;
        height: 100vh;
        padding: 20px;
    }
    main section:nth-child(1) {
        flex-grow: 1;
    }
    main section:nth-child(2) {
        width: 30%;
    }

    .ranking-box {
        display: flex;
    }
</style>

<div id="carouselExampleIndicators" class="carousel slide" data-bs-ride="carousel">
    <div class="carousel-indicators">
        <button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="0" class="active" aria-current="true" aria-label="Slide 1"></button>
        <button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="1" aria-label="Slide 2"></button>
        <button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="2" aria-label="Slide 3"></button>
    </div>
    <div class="carousel-inner" style="height: 300px">
        <div class="carousel-item active">
            <img src="https://source.unsplash.com/800x600/?nature,water" class="d-block w-100" alt="...">
        </div>
        <div class="carousel-item">
            <img src="https://source.unsplash.com/800x600/?nature,summer" class="d-block w-100" alt="...">
        </div>
        <div class="carousel-item">
            <img src="https://source.unsplash.com/800x600/?nature,fall" class="d-block w-100" alt="...">
        </div>
    </div>
    <button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide="prev">
        <span class="carousel-control-prev-icon" aria-hidden="true"></span>
        <span class="visually-hidden">Previous</span>
    </button>
    <button class="carousel-control-next" type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide="next">
        <span class="carousel-control-next-icon" aria-hidden="true"></span>
        <span class="visually-hidden">Next</span>
    </button>
</div>

<main>
    <section id="test-area">
        <div><h2>메인에 들어갈 프로젝트 목록</h2></div>
        <div>

        </div>
    </section>
    <section id="ranking-area">
        <h2>랭킹 영역</h2>
        <div class="ranking-box">
            <div><span>1</span></div>
            <div>프로젝트 개요</div>
            <div><img src="/resources/img/altImg.png" alt="이미지 없음"></div>
        </div>
    </section>
</main>

<%@include file="component/footer.jsp"%>