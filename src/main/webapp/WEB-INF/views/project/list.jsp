<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%-- header --%>
<%@include file="/WEB-INF/views/component/header.jsp" %>

<script src="https://kit.fontawesome.com/c3d0d95309.js" crossorigin="anonymous"></script>

<style>
    /* 마감된 프로젝트 표시 */
    #project-status {
        color: white;
        background: red;
        padding: 3px 5px;
        font-weight: 700;
        font-size: 12px;
        border-radius: 15px;
    }

    #category-section .container {
        overflow: auto;
    }

    #category-section .row {
        flex-flow: nowrap;
    }
</style>

<script>

    $(document).ready(function () {

        // 검색어 입력
        $("input[name=keyword]").on("keyup", function (key) {
            if (key.keyCode == 13) {
                getList('${pagination.category}');
            }
        })
        // 필터 선택
        $("select[name=filter]").change(function () {
            getList('${pagination.category}');
        })
        // 정렬 선택
        $("select[name=order]").change(function () {
            getList('${pagination.category}');
        })
        // 필터, 정렬 조건 유지
        setCondition()

        // 개별 프로젝트 이동
        $(".project-card").click(function () {
            const projectNo = $(this).attr("data-projectNo")
            location.href = '/project/' + projectNo
        })

    })


    // 프로젝트 리스트 조회
    function getList(category) {
        var curPage = '${pagination.curPage}';
        var keyword = $("input[name=keyword]").val();
        if (category === undefined) var category = '';
        var category = category;
        var filter = $("select[name=filter]").val();
        var order = $("select[name=order]").val();

        console.log(
            'cuarPage: ' + curPage
            + ' / keyward: ' + keyword
            + ' / category: ' + category
            + ' / filter: ' + filter
            + ' / order: ' + order
        )

        location.href = "/project/list?curPage=" + curPage + "&keyword=" + keyword + "&category=" + category + "&filter=" + filter + "&order=" + order;

    }

    // 정렬 조건 유지
    function setCondition() {
        const selectOrder = document.querySelector("select[name=order]")
        const selectFilter = document.querySelector("select[name=filter]")

        for (i = 0; i < selectOrder.length; i++) {
            if (selectOrder.options[i].value == '${pagination.order}') {
                selectOrder.options[i].selected = true
                break
            }
        }

        for (i = 0; i < selectFilter.length; i++) {
            if (selectFilter.options[i].value == '${pagination.filter}') {
                selectFilter.options[i].selected = true
                break
            }
        }
    }

</script>


<main>
    <!-- 카테고리 -->
    <section id="category-section">
        <div class="container py-5">
            <div class="row">
                <div class="col text-center" onclick="getList()">
                    <div><img src="/resources/img/project/category/all.svg" style="withd: 800px; height: 60px"></div>
                    <span>전체</span>
                </div>

                <c:forEach var="c" items="${cList}">
                    <div class="col text-center me-4" onclick="getList('${c.categoryNo}')">
                        <div><img src="/resources/img/project/category/${c.categoryNo}.svg"
                                  style="withd: 80px; height: 60px"></div>
                        <span>${c.categoryName}</span>
                    </div>
                </c:forEach>

            </div>
        </div>
    </section>

    <!-- /카테고리 -->


    <!-- 프로젝트 목록 -->
    <section>

        <div class="album py-5 bg-light">

            <!-- 프로젝트 목록 네비게션바 -->
            <div class="container">
                <div class="row">
                    <div class="col-auto me-auto">
                        <input class="form-control" list="datalistOptions" placeholder="Type to search..."
                               name="keyword">
                        <datalist id="datalistOptions">
                            <!-- <option value="추천 검색어1">
                            <option value="추천 검색어2"> -->
                        </datalist>
                    </div>
                    <div class="col-auto">
                        <select class="form-select" name="filter">
                            <option value="" selected>선택안됨</option>
                            <option value="3">진행중</option>
                            <option value="4">종료된</option>
                        </select>
                    </div>
                    <div class="col-auto">
                        <select class="form-select" name="order">
                            <option value="" selected>선택안됨</option>
                            <option value="open_date">오픈일</option>
                            <option value="close_date">마감일</option>
                        </select>
                    </div>
                </div>

                <hr>
            </div>
            <!-- /프로젝트 목록 네비게션바 -->

            <div class="container">
                <div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 g-3">

                    <c:forEach var="p" items="${list}">
                    <div class="col project-card" style="max-width: 250px;" data-projectNo="${p.projectNo}">
                        <div class="card shadow-sm">
                            <img src="${p.projectImage}" style="min-height: 250px;">
                            <div class="card-body">
                                <strong>${p.projectTitle}</strong>
                                    <c:if test="${p.projectStep eq 4}">
                                    <span id="project-status">마감</span>
                                    </c:if>
                                <p class="card-text">${p.projectIntro}</p>
                                <progress value="${p.sum}" max="${p.projectPrice}"></progress>
                                <div class="row fs-6">
                                    <div class="col d-flex">
                                        <span style="margin-right: 10px;">
                                        <fmt:formatNumber value="${p.sum / p.projectPrice}" type="percent"/>
                                        </span>
                                        <span>
                                        <fmt:formatNumber value="${p.projectPrice}" pattern="#,###"/>
                                        </span>
                                    </div>
                                    <div class="col text-end">
                                        <span>
                                        <fmt:formatDate value="${p.closeDate}" pattern="yy/MM/dd"/>
                                        </span>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    </c:forEach>

                </div>
            </div>

        </div>

    </section>
    <!-- /프로젝트 목록 -->

</main>

<%-- footer --%>
<%@include file="/WEB-INF/views/component/footer.jsp" %>
