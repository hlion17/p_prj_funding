<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%-- 에디터 로드 --%>
<script src="/resources/ckeditor_basic/ckeditor.js"></script>

<script>
    $(document).ready(function () {
        // nav2 숨기기
        $("#project-nav2").css("display", "none")
        // 에디터 표시
        $("#btn-editor").click(function () {
            $(this).css("display", "none")
            $("#cm-write").toggle()
        })
        // 게시글 작성
        $("#cm-write button").click(function () {
            const content = CKEDITOR.instances["community-writer"].getData()
            console.log(content)
            $.ajax({
                method: "POST"
                , url: "/project/board/write"
                , dataType: "JSON"
                , data: {projectNo: ${projectNo}, content: content}
                , success: function (res) {
                    if (res.error != undefined) {
                        alert(
                            "error: " + res.error + "\n"
                            + res.message)
                        return false;
                    }
                    getCommunityPage(${projectNo});
                }
                , error: function(error) {
                    console.log("error: ", error)
                }
            })
        })
        // 채팅 영역 활성화
        $("#btn-chat").click(function () {
            if ('${loginId}' == '') {
                alert("로그인이 필요한 서비스 입니다.")
                return false
            }
            $("#chat-container").toggle("300");
        })
    })
</script>

<style>
    .write-btn {
        border: 1px solid #ccc;
        margin: 20px;
        padding: 20px 30px;
        /*background: rgba(255, 140, 0, 0.1);*/
        background: whitesmoke;
    }
    #cm-write > div:last-child {
        padding: 10px 0;
        display: flex;
        justify-content: flex-end;
    }
    #cm-filter {
        display: flex;
        margin: 20px;
    }
    #cm-filter > div {
        text-decoration: line-through;
        border: 1px solid #ccc;
        border-radius: 15px;
        margin-right: 10px;
        padding: 7px 15px;
        font-size: 12px;
        background: whitesmoke;
    }
    .post-box {
        border-bottom: 1px solid #ccc;
        padding: 10px;
        margin-bottom: 10px;
    }
    .post-box > div {
        padding: 10px 0;
    }
    .post-box .post-info {
        display: flex;
    }
    .post-box .post-info > div {
        margin-right: 10px;
    }
    .post-box .post-info > .writer {
        font-size: 16px;
        font-weight: 700;
    }
    .post-box .post-info > .regDate {
        font-size: 12px;
        color: #ccc;
        line-height: 25px;
    }

    #content-right {
        width: 400px;
        max-width: 400px;
    }
</style>

<style>
    #chat-container {
        display: none;
    }
    #btn-chat {
        text-align: center;
        padding: 10px 0;
        border: 1px solid #ccc;
        margin: 20px 10px;
    }
    #chat-box {
        max-height: 400px;
        overflow: auto;
    }
    #chat-box {
        display: flex;
        flex-direction: column;
        list-style: none;
        border: 1px solid #ccc;
        border-radius: 5px;
        height: 350px;
        max-height: 350px;
        margin: 0;
        padding: 20px;
    }
    #chat-control > input {
        width: 100%;
    }

    /*  메시지 템플릿 */
    .message-container {
        max-width: 80%;
    }
    .message-header {
        display: flex;
        align-items: center;
        padding: 5px 0;
    }
    .sender {
        font-size: 14px;
        margin-right: 10px;
    }
    .message-date {
        font-size: 12px;
    }
    .message-body {
        display: inline-block;
        font-size: 14px;
        padding: 5px 10px;
        border-radius: 0px 15px 15px;
        word-break: break-all;
    }
    #chat-control {
        padding: 10px 0px;
    }
</style>

<div id="content-left">
<%--    <div class="write-btn">프로젝트를 후원한 회원만 작성할 수 있습니다.</div>--%>
    <div class="write-btn" id="btn-editor">작성할 메시지를 입력해주세요</div>
    <div id="cm-write" style="display: none;">
        <textarea id="community-writer" name="content"></textarea>
        <div>
            <button>작성</button>
        </div>
    </div>
    <%-- 필터링 기능 추가 예정 --%>
<%--    <div id="cm-filter">
        <div>창작자의 글</div>
        <div>전체</div>
    </div>--%>
    <div id="cm-posts">
        <c:forEach var="p" items="${list}">
        <div class="post-box">
            <div class="post-info">
                <div class="writer">${p.member.id}</div>
                <div class="regDate"><fmt:formatDate value="${p.regDate}" pattern="yy/MM/dd" /></div>
            </div>
            <div class="post-content">${p.content}</div>
<%--            <div class="reply-area">댓글 영역</div>--%>
        </div>
        </c:forEach>
    </div>
</div>
<div id="sticky-holder">
    <div id="content-right">
        <%-- 채팅 영역 --%>
        <div id="btn-chat">
            <span>채팅하기</span>
        </div>
        <div id="chat-container">
            <div id="chat-box">
            </div>
            <div id="chat-control">
                <input type="text" id="chat-message" placeholder="메시지를 입력해주세요">
            </div>
        </div>

    </div>
</div>

<%-- 메시지 템플릿 --%>
<div class="message-container" id="message-template" style="display: none;">
    <div class="message-header">
        <div class="sender"></div>
        <div class="message-date"></div>
    </div>
    <div class="message-body"></div>
</div>


<script>
    // 에디터 적용
    CKEDITOR.replace("community-writer");
</script>

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
        const result = $("#chat-box")
        const template = $("#message-template").clone()

        template.removeAttr("id")
        template.css("display", "block")
        const date = new Date();
        const dateParse = date.getHours() + ":" + date.getMinutes();

        template.find(".message-date").text(dateParse)
        template.find(".sender").text(recv.sender)
        template.find(".message-body").text(recv.message)

        if (recv.sender == '${loginId}') {
            console.log("check")
            template.find(".message-body").css("background", "gold")
            template.css("align-self", "flex-start")
        } else {
            template.find(".message-body").css("background", "whitesmoke")
            template.css("align-self", "flex-end")
        }

        console.log(recv.sender)
        console.log('${loginId}')

        result.append(template)

        // 채팅 스크롤 맨 아래로 포커싱
        const chatBox = document.getElementById("chat-box");
        // clientHeight(보여지는 영역) = scrollHeight(전체 영역) - scrollTop(숨겨진 영역)
        // 숨겨진 영역 높이를 전체 높히 만큼 쭉 밀어서 스크롤 맨 밑에 오도록 설정
        chatBox.scrollTop = chatBox.scrollHeight;
    }
    // 웹소켓 접속
    function connect() {
        ws.connect({}, function(frame) {
            ws.subscribe("/topic/chat/room/"+'${roomId}', function(message) {
                var recv = JSON.parse(message.body);
                recvMessage(recv);
            });
            //ws.send("/app/chat/message", {}, JSON.stringify({type:'ENTER', roomId: '${roomId}', sender: '${loginId}'}));
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
