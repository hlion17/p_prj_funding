<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@include file="/WEB-INF/views/component/header.jsp"%>

<script>
    // 빈칸 검증
    function emptyCheck(val, msg) {
        if (val == "") {
            alert(msg + " 입력해주세요")
            return false
        }
        return true
    }

    $(document).ready(function() {
        // 회원가입 정보 제출
        $("#btn-login").click(function() {
            const id = $("input[name=id]").val()
            const pw = $("input[name=pw]").val()

            if (!emptyCheck(id, "아이디")) return false
            if (!emptyCheck(pw, "비밀번호")) return false

            $.ajax({
                type: "POST",
                url: "/member/login",
                dataType: "JSON",
                data: {
                    id: id,
                    pw: pw
                },
                success: function(res) {
                    console.log(res)
                    if (res.msg != undefined) {
                        alert(res.msg)
                        return false;
                    }
                    if (res.dest != undefined) {
                        location.href = res.dest;
                    } else {
                        location.href = '/';
                    }
                },
                error: function(request, status, error) {
                    // alert("code: " + request.status + "\n" +
                    //     "Message: " + request.respenseJSON.message)
                }
            })


        })
    })
</script>

<main>

    <div class="container-md my-5" style="max-width: 300px;">

        <div class="mb-3">
            <label for="id" class="form-label">아이디</label>
            <input type="text" class="form-control" id="id" name="id" placeholder="아이디를 입력해주세요" required>
        </div>
        <div class="mb-3">
            <label for="pw" class="form-label">비밀번호</label>
            <input type="password" class="form-control" id="pw" name="pw" placeholder="비밀번호를 입력해주세요" required>
        </div>

        <div class="row justify-content-center my-5">
            <button class="btn btn-primary col-auto mx-3" id="btn-login">로그인</button>
            <button class="btn btn-danger col-auto mx-3" onclick="history.back()">취소</button>
        </div>

    </div>

</main>

<%@include file="/WEB-INF/views/component/footer.jsp"%>