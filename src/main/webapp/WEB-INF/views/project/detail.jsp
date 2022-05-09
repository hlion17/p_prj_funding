<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%-- header --%>
<%@include file="/WEB-INF/views/component/header.jsp" %>


<style>
    img {
        max-width: 100%;
    }
</style>

<div class="container">
    <div class="p-content-section">
        <h2>프로젝트 제목</h2>
        <hr>
        <p>${project.projectTitle}</p>
    </div>
    <div class="p-content-section">
        <h2>카테고리</h2>
        <hr>
        <p>
            (categoryNo - categoryId 이름 차이로 가져오지 못함)
            ${project.categoryId}
        </p>
    </div>
    <div class="p-content-section">
        <h2>대표사진</h2>
        <hr>
        <p>
            <img src="${project.projectImage}" alt="">
        </p>
    </div>
    <div class="p-content-section">
        <h2>소개글</h2>
        <hr>
        <p>${project.projectIntro}</p>
    </div>
    <div class="p-content-section">
        <h2>프로젝트 예산 계획</h2>
        <hr>
        <p>${project.budgetPlan}</p>
    </div>
    <div class="p-content-section">
        <h2>프로젝트 일정</h2>
        <hr>
        <p>${project.schedulePlan}</p>
    </div>
    <div class="p-content-section">
        <h2>목표금액</h2>
        <hr>
        <p>${project.projectPrice}</p>
    </div>
    <div class="p-content-section">
        <h2>프로젝트 오픈일</h2>
        <hr>
        <p>
            <fmt:formatDate value="${project.openDate}" pattern="yyyy-MM-dd"/>
        </p>
    </div>
    <div class="p-content-section">
        <h2>프로젝트 마감일</h2>
        <hr>
        <p>
            <fmt:formatDate value="${project.closeDate}" pattern="yyyy-MM-dd"/>
        </p>
    </div>
    <div class="p-content-section">
        <h2>예상 배송시작일</h2>
        <hr>
        <p>
            <fmt:formatDate value="${project.deliveryDate}" pattern="yyyy-MM-dd"/>
        </p>
    </div>
    <div class="p-content-section">
        <h2>소개글 내용(이미지 하드코딩)</h2>
        <hr>
        <p>
            ${project.projectContent}
        </p>
    </div>
    <div class="p-content-section">
        <div class="row text-center">
            <button class="btn btn-success" onclick="location.href='/project/list'">목록으로</button>
        </div>
    </div>
</div>


<%-- footer --%>
<%@include file="/WEB-INF/views/component/footer.jsp" %>
