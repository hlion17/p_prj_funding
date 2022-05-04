<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@include file="component/header.jsp"%>

<div class="container my-5">
<h1>메인화면</h1>
<hr>

로그인 아이디: ${sessionScope.loginId}

    <button id="test-button" onclick="test()">test</button>
</div>

<script>
    function test() {
        $.ajax({
            type: "get"
            , url: "/test"
            , dataType: "json"
            , data: {}
            , success: function(res) {
                console.log("성공")
            }
            , error: function() {
                console.log("ajax 실패")
            }
        })
    }

</script>

<%@include file="component/footer.jsp"%>