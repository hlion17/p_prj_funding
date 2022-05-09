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

<section>
    <div class="container">
        <form action="/project/update" method="post">
        <input type="hidden" name="projectNo" value="${project.projectNo}">
        <div class="editor-section">
            <h2>프로젝트 이름</h2>
            <input type="text" name="projectTitle" value="${project.projectTitle}" style="width: 100%">
        </div>
        <div class="editor-section">
            <h2>프로젝트 카테고리</h2>
            <select name="categoryId" id="select-category">
                <c:forEach var="c" items="${cList}">
                <option value="${c.categoryNo}">${c.categoryName}</option>
                </c:forEach>
            </select>
        </div>
        <div class="editor-section">
            <h2>프로젝트 소개</h2>
            <textarea name="projectIntro" id="projectIntro-ck" cols="30" rows="10">${project.projectIntro}</textarea>
        </div>
        <div class="editor-section" id="img-section">
            <h2>프로젝트 대표 이미지 등록</h2>
            <input type="file" id="file-upload">
            <img src="${project.projectImage}" alt="" id="upload-img">
        </div>
        <div class="editor-section">
            <h2>프로젝트 내용</h2>
            <textarea name="projectContent" id="projectContent-ck" cols="30" rows="10">${project.projectContent}</textarea>
        </div>

        <hr />

        <div class="editor-section">
            <h2>프로젝트 목표 금액</h2>
            <input type="text" name="projectPrice" value="${project.projectPrice}">
        </div>
        <div>
            <h2>프로젝트 예산</h2>
            <textarea name="budgetPlan" id="budgetPlan-ck" cols="30" rows="10">${project.budgetPlan}</textarea>
        </div>

        <hr />

        <div class="editor-section">
            <h2>프로젝트 일정</h2>
            <textarea name="schedulePlan" id="schedulePlan-ck" cols="30" rows="10">${project.schedulePlan}</textarea>
        </div>
        <div class="editor-section">
            <h2>오픈 예정일</h2>
            <input type="date" name="openDate"
                   value="<fmt:formatDate value="${project.openDate}" pattern="yyyy-MM-dd"/>">
        </div>
        <div class="editor-section">
            <h2>종료 예정일</h2>
            <input type="date" name="closeDate"
                   value="<fmt:formatDate value="${project.closeDate}" pattern="yyyy-MM-dd"/>">
        </div>
        <div class="editor-section">
            <h2>배송 예정일</h2>
            <input type="date" name="deliveryDate"
                   value="<fmt:formatDate value="${project.deliveryDate}" pattern="yyyy-MM-dd"/>">
        </div>
        <div class="editor-section">
            <button type="submit">제출</button>
            <button onclick="history.back()">취소</button>
        </div>
        </form>
    </div>
</section>

<script>
    CKEDITOR.replace("projectIntro-ck");
    CKEDITOR.replace("projectContent-ck", {
        filebrowserUploadUrl: '/ck/upload'
    });
    CKEDITOR.replace("budgetPlan-ck");
    CKEDITOR.replace("schedulePlan-ck");
</script>

<%-- footer --%>
<%@include file="/WEB-INF/views/component/footer.jsp"%>