<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%-- header --%>
<%@include file="/WEB-INF/views/component/header.jsp"%>

<script>
    $(document).ready(function () {
        $("#btn-detail").click(function () {
            location.href="/member/edit"
        })
    })
</script>

<main>
    <div class="container my-5">
        <div class="mb-3 row">
            <label for="memberNo" class="col-sm-2 col-form-label">회원번호</label>
            <div class="col-sm-3">
                <input type="text" readonly class="form-control-plaintext" id="memberNo" value="${member.memberNo}">
            </div>
        </div>
        <div class="mb-3 row">
            <label for="id" class="col-sm-2 col-form-label">아이디</label>
            <div class="col-sm-3">
                <input type="text" readonly class="form-control-plaintext" id="id" value="${member.id}">
            </div>
        </div>
        <div class="mb-3 row">
            <label for="nick" class="col-sm-2 col-form-label">닉네임</label>
            <div class="col-sm-3">
                <input type="text" readonly class="form-control-plaintext" id="nick" value="${member.nick}">
            </div>
        </div>
        <div class="mb-3 row">
            <label for="email" class="col-sm-2 col-form-label">이메일</label>
            <div class="col-sm-3">
                <input type="text" readonly class="form-control-plaintext" id="email" value="${member.email}">
            </div>
        </div>
        <div class="mb-3 row">
            <label for="grade" class="col-sm-2 col-form-label">상태</label>
            <div class="col-sm-3">
                <input type="text" readonly class="form-control-plaintext" id="grade" value="${member.grade}">
            </div>
        </div>
        <div class="mb-3 row">
            <label for="phone" class="col-sm-2 col-form-label">핸드폰</label>
            <div class="col-sm-3">
                <input type="text" readonly class="form-control-plaintext" id="phone" value="${member.phone}">
            </div>
        </div>
        <div class="row justify-content-center">
            <button type="button" class="btn btn-outline-primary col-auto" id="btn-detail">수정하기</button>
        </div>
    </div>
</main>

<%-- footer --%>
<%@include file="/WEB-INF/views/component/footer.jsp"%>
