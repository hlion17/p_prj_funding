<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%-- header --%>
<%@include file="/WEB-INF/views/component/header.jsp"%>

<main>

    <div class="container mt-5 mx-auto" style="width: 600px">
        <div class="row">
            <label for="id" class="col-4 col-form-label">아이디</label>
            <div class="col-8">
                <input type="text" id="id" name="id">
            </div>
        </div>

        <div class="row my-3">
            <label for="nick" class="col-4 col-form-label">닉네임</label>
            <div class="col-8">
                <input type="text" id="nick" name="nick">
            </div>
        </div>

        <div class="row  my-3">
            <label for="pw" class="col-4 col-form-label">비밀번호</label>
            <div class="col-8">
                <input type="password" id="pw" name="pw">
            </div>
        </div>

        <div class="row  mt-3">
            <label for="pwch" class="col-4 col-form-label">비밀번호 확인</label>
            <div class="col-auto">
                <input type="password" id="pwch">
            </div>

        </div>

        <div class="row">
            <div class="col-4"></div>
            <div class="col-8">
                <span style="color: red;">비밀번호 확인 메시지</span>
            </div>
        </div>

        <div class="row  my-3">
            <label for="member-grade" class="col-4 col-form-label">회원구분</label>
            <div class="col-auto" id="member-grade">
                <div class="form-check">
                    <input class="form-check-input" type="radio" name="grade" value="0" id="radio1" checked>
                    <label class="form-check-label" for="radio1">
                        일반회원
                    </label>
                </div>
                <div class="form-check">
                    <input class="form-check-input" type="radio" name="grade" value="1" id="radio2">
                    <label class="form-check-label" for="radio2">
                        사업자
                    </label>
                </div>
            </div>
        </div>

        <div class="row d-flex justify-content-center mt-5">
            <button type="button" class="btn btn-primary col-2 me-3">제출</button>
            <button type="button" class="btn btn-danger col-2">취소</button>
        </div>
    </div>
</main>

<%-- footer --%>
<%@include file="/WEB-INF/views/component/footer.jsp"%>
