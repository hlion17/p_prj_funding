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
    <div id="cm-filter">
        <div>창작자의 글</div>
        <div>전체</div>
        필터링 기능 추가 예정
    </div>
    <div id="cm-posts">
        <c:forEach var="p" items="${list}">
        <div class="post-box">
            <div class="post-info">
                <div class="writer">${p.member.id}</div>
                <div class="regDate"><fmt:formatDate value="${p.regDate}" pattern="yy/MM/dd" /></div>
            </div>
            <div class="post-content">${p.content}</div>
            <div class="reply-area">댓글 영역</div>
        </div>
        </c:forEach>
    </div>
</div>
<div id="sticky-holder">
    <div id="content-right">
    채팅 추가 예정
    </div>
</div>

<script>
    // 에디터 적용
    CKEDITOR.replace("community-writer");
</script>
