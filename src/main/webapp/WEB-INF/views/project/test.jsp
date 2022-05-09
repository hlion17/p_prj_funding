<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="C" uri="http://java.sun.com/jsp/jstl/core" %>

<%-- header --%>
<%@include file="/WEB-INF/views/component/header.jsp"%>

<div class="container">
<C:forEach var="p" items="${list}">
<ul>
<c:if test="${p.projectStep eq 0}">
<li>${p.projectTitle}</li>
<button class="btn-del" data-projectNo="${p.projectNo}">삭제</button>
</c:if>
</ul>
</C:forEach>
</div>

<script>
    $(".btn-del").click(function () {
            const projectNo = $(this).attr("data-projectNo")
            console.log(projectNo)
            sendDataByPost("/project/delete", {projectNo: projectNo})
        })
</script>

<%-- footer --%>
<%@include file="/WEB-INF/views/component/footer.jsp"%>
