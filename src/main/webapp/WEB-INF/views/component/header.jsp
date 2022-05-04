<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Insert title here</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-2.2.4.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
    <script src="https://kit.fontawesome.com/c3d0d95309.js" crossorigin="anonymous"></script>

    <script>
    // 사용자 정의 POST 요청 함수
    function sendDataByPost(path, parameters, method = "post") {
        const form = document.createElement("form")
        form.method = method
        form.action = path
        document.body.appendChild(form)

        for (const key in parameters) {
            const formField = document.createElement("input")
            formField.type = "hidden"
            formField.name = key
            formField.value = parameters[key]

            form.appendChild(formField)
        }
        form.submit()
    }
    </script>

    <%-- 웹소켓 관련 --%>
    <script src="https://cdn.jsdelivr.net/npm/sockjs-client@1/dist/sockjs.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.min.js"></script>
    <script>
    var loginId = '${loginId}'
    var sock = new SockJS("/ws/chat");
    var ws = Stomp.over(sock);
    var reconnect = 0;
    // 받은 메시지 처리
    function alertMessage(recv) {
        const alert = $("#user-alert")
        alert.css("display", "block")
        alert.text(recv.message)
        setTimeout(function () {
            alert.css("display", "none")
        }, 5 * 1000);
    }
    // 웹소켓 접속
    function connect() {
        // 로그인 했을 경우에만 웹소켓 접속
        if (loginId !== '') {
            ws.connect({}, function(frame) {
                // 접속시 세션에 등록된 아이디로 메시지 브로커 구독
                ws.subscribe("/queue/user-" + "${loginId}", function (message) {
                    var recv = JSON.parse(message.body);
                    alertMessage(recv);
                });
            }, function(error) {
                if(reconnect++ <= 5) {
                    setTimeout(function() {
                        console.log("connection reconnect");
                        sock = new SockJS("/ws/chat");
                        ws = Stomp.over(sock);
                        connect();
                    },10*1000);
                }
            })
        }
    }
    // 웹소켓 접속
    connect();
    </script>
</head>
<body>

<%-- 알림창 --%>
<div class="alert alert-success" role="alert" id="user-alert" style="display: none;"></div>

<!-- header -->
<header>
    <nav class="navbar navbar-expand-lg navbar-light" style="background-color: #e3f2fd;">
        <div class="container py-2">
            <a class="navbar-brand" href="/">크라우드 펀딩</a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                    <li class="nav-item">
                        <a class="nav-link active" aria-current="page" href="#">프로젝트</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/chat/room">채팅</a>
                    </li>
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                            임시메뉴
                        </a>
                        <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                            <li><a class="dropdown-item" href="#">임시메뉴1</a></li>
                            <li><a class="dropdown-item" href="#">임시메뉴1</a></li>
                            <li>
                                <hr class="dropdown-divider">
                            </li>
                            <li><a class="dropdown-item" href="#">임시메뉴3</a></li>
                        </ul>
                    </li>
                </ul>
                <c:choose>
                <c:when test="${not empty sessionScope.loginId}">
                <div class="col-md-3 text-end">
                    <button type="button" class="btn btn-outline-primary me-2" onclick="location.href='/member/logout'">로그아웃</button>
                    <button type="button" class="btn btn-primary" onclick="location.href='/member/detail?id=${sessionScope.loginId}'">회원정보</button>
                </div>
                </c:when>
                <c:otherwise>
                <div class="col-md-3 text-end">
                    <button type="button" class="btn btn-outline-primary me-2" onclick="location.href='/member/login'">로그인</button>
                    <button type="button" class="btn btn-primary" onclick="location.href='/member/join'">회원가입</button>
                </div>
                </c:otherwise>
                </c:choose>
            </div>
        </div>
    </nav>
</header>
<!-- /header -->
