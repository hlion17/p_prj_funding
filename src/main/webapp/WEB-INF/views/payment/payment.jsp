<%@ page import="java.util.Date" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%-- 오늘 날짜 --%>
<c:set var="today" value="<%=new Date()%>" />

<%-- header --%>
<%@include file="/WEB-INF/views/component/header.jsp" %>

<style>
    button {
        border: none;
        padding: 10px;
        border-radius: 10px;
    }
    #wrapper {
        width: 1080px;
        margin: 50px auto;
    }
    #payment-body {
        display: flex;
    }
    #body-left {
        width: 60%;
        padding: 20px;
    }
    #body-right {
        flex-grow: 1;
        padding: 20px;
    }
    #project-area {
        display: flex;
        padding-bottom: 30px;
        border-bottom: 1px solid #ccc;
    }
    #project-area > div:first-child {
        width: 100px;
        height: 100px;
    }
    #project-area div:first-child img {
        width: 100%;
        height: 100%;
    }
    #project-info {
        display: flex;
        flex-direction: column;
        justify-content: space-around;
        margin-left: 20px;
    }
    #project-info span:first-child {
        color: rgb(192,192,192);
        font-weight: 700;
    }
    #project-info > div {
        display: flex;
    }
    #project-info > div > * {
        padding-right: 20px;
    }
    /* 박스영역 설정 */
    .box-style {
        display: flex;
        flex-direction: column;
        border: 1px solid #ccc;
        padding: 10px;
        border-radius: 5px;
    }
    .box-style div {
        display: flex;
    }
    .box-style p {
        font-weight: 700;
        width: 80px;
    }
    #reward-box {
        display: flex;
        flex-direction: column;
    }
    #reward-box > div {
        display: flex;
        width: 300px;
    }
    #reward-box > div > p:first-child {
        width: 80px;
        font-weight: 700;
    }
    #reward-box > div > p:nth-child(2) {
        flex-grow: 1;
    }
    .body-component {
        margin: 30px 0;
    }
    .body-component > p:first-child {
        font-weight: 700;
        font-size: 20px;
    }
    #payment-area button {
        width: 100%;
    }
    #payment-area .body-component:first-child > div:first-child {
        display: flex;
        flex-direction: row;
        justify-content: space-between;
        margin-top: 75px;
    }
    #payment-area .body-component:first-child > div:nth-child(2) {
        margin-top: 20px;
    }
    #delivery-area .box-style > div {
        margin-bottom: 10px;
    }
    #delivery-area .box-style > div > p {
        width: 150px;
    }
    #delivery-area .box-style > div > input {
        width: 100%;
    }

</style>

<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<!-- iamport.payment.js -->
<script type="text/javascript" src="https://cdn.iamport.kr/js/iamport.payment-1.2.0.js"></script>
<script>
    // 주소 조회 API
    function searchPost() {
        new daum.Postcode({
            oncomplete: function(data) {
                // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분입니다.
                // 예제를 참고하여 다양한 활용법을 확인해 보세요.
                const address = $("input[name=address]")
                const zonecode = $("input[name=zonecode]")
                address.val(data.address)
                zonecode.val(data.zonecode)
            }
        }).open();
    }
    // 핸드폰 번호 값 검증
    function checkPhone(val) {
        const regPhone = /^01([0|1|6|7|8|9])-?([0-9]{3,4})-?([0-9]{4})$/
        if (regPhone.test(val) != true) {
            return false
        }
        return true
    }
    // 공백 값 검증
    function isEmpty(parameters) {
        for (const key in parameters) {
            if (parameters[key] == "" || parameters[key] == undefined) {
                return true
            }
        }
        return false
    }

    $(document).ready(function () {
        $("#extra-pay").on("keyup", function () {
            let value = Number($("#extra-pay").val())
            value += Number(${reward.rewardPrice})
            $("#final-payment-amount").text(value.toLocaleString())

        })



    })

</script>

<div id="wrapper">
    <div id="payment-head">
        <div id="project-area">
            <div>
                <img src="${project.projectImage}" alt="">
            </div>
            <div id="project-info">
                <span>${project.category.categoryName}</span>
                <h3>${project.projectTitle}</h3>
                <div>
                    <strong>${project.sum}원</strong>
                    <span><fmt:formatNumber value="${project.sum/project.projectPrice}" type="percent" /></span>
                    <span><fmt:parseNumber value="${(project.closeDate.time - today.time) / (1000 * 60 * 60 * 24)}" integerOnly="true" />일 남음</span>
                </div>
            </div>
        </div>
    </div>
    <div id="payment-body">
        <div id="body-left">
            <div id="reward-area" class="body-component">
                <p>선물정보</p>
                <div class="box-style" id="reward-box">
                    <div>
                        <p>선물구성</p>
                        <span>${reward.rewardIntro}</span>
                    </div>
                    <div>
                        <p>선물금액</p>
                        <span><fmt:formatNumber value="${reward.rewardPrice}" pattern="#,###" /></span>
                    </div>
                </div>
            </div>
            <div class="body-component">
                <p>추가후원 금액</p>
                <div class="box-style">
                    <input class="box-style" type="text" id="extra-pay" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');">
                </div>
            </div>
            <div id="member-area" class="body-component">
                <p>후원자 정보</p>
                <div class="box-style" id="member-box">
                    <div>
                        <p>연락처</p>
                        <span>${member.phone}</span>
                    </div>
                    <div>
                        <p>이메일</p>
                        <span>${member.email}</span>
                    </div>
                </div>
            </div>
            <div id="delivery-area" class="body-component">
                <p>배송지 추가</p>
                <div class="box-style">
                    <div>
                        <p>받는 사람</p>
                        <input name="recipientName" type="text">
                    </div>
                    <div>
                        <p>주소</p>
                        <input name="address" type="text" name="address" onclick="searchPost()">
                    </div>
                    <div>
                        <p>상세주소</p>
                        <input  name="addressDetail" type="text">
                        <input name="zonecode" type="hidden">
                    </div>
                    <div>
                        <p>수령인 연락처</p>
                        <input name="recipientPhone" type="text">
                    </div>
                </div>
            </div>
            <div id="payment-method-area" class="body-component">
                <p>결제수단</p>
                <div class="box-style" id="delivery-box">
                    결제수단 영역
                </div>
            </div>
        </div>
        <div id="body-right">
            <div id="payment-area">
                <div class="body-component">
                    <div class="box-style">
                        <div>최종 후원금액</div>
                        <p id="final-payment-amount"><fmt:formatNumber value="${reward.rewardPrice}" pattern="#,###" /></p>
                    </div>
                    <div>
                        <button onclick="requestPay()">후원하기</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script>
    // 결제
    var IMP = window.IMP; // 생략 가능
    IMP.init("imp80901777");
    function requestPay() {
        const recipientName = $("input[name=recipientName]").val()
        const address = $("input[name=address]").val()
        const addressDetail = $("input[name=addressDetail]").val()
        const recipientPhone = $("input[name=recipientPhone]").val()
        const zonecode = $("input[name=zonecode]").val()
        const extraPay = $("#extra-pay").val()

        console.log("수령인: ", recipientName)
        console.log("주소: ", address)
        console.log("상세주소: ", addressDetail)
        console.log("수령인 연락처: ", recipientPhone)
        console.log("우편번호: ", zonecode)
        console.log("추가금액: ", extraPay)

        // 값 검증
        if (!checkPhone(recipientPhone)) {
            console.log("핸드폰 형식이 잘못됨")
            return false
        }
        if (isEmpty([recipientName, address, recipientPhone])) {
            console.log("빈값이 포함됨")
            return false
        }

        IMP.request_pay({ // param
            pg: "html5_inicis",
            pay_method: "card",
            merchant_uid: "merchant_" + new Date().getTime(),
            name: "${reward.rewardIntro}",
            amount: Number(${reward.rewardPrice}) + Number(extraPay),  // 여기가 위변조 가능성?
            buyer_email: "${member.email}",
            buyer_name: "${member.name}",
            buyer_tel: "${member.phone}",
            buyer_addr: address + " " + addressDetail,
            buyer_postcode: zonecode
        }, function (rsp) { // callback
            console.log(rsp)  // ***********************
            // 결제 요청이 성공한 경우
            if (rsp.success) {
                const res_amount = rsp.amount;  // 요청완료 결과 결제 금액 *********
                // 결제 성공한 경우 WAS로 결제 정보 전달
                jQuery.ajax({
                    url: "/payment/verification",
                    method: "POST",
                    headers: { "Content-Type": "application/json" },
                    <%--data: {--%>
                    <%--    // 서버는 클라이언트로부터 결제 정보를 수신 후 결제금액 위변조 여부 검증--%>
                    <%--    imp_uid: rsp.imp_uid,  // 결제번호--%>
                    <%--    reward_no: ${reward.rewardNo},--%>
                    <%--    merchant_uid: rsp.merchant_uid  // 주문번호--%>
                    <%--}--%>
                    // 서버는 클라이언트로부터 결제 정보를 수신 후 결제금액 위변조 여부 검증
                    data: JSON.stringify({
                            imp_uid: rsp.imp_uid,  // 해당 uid, server 에서 uid 로 요청 보내서 결제금액 확인  ***********
                            // 위의 rewardPrice 값을 위변조 할 수 있다면 여기도 위변조 할 수 있는 것 아닐까?
                            reward_no: ${reward.rewardNo},
                            extraPay: extraPay,
                            merchant_uid: rsp.merchant_uid
                        })
                }).done(function (data) {
                    // 가맹점 서버 결제 API 성공시 로직
                    console.log("서버 응답결과: ", data)  // ******************
                    // switch (data.rsp) {
                    //     case "success" :
                    //         // 검증 성공시 로직
                    //         break
                    //     case "fail" :
                    //         // 검증 실패시 로직
                    //         break
                    // }
                })
            } else {
                // 결제에 실패했을 경우
                // 에러메시지, 결제 상태 코드 변경하는 로직
            }
        });
    }
</script>

<%-- footer --%>
<%@include file="/WEB-INF/views/component/footer.jsp" %>