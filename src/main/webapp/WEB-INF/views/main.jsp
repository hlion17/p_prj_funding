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
    #intro {
        width: 60%;
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

    /*  메인 인트로  */
    #intro-content {
        padding: 10px 0;
    }
    .intro-section {
        display: flex;
    }
    .intro-section-img > img{
        width: 350px;
        height: 500px;
    }
    .intro-section-text {
        padding: 20px;
    }
    .intro-section-text > div {
        margin-bottom: 40px;
    }

    #btn-project {
        border: 1px solid cornflowerblue;
        color: rgb(100 149 237);
        border-radius: 10px;
        padding: 5px 10px;
        background: rgba(100, 150, 230, 0.1);
    }
</style>

<script>
    $(document).ready(function () {
        $(".ranking-box").click(function () {
            location.href="/project/" + $(this).attr("data-projectNo")
        })
    })
</script>

<main>
    <section id="intro">
        <div id="intro-header">
            <h2>여러분들의 꿈을 함께 실현하세요!</h2>
        </div>
        <div id="intro-content">
            <div class="intro-section">
                <div class="intro-section-img">
                    <img src="/resources/img/fund-main-img.jpg" alt="">
                </div>
                <div class="intro-section-text">
                    <div>
                        <h4>아이디어를 표현하세요!</h4>
                        <p>여러분들이 가지고 있는 무한한 상상력을 프로젝트로 표현해보세요</p>
                    </div>
                    <div>
                        <h4>함께해요!</h4>
                        <p>마음에 드는 아이디어가 있으면 후원을 통해 창작자를 지원합니다.</p>
                    </div>
                    <div>
                        <h4>보상을 받아보세요!</h4>
                        <p>창작자는 후원자에게 감사의 의미로 리워드를 제공할 수 있어요!</p>
                    </div>
                    <button id="btn-project" onclick="location.href='/project/list'">프로젝트 보러가기&nbsp;&rarr;</button>
                </div>
            </div>
        </div>
    </section>
    <section id="ranking-area">
        <h2>인기 프로젝트</h2>
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