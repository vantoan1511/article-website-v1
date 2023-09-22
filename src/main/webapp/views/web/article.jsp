<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/common/taglib.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>${article.title}</title>
</head>
<body>
<section class="single">
    <div class="container">
        <div class="row">
            <div class="col-md-4 sidebar" id="sidebar">
                <aside>
                    <div class="aside-body">
                        <figure class="ads">
                            <img src="/template/web/images/ad.png">
                            <figcaption>Quảng cáo nè</figcaption>
                        </figure>
                    </div>
                </aside>
            </div>
            <div class="col-md-8">
                <ol class="breadcrumb">
                    <li><a href="/home">Trang chủ</a></li>
                    <li class="active">${article.category.name}</li>
                </ol>
                <article class="article main-article">
                    <header>
                        <h1>${article.title}</h1>
                        <ul class="details">
                            <li>
                                <i class="bi bi-clock"></i>
                                <fmt:formatDate value="${article.createdDate}" pattern="EEEE, HH:mm dd/MM/yyyy"/>
                            </li>
                            <li>
                                <a href="/category?code=${article.category.code}">
                                    ${article.category.name}
                                </a>
                            </li>
                            <li>Bởi <a href="#">${article.createdBy}</a></li>
                            <li><i class="bi bi-eye"></i> ${article.views}</li>
                            <c:if test="${session.user.role.code == 'admin'}">
                                <li>
                                    <i class="bi bi-pencil"></i>
                                    <a href="/admin/article?tab=edit&id=${article.id}">Sửa</a>
                                </li>
                            </c:if>
                        </ul>
                    </header>
                    <div class="main">${article.content}</div>
                    <footer>
                    </footer>
                </article>
                <div class="line">
                </div>
                <div class="line thin"></div>

                <%--comments section--%>
                <div class="comments">
                    <h2 id="cmt" class="title">
                        Bình luận (${comments.size()})
                        <a id="show-comment" href="">
                            Hiện bình luận
                            <i class="bi bi-arrow-down-square"></i>
                        </a>
                    </h2>
                    <c:if test="${not empty session}">
                        <form class="row">
                            <div class="col-md-12">
                                <h3 class="title">Viết bình luận</h3>
                            </div>
                            <div class="form-group col-md-12">
                                <div id="reply-section">
                                    <a id="cancel-btn" href="#" title="Xóa">
                                        <i class="bi bi-x-lg"></i>
                                    </a>
                                    Phản hồi <i id="parent-content"></i>
                                </div>
                                <input type="hidden" id="parentId" value="">
                                <input type="hidden" id="id" value="">
                                <textarea id="comment-content" name="content" class="form-control"
                                          placeholder="Bình luận tại đây"></textarea>
                            </div>
                            <div class="form-group col-md-3">
                                <button id="submitComment" class="btn btn-primary btn-rounded"
                                        type="button">
                                    <i class="bi bi-send"></i>Gửi
                                </button>
                            </div>
                        </form>
                    </c:if>
                    <c:if test="${empty session}">
                        <div>
                            <a href="/login?next=http://localhost:8080/article?id=${article.id}#cmt">
                                Đăng nhập
                            </a> để viết/phản hồi bình luận
                        </div>
                    </c:if>
                    <div id="comment-list" class="comment-list" hidden="hidden">
                        <c:forEach var="comment" items="${comments}">
                            <div class="item">
                                <div class="user">
                                    <figure>
                                        <img src="${comment.user.avatar}">
                                    </figure>
                                    <div class="details">
                                        <h5 class="name">${comment.user.fullname}
                                            <c:if test="${session.user.id == comment.userId}">
                                                <a href="#">
                                                    <i class="bi bi-pencil-square"></i>
                                                </a>
                                            </c:if>
                                        </h5>
                                        <div class="time">
                                            <fmt:formatDate value="${comment.createdDate}"
                                                            pattern="HH:mm dd/MM/yyyy"/>
                                        </div>
                                        <div class="description">
                                            <c:if test="${comment.parentId != 0}">
                                                <blockquote>
                                                        ${comment.parent.content}
                                                </blockquote>
                                            </c:if>
                                                ${comment.content}
                                        </div>
                                        <c:if test="${not empty session}">
                                            <footer>
                                                <a onclick="replyCmt(${comment.id}, '${comment.content}')" href="#cmt">Phản
                                                    hồi</a>
                                            </footer>
                                        </c:if>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                        <div>
                            <a id="hide-comment" href="">
                                Ẩn bình luận
                                <i class="bi bi-arrow-up-square"></i>
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
<script>
    $(document).ready(() => {
        $('#show-comment').click(function (e) {
            e.preventDefault();
            $('#comment-list').show();
        });
        $('#hide-comment').click(function (e) {
            e.preventDefault();
            $('#comment-list').hide();
        });
        $('#reply-section').hide();
        $('#cancel-btn').click((e) => {
            e.preventDefault();
            $('#reply-section').hide();
        });
        $('#comment-content').focusin(() => {
            console.log('is focus')
        });
    });

    function replyCmt(id, content) {
        $('#parent-content').text('\'' + content + '\'');
        $('#reply-section').show();
    }
</script>
</body>
</html>
