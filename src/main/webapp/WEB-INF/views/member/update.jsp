<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%-- header --%>
<%@include file="/WEB-INF/views/component/header.jsp"%>

<script>
    $(document).ready(function () {
        $("#btn-detail").click(function () {
            const nick = $("input[name=nick]").val();
            const phone = $("input[name=phone]").val();
            const email = $("input[name=email]").val();

            sendDataByPost("/member/update", {memberNo: '${member.memberNo}', id: '${member.id}', nick: nick, phone: phone, email: email})
        })
    })
</script>

<main>
    <div class="container my-5">
        <div class="mb-3 row">
            <label for="nick" class="col-sm-2 col-form-label">닉네임</label>
            <div class="col-sm-3">
                <input type="text"  class="form-control-plaintext" id="nick" name="nick" value="${member.nick}">
            </div>
        </div>
        <div class="mb-3 row">
            <label for="email" class="col-sm-2 col-form-label">이메일</label>
            <div class="col-sm-3">
                <input type="text"  class="form-control-plaintext" id="email" name="email" value="${member.email}">
            </div>
        </div>
        <div class="mb-3 row">
            <label for="phone" class="col-sm-2 col-form-label">핸드폰</label>
            <div class="col-sm-3">
                <input type="text" class="form-control-plaintext" id="phone" name="phone" value="${member.phone}">
            </div>
        </div>
        <div class="row justify-content-center">
            <button type="button" class="btn btn-outline-primary col-auto" id="btn-detail">수정하기</button>
            <button type="button" class="btn btn-outline-danger col-auto" onclick="history.back()">취소</button>
        </div>
    </div>
</main>

<%-- footer --%>
<%@include file="/WEB-INF/views/component/footer.jsp"%>
