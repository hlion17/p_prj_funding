<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%-- header --%>
<%@include file="/WEB-INF/views/component/header.jsp"%>

<script>
    // POST 방식으로 데이터 보내는 함수
    function sendDataByPost(path, parameters, method = "post") {
        const form = document.createElement("form")
        form.method = method
        form.action = path
        document.body.appenChild(form)

        for (const key in parameters) {
            const formField = document.createElement("input")
            formField.type = "hidden"
            formField.name = key
            formField.value = parameters[key]

            form.appenChild(formField)
        }
        form.submit()
    }
    // 빈칸 검증
    function emptyCheck(val, msg) {
        if (val == "") {
            alert(msg + " 입력해주세요")
            return false
        }
        return true
    }
    // 아이디 검증
    function validateId(id) {
        if (!emptyCheck(id, '아이디를')) return false
        if (!/^[a-zA-Z0-9]{4,}$/.test(id)) {
            alert("아이디는 4자 이상 영어 대소문자와 숫자로만 입력해주세요")
            return false
        }
        return true
    }
    // 비밀번호 검증
    function validatePw(pw, pwch) {
        if (!emptyCheck(pw, "비밀번호를")) return false
        if (!/^[a-zA-Z0-9]{4,}$/.test(pw)) {
            alert("비밀번호는 4자 이상의 영어 대소문자와 숫자로만 입력해주세요")
            return false
        }
        if (pw != pwch) {
            alert("비밀번호 확인이 동일하지 않습니다.")
            return false
        }
        return true
    }


    $(document).ready(function() {
        // 폼데이터 검증 후 회원가입 정보 제출
        $("#btnJoin").click(function() {
            const id = $("input[name=id]").val()
            const pw = $("input[name=pw]").val()
            const name = $("input[name=name]").val()
            const nick = $("input[name=nick]").val()
            const email = $("input[name=email]").val()
            const grade = $("input[name=grade]:checked").val()
            const pwch = $("#pwch").val()

            if (!validateId(id)) return false
            if (!validatePw(pw, pwch)) return false
            if (!emptyCheck(nick, "닉네임을")) return false
            if (!emptyCheck(name, "이름을")) return false
            if (!emptyCheck(email, "이메일을")) return false

            // sendDataByPost("/member/join", {id: id, pw: pw, nick: nick, grade: grade})
            $.ajax({
                type: "POST"
                , url: "/member/join"
                , dataType: "json"
                , data: {id: id, pw: pw, name: name, nick: nick, email: email, grade: grade}
                , success: function(res) {
                    if (res.result) {
                        alert("가입되셨습니다.")
                        location.href = "/member/login"
                    } else {
                        alert("회원가입에 실패했습니다.")
                    }
                }
                , error: function(request, status, error) {
                    alert("code: " + request.status + "\n"
                        +"message: " + request.responseJSON.message);
                }
            })
        })
        // 비밀번호 확인 html 메시지 출력
        $("#pwch").keyup(function(){
            let pw = $("input[name=pw]").val()
            let pwch = $("#pwch").val()
            let msg = $("#pw-chk-msg")

            if (pw !== pwch) {
                msg.text("비밀번호가 일치하지 않습니다.")
                msg.css("color", "red")
            } else {
                msg.text("비밀번호가 일치")
                msg.css("color", "green")
            }
        })

    })
</script>

<main>

    <div class="container mt-5 mx-auto" style="width: 600px">
        <div class="row">
            <label for="id" class="col-4 col-form-label">아이디</label>
            <div class="col-8">
                <input type="text" id="id" name="id" required>
            </div>
        </div>

        <div class="row my-3">
            <label for="name" class="col-4 col-form-label">이름</label>
            <div class="col-8">
                <input type="text" id="name" name="name" required>
            </div>
        </div>

        <div class="row my-3">
            <label for="nick" class="col-4 col-form-label" required>닉네임</label>
            <div class="col-8">
                <input type="text" id="nick" name="nick">
            </div>
        </div>

        <div class="row my-3">
            <label for="email" class="col-4 col-form-label" required>Email</label>
            <div class="col-8">
                <input type="email" id="email" name="email">
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
                <span id="pw-chk-msg"></span>
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
            <button type="button" id="btnJoin" class="btn btn-primary col-2 me-3">회원가입</button>
            <button type="button" class="btn btn-danger col-2">취소</button>
        </div>
    </div>
</main>

<%-- footer --%>
<%@include file="/WEB-INF/views/component/footer.jsp"%>
