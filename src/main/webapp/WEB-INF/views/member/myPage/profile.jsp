<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%-- header --%>
<%@include file="/WEB-INF/views/component/header.jsp"%>

<style>
    p {
        margin: 0;
    }
    button {
        border: none;
        outline: none;
        padding: 5px 10px;
        border-radius: 10px;
        background: lightgrey;
    }
    main > div {
        width: 1080px;
        margin: 0 auto;
        padding: 20px 0;
    }
    #profile-header {
        border-bottom: 1px solid rgba(0, 0, 0, 0.1);
    }
    #profile-nav {
        margin-top: 20px;
    }
    #profile-nav ul {
        display: flex;
        list-style: none;
        margin: 0;
        padding: 0;
    }
    #profile-nav li {
        margin-right: 20px;
        height: 40px;
    }
    .active-nav {
        border-bottom: 2px solid darkorange;
    }

    /* 회원정보 영역 */
    #profile-wrapper {
        display: flex;
    }
    #member-info-side {
        flex-grow: 1;
        padding: 20px;
    }
    #member-info-side > div {
        /*  테스트 확인용 영역 */
        width: 100%;
        height: 200px;
        border: 1px solid #ccc;
        border-radius: 10px;
        padding: 10px;
    }
    #member-info-wrapper {
        display: flex;
        flex-direction: column;
        width: 70%;
        padding: 20px;
    }
    .member-info-row {
        display: flex;
        flex-direction: column;
        margin: 20px 0;
        padding-bottom: 20px;
        border-bottom: 1px solid #ccc;
    }

    .member-info-title {
        display: flex;
        justify-content: space-between;
        width: 100%;
        margin-bottom: 5px;
    }
    .member-info-title > p {
        font-size: 20px;
        font-weight: 700;
    }
    .member-info-title > div {
        font-size: 15px;
        color: blue;
    }
    .member-info-content {
        display: flex;
        justify-content: space-between;
        margin-left: 10px;
        padding: 5px 0;
    }
    .member-info-content input {
        width: 90%;
    }

</style>

<script>
    $(document).ready(function () {
        $(".btn-show-modify").click(function () {
            const btn = $(this)
            const targetRow = $(this).parents(".member-info-row")
            const modifyInput = targetRow.find("[class~=member-modify]")
            const name = $(this).attr("data-name")
            const value = $(this).attr("data-value")

            if (modifyInput.length == 1) {
                modifyInput.remove()
                btn.text("변경")
            } else {
                btn.text("취소")
                const form = $("<form class='member-info-content member-modify'></form>")
                const input = $("<input type='text'>")
                const button = $("<button class='btn-modify' onclick='update()'>저장</button>")
                form.attr("method", "POST")
                form.attr("action", "/member/update")
                input.attr("value", value)
                input.attr("name", name)
                form.append(input)
                form.append(button)
                targetRow.append(form)
            }
        })
    })
</script>

<main>
    <div id="profile-header">
        <div>
            <h1>회원관리</h1>
        </div>
        <div id="profile-nav">
            <ul>
                <li class="active-nav">회원정보</li>
                <li style="text-decoration: line-through">임시메뉴</li>
            </ul>
        </div>
    </div>
    <div id="profile-wrapper">
        <%-- 회원정보 영역 --%>
        <div id="member-info-wrapper">
            <div class="member-info-row">
                <div class="member-info-title">
                    <p>아이디</p>
                </div>
                <div class="member-info-content">${member.id}</div>
            </div>
            <div class="member-info-row">
                <div class="member-info-title">
                    <p>닉네임</p>
                    <div class="btn-show-modify" data-value="${member.nick}" data-name="nick">변경</div>
                </div>
                <%-- 회원가입시 닉네임을 등록하지 않을 수 있다고 가정 --%>
                <%-- 다른 항목도 필요한 경우 다음과 같이 처리 --%>
                <c:choose>
                    <c:when test="${empty member.nick}">
                        <div class="member-info-content" style="color: darkgrey;">등록된 닉네임이 없습니다.</div>
                    </c:when>
                    <c:otherwise>
                        <div class="member-info-content">${member.nick}</div>
                    </c:otherwise>
                </c:choose>
            </div>
            <div class="member-info-row">
                <div class="member-info-title">
                    <p>이메일</p>
                    <div class="btn-show-modify" data-value="${member.email}" data-name="email">변경</div>
                </div>
                <div class="member-info-content">${member.email}</div>
            </div>
            <div class="member-info-row">
                <div class="member-info-title">
                    <p>전화번호</p>
                    <div class="btn-show-modify" data-value="${member.phone}" data-name="phone">변경</div>
                </div>
                <div class="member-info-content">${member.phone}</div>
            </div>
        </div>
        <%-- 회원정보 사이드 영역 --%>
        <div id="member-info-side">
            <div style="white-space: pre-line;">회원 정보에 관한
                부차적인 정보가 들어갈 영역
                메모:
                * 회원탈퇴 기능
            </div>
        </div>
    </div>
</main>

<%-- footer --%>
<%@include file="/WEB-INF/views/component/footer.jsp"%>