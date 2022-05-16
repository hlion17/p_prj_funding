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
        /*height: 350px;*/
        padding: 40px;
        border: 1px solid rgba(0, 0, 0, 0.04);
        background: white;
        box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
    }
    .item-make-component {
        margin: 20px 0;
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

    /* 리워드 페이지 아이콘 */
    #second-nav ul > li:nth-child(1) i {
        color: darkorange;
    }
    /* 리워드 옵션 */
    #reward-option-template {
        display: none;
    }
    .reward-option {
        display: flex;
        justify-content: space-between;
        align-items: center;
        width: 80%;
        padding: 10px;
        margin: 10px 0;
        border: 1px solid rgba(0, 0, 0, 0.04);
    }
    .reward-option p {
        margin: 0;
    }
    .reward-option p {
    }
    /* 생성된 리워드 */
    #my-reward-template {
        display: none;
    }
    .my-reward {
        display: flex;
        flex-direction: column;
        justify-content: space-between;
        width: 250px;
        padding: 20px;
        margin: 20px;
        position: relative;
        background: white;
        box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
    }
    .my-reward div:first-child {
        margin-bottom: 10px;
    }
    .my-reward div:first-child p {
        font-size: 16px;
        font-weight: 700;
        margin: 0;
    }
    .my-reward > button {
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
                const optionList = $("#option-list")
                // 옵션 리스트 select 태그에 출력
                for (i = 0; i < res.list.length; i++) {
                    optionList.append($("<option>",
                        {value: res.list[i].optionNo
                            , text: res.list[i].optionName}))
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
    // 옵션 삭제(HTML 요소 삭제)
    function deleteOption(target) {
        const targetOption = $(target).parents(".reward-option")
        targetOption.remove()
    }
    // 리워드 조회
    function getRewards() {
        $.ajax({
            type: "GET"
            , url: "/rewards"
            , dataType: "JSON"
            , data: {projectNo: "${project.projectNo}"}
            , success: function(res) {
                // 화면에 표시된 리워드 목록 제거
                $("#item-area li").remove()
                // 조회 결과 리워드 목록랜더링
                for (var i in res.list) {
                    renderReward(
                        res.list[i].rewardPrice
                        , res.list[i].rewardIntro
                        , res.list[i].rewardContent
                        , res.list[i].rewardNo)
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
    // 리워드 생성
    function createReward() {
        // 삽입 할 리워드 데이터
        const rewardPrice = $("input[name=rewardPrice]").val()
        const rewardInfo = $("input[name=rewardInfo]").val()
        // 옵션 데이터
        const option = $(".reward-option")
        let rewardContent = ""
        for (i = 0; i < option.length; i++) {
            const countVal = $(option[i]).children().eq(1).find("input").val()
            rewardContent += $(option[i]).find("p").text() + " x" + countVal + ";"
        }

        $.ajax({
            type: "POST"
            , url: "/reward/create"
            , dataType: "JSON"
            , data: {
                projectNo: ${project.projectNo}
                , rewardPrice: rewardPrice
                // DB에 reward_intro 인데 지금 것 rewardInfo 로 작업해서 차이가 발생
                , rewardIntro: rewardInfo
                , rewardContent: rewardContent
            }
            , success: function(res) {
                if (res.result == 1) {
                    // 서버에서 리워드 목록 조회
                    getRewards()
                }
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
    // 리워드 삭제
    function deleteReward(target) {
        const rewardNo = target.getAttribute("data-rewardNo")
        $.ajax({
            type: "POST"
            , url: "/rewards/delete"
            , dataType: "JSON"
            , data: {rewardNo: rewardNo}
            , success: function (res) {
                console.log("ajax 성공: ", res)
                //location.reload()
                getRewards()
            }
            , error: function (e) {
                console.log("ajax 실패: ", e)
            }
        })
    }
    // 선택된 옵션 화면에 표시
    function renderOptions(select) {
        // 선택된 option 이름값
        const option = select.options[select.selectedIndex].text
        // 옵션 템플릿 복제
        const template = $("#reward-option-template").clone()
        template.removeAttr("id")
        template.css("display", "flex")
        template.addClass("reward-option")
        template.find("p").text(option)  // 템플릿에 선택된 option 값 넣기
        // 옵션 영역에 요소 표시
        const area = $("#selected-option-area")
        area.append(template)
    }
    // 리워드 요소 랜더링
    function renderReward(rewardPrice, rewardInfo, rewardContent, rewardNo) {
        // 템플릿 복제
        const template = $("#my-reward-template").clone()
        template.removeAttr("id")
        template.css("display", "flex")
        template.addClass("my-reward")
        // 리워드 금액, 리워드 설명 데이터 삽입
        template.children().eq(0).find("p").text(rewardPrice)
        template.children().eq(1).find("p").text(rewardInfo)
        // 리워드 옵션 데이터 삽입
        const contentArr = rewardContent.split(";")
        for (let i in contentArr) {
            if (contentArr[i] != "") {
                const li = $("<li>" + contentArr[i] +"</li>");
                template.children().eq(1).find("ul").append(li)
            }
        }

        // 리워드 식별값 주입
        template.find("button").attr("data-rewardNo", rewardNo)

        // 리워드 생성 결과 영역에 데이터 표시
        const result = $("#item-area")
        result.append(template)
    }

    $(document).ready(function () {
        // 페이지 로드시 프로젝트 리워드 조회
        getRewards()
        // 페이지 로드시 프로젝트의 리워드 옵션 조회
        getOptions()
        // 만들기 버튼 클릭시 리워드 카드 생성
        $("#btn-option-save").click(function() {
            // 리워드 카드 생성
            createReward()
            // 데이터 칸 초기화
            $("input[name=rewardPrice]").val("")
            $("input[name=rewardInfo]").val("")
            $("#option-list option:eq(0)").prop("selected", true)
            $(".reward-option").remove()
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
                <li onclick="location.href='/project/editor/${project.projectNo}/reward'"><i class="fa-solid fa-gift"></i>리워드</li>
                <li onclick="location.href='/project/editor/${project.projectNo}/reward_option'"><i class="fa-solid fa-bars"></i>옵션</li>
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
                            <%-- 리워드 생성 영역 --%>
                        </ul>
                    </div>
                </div>
                <div class="editor-section-right">
                    <div id="item-make">
                        <div class="item-make-component">
                            <p>리워드 만들기</p>
                            <p>선물은 후원자에게 프로젝트의 가치를 전달하는 수단입니다. 다양한 금액대로 여러 개의 선물을 만들어주세요. 펀딩 성공률이 높아지고, 더 많은 후원 금액을 모금할 수 있어요.</p>
                        </div>
                        <div class="item-make-component">
                            <p>리워드 금액</p>
                            <input type="text" name="rewardPrice" placeholder="리워드 금액을 입력해주세요">
                        </div>
                        <div class="item-make-component">
                            <p>리워드 설명을 작성해주세요</p>
                            <input type="text" name="rewardInfo" placeholder="리워드에 대한 설명을 작성해주세요">
                        </div>
                        <div class="item-make-component">
                            <p>옵션을 선택해주세요</p>
                            <select id="option-list" onchange="renderOptions(this)">
                                <option disabled selected="selected">옵션선택</option>
                                <%-- 옵션 생성 영역 --%>
                            </select>
                            <div id="selected-option-area">
                                <%-- 선택된 옵션 표시 영역 --%>
                            </div>
                        </div>
                        <div class="item-make-component">
                            <button id="btn-option-save">만들기</button>
                        </div>
                    </div>
                </div>
            </section>

            <%-- 템플릿 코드들 --%>

            <%-- 옵션 카드 템플릿 --%>
            <li id="item-template" style="display: none;">
                <div><p></p></div>
                <button class="btn-delete-option" onclick="deleteOption(this)"><i class="fa-solid fa-trash-can"></i></button>
            </li>
            <%-- 옵션 표시 템플릿 --%>
            <div id="reward-option-template">
                <div><p></p></div>
                <div>
                    <input type="number" value="1" min="1">
                    <button onclick="deleteOption(this)">삭제</button>
                </div>
            </div>
            <%-- 리워드 템플릿 --%>
            <li id="my-reward-template" class="my-reward">
                <div><p></p></div>
                <div>
                    <p></p>
                    <ul></ul>
                </div>
                <button class="btn-delete-option" onclick="deleteReward(this)"><i class="fa-solid fa-trash-can"></i></button>
            </li>

        </div>
    </div>
</div>

<%-- footer --%>
<%@ include file="/WEB-INF/views/component/footer.jsp"%>