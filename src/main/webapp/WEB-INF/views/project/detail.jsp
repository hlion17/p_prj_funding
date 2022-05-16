<%@ page import="java.util.Date" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%-- 오늘 날짜 --%>
<c:set var="today" value="<%=new Date()%>" />

<%-- header --%>
<%@include file="/WEB-INF/views/component/header.jsp" %>

<style>
    button {
        border: none;
        outline: none;
        border-radius: 5px;
        padding: 10px;
    }
    .wrapper {
        width: 1080px;
        margin: 50px auto 0px auto;
    }
    #project-header-wrapper {
        display: flex;
        flex-direction: column;
    }
    .project-header-row {
        display: flex;
        justify-content: center;
        margin-bottom: 20px;
    }
    #category-name {
        background: whitesmoke;
        padding: 10px 20px;
        border-radius: 10px;
    }
    .project-header-row-left {
        width: 50%;
        padding: 30px;
    }
    .project-header-row-left img {
        width: 100%;
        height: 100%;
    }
    .project-header-row-right {
        padding: 30px;
    }
    .project-header-row-right p {
        margin: 0;
    }
    .inner-row {
        margin-bottom: 10px;
    }
    #header-short1 {
        border-bottom: 1px solid #ccc;
        margin-bottom: 20px;
    }
    #header-short1 .inner-row p {
        font-weight: 400;
    }
    #header-short1 .inner-row div, #header-short1 .inner-row span {
        font-size: 20px;
        font-weight: 700;
    }
    #header-short2 {
        margin-bottom: 20px;
        font-size: 12px;
        border-bottom: 1px solid #ccc;
    }
    #header-short2 .inner-row {
        display: flex;
    }
    #header-short2 .inner-row p {
        width: 100px;
        font-weight: 700;
    }
    #header-short3 button:nth-child(2) {
        background: darkorange;
        color: white;
    }
    /* 프로젝트 네비게이션*/
    #project-nav1, #project-nav2 {
        display: flex;
        justify-content: center;
        align-items: center;
        height: 60px;
        position: sticky;
        top: 0px;
        /*box-shadow: 0px 2px 10px rgba(0, 0, 0, 0.1);*/
        z-index: 999;
        background: white;
    }
    #project-nav1 {
        border: 1px solid rgba(0, 0, 0, 0.1);
    }
    #project-nav2 {
        position: sticky;
        top: 60px;
        box-shadow: none;
        background: white;
    }
    #project-nav1 ul, #project-nav2 ul {
        display: flex;
        height: 35px;
        width: 1000px;
        padding: 0;
        margin: 0;
        list-style: none;
        font-weight: 700;
    }
    #project-nav1 li, #project-nav2 li {
        margin-right: 20px;
    }
    #project-nav2 li {
        font-size: 12px;
        background: whitesmoke;
        padding: 7px 15px;
        border-radius: 15px;
        border: 1px solid #ccc;
    }
    #project-nav2 a {
        text-decoration: none;
    }
    /* 프로젝트 본문 */
    #content-wrapper {
        display: flex;
    }
    #content-left {
        width:70%;
        padding: 0 30px;
    }
    #content-left img {
        width:100%!important;
    }
    .project-section {
        margin: 40px 0;
    }
    .project-section > p{
        font-size: 32px;
        font-weight: 700;
    }

    /* 기타 */
    #to-list {
        background: darkorange!important;
    }
    #to-list a {
        color: white;
    }
    /* 리워드 */
    #sticky-holder {
        flex-grow: 1;
    }
    #content-right {
        height: 80vh;
        font-size: 20px;
        font-weight: 700;
        position: sticky;
        top: 120px;
        overflow: auto;
        /* scroll bar hidden */
        /*-ms-overflow-style: none;  !* IE and Edge *!*/
        /*scrollbar-width: none;  !* Firefox *!*/
    }
    /* scroll bar hidden */
    /*#content-right::-webkit-scrollbar {*/
    /*    display: none;*/
    /*}*/
    .reward-card {
        width: 300px;
        box-shadow: 0px 2px 10px rgba(0, 0, 0, 0.1);
        border: 1px solid #ccc;
        padding: 10px 20px;
        margin: 10px 0;
        cursor: pointer;
    }
    .reward-card p:first-child {
        font-size: 23px;
        font-weight: 700;
    }
    .reward-card p:nth-child(2) {
        font-size: 14px;
    }
    .reward-card li {
        font-size: 12px;
        color: rgb(109, 109, 109);
        margin-bottom: 5px;
    }
</style>

<div class="wrapper">
    <%-- 프로젝트 헤더 --%>
    <div id="project-header-wrapper">
        <div class="project-header-row">
            <div id="category-name">${project.category.categoryName}</div>
        </div>
        <div class="project-header-row">
            <h1>${project.projectTitle}</h1>
        </div>
        <div class="project-header-row">
            <div class="project-header-row-left">
                <div>
                    <img src="${project.projectImage}" alt="">
                </div>
            </div>
            <div class="project-header-row-right">
                <div id="header-short1">
                    <div class="inner-row">
                        <p>모인금액</p>
                        <span><fmt:formatNumber value="${project.sum}" pattern="#,###" /></span>원
                        <span><fmt:formatNumber value="${project.sum/project.projectPrice}" type="percent" /></span>
                    </div>
                    <div class="inner-row">
                        <p>마감일</p>
                        <span><fmt:parseNumber value="${(project.closeDate.time - today.time) / (1000 * 60 * 60 * 24)}" integerOnly="true" /></span>일 남음
                    </div>
                    <div class="inner-row">
                        <p>후원자</p>
                        <span>${contributors}</span>명
                    </div>
                </div>
                <div id="header-short2">
                    <div class="inner-row">
                        <p>목표금액</p>
                        <div><fmt:formatNumber value="${project.projectPrice}" pattern="#,###" /></div>
                    </div>
                    <div class="inner-row">
                        <p>펀딩기간</p>
                        <div>
                            <fmt:formatDate value="${project.openDate}" pattern="yyyy/MM/dd"/>
                            ~
                            <fmt:formatDate value="${project.closeDate}" pattern="yyyy/MM/dd"/>
                        </div>
                    </div>
                    <div class="inner-row">
                        <p>배송예정일</p>
                        <div><fmt:formatDate value="${project.deliveryDate}" pattern="yyyy-MM-dd"/></div>
                    </div>
                </div>
                <div id="header-short3">
                    <button><i class="fa-solid fa-heart"></i></button>
                    <button onclick="location.replace('#content-right')">이 프로젝트 후원하기</button>
                </div>
            </div>
        </div>
    </div>
</div>

<%-- 프로젝트 네비게이션 --%>
<div id="project-nav1">
    <ul>
        <li>프로젝트 계획</li>
        <li>커뮤니티</li>
    </ul>
</div>
<div id="project-nav2">
    <ul>
        <li onclick="location.replace('#project-intro')">소개</li>
        <li onclick="location.replace('#project-content')">내용</li>
        <li onclick="location.replace('#project-budget')">일정</li>
        <li onclick="location.replace('#project-schedule')">예산</li>
        <li onclick="location.href='/project/list'" id="to-list">목록으로</li>
    </ul>
</div>

<%-- 프로젝트 본문 --%>
<div class="wrapper">
    <div id="content-wrapper">
        <div id="content-left">
            <div id="project-intro" class="project-section">
                <p>#프로젝트 소개</p>
                <div>${project.projectIntro}</div>
            </div>
            <div id="project-content" class="project-section">
                <p>#프로젝트 내용</p>
                <div>${project.projectContent}</div>
            </div>
            <div id="project-schedule" class="project-section">
                <p>#프로젝트 일정</p>
                <div>${project.schedulePlan}</div>
            </div>
            <div id="project-budget" class="project-section">
                <p>#프로젝트 예산</p>
                <div>${project.budgetPlan}</div>
            </div>
        </div>
        <div id="sticky-holder">
            <div id="content-right">
                <p>선물선택</p>
                <c:forEach var="r" items="${rewards}">
                <div class="reward-card">
                    <p><fmt:formatNumber value="${r.rewardPrice}" pattern="#,###" /></p>
                    <p>${r.rewardIntro}</p>
                    <ul>
                        <c:forEach var="c" items="${r.rewardContent.split(';')}">
                        <li>${c}</li>
                        </c:forEach>
                    </ul>
                </div>
                </c:forEach>
            </div>
        </div>
    </div>
</div>

<%-- footer --%>
<%@include file="/WEB-INF/views/component/footer.jsp" %>
