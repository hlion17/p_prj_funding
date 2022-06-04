<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%-- header --%>
<%@include file="/WEB-INF/views/component/header.jsp"%>

<style>
    main > div {
        width: 1080px;
        margin: 0 auto;
    }
    #myProject-header {
        border-bottom: 1px solid #ccc;
        padding: 20px 0;
    }
    #myProject-header > div {
        /*padding: 20px 0;*/
    }
    #myProject-nav {
        margin-top: 20px;
    }
    #myProject-nav ul {
        display: flex;
        list-style: none;
        margin: 0;
        padding: 0;
    }
    #myProject-nav li {
        border: 1px solid #ccc;
        border-radius: 10px;
        padding: 5px 10px;
        margin-right: 10px;
        text-align: center;
        cursor: pointer;
    }
    /*  프로젝트 리스트 영역 */
    #myProject-wrapper {
        padding: 20px 10px;
    }
    .step-area > div:first-child {
        font-size: 20px;
        font-weight: 700;
        margin-bottom: 20px;
    }
    .step-area hr {
        color: darkorange;
        height: 2px;
    }
    .myProject-box {
        border: 1px solid #ccc;
        display: flex;
        justify-content: space-between;
        width: 100%;
        padding: 20px 10px;
        margin-bottom: 20px;
    }
    .myProject-content-area {
        display: flex;
    }
    .myProject-content-area > .img-box {
        width: 160px;
        height: 110px;
        margin-right: 10px;
    }
    .myProject-content-area > .img-box img {
        width: 100%;
        height: 100%;
    }
    .myProject-content-area > .content-box {
        display: flex;
        flex-direction: column;
    }
    .myProject-content-area > .content-box > div {
        margin-bottom: 5px;
    }
    .myProject-content-area > .content-box > div:nth-child(1) {
        width: 50px;
        text-align: center;
        border-radius: 5px;
        font-size: 12px;
        padding: 3px;
        background: rgba(0, 0, 0, 0.1);
        color: white;
    }
    .myProject-content-area > .content-box > div:nth-child(2) {
        font-size: 16px;
        font-weight: 700;
    }
    .myProject-content-area > .content-box > div:nth-child(3) {
        font-size: 12px;
    }
    .active-nav {
        /*background: rgba(255, 140, 0, 0.3);*/
        background: darkorange;
        color: white;
        border: none !important;
    }

    .myProject-button-area {
        display: flex;
        flex-direction: column;
        justify-content: space-around;
        width: 150px;
    }
    .myProject-button-area > div {
        border-radius: 10px;
        padding: 5px;
        text-align: center;
    }
    .myProject-button-area > .btn-manage {
        background: rgba(0, 0, 0, 0.1);
        cursor: pointer;
    }
    .myProject-button-area > .btn-delete {
        background: rgba(255, 140, 0, 0.3);
        cursor: pointer;
    }
</style>

<script>
    $(document).ready(function () {
        // 진행상태 색 부여
        stepColor()
        // 섹션 이동 이벤트 부여
        // $("#myProject-nav li").click(function () {
        //     const stepName = $(this).text()
        //     const dest = $(".step-name")
        //     for (i in dest) {
        //         if ($(dest[i]).text().startsWith(stepName)) {
        //             const offset = $(dest[i]).offset()
        //             $("html, body").animate({scrollTop: offset.top}, 100)
        //             return false
        //         }
        //     }
        // })
        // 프로젝트 구분에 따른 조회
        $("#myProject-nav li").click(function () {
            const target = $(this)
            $.ajax({
                method: "GET"
                , url: "/myPage/myProject"
                , dataType: "HTML"
                , data: {projectStep: $(this).attr("data-step")}
                , success: function (res) {
                    console.log("ajax 성공")
                    $("#myProject-wrapper").html(res)
                    $("#myProject-nav li").removeClass("active-nav")
                    target.addClass("active-nav")
                }
                , error: function (error) {
                    console.log("ajax 실패: ", error)
                }
            })
        })
        // 프로젝트 삭제
        $(".btn-delete").click(function () {
            const projectNo = $(this).attr("data-projectNo")
            const projectTitle = $(this).attr("data-projectTitle")
            if (confirm("정말로 \"" + projectTitle + "\" 프로젝트를 삭제하시겠습니까?")) {
                $.ajax({
                    method: "POST"
                    , url: "/project/delete"
                    , dataType: "json"
                    , data: {projectNo: projectNo}
                    , success: function (res) {
                        if (res.message != "" && res.message != undefined) {
                            alert(res.message)
                            location.href="/myPage/myProject"
                        }
                    }
                    , error: function (error) {
                        console.log("error: ", error)
                    }
                })
            }
        })
        // 프로젝트 관리 및 바로가기
        $(".btn-manage").click(function () {
            const projectNo = $(this).attr("data-projectNo")
            const projectStep = $(this).attr("data-projectStep")
            if (projectStep == 0) {
                location.href="/project/editor/"+ projectNo + "/basic";
            } else if (projectStep == 3 || projectStep == 4) {
                location.href="/project/"+ projectNo
            }
        });
    })  // end of document on load
    // 진행상태 색
    function stepColor() {
        const stepName = $(".content-box div:first-child")
        for (i in stepName) {
            const name = $(stepName[i])

            switch (name.text()) {
                case '작성중':
                    name.css("background", "goldenrod");
                    break
                case '심사중':
                    name.css("background", "cornflowerblue");
                    break
                case '반려됨':
                    name.css("background", "darkgrey");
                    break
                case '진행중':
                    name.css("background", "limegreen");
                    break
                case '종료됨':
                    name.css("background", "indianred");
                    break

            }
        }
    }
    // 프로젝트 관리 및 바로가기
    function manage(e) {
        const projectNo = $(e).attr("data-projectNo")
        const projectStep = $(e).attr("data-projectStep")
        if (projectStep == 0) {
            location.href="/project/editor/"+ projectNo + "/basic";
        } else if (projectStep == 3 || projectStep == 4) {
            location.href="/project/"+ projectNo
        }
    }
    // 프로젝트 삭제
    function del(e) {
        const projectNo = $(e).attr("data-projectNo")
        const projectTitle = $(e).attr("data-projectTitle")
        if (confirm("정말로 \"" + projectTitle + "\" 프로젝트를 삭제하시겠습니까?")) {
            $.ajax({
                method: "POST"
                , url: "/project/delete"
                , dataType: "json"
                , data: {projectNo: projectNo}
                , success: function (res) {
                    if (res.message != "" && res.message != undefined) {
                        alert(res.message)
                        location.href="/myPage/myProject"
                    }
                }
                , error: function (error) {
                    console.log("error: ", error)
                }
            })
        }
    }
</script>

<main>
    <div id="myProject-header">
        <div>
            <h1>내가 만든 프로젝트</h1>
        </div>
        <div id="myProject-nav">
            <ul>
                <li class="active-nav" onclick="location.href='/myPage/myProject';">전체</li>
                <li data-step="0">작성중</li>
                <li data-step="1">심사중</li>
                <li data-step="2">반려됨</li>
                <li data-step="3">진행중</li>
                <li data-step="4">종료됨</li>
            </ul>
        </div>
    </div>
    <div id="myProject-wrapper">
        <c:forEach var="m" items="${map}">
        <div class="step-area">
            <div class="step-name">${m.value.get(0).stepName} <span>${m.value.size()}</span></div>
            <c:forEach var="p" items="${m.value}">
            <div class="myProject-box">
                <div class="myProject-content-area">
                    <div class="img-box">
                        <img src="${p.projectImage}" alt="">
                    </div>
                    <div class="content-box">
                        <div>${p.stepName}</div>
                        <div>${p.projectTitle}</div>
                        <div>${p.projectIntro}</div>
                    </div>
                </div>
                <div class="myProject-button-area">
                    <c:if test="${p.projectStep eq 0}">
                    <div class="btn-manage" data-projectNo="${p.projectNo}" data-projectStep="${p.projectStep}">관리</div>
                    </c:if>
                    <c:if test="${p.projectStep eq 3 || p.projectStep eq 4}">
                        <div class="btn-manage" data-projectNo="${p.projectNo}" data-projectStep="${p.projectStep}">바로가기</div>
                    </c:if>
                    <c:if test="${p.projectStep eq 0 || p.projectStep eq 2}">
                    <div class="btn-delete" data-projectNo="${p.projectNo}" data-projectTitle="${p.projectTitle}">삭제</div>
                    </c:if>
                </div>
            </div>
            </c:forEach>
            <hr>
        </div>
        </c:forEach>
    </div>
</main>

<%-- footer --%>
<%@include file="/WEB-INF/views/component/footer.jsp" %>