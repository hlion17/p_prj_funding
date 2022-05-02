<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%-- header --%>
<%@include file="/WEB-INF/views/component/header.jsp"%>

<div class="container">

    <div>
        <h2>${roomId}</h2>
    </div>
    <div class="input-group">
        <div class="input-group-prepend">
            <label class="input-group-text">내용</label>
        </div>
        <input type="text" class="form-control" id="chat-message">
        <div class="input-group-append">
            <button class="btn btn-primary" type="button" onclick="sendMessage();">보내기</button>
        </div>
    </div>
    <ul class="list-group">
        <li class="list-group-item" id="chat-result">
        </li>
    </ul>
    <div></div>

</div>

<script src="https://cdn.jsdelivr.net/npm/sockjs-client@1/dist/sockjs.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.min.js"></script>

<script>

    $(document).ready(function () {
        $("#chat-message").keydown(function (key) {
            if (key.keyCode == 13) {
                sendMessage();
                $("#chat-message").val("")
            }
        });
    })

    // websocket & stomp initialize
    var sock = new SockJS("/ws/chat");
    var ws = Stomp.over(sock);
    var reconnect = 0;
    // 보내는 메시지
    function sendMessage() {
        const message = $("#chat-message").val();
        ws.send("/app/chat/message", {}, JSON.stringify({type:'TALK', roomId: '${roomId}', sender: '${loginId}', message: message}));
    }
    // 받는 메시지
    function recvMessage(recv) {
        console.log(recv)
        const result = $("#chat-result")
        const li = $("<li></li>")

        li.addClass("list-group-item");
        li.addClass("list-group-item-action");
        li.text(recv.sender + ": " + recv.message)
        result.append(li)
        //this.messages.unshift({"type":recv.type,"sender":recv.type=='ENTER'?'[알림]':recv.sender,"message":recv.message})

    }
    // 웹소켓 접속
    function connect() {
        // pub/sub event
        ws.connect({}, function(frame) {
            ws.subscribe("/topic/chat/room/"+'${roomId}', function(message) {
                var recv = JSON.parse(message.body);
                recvMessage(recv);
            });
            ws.send("/app/chat/message", {}, JSON.stringify({type:'ENTER', roomId: '${roomId}', sender: '${loginId}'}));
        }, function(error) {
            if(reconnect++ <= 5) {
                setTimeout(function() {
                    console.log("connection reconnect");
                    sock = new SockJS("/ws/chat");
                    ws = Stomp.over(sock);
                    connect();
                },10*1000);
            }
        });
    }

    connect();


</script>

<%-- footer --%>
<%@include file="/WEB-INF/views/component/footer.jsp"%>
