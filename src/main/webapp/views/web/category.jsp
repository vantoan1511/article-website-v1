<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@include file="/common/taglib.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>Thể loại - ${model.category.name}</title>
</head>
<body>
<section class="category">
    <div class="container">
        <div class="row">
            <div class="col-md-8 text-left">
                <div class="row">
                    <div class="col-md-12">
                        <ol class="breadcrumb">
                            <li><a href="/home">Trang chủ</a></li>
                            <li class="active">${model.category.name}</li>
                        </ol>
                        <h1 class="page-title">Thể loại: ${model.category.name}</h1>
                        <p class="page-subtitle">
                            Hiển thị tất cả bài viết thuộc thể loại <i>${model.category.name}</i>
                        </p>
                    </div>
                </div>
                <div class="line"></div>
                <div class="row">
                    <c:forEach var="article" items="${model.articles}">
                        <article class="col-md-12 article-list">
                            <div class="inner">
                                <figure>
                                    <a href="/article?id=${article.id}">
                                        <img src="${article.thumbnail}">
                                    </a>
                                </figure>
                                <div class="details">
                                    <div class="detail">
                                        <div class="category">
                                            <a href="/categories/${article.category.code}">${article.category.name}</a>
                                        </div>
                                        <div class="time">
                                            <fmt:formatDate value="${article.createdDate}"
                                                            pattern="EEEE, HH:mm dd/MM/yyyy"/>
                                        </div>
                                    </div>
                                    <h1><a href="/article?id=${article.id}">${article.title}</a>
                                    </h1>
                                    <p>
                                            ${article.description}
                                    </p>
                                    <footer>
                                        <a class="btn btn-primary more" href="/article?id=${article.id}">
                                            <div>Đọc tiếp</div>
                                            <div><i class="ion-ios-arrow-thin-right"></i></div>
                                        </a>
                                    </footer>
                                </div>
                            </div>
                        </article>
                    </c:forEach>
                    <div class="col-md-12 text-center">
                        <ul id="pagination" class="pagination"></ul>
                        <div class="pagination-help-text">
                            <c:if test="${model.totalItems > 0}">
                                Hiển thị
                                <c:if test="${model.limit <= model.totalItems}">${model.limit}</c:if>
                                <c:if test="${model.limit > model.totalItems}">${model.totalItems}</c:if>
                                trong số ${model.totalItems} bài viết &dash; Trang ${model.page}
                            </c:if>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-md-4 sidebar">
                <aside>
                    <div class="aside-body">
                        <figure class="ads">
                            <a href="#">
                                <img src="<c:url value="/template/web/images/ad.png"/>">
                            </a>
                            <figcaption>Quảng cáo ở đây</figcaption>
                        </figure>
                    </div>
                </aside>
            </div>
        </div>
    </div>
</section>
<script>
    $(document).ready(() => {
        //pagination
        const categoryCode = '${model.category.code}';
        const limit = '${model.limit}';
        let start = ${model.page};
        let totalPages = ${model.totalPages};

        if (totalPages >= start) {
            $('#pagination').twbsPagination({
                startPage: start,
                totalPages: totalPages,
                visiblePages: 7,
                first: 'Đầu',
                last: 'Cuối',
                next: 'Tiếp',
                prev: 'Lùi',
                onPageClick: function (event, page) {
                    if (page !== start) {
                        window.location.href = '/categories/' + categoryCode + '?page=' + page + '&limit=' + limit;
                    }
                }
            });
        }
    })
</script>
</body>
</html>
