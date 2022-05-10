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
        // 불러온 데이터
        // 기본정보
        const loadProjectTitle = `${project.projectTitle}`
        const loadCategoryId = `${project.categoryId}`
        const loadProjectIntro = `${project.projectIntro}`
        const loadProjectImage = `${project.projectImage}`
        const loadProjectContent = `${project.projectContent}`
        // 예산 정보
        const loadProjectPrice = `${project.projectPrice}`
        const loadBudgetPlan = `${project.budgetPlan}`
        // 일정 정보
        const loadSchedulePlan = `${project.schedulePlan}`
        const loadOpenDate = `<fmt:formatDate value="${project.openDate}" pattern="yyyy-MM-dd"/>`
        const loadCloseDate = `<fmt:formatDate value="${project.closeDate}" pattern="yyyy-MM-dd"/>`
        const loadDeliveryDate = `<fmt:formatDate value="${project.deliveryDate}" pattern="yyyy-MM-dd"/>`

        console.log(loadProjectTitle, loadCategoryId, loadProjectIntro
            , loadProjectImage, loadProjectContent, loadProjectPrice
            , loadBudgetPlan, loadSchedulePlan, loadOpenDate
            , loadCloseDate, loadDeliveryDate )


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








        // 데이터 변경 감지
        // $("#test1").click(function () {
        //     console.log("테스트")
        //     const test = CKEDITOR.instances["projectContent-ck"].getData()
        //     console.log(test)
        // })

        $("input[name]").change(function () {

            var selectList = document.getElementById("select-category")

            console.log(loadCategoryId)
            console.log(selectList.options[selectList.selectedIndex].value)
            console.log(loadProjectImage)
            console.log($("#img-section img").prop("src"))
            console.log(loadProjectPrice)
            console.log($("input[name=projectPrice]").val())
            console.log(loadOpenDate)
            console.log($("input[name=openDate]").val())
            console.log(loadCloseDate)
            console.log($("input[name=closeDate]").val())
            console.log(loadDeliveryDate)
            console.log($("input[name=deliveryDate]").val())
            if (loadProjectTitle != $("input[name=projectTitle]").val()) {
                $("#test1").removeAttr("disabled")
                return false
            }
            $("#test1").attr("disabled", "true")
        })
        // ckeditor 내용일치 검증
        CKEDITOR.instances["projectIntro-ck"].on("instanceReady", function(){
            this.document.on("keyup", function () {
                if (loadProjectIntro != CKEDITOR.instances['projectIntro-ck'].getData()) {
                    $("#test1").removeAttr("disabled")
                    return false
                }
                $("#test1").attr("disabled", "true")
            });
        });
        CKEDITOR.instances["projectContent-ck"].on("instanceReady", function(){
            this.document.on("keyup", function () {
                if (loadProjectContent != CKEDITOR.instances['projectContent-ck'].getData()) {
                    $("#test1").removeAttr("disabled")
                    return false
                }
                $("#test1").attr("disabled", "true")
            });
        });
        CKEDITOR.instances["budgetPlan-ck"].on("instanceReady", function(){
            this.document.on("keyup", function () {
                if (loadBudgetPlan != CKEDITOR.instances['budgetPlan-ck'].getData()) {
                    $("#test1").removeAttr("disabled")
                    return false
                }
                $("#test1").attr("disabled", "true")
            });
        });
        CKEDITOR.instances["schedulePlan-ck"].on("instanceReady", function(){
            this.document.on("keyup", function () {
                if (loadSchedulePlan != CKEDITOR.instances['schedulePlan-ck'].getData()) {
                    $("#test1").removeAttr("disabled")
                    return false
                }
                $("#test1").attr("disabled", "true")
            });
        });







    })
</script>

<script>
    $(document).ready(function () {

    })
</script>

<section>
    <button id="test1" disabled="true" style="position: fixed">테스트</button>
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