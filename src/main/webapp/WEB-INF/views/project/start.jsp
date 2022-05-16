<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%-- header --%>
<%@include file="/WEB-INF/views/component/header.jsp"%>

<style>
    /*div {*/
    /*    border: 1px solid black;*/
    /*}*/
    button {
        border: none;
        outline: none;
        padding: 10px;
        border-radius: 10px;
    }
    @media screen and (max-width: 1000px) {
        #left-content {
            display: none;
        }
    }
    #start-container {
        display: flex;
        width: 100%;
    }
    #left-content {
        width: 450px;
        background: url(https://source.unsplash.com/featured/?{winter});
        background-size: cover;
        background-repeat: no-repeat;
        height: 100vh;
    }
    #right-content {
        flex-grow: 1;
        width: auto;
        height: 100vh;
        overflow: auto;
    }
    #right-content > div.start-content {
        width: 450px;
        margin: 0 auto;
    }
    .start-info-msg {
        margin-top: 50px;
        margin-bottom: 10px;
        color: darkorange;
    }
    #on-writing-box {
        display: flex;
        flex-direction: column;
        height: 400px;
        overflow: auto;
        padding: 20px;
        border: 1px solid #ccc;

        /* scroll bar hidden */
        -ms-overflow-style: none;  /* IE and Edge */
        scrollbar-width: none;  /* Firefox */
    }
    /* scroll bar hidden */
    #on-writing-box::-webkit-scrollbar {
        display: none;
    }
    .start-on-writing {
        display: flex;
        justify-content: space-between;
        align-items: center;
        margin: 10px 0;
        padding: 10px;
        box-shadow: 0px 2px 10px rgba(0, 0, 0, 0.1);
    }
    .start-on-writing img, .start-on-writing > div > div > p {
        width: 70px;
        height: 70px;
        margin: 0;
    }
    .start-on-writing > div:first-child {
        display: flex;
        align-items: center;
    }
    .start-on-writing > div > p {
        max-width: 200px;
        margin: 0;
        padding: 0 10px;
        font-size: 14px;
    }
    .start-on-writing > button {
        font-size: 12px;
        width: 90px;
        height: 40px;
    }
    .start-on-writing > div {
        display: flex;
    }
    .start-new-category {
        margin: 50px 0;
    }
    .start-new-category > div {
        display: flex;
    }
    #category-list {
        margin-top: 20px;
    }
    #category-list > ul {
        display: flex;
        flex-wrap: wrap;
        list-style: none;
        padding: 0;
    }
    #category-list > ul > li {
        display: inline-block;
        width: auto;
        padding: 10px;
        margin: 5px;
        border: 1px solid #ccc;
        border-radius: 15px;
        font-weight: 400;
    }
    .start-new-intro {
        display: flex;
        flex-direction: column;
        flex-wrap: nowrap;
    }
    .start-new-intro > div > textarea {
        resize: none;
        width: 100%;
    }
    .start-new-intro > div:last-child {
        text-align: right;
    }
    .start-new-intro > div:last-child > button {
        text-align: right;
        padding: 10px;
    }
</style>

<script>
    $(document).ready(function () {
        // 선택한 카테고리 항목 css 변경, 체크 속성 추가
        $("#category-list li").click(function () {
            $("#category-list li").css("background", "none")
            $("#category-list li").css("color", "black")
            $("#category-list li").css("border", "1px solid #ccc")
            $("#category-list li").removeAttr("data-category-checked")
            $(this).css("background", "darkorange")
            $(this).css("color", "white")
            $(this).css("border", "none")
            $(this).attr("data-category-checked", "checked")
        });
        // 작성중인 프로젝트로 이동
        $(".on-writing").click(function() {
            const projectNo = $(this).attr("data-projectNo")
            location.href = "/project/editor/" + projectNo + "/basic"
        })
        // 프로젝트 생성
        $("#btn-create-project").click(function () {
            const categoryId = $("#category-list li[data-category-checked=checked]").attr("data-categoryNo")
            const projectIntro = $("#projectIntro").val()
            const memberNo = '${sessionScope.loginMemberNo}'

            // 입력값 검증
            if (categoryId == undefined) {
                alert("카테고리를 선택해주세요")
                return false
            }
            if (projectIntro == '') {
                alert("프로젝트 소개글을 입력하여 프로젝트를 생성해주세요")
                return false
            }
            if (memberNo == '') {
                // 해당 메시지가 나오면 이상한거.. start page 를 가져올 때 로그인 검증함
                alert("로그인 상태를 확인해주세요")
                return false
            }
            // 프로젝트 생성
            sendDataByPost("/project/create", {
               categoryId: categoryId
               , projectIntro: projectIntro
               , memberNo: memberNo})

            })
    })
</script>

</head>
<body>

<main class="container">

<div id="start-container">
    <div id="left-content"></div>
    <div id="right-content">
        <div class="start-content">
            <c:if test="${not empty list}">
            <div class="start-info-msg">
                <i class="fa-solid fa-pencil"></i>
                <span>작성중인 프로젝트</span>
            </div>
            <div id="on-writing-box">
                <c:forEach var="p" items="${list}">
                <div class="start-on-writing">
                    <div>
                        <div>
                            <c:if test="${not empty p.projectImage}">
                            <img src="${p.projectImage}" alt="">
                            </c:if>
                            <c:if test="${empty p.projectImage}">
                            <p>no image</p>
                            </c:if>
                        </div>
                        <p>${p.projectTitle}</p>
                    </div>
                    <button data-projectNo="${p.projectNo}" class="on-writing">이어서 작성</button>
                </div>
                </c:forEach>
            </div>
            </c:if>
            <div class="start-new-category">
                <h2>프로젝트의 카테고리</h2>
                <div id="category-list">
                    <ul>
                        <c:forEach var="c" items="${cList}">
                        <li data-categoryNo="${c.categoryNo}">${c.categoryName}</li>
                        </c:forEach>
                    </ul>
                </div>
            </div>
            <div class="start-new-intro">
                <h2>프로젝트 생성</h2>
                <p>간단한 프로젝트 소개와 함께 프로젝트를 생성하세요</p>
                <div>
                    <textarea name="" id="projectIntro" cols="60" rows="10"></textarea>
                </div>
                <div>
                    <button id="btn-create-project">다음</button>
                </div>
            </div>
        </div>
    </div>
</div>

</main>


<%-- footer --%>
<%@include file="/WEB-INF/views/component/footer.jsp"%>
