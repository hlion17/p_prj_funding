<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script>
    stepColor()
</script>

<c:forEach var="m" items="${map}">
    <div class="step-area">
        <div class="step-name">${m.value.get(0).stepName} <span>${m.value.size()}</span></div>
        <c:forEach var="p" items="${m.value}">
            <div class="myProject-box">
                <div class="myProject-content-area">
                    <div class="img-box">
                        <img src="${p.projectImage}" alt="">
                    </div>
                    <div class="content-box">
                        <div>${p.stepName}</div>
                        <div>${p.projectTitle}</div>
                        <div>${p.projectIntro}</div>
                    </div>
                </div>
                <div class="myProject-button-area">
                    <c:if test="${p.projectStep eq 0}">
                        <div class="btn-manage" data-projectNo="${p.projectNo}" data-projectStep="${p.projectStep}" onclick="manage(this)">관리</div>
                    </c:if>
                    <c:if test="${p.projectStep eq 3 || p.projectStep eq 4}">
                        <div class="btn-manage" data-projectNo="${p.projectNo}" data-projectStep="${p.projectStep}" onclick="manage(this)">바로가기</div>
                    </c:if>
                    <c:if test="${p.projectStep eq 0 || p.projectStep eq 2}">
                        <div class="btn-delete" data-projectNo="${p.projectNo}" data-projectTitle="${p.projectTitle}" onclick="del(this)">삭제</div>
                    </c:if>
                </div>
            </div>
        </c:forEach>
        <hr>
    </div>
</c:forEach>
