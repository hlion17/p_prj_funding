<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

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

<style>
    main {
        width: 1280px;
        margin: 0 auto;
        display: flex;
        padding: 20px;
    }
    section {
        height: 100vh;
        padding: 20px;
    }
    main section:nth-child(1) {
        flex-grow: 1;
    }
    main #ranking-area {
        width: 40%;
    }

    /* 좋아요 랭킹 프로젝트 */
    .ranking-box {
        display: flex;
        width: 100%;
        height: 100px;
        border: 1px solid rgba(0,0,0,0.1);
        border-radius: 5px;
        margin-top: 20px;
    }
    .ranking-box:hover {
        box-shadow: 5px 5px 7px rgba(0,0,0,0.1);
    }
    .ranking-box .rb-rank {
        width: 10%;
        text-align: center;
        font-size: 28px;
        font-weight: 700;
        padding: 10px 0;
    }
    .ranking-box .rb-content {
        display: flex;
        flex-direction: column;
        justify-content: space-between;
        width: 60%;
        padding: 10px 0;
    }
    .ranking-box .rb-content > div:first-child {
        font-weight: bold;
    }
    .ranking-box .rb-content .rb-etc {
        display: flex;
        font-size: 12px;
    }
    .ranking-box .rb-content .rb-etc > div:first-child {
        color: darkorange;
        margin-right: 10px;
    }
    .ranking-box .rb-content .rb-etc > div:nth-child(2) {
        color: dimgrey;
    }
    .ranking-box .rb-img {
        width: 30%;
        height: 100%;
        padding: 10px;
    }
    .ranking-box .rb-img img {
        width: 100%;
        height: 100%;
    }

</style>

<script>
    $(document).ready(function () {
        $(".ranking-box").click(function () {
            location.href="/project/" + $(this).attr("data-projectNo")
        })
    })
</script>

<%-- carousel --%>
<%--<div id="carouselExampleIndicators" class="carousel slide" data-bs-ride="carousel">--%>
<%--    <div class="carousel-indicators">--%>
<%--        <button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="0" class="active" aria-current="true" aria-label="Slide 1"></button>--%>
<%--        <button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="1" aria-label="Slide 2"></button>--%>
<%--        <button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="2" aria-label="Slide 3"></button>--%>
<%--    </div>--%>
<%--    <div class="carousel-inner" style="height: 300px">--%>
<%--        <div class="carousel-item active">--%>
<%--            <img src="https://source.unsplash.com/800x600/?nature,water" class="d-block w-100" alt="...">--%>
<%--        </div>--%>
<%--        <div class="carousel-item">--%>
<%--            <img src="https://source.unsplash.com/800x600/?nature,summer" class="d-block w-100" alt="...">--%>
<%--        </div>--%>
<%--        <div class="carousel-item">--%>
<%--            <img src="https://source.unsplash.com/800x600/?nature,fall" class="d-block w-100" alt="...">--%>
<%--        </div>--%>
<%--    </div>--%>
<%--    <button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide="prev">--%>
<%--        <span class="carousel-control-prev-icon" aria-hidden="true"></span>--%>
<%--        <span class="visually-hidden">Previous</span>--%>
<%--    </button>--%>
<%--    <button class="carousel-control-next" type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide="next">--%>
<%--        <span class="carousel-control-next-icon" aria-hidden="true"></span>--%>
<%--        <span class="visually-hidden">Next</span>--%>
<%--    </button>--%>
<%--</div>--%>
<%--위에 광고성 프로젝트 삽입--%>


<main>
    <section id="test-area">
        <div><h2>메인에 들어갈 프로젝트 목록</h2></div>
        <div>
            메인에 나열할 프로젝트 구상
        </div>
    </section>
    <section id="ranking-area">
        <h2>프로젝트 랭킹(좋아요)</h2>
        <c:forEach var="p" items="${likeRank}" varStatus="r">
        <div class="ranking-box" data-projectNo="${p.projectNo}">
            <div class="rb-rank">${r.index + 1}</div>
            <div class="rb-content">
                <div class="rb-title">${p.projectTitle}</div>
                <div class="rb-etc">
                    <div>
                        <fmt:formatNumber value="${p.sum / p.projectPrice}" type="percent" />
                    </div>
                    <div>${p.category.categoryName}</div>
                </div>
            </div>
            <div class="rb-img">
                <img src="${p.projectImage}" alt="">
            </div>
        </div>
        </c:forEach>
    </section>
</main>

<%@include file="component/footer.jsp"%>