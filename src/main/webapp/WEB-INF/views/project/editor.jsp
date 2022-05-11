<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%-- header --%>
<%@include file="/WEB-INF/views/component/header.jsp"%>

<style>
    div {
        border: 1px solid black;
    }
    #editor-project {
        width: 70%;
        margin: 0 auto;
    }
    .editor-section {
        margin: 20px 0;
    }
    #img-section img {
        width: 250px;
        height: 200px;
    }
</style>
<script src="https://code.jquery.com/jquery-2.2.4.min.js"></script>
<script src="/resources/ckeditor_standard/ckeditor.js"></script>

<script>
    $(document).ready(function() {
        // 작성중인 프로젝트 카테고리 선택
        const select = document.querySelector("#select-category")
        for (i = 0; select.length; i++) {
            if (select.options[i].value == '${project.categoryId}') {
                select.options[i].selected = true
                break
            }
        }
        // 대표사진 등록
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
                    console.log("성공")
                    console.log(res)

                    $("#upload-img").removeAttr("src")
                    $("#upload-img").attr("src", res.fileUrl)
                    const input = $("<input type='hidden' name='projectImage'>")

                    input.attr("value", res.fileUrl)
                    $("#img-section").append(input)
                }
                , error: function() {
                    console.log("ajax 실패")
                }
            })
        })

    })
</script>

<%-- 에디터 내용 변경 감지 --%>
<script>
$(document).ready(function () {
    // 이전 작성 내용 상수값으로 기억
    // 기본정보
    const loadedProjectTitle = `${project.projectTitle}`
    const loadedCategoryId = `${project.categoryId}`
    const loadedProjectIntro = `${project.projectIntro}`
    const loadedProjectImage = `http://${pageContext.request.getHeader("host")}${project.projectImage}`
    // 프로젝트 내용
    const loadedProjectContent = `${project.projectContent}`
    // 예산 정보
    const loadedProjectPrice = `${project.projectPrice}`
    const loadedBudgetPlan = `${project.budgetPlan}`
    // 일정 정보
    const loadedSchedulePlan = `${project.schedulePlan}`
    const loadedOpenDate = `<fmt:formatDate value="${project.openDate}" pattern="yyyy-MM-dd"/>`
    const loadedCloseDate = `<fmt:formatDate value="${project.closeDate}" pattern="yyyy-MM-dd"/>`
    const loadedDeliveryDate = `<fmt:formatDate value="${project.deliveryDate}" pattern="yyyy-MM-dd"/>`
    // name 속성이 있는 모든 요소에 변경감지 이벤트 부여
    // ** input 태그 type=text 에 onchange 이벤트가 걸리지 않았던 이유는
    // ** input 태그 type=text 에 blur 된 이후 onchange 이벤트가 걸리기 때문이라고 한다.
    // ** [참고]: https://karismamun.tistory.com/66
    $("[name], #upload-img").on("change keyup load", function () {
        // 변경된 작성 내용
        // 기본정보
        let editedProjectTitle = $("input[name=projectTitle]").val()
        let editedCategoryId = $("#select-category option:selected").val()
        let editedProjectImage = $("#img-section img").prop("src")
        // 예산 정보
        let editedProjectPrice = $("input[name=projectPrice]").val()
        // 일정 정보
        let editedOpenDate = $("input[name=openDate]").val()
        let editedCloseDate = $("input[name=closeDate]").val()
        let editedDeliveryDate = $("input[name=deliveryDate]").val()
        // 불러온 내용에서 변경된 사항이 있는 경우
        if (loadedProjectTitle != editedProjectTitle
            || loadedCategoryId != editedCategoryId
            || loadedProjectImage != editedProjectImage
            || loadedProjectPrice != editedProjectPrice
            || loadedOpenDate != editedOpenDate
            || loadedCloseDate != editedCloseDate
            || loadedDeliveryDate != editedDeliveryDate) {

            $("#save-project").removeAttr("disabled")
            return false
        } else {
            $("#save-project").attr("disabled", "true")
        }
    })
    // ckeditor 내용일치 검증
    // 프로젝트 소개
    CKEDITOR.instances["projectIntro-ck"].on("instanceReady", function(){
        this.document.on("keyup", function () {
            let editedProjectIntro = CKEDITOR.instances['projectIntro-ck'].getData()
            if (loadedProjectIntro != editedProjectIntro) {
                $("#save-project").removeAttr("disabled")
                return false
            }
            $("#save-project").attr("disabled", "true")
        });
    });
    // 프로젝트 내용
    CKEDITOR.instances["projectContent-ck"].on("instanceReady", function(){
        this.document.on("keyup", function () {
            let editedProjectContent = CKEDITOR.instances['projectContent-ck'].getData()
            if (loadedProjectContent != editedProjectContent) {
                $("#save-project").removeAttr("disabled")
                return false
            }
            $("#save-project").attr("disabled", "true")
        });
    });
    // 프로젝트 예산
    CKEDITOR.instances["budgetPlan-ck"].on("instanceReady", function(){
        this.document.on("keyup", function () {
            let editedBudgetPlan = CKEDITOR.instances['budgetPlan-ck'].getData()
            if (loadedBudgetPlan != editedBudgetPlan) {
                $("#save-project").removeAttr("disabled")
                return false
            }
            $("#save-project").attr("disabled", "true")
        });
    });
    // 프로젝트 일정
    CKEDITOR.instances["schedulePlan-ck"].on("instanceReady", function(){
        this.document.on("keyup", function () {
            let editedSchedulePlan = CKEDITOR.instances['schedulePlan-ck'].getData()
            if (loadedSchedulePlan != editedSchedulePlan) {
                $("#save-project").removeAttr("disabled")
                return false
            }
            $("#save-project").attr("disabled", "true")
        });
    });
})

</script>

<section>
    <button id="save-project" disabled="true">테스트</button>
    <button onclick="location.href='/editor/test'">기본정보 페이지 이동</button>
    <div class="container">
        <form action="/project/update" method="post">
            <%-- 프로젝트 식별값 --%>
            <input type="hidden" name="projectNo" value="${project.projectNo}">
            <%-- 프로젝트 이름 --%>
            <div class="editor-section">
                <h2>프로젝트 이름</h2>
                <input type="text" name="projectTitle" value="${project.projectTitle}" style="width: 100%">
            </div>
            <%-- 프로젝트 카테고리 --%>
            <div class="editor-section">
                <h2>프로젝트 카테고리</h2>
                <select name="categoryId" id="select-category">
                    <c:forEach var="c" items="${cList}">
                    <option value="${c.categoryNo}">${c.categoryName}</option>
                    </c:forEach>
                </select>
            </div>
            <%-- 프로젝트 소개 --%>
            <div class="editor-section">
                <h2>프로젝트 소개</h2>
                <textarea name="projectIntro" id="projectIntro-ck" cols="30" rows="10">${project.projectIntro}</textarea>
            </div>
            <%-- 프로젝트 대표 이미지 --%>
            <div class="editor-section" id="img-section">
                <h2>프로젝트 대표 이미지 등록</h2>
                <input type="file" id="file-upload">
                <img src="${project.projectImage}" alt="" id="upload-img">
            </div>
            <%-- 프로젝트 내용--%>
            <div class="editor-section">
                <h2>프로젝트 내용</h2>
                <textarea name="projectContent" id="projectContent-ck" cols="30" rows="10">${project.projectContent}</textarea>
            </div>

            <%-- 예산 정보 섹션 --%>
            <hr />
            <%-- 프로젝트 목표금액 --%>
            <div class="editor-section">
                <h2>프로젝트 목표 금액</h2>
                <input type="text" name="projectPrice" value="${project.projectPrice}">
            </div>
            <div>
                <h2>프로젝트 예산</h2>
                <textarea name="budgetPlan" id="budgetPlan-ck" cols="30" rows="10">${project.budgetPlan}</textarea>
            </div>

            <%-- 일정 정보 섹션 --%>
            <hr />
            <%-- 프로젝트 일정 --%>
            <div class="editor-section">
                <h2>프로젝트 일정</h2>
                <textarea name="schedulePlan" id="schedulePlan-ck" cols="30" rows="10">${project.schedulePlan}</textarea>
            </div>
            <%-- 오픈 예정일 --%>
            <div class="editor-section">
                <h2>오픈 예정일</h2>
                <input type="date" name="openDate"
                       value="<fmt:formatDate value="${project.openDate}" pattern="yyyy-MM-dd"/>">
            </div>
            <%-- 종료 예정일 --%>
            <div class="editor-section">
                <h2>종료 예정일</h2>
                <input type="date" name="closeDate"
                       value="<fmt:formatDate value="${project.closeDate}" pattern="yyyy-MM-dd"/>">
            </div>
            <%-- 배송 예정일 --%>
            <div class="editor-section">
                <h2>배송 예정일</h2>
                <input type="date" name="deliveryDate"
                       value="<fmt:formatDate value="${project.deliveryDate}" pattern="yyyy-MM-dd"/>">
            </div>
            <%-- 제출 버튼 --%>
            <div class="editor-section">
                <button type="submit">제출</button>
                <button onclick="history.back()">취소</button>
            </div>
        </form>
    </div>
</section>


<script>
    // ck 에디터 적용
    CKEDITOR.replace("projectIntro-ck");
    CKEDITOR.replace("projectContent-ck", {
        filebrowserUploadUrl: '/ck/upload'
    });
    CKEDITOR.replace("budgetPlan-ck");
    CKEDITOR.replace("schedulePlan-ck");
</script>

<%-- footer --%>
<%@include file="/WEB-INF/views/component/footer.jsp"%>