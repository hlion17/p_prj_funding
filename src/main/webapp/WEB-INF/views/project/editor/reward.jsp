<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%-- header --%>
<%@ include file="/WEB-INF/views/component/header.jsp"%>

<style>
    /* 레이아웃 확인용 */
    div {
        /*border: 1px solid black;*/
    }
    /* 버튼 전체 */
    button {
        border: none;
        outline: none;
        box-shadow: none;
        padding: 10px;
        border-radius: 5px;
    }
    /* 전체 wrapper */
    #wrapper {
        position: relative;
        background: rgb(252, 252, 252);
        /*background: beige;*/
    }
    /* 헤더 메뉴 */
    #menu-wrapper {
        position: sticky;
        top: 0;
        z-index: 999;
        background: white;
    }
    #header-menu {
        display: flex;
        justify-content: space-between;
        align-items: center;
        width: 1080px;
        margin: 0 auto;
        padding: 30px 0;
        background: white;
    }
    #header-menu div:first-child{
        font-size: 30px;
        font-weight: bold;
    }
    /* 프로젝트 제목 */
    #title-wrapper {
        background: white;
    }
    #header-title {
        width: 1080px;
        margin: 0 auto;
        padding: 20px 0;
    }
    /* 에디터 네비게이션 */
    #nav-wrapper {
        display: flex;
        justify-content: center;
        /*width: 100%;*/
        position: sticky;
        top: 105px;
        left: 0;
        z-index: 999;
        background: white;
        box-shadow: 0px 2px 8px rgba(0, 0, 0, 0.04);
    }
    #header-nav {
        width: 1080px;
    }
    #header-nav ul {
        display: flex;
        height: 60px;
        align-items: center;
        padding: 0;
        margin: 0 auto;
        position: relative;
    }
    #header-nav ul > li{
        display: inline-block;
        height: 40px;
        margin-right: 20px;
        position: relative;
        cursor: pointer;
    }
    /* 네비게이션 메뉴 선택 스타일 */
    .nav-select {
        font-weight: 700;
    }
    .nav-select::after{
        content: "";
        display: block;
        width: 100%;
        height: 2px;
        position: absolute;
        left: 0;
        bottom: 0;
        background: darkorange;
    }
    /* 에디터 본문 */
    #body-wrapper {
        width: 1080px;
        margin: 0 auto;
        padding: 50px 0;
    }
    .editor-section {
        display: flex;
        min-height: 150px;
        border-bottom: 1px solid rgba(0, 0, 0, 0.04);
    }
    .editor-section .editor-section-left {
        display: flex;
        flex-direction: column;
        width: 400px;
        padding: 20px 40px 20px 5px;
    }
    .editor-section-left .info-title {
        font-size: 1.2em;
        font-weight: 700;
    }
    .editor-section-left .info-content {
        font-size: 0.8em;
    }
    .editor-section .editor-section-right {
        flex-grow: 1;
        padding: 20px 60px;
    }
    .editor-section-right {
        margin-top: 65px;
    }
    .editor-section-right p {
        font-weight: 700;
        font-size: 0.8em;
    }
    .editor-section-right [name] {
        width: 100%;
    }

    /* 변경 사항 저장 버튼 active 스타일 */
    .save-project {
        background: rgb(248, 100, 83);
        color: white;
    }

    /* 두 번쨰 네비게이션 스타일 */
    #second-nav-wrapper {
        display: flex;
        justify-content: center;
        /*width: 100%;*/
        position: sticky;
        top: 165px;
        left: 0;
        z-index: 999;
        background: white;
        box-shadow: 0px 2px 8px rgba(0, 0, 0, 0.04);
        border-top: 1px solid rgba(0, 0, 0, 0.04);
    }
    #second-nav-wrapper > div {
        width: 1080px;
    }
    #second-nav-wrapper ul {
        display: flex;
        height: 60px;
        align-items: center;
        padding: 0;
        margin: 0 auto;
        position: relative;
    }
    #second-nav-wrapper ul > li {
        display: inline-block;
        height: 40px;
        margin-right: 20px;
        padding: 7px 0;
        position: relative;
        cursor: pointer;
    }
    #second-nav-wrapper li > i {
        padding-right: 10px;
        font-size: 12px;
    }
    /* 리워드 2차 네비게이션 탭 아이콘 색 변경 */
    .active_icon {
        color: darkorange;
    }

    /* 리워드 옵션 페이지 */
    #item-make {
        display: flex;
        flex-direction: column;
        justify-content: space-between;
        height: 350px;
        padding: 40px;
        border: 1px solid rgba(0, 0, 0, 0.04);
        background: white;
        box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
    }
    #item-make .item-make-component:first-child {
        border-bottom: 1px solid #ccc;
    }
    #item-make .item-make-component:nth-child(2) > input {
        border: 0;
        border-bottom: 1px solid #ccc;
        padding: 10px 0;
    }
    .item-make-component p:nth-child(1) {
        font-size: 16px;
        font-weight: 700;
    }
    .item-make-component p:nth-child(2) {
        font-size: 14px;
        color: rgb(109, 109, 109);
    }
    #btn-option-save {
        width: 100px;
    }
    .my-item {
        display: flex;
        justify-content: space-between;
        width: 250px;
        padding: 20px;
        margin: 20px;
        position: relative;
        background: white;
        box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
    }
    .my-item p {
        font-size: 16px;
        font-weight: 700;
        margin: 0;
    }
    .my-item > button {
        padding:5px 10px;
        position: absolute;
        top: 10px;
        right: 10px;
        background: white;
        border:1px solid #ccc;
        font-size: 16px;
        font-weight: 700;
    }


</style>

<%-- 리워드 페이지 자바스크립트 --%>
<script>
    // 네비게이션 메뉴 이동
    function navButton(viewName) {
        const url = "/project/editor/${project.projectNo}/" + viewName
        location.href=url
    }
    // 옵션 조회
    function getOptions() {
        $.ajax({
            type: "GET"
            , url: "/options"
            , dataType: "JSON"
            , data: {projectNo: "${project.projectNo}"}
            , success: function(res) {
                // 리워드 옵션 요소를 화면에 뿌려준다.
                $("#item-area li").remove()
                for (i = 0; i < res.list.length; i++) {
                    const obj = $("#item-template").clone()
                    obj.removeAttr("id")
                    obj.css("display", "block")
                    obj.find("p").text(res.list[i].optionName)
                    obj.find("button").attr("data-optionNo", res.list[i].optionNo)
                    obj.addClass("my-item")
                    const target = $("#item-area")
                    target.append(obj)
                }
            }
            , error: function (jqXHR) {
                console.log("ajax 실패")
                console.log(jqXHR.status)
                console.log(jqXHR.statusText)
                // console.log(jqXHR.responseText)
                console.log(jqXHR.readyState)
            }

        })
    }
    // 옵션 삭제
    function deleteOption(target) {
        const optionNo = target.getAttribute("data-optionNo")
        $.ajax({
            type: "POST"
            , url: "/options/delete"
            , dataType: "JSON"
            , data: {optionNo: optionNo}
            , success: function (res) {
                console.log("ajax 성공: ", res)
                //location.reload()
                getOptions()
            }
            , error: function (e) {
                console.log("ajax 실패: ", e)
            }
        })
    }
    // 옵션 생성
    function createOption() {
        const itemName = $("input[name=optionName]").val()
        const obj = $("#item-template").clone()

        $.ajax({
            type: "POST"
            , url: "/option/create"
            , dataType: "JSON"
            , data: {optionName: itemName, projectNo: ${project.projectNo}}
            , success: function(res) {
                obj.removeAttr("id")
                obj.css("display", "block")
                obj.find("p").text(itemName)
                obj.addClass("my-item")
                const target = $("#item-area")
                target.append(obj)

                $("input[name=optionName]").val("")
            }
            , error: function(jqXHR) {
                console.log("ajax 실패", jqXHR)
                console.log("ajax 실패")
                console.log(jqXHR.status)
                console.log(jqXHR.statusText)
                // console.log(jqXHR.responseText)
                console.log(jqXHR.readyState)
            }
        })
    }

    $(document).ready(function () {
        // 페이지 로드시 프로젝트의 리워드 옵션 조회
        getOptions()
        // 만들기 버튼 클릭시 옵션 생성
        $("#btn-option-save").click(function() {
            createOption()
        })
        // 옵션 생성 input enter event
        $("input[name=optionName]").on("keydown", function (e) {
            if (e.keyCode == 13) {
                createOption()
            }
        })
    })// document onload 마지막


</script>

<%-- 프로젝트 내용 변경 감지 --%>
<%-- 프로젝트 내용 --%>
<script>
    $(document).ready(function () {

        // 불러온 내용 상수값 저장 (변경된 값과 비교 대상)
        // 일정 정보
        const loadedSchedulePlan = `${project.schedulePlan}`
        const loadedOpenDate = `<fmt:formatDate value="${project.openDate}" pattern="yyyy-MM-dd"/>`
        const loadedCloseDate = `<fmt:formatDate value="${project.closeDate}" pattern="yyyy-MM-dd"/>`
        const loadedDeliveryDate = `<fmt:formatDate value="${project.deliveryDate}" pattern="yyyy-MM-dd"/>`
        // name 속성이 있는 모든 요소에 변경감지 이벤트 부여
        $("[name]").on("change keyup", function () {
            // 변경된 작성 내용
            // 일정 정보
            let editedOpenDate = $("input[name=openDate]").val()
            let editedCloseDate = $("input[name=closeDate]").val()
            let editedDeliveryDate = $("input[name=deliveryDate]").val()
            // 불러온 내용에서 변경된 사항이 있는 경우
            if (loadedOpenDate != editedOpenDate
                || loadedCloseDate != editedCloseDate
                || loadedDeliveryDate != editedDeliveryDate) {
                $("#save-project").removeAttr("disabled")
                $("#save-project").addClass("save-project")
                return false
            } else {  // 변경사항이 없는경우
                $("#save-project").attr("disabled", "disabled")
                $("#save-project").removeClass("save-project")
            }
        })
        // document onload 마지막
    })

</script>

<div id="wrapper">
    <%-- 헤더 메뉴 --%>
    <div id="menu-wrapper">
        <div id="header-menu">
            <div onclick="location.href='/project/start'">&larr;</div>
            <div>
                <button id="save-project" disabled>저장하기</button>
            </div>
        </div>
    </div>
    <%-- 프로젝트 제목 --%>
    <div id="title-wrapper">
        <div id="header-title">
            <h1>프로젝트 기획</h1>
        </div>
    </div>
    <%-- 에디터 네비게이션 --%>
    <div id="nav-wrapper">
        <div id="header-nav">
            <ul>
                <li onclick="navButton('basic')">기본정보</li>
                <li onclick="navButton('content')">내용작성</li>
                <li onclick="navButton('schedule')">일정정보</li>
                <li onclick="navButton('budget')">예산정보</li>
                <li class="nav-select" onclick="navButton('reward')">리워드</li>
            </ul>
        </div>
    </div>
    <%-- 에디터 2차 네비게이션 --%>
    <div id="second-nav-wrapper">
        <div id="second-nav">
            <ul>
                <li onclick=""><i class="fa-solid fa-gift"></i>리워드</li>
                <li onclick=""><i class="fa-solid fa-bars"></i>옵션</li>
            </ul>
        </div>
    </div>
    <%-- 에디터 바디 --%>
    <div id="editor-body">
        <div id="body-wrapper">
            <%-- 프로젝트 제목 섹션 --%>
            <section class="editor-section">
                <div class="editor-section-left">
                    <div class="info-title"><p>내가 만든 선물</p></div>
                    <div class="info-content">
                        <ul id="item-area">
                            <%-- 옵션 생성 영역 --%>
                        </ul>
                    </div>
                </div>
                <div class="editor-section-right">
                    <div id="item-make">
                        <div class="item-make-component">
                            <p>아이템 만들기</p>
                            <p>아이템은 선물에 포함되는 구성 품목을 말합니다. 특별한 물건부터 의미있는 경험까지 선물을 구성할 아이템을 만들어 보세요.</p>
                        </div>
                        <div class="item-make-component">
                            <p>아이템 이름</p>
                            <input type="text" name="optionName" placeholder="아이템 이름을 입력해주세요">
                        </div>
                        <div class="item-make-component">
                            <button id="btn-option-save">만들기</button>
                        </div>
                    </div>
                </div>
            </section>

            <%-- 옵션 카드 템플릿 --%>
            <li id="item-template" style="display: none;">
                <div><p></p></div>
                <button class="btn-delete-option" onclick="deleteOption(this)"><i class="fa-solid fa-trash-can"></i></button>
            </li>

        </div>
    </div>
</div>

<%-- footer --%>
<%@ include file="/WEB-INF/views/component/footer.jsp"%>