<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%-- header --%>
<%@include file="/WEB-INF/views/component/header.jsp"%>

<style>
    /* 마감된 프로젝트 표시 */
    #project-status {
        color: white;
        background: red;
        padding: 3px 5px;
        font-weight: 700;
        font-size: 12px;
        border-radius: 15px;
    }

    #category-section .container {
        overflow: auto;
    }

    #category-section .row {
        flex-flow: nowrap;
    }
</style>

<style>
    .project-card {
        width: 250px;
        margin: 0 20px;
    }
    .section-img {
        padding: 3px;
        height: 250px;
    }
    .section-img img {
        width: 100%;
        object-fit: cover;
    }
    .section-body {
        display: flex;
        flex-direction: column;
        /*justify-content: space-around;*/
        min-height: 220px;
    }
    [class^="body"] {
        margin: 5px 0;
    }
    .section-body .body-title {
        margin: 5px 0;
        font-size: 14px;
        height: 40px;
        overflow: hidden;
    }
    .section-body .body-intro {
        font-size: 12px;
        height: 60px;
        overflow: hidden;
    }
    .section-body .body-progress {
        flex-grow: 1;
    }
    .section-body .body-progress progress {
        width: 100%;
    }
    .section-body .body-etc {
        flex-grow: 1;
    }
    .section-body .body-etc span {
        font-size: 12px;
    }
    .section-body .body-etc div:nth-child(2) {
        display: flex;
        justify-content: flex-end;
    }
    .section-body .body-etc div:first-child > span:first-child{
        color: red;
    }
    #category-nav img {
        padding: 10px;
    }
</style>

<style>
    main > div {
        width: 1080px;
        margin: 0 auto;
    }
    #likeProject-header {
        border-bottom: 1px solid rgba(0, 0, 0, 0.04);
        padding: 20px 0;
    }
    #likeProject-nav {
        margin-top: 20px;
    }
    #likeProject-nav ul {
        list-style: none;
        display: flex;
        margin: 0;
        padding: 0;
    }
    #likeProject-nav li {
        display: flex;
        flex-direction: column;
        padding: 10px;
        margin-right: 10px;
    }
    .active-nav {
        border-bottom: 2px solid darkorange;
    }
    /* 관심 프로젝트 목록 */
    #likeProject-wrapper {
        display: flex;
        margin-top: 20px;
        padding: 20px 0;
    }

</style>

<main>
    <div id="likeProject-header">
        <div>
            <h1>관심 프로젝트</h1>
        </div>
        <div id="likeProject-nav">
            <ul>
                <li class="active-nav">좋아한</li>
                <li style="text-decoration: line-through">알림신청</li>
            </ul>
        </div>
    </div>
    <div id="likeProject-wrapper">
        <c:forEach var="p" items="${list}">
        <div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 g-3">
                <%-- 테스트 영역 --%>
                <div class="col project-card" data-projectNo="${p.projectNo}">
                    <!-- 카드 영역 -->
                    <div class="card shadow-sm card-section">
                        <!-- 카드 이미지 -->
                        <div class="section-img">
                            <c:if test="${not empty p.projectImage}">
                                <img src="${p.projectImage }" alt="준비중" style="min-height: 250px;">
                            </c:if>
                            <c:if test="${empty p.projectImage }">
                                <img src="/resources/img/altImg.png" style="min-height: 250px;">
                            </c:if>
                        </div>
                        <!-- 카드 몸통 -->
                        <div class="card-body section-body">
                            <!-- 타이틀 영역 -->
                            <div class="body-title">
                                <strong>${p.projectTitle}</strong>
                                <c:if test="${p.projectStep eq 4}">
                                    <span id="project-status">마감</span>
                                </c:if>
                            </div>
                            <!-- 인트로 영역 -->
                            <div class="body-intro">
                                <p class="card-text">${p.projectIntro}</p>
                            </div>
                            <!-- 진행바 영역 -->
                            <div class="body-progress">
                                <progress value="${p.sum}" max="${p.projectPrice}"></progress>
                            </div>
                            <!-- 기타 정보 영역 -->
                            <div class="row fs-6 body-etc">
                                <div class="col d-flex">
                                    <span style="margin-right: 10px;"><fmt:formatNumber value="${p.sum / p.projectPrice}" type="percent"/></span>
                                    <span><fmt:formatNumber value="${p.projectPrice}" pattern="#,###"/></span>
                                </div>
                                <div class="col text-end">
                                    <span><fmt:formatDate value="${p.closeDate}" pattern="yy/MM/dd"/></span>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
        </div>
        </c:forEach>
    </div>
</main>

<%-- footer --%>
<%@include file="/WEB-INF/views/component/footer.jsp"%>