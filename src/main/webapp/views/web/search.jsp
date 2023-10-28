<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/common/taglib.jsp" %>

<!DOCTYPE html>
<html>
<head>
    <title>Tìm kiếm</title>
</head>
<body>
<section class="search">
    <div class="container">
        <div class="row">
            <div class="col-md-3">
                <aside>
                    <h2 class="aside-title">Tìm kiếm</h2>
                    <div class="aside-body">
                        <p>
                            Tìm kiếm với từ khóa kết hợp với bộ lọc bên dưới để kết quả được chính xác hơn.
                        </p>
                        <form id="keyword-form">
                            <div class="form-group">
                                <div class="input-group">
                                    <input id="keyword" type="text" name="keyword" class="form-control"
                                           placeholder="Nhập từ khóa tìm kiếm..." value="${model.keyword}">
                                    <div class="input-group-btn">
                                        <button id="keyword-submit" class="btn btn-primary">
                                            <i class="ion-search"></i>
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </form>
                    </div>
                </aside>
                <aside>
                    <h2 class="aside-title">Bộ lọc</h2>
                    <div class="aside-body">
                        <form class="checkbox-group">
                            <div class="group-title">Thời gian</div>
                            <div class="form-group">
                                <label><input id="anytime" type="radio" name="date" checked> Bất kỳ</label>
                            </div>
                            <div class="form-group">
                                <label><input id="today" type="radio" name="date"> Hôm nay</label>
                            </div>
                            <div class="form-group">
                                <label><input id="last-week" type="radio" name="date"> Tuần trước</label>
                            </div>
                            <div class="form-group">
                                <label><input id="last-month" type="radio" name="date"> Tháng trước</label>
                            </div>
                            <br>
                            <div class="group-title">Thể loại</div>
                            <div class="form-group">
                                <label>
                                    <input id="all" type="radio" name="category" checked> Tất cả
                                </label>
                            </div>
                            <c:forEach var="category" items="${model.categories}">
                                <div class="form-group">
                                    <label>
                                        <input id="${category.code}" class="check-box" type="radio"
                                               name="category"
                                               <c:if test="${category.code == model.categoryCode}">checked</c:if>
                                        > ${category.name}
                                    </label>
                                </div>
                            </c:forEach>
                        </form>
                    </div>
                </aside>
            </div>
            <div class="col-md-9">
                <div class="nav-tabs-group">
                    <ul class="nav-tabs-list">
                        <li id="title"><a href="">Tất cả</a></li>
                        <li id="latest"><a href="">Mới nhất</a></li>
                        <li id="oldest"><a href="">Cũ nhất</a></li>
                        <li id="popular"><a href="">Phổ biến</a></li>
                    </ul>
                    <div class="nav-tabs-right">
                        Dòng
                        <label>
                            <select id="limit-select" class="form-control">
                                <option value="2">2</option>
                                <option value="5">5</option>
                                <option value="10">10</option>
                                <option value="15">15</option>
                            </select>
                        </label>
                    </div>
                </div>
                <div class="search-result">
                    Tìm thấy ${model.articles.size()} kết quả cho từ khóa "${model.keyword}"
                </div>
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
                                        <time>
                                            <fmt:formatDate value="${article.createdDate}" pattern="HH:mm dd/MM/yyyy"/>
                                        </time>
                                    </div>
                                    <h1><a href="/article?id=${article.id}">${article.title}</a></h1>
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
        </div>
    </div>
</section>
<script>
    $(document).ready(() => {
        const dateFilter = $('input[name=date]');
        const categoryFilter = $('input[name=category]');
        const url = '/search?';
        const keyword = $('#keyword');
        let categoryCode = '${model.categoryCode}';
        let dateFormat = '${model.dateFormat}';
        //init date filter
        dateFilter.each(function () {
            const id = $(this).attr('id');
            if (id === dateFormat) {
                $(this).attr('checked', true);
            }
        });

        $('input').iCheck({
            checkboxClass: 'icheckbox_square-red',
            radioClass: 'iradio_square-red',
            cursor: true
        });

        //send a new request upon the filters
        dateFilter.on('ifChecked', (e) => {
            dateFormat = $(e.currentTarget).prop('id');
            window.location.href = url + 'keyword=' + keyword.val() +
                '&dateFormat=' + dateFormat + '&categoryCode=' + categoryCode;
        });

        categoryFilter.on('ifChecked', (e) => {
            categoryCode = $(e.currentTarget).prop('id');
            window.location.href = url + 'keyword=' + keyword.val() +
                '&dateFormat=' + dateFormat + '&categoryCode=' + categoryCode;
        });

        $('#keyword-submit').click((e) => {
            e.preventDefault();
            window.location.href = url + 'keyword=' + keyword.val() + '&categoryCode=' + categoryCode;
        });

        //init sort
        const sorter = $('.nav-tabs-list li');
        sorter.each(function () {
            const id = $(this).attr('id');
            let sortBy = '${model.sortBy}';
            let sortOrder = '${model.sortOrder}';
            if (sortBy === 'createddate') {
                if (sortOrder === 'ASC') {
                    sortBy = 'oldest';
                } else {
                    sortBy = 'latest';
                }
            }
            if (sortBy === 'views') {
                sortBy = 'popular';
            }
            if (sortBy === id) {
                $(this).addClass('active');
            }
        });

        sorter.click((e) => {
            e.preventDefault();
            const id = $(e.currentTarget).attr('id');
            var sortBy = 'title';
            var sortOrder = 'ASC';
            if (id === 'latest') {
                sortBy = 'createddate';
                sortOrder = 'DESC';
            } else if (id === 'oldest') {
                sortBy = 'createddate';
                sortOrder = 'ASC';
            } else if (id === 'popular') {
                sortBy = 'views';
                sortOrder = 'DESC';
            }
            window.location.href = url + 'keyword=' + keyword.val() +
                '&dateFormat=' + dateFormat + '&categoryCode=' + categoryCode +
                '&sortBy=' + sortBy + '&sortOrder=' + sortOrder;
        });

        //pagination
        const sortBy = '${model.sortBy}';
        const sortOrder = '${model.sortOrder}';
        const select = $('#limit-select');
        const limit = '${model.limit}';
        let start = ${model.page};
        let totalPages = ${model.totalPages};

        select.find('option').each(function () {
            if ($(this).val() === limit) {
                $(this).attr('selected', true);
            }
            console.log($(this).val());
        });
        select.change(function () {
            window.location.href = url + 'keyword=' + keyword.val() +
                '&dateFormat=' + dateFormat + '&categoryCode=' + categoryCode +
                '&sortBy=' + sortBy + '&sortOrder=' + sortOrder + '&page=1&limit=' + select.val();
        });
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
                        window.location.href = url + 'keyword=' + keyword.val() +
                            '&dateFormat=' + dateFormat + '&categoryCode=' + categoryCode +
                            '&sortBy=' + sortBy + '&sortOrder=' + sortOrder + '&page=' + page + '&limit=' + select.val();
                    }
                }
            });
        }
    });
</script>
</body>
</html>
