<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%-- header --%>
<%@ include file="/WEB-INF/views/component/header.jsp"%>

<style>
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

</style>

<%-- ck Editor Basic load --%>
<script src="/resources/ckeditor_basic/ckeditor.js"></script>

<script>
    $(document).ready(function () {
        // ck Editor 적용
        CKEDITOR.replace("budgetPlan-ck");
    })
    // 네비게이션 메뉴 이동
    function navButton(viewName) {
        const url = "/project/editor/${project.projectNo}/" + viewName
        location.href=url
    }
    // 폼 제출
    function submitButton() {
        $("form[name=update-form]").submit()
    }
</script>
<%-- 프로젝트 내용 변경 감지 --%>
<%-- 예산 계획, 목표금액 --%>
<script>
    $(document).ready(function () {

        // 불러온 내용 상수값 저장 (변경된 값과 비교 대상)
        // 예산 정보
        const loadedProjectPrice = `${project.projectPrice}`
        const loadedBudgetPlan = `${project.budgetPlan}`.replace('\n','')
        // name 속성이 있는 모든 요소에 변경감지 이벤트 부여
        $("[name]").on("change keyup", function () {
            // 변경된 작성 내용
            // 일정 정보
            let editedProjectPrice = $("input[name=projectPrice]").val()
            // 불러온 내용에서 변경된 사항이 있는 경우
            if (loadedProjectPrice != editedProjectPrice) {
                $("#save-project").removeAttr("disabled")
                $("#save-project").addClass("save-project")
                return false
            } else {  // 변경사항이 없는경우
                $("#save-project").attr("disabled", "disabled")
                $("#save-project").removeClass("save-project")
            }
        })
        // 프로젝트 소개
        CKEDITOR.instances["budgetPlan-ck"].on("instanceReady", function(){
            this.document.on("keyup", function () {
                console.log("test")
                let editedBudgetPlan = CKEDITOR.instances['budgetPlan-ck'].getData()
                if (loadedBudgetPlan != editedBudgetPlan) {
                    $("#save-project").removeAttr("disabled")
                    $("#save-project").addClass("save-project")
                    return false
                }
                $("#save-project").attr("disabled", "disabled")
                $("#save-project").removeClass("save-project")
            });
        });
        // document onload 마지막
    })

</script>

<div id="wrapper">
    <%-- 헤더 메뉴 --%>
    <div id="menu-wrapper">
        <div id="header-menu">
            <div onclick="location.href='/project/start'">&larr;</div>
            <div>
                <button id="save-project" disabled onclick="submitButton()">저장하기</button>
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
                <li class="nav-select" onclick="navButton('budget')">예산정보</li>
                <li onclick="navButton('reward')">리워드</li>
            </ul>
        </div>
    </div>
    <div id="header-wrapper">
    </div>
    <%-- 에디터 바디 --%>
    <div id="editor-body">
        <form action="/project/update" method="post" name="update-form">
        <input type="hidden" name="projectNo" value="${project.projectNo}">
        <input type="hidden" name="categoryId" value="${project.categoryId}">
        <input type="hidden" name="memberNo" value="${project.memberNo}">
        <div id="body-wrapper">
            <%-- 프로젝트 제목 섹션 --%>
            <section class="editor-section">
                <div class="editor-section-left">
                    <div class="info-title"><p>프로젝트 목표금액</p></div>
                    <div class="info-content">
                        <p>프로젝트를 완수하기 위해 필요한 금액을 설정해주세요.</p>
                    </div>
                </div>
                <div class="editor-section-right">
                    <div>
                        <p>목표금액</p>
                        <input type="text" name="projectPrice"
                               value="${project.projectPrice}" placeholder="50만원 이상의 금액을 임력해주세요">
                    </div>
                </div>
            </section>
            <section class="editor-section">
                <div class="editor-section-left">
                    <div class="info-title"><p>프로젝트 예산</p></div>
                    <div class="info-content">
                        <p>설정하신 목표금액을 어디에 사용하실지 굼체적인 지출항목을 적어주세요</p>
                    </div>
                </div>
                <div class="editor-section-right">
                    <div>
                        <textarea name="budgetPlan" id="budgetPlan-ck" cols="30" rows="10">${project.budgetPlan}</textarea>
                    </div>
                </div>
            </section>
        </div>
        </form>
    </div>
</div>

<%-- footer --%>
<%@ include file="/WEB-INF/views/component/footer.jsp"%>