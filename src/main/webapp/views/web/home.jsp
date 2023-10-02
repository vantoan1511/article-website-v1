<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/common/taglib.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title></title>
</head>
<body>
<section class="best-of-the-week">
    <div class="container">
        <h1>
            <div class="text">Tin mới nhất</div>
            <div class="carousel-nav" id="best-of-the-week-nav">
                <div class="prev">
                    <i class="ion-ios-arrow-left"></i>
                </div>
                <div class="next">
                    <i class="ion-ios-arrow-right"></i>
                </div>
            </div>
        </h1>
        <div class="owl-carousel owl-theme carousel-1">
            <c:forEach var="article" items="${latest}">
                <article class="article">
                    <div class="inner">
                        <figure>
                            <a href="/article?id=${article.id}">
                                <img src="${article.thumbnail}" alt="${article.title}">
                            </a>
                        </figure>
                        <div class="padding">
                            <div class="detail">
                                <div class="time">
                                    <i class="bi bi-clock"></i>
                                    <fmt:formatDate value="${article.createdDate}" pattern="HH:mm dd/MM/yy"/>
                                </div>
                                <div class="category">
                                    <a href="/categories/${article.category.code}">${article.category.name}</a>
                                </div>
                            </div>
                            <h2 class="truncate-2lines">
                                <a href="/article?id=${article.id}">${article.title}</a>
                            </h2>
                            <p class="truncate-3lines">${article.description}</p>
                        </div>
                    </div>
                </article>
            </c:forEach>
        </div>
    </div>
</section>
<section class="home">
    <div class="container">
        <div class="line">
            <div>Phổ biến</div>
        </div>
        <div class="row">
            <c:forEach var="article" items="${popular}">
                <article class="col-md-12 article-list">
                    <div class="inner">
                        <figure>
                            <a href="/article?id=${article.id}">
                                <img src="${article.thumbnail}">
                            </a>
                        </figure>
                        <div class="details">
                            <div class="detail">
                                <time>
                                    <i class="bi bi-clock"></i>
                                    <fmt:formatDate value="${article.createdDate}" pattern="HH:mm dd/MM/yy"/>
                                </time>
                                <div class="category">
                                    <a href="/categories/${article.category.code}">${article.category.name}</a>
                                </div>
                            </div>
                            <h1>
                                <a href="/article?id=${article.id}">${article.title}</a>
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
        </div>
    </div>
</section>
<script>
    $(document).ready(() => {

        articlePaginate();

        function articlePaginate() {
            const url = '/home';
            var startPage = ${model.page};
            var totalPages = ${model.totalPages};
            var sortBy = '${model.sortBy}';
            var sortOrder = '${model.sortOrder}';
            var limit = ${model.limit};
            $('#pagination').twbsPagination({
                startPage: startPage,
                totalPages: totalPages,
                visiblePages: 7,
                first: 'Đầu',
                last: 'Cuối',
                next: 'Tiếp',
                prev: 'Lùi',
                onPageClick: function (event, page) {
                    if (page !== startPage) {
                        window.location.href = url + '?page=' + page + '&limit=' + limit
                            + '&sortBy=' + sortBy + '&sortOrder=' + sortOrder;
                    }
                }
            });
        }
    });
</script>
</body>
</html>
