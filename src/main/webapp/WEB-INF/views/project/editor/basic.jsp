<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%-- header --%>
<%@ include file="/WEB-INF/views/component/header.jsp"%>

<%-- 페이지 스타일 --%>
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
        box-shadow: 0px 8px 8px rgba(0, 0, 0, 0.04);
        z-index: 999;
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
    #header-nav ul > li {
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
    #editor-body {
    }
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

    /* 이미지 섹션 */
    .img-section {
        display: flex;
        justify-content: space-between;
    }
    .img-section div:first-child {
        width: 300px;
    }

    /* 변경 사항 저장 버튼 active 스타일 */
    .save-project {
        background: rgb(248, 100, 83);
        color: white;
    }

    /* *********************************** - 여기까지 해서 파일로 빼서 link - project_editor.css */

    /* 파일 업로드 카드*/
    #upload-card {
        display: flex;
        flex-direction: column;
        border: 1px solid rgba(0,0,0,0.1);
        width: 100%;
        padding: 20px;
        text-align: center;
        background: #f6f5f5;
    }
    #upload-card span {
        font-size: 0.8em;
        color: rgb(248, 100, 83);
    }
    .fa-upload {
        color: rgb(248, 100, 83);
    }
    #file-upload {
        opacity: 0;
        width: 100%;
        height: 100%;
        position: absolute;
        top: 0;
        left: 0;
    }
    #upload-result {
        width: 100%;
        height: 200px;
    }
    #upload-result img {
        max-width: 200px;
        max-height: 200px;
    }

</style>

<%-- ck Editor Basic load --%>
<script src="/resources/ckeditor_basic/ckeditor.js"></script>

<script>
    $(document).ready(function () {

        // ck Editor 적용
        CKEDITOR.replace("projectIntro-ck");
        // 페이지 로드시 select 태그에 해당 프로젝트 저장된 카테고리 선택
        const select = document.querySelector("#select-category")
        for (i = 0; select.length; i++) {
            if (select.options[i].value == '${project.categoryId}') {
                select.options[i].selected = true
                break
            }
        }
        // 프로젝트 대표 사진 서버에 업로드
        $("#file-upload").on("change", function (e) {
            const file = e.target.files[0]
            var form = document.createElement("form")
            var formData = new FormData(form)
            formData.append("file", file)
            $.ajax({
                type: "POST"
                , url: "/project/fileUpload"
                , dataType: "JSON"
                , data: formData
                , contentType: false
                , processData: false
                , success: function(res) {
                    // 기존 이미지를 제거하고 업로드한 이미지로 변경
                    $("#uploaded-img").removeAttr("src")
                    $("#uploaded-img").attr("src", res.fileUrl)
                    // 프로젝트 업데이트 요청시 저장할 변수 생성
                    const input = $("<input type='hidden' name='projectImage'>")
                    input.attr("value", res.fileUrl)
                    $("#upload-result").append(input)
                }
                , error: function() {
                    console.log("ajax 실패")
                }
            })
        })
        // document onload 마지막
    })
    // document onload 밖 영역
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

<%-- 프로젝트 내용 변경 감지 스크립트 --%>
<%-- 제목, 카테고리, 소개, 대표사진 --%>
<script>
    $(document).ready(function () {

        // 불러온 내용 상수값 저장 (변경된 값과 비교 대상)
        const loadedProjectTitle = `${project.projectTitle}`
        const loadedCategoryId = `${project.categoryId}`
        const loadedProjectIntro = `${project.projectIntro}`.replace("\n","")
        const loadedProjectImage = `http://${pageContext.request.getHeader("host")}${project.projectImage}`
        // name 속성이 있는 모든 요소에 변경감지 이벤트 부여
        $("[name], #uploaded-img").on("change keyup load", function () {
            // 변경된 작성 내용
            // 기본정보
            let editedProjectTitle = $("input[name=projectTitle]").val()
            let editedCategoryId = $("#select-category option:selected").val()
            let editedProjectImage = $("#upload-result img").prop("src")
            // 불러온 내용에서 변경된 사항이 있는 경우
            if (loadedProjectTitle != editedProjectTitle
                || loadedCategoryId != editedCategoryId
                || loadedProjectImage != editedProjectImage) {
                // 아래 중복 로직, 함수로 빼면 ck 에디터 쪽에 있는
                // 버튼 변경 코드 중복 제거할 수 있지 않을까?
                $("#save-project").removeAttr("disabled")
                $("#save-project").addClass("save-project")
                return false
            } else {  // 변경사항이 없는경우
                $("#save-project").attr("disabled", "disabled")
                $("#save-project").removeClass("save-project")
            }
        })
        // 프로젝트 소개
        CKEDITOR.instances["projectIntro-ck"].on("instanceReady", function(){
            this.document.on("keyup", function () {
                let editedProjectIntro = CKEDITOR.instances['projectIntro-ck'].getData()
                if (loadedProjectIntro != editedProjectIntro) {
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
                <button id="save-project" onclick="submitButton()" disabled>저장하기</button>
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
                <li class="nav-select" onclick="navButton('basic')">기본정보</li>
                <li onclick="navButton('content')">내용작성</li>
                <li onclick="navButton('schedule')">일정정보</li>
                <li onclick="navButton('budget')">예산정보</li>
                <li onclick="navButton('reward')">리워드</li>
            </ul>
        </div>
    </div>
    <%--<div id="header-wrapper">
    </div>--%>
    <%-- 에디터 바디 --%>
    <div id="editor-body">
        <form action="/project/update" method="post" name="update-form">
        <input type="hidden" name="projectNo" value="${project.projectNo}">
        <div id="body-wrapper">
            <%-- 프로젝트 제목 섹션 --%>
            <section class="editor-section">
                <div class="editor-section-left">
                    <div class="info-title"><p>프로젝트 제목</p></div>
                    <div class="info-content">
                        <p>프로젝트의 주체, 창작물의 특징이 들어나는 멋진 제목을 붙여주세요.</p>
                    </div>
                </div>
                <div class="editor-section-right">
                    <p>제목</p>
                    <input type="text" name="projectTitle"
                           value="${project.projectTitle}" placeholder="프로젝트 제목을 입력해주세요">
                </div>
            </section>
            <%-- 프로젝트 카테고리 섹션 --%>
            <section class="editor-section">
                <div class="editor-section-left">
                    <div class="info-title"><p>카테고리</p></div>
                    <div class="info-content">
                        <p>프로젝트 성격과 가장 일치하는 카테고리를 선택해주세요.
                            적합하지 않을 경우 운영자에 의해 조정될 수 있습니다.</p>
                    </div>
                </div>
                <div class="editor-section-right">
                    <div>
                        <p>카테고리</p>
                        <select name="categoryId" id="select-category">
                            <c:forEach var="c" items="${cList}">
                                <option value="${c.categoryNo}">${c.categoryName}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
            </section>
            <%-- 프로젝트 소개 섹션 --%>
            <section class="editor-section">
                <div class="editor-section-left">
                    <div class="info-title"><p>프로젝트 소개</p></div>
                    <div class="info-content">
                        <p>후원자 분들이 프로젝트를 빠르게 이해할 수 있도록
                        명확하고 간락하게 소개해주세요</p>
                    </div>
                </div>
                <div class="editor-section-right">
                    <div>
                        <textarea name="projectIntro" id="projectIntro-ck" cols="30" rows="10">${project.projectIntro}</textarea>
                    </div>
                </div>
            </section>
            <%-- 프로젝트 대표이미지 섹션 --%>
            <section class="editor-section">
                <div class="editor-section-left">
                    <div class="info-title"><p>프로젝트 대표 이미지</p></div>
                    <div class="info-content">
                        <p>후원자들이 프로젝트의 내용을 쉽게 파악하고 좋은 인상을
                            받을 수 있도록 이미지 가이드라인을 따라주세요</p>
                    </div>
                </div>
                <div class="editor-section-right img-section">
                    <div>
                        <p>대표이미지 등록</p>
                        <%-- 이미지 업로드 카드 --%>
                        <div id="upload-card" style="position: relative">
                            <p><i class="fa-solid fa-upload"></i>&nbsp;이미지 업로드</p>
                            <p>하나의 이미지 업로드 가능</p>
                            <p>파일 형식: jpg 또는 png</p>
                            <p>사이즈: 가로 1,240px, 세로 930px 이상</p>
                            <span>※ 이미지를 등록 후 저장해야 반영됩니다.</span>
                            <input type="file" id="file-upload">
                        </div>
                    </div>
                    <div>
                        <%-- 이미지 업로드 결과 --%>
                        <div id="upload-result">
                            <p>등록 될 이미지</p>
                            <img src="${project.projectImage}" alt="" id="uploaded-img">
                        </div>
                    </div>
                </div>
            </section>
        </div>
        </form>
    </div>
</div>

<%-- footer --%>
<%@ include file="/WEB-INF/views/component/footer.jsp"%>