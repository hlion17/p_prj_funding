<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%-- header --%>
<%@include file="/WEB-INF/views/component/header.jsp"%>

<style>
    main {
        display: flex;
        flex-direction: column;
        justify-content: center;
    }
    main > div {
        width: 1080px;
        margin: 0 auto;
        padding: 20px 0;
    }
    #support-header {
        border-bottom: 1px solid #ccc;
    }
    #support-header > div:nth-child(2) {
        display: flex;
        justify-content: space-between;
        align-items: center;
        margin-top: 20px;
    }
    /* 후원 리스트 */
    #support-list-wrapper {

    }
    .support-list-box {
        display: flex;
        justify-content: space-between;
        width: 100%;
        height: 130px;
        padding: 10px;
        margin-bottom: 20px;
        border: 1px solid #ccc;
    }
    .support-box-content-area {
        display: flex;
    }
    .support-box-img {
        width: 160px;
        height: 110px;
        margin-right: 10px
    }
    .support-box-img > img{
        width: 100%;
        height: 100%;
    }
    .support-box-content {
        margin-right: 10px;
    }
    .support-box-content > div:nth-child(1){
        font-size: 12px;
        color: rgb(109, 109, 109);
    }
    .support-box-content > div:nth-child(2){
        font-size: 18px;
        font-weight: 700;
    }
    .support-box-content > div:nth-child(3){
        font-size: 14px;
        color: rgb(109, 109, 109);
    }
    .support-box-content > div:nth-child(4){
        font-size: 12px;
    }
    .support-box-button {
        display: flex;
        justify-content: center;
        align-items: center;
        padding: 20px;
    }
    .support-box-button > div {
        padding: 10px;
        border: 1px solid darkorange;
        color: darkorange;
        background: rgba(255, 140, 0, 0.1);
        font-weight: 700;
    }

    #support-searchbar {
        position: relative;
    }
    #support-searchbar > i {
        position: absolute;
        top: 7px;
        right: 10px;
    }
</style>

<script>
    $(document).ready(function () {
        // 후원 프로젝트 검색
        $("#search-btn").keydown(function (e) {
            if (e.keyCode == 13) {
                const keyword = $(this).val()
                location.href="/myPage/support?keyword="+keyword
            }
        })
        // 검색어 클리어
        $("#support-searchbar > i").click(function () {
            location.href="/myPage/support"
        })
        // 프로젝트 이동
        $(".support-box-button").click(function () {
            const projectNo = $(this).attr("data-projectNo")
            location.href = "/project/" + projectNo
        })
    })
</script>

<main>
    <%-- 후원목록 페이지 헤더 --%>
    <div id="support-header">
        <div>
            <h1>후원 현황</h1>
        </div>
        <div>
            <div>
                <span>${list.size()}</span>
                개의 후원 프로젝트가 있습니다.
            </div>
            <div id="support-searchbar">
                <input id="search-btn" type="text" placeholder="프로젝트 검색" value="${keyword}">
                <c:if test="${keyword ne null && keyword ne ''}">
                <i class="fa-solid fa-xmark"></i>
                </c:if>
            </div>
        </div>
    </div>
    <%-- 후원목록 --%>
    <div id="support-list-wrapper">
        <c:forEach var="p" items="${list}">
        <div class="support-list-box">
            <div class="support-box-content-area">
                <div class="support-box-img">
                    <img src="${p.get("projectImage")}" alt="이미지 없음">
                </div>
                <div class="support-box-content">
                    <div>결제일 <fmt:formatDate value="${p.get('paymentDay')}" pattern="yyyy.MM.dd"/></div>
                    <div>${p.get("projectTitle")}</div>
                    <div>${p.get("rewardIntro")}</div>
                    <div>${p.get("paymentTotal")}원 결제 완료</div>
                </div>
            </div>
            <div class="support-box-button" data-projectNo="${p.get("projectNo")}">
                <div>후기 작성하기</div>
            </div>
        </div>
        </c:forEach>
    </div>
</main>


<%-- footer --%>
<%@include file="/WEB-INF/views/component/footer.jsp"%>
