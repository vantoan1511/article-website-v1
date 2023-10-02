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
                                <a href="/categories/${article.category.code}">
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
                        <form id="comment-form" class="row">
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
                                <input type="hidden" id="parentId" name="parentId" value="">
                                <input type="hidden" id="id" name="id" value="">
                                <input type="hidden" id="userId" name="userId" value="${session.user.id}">
                                <input type="hidden" id="articleId" name="articleId" value="${article.id}">
                                <textarea id="comment-content" name="content" class="form-control"
                                          placeholder="Bình luận tại đây"></textarea>
                            </div>
                            <div class="form-group col-md-12 text-center">
                                <button id="comment-submit" class="btn btn-primary btn-rounded"
                                        type="button">
                                    <i class="bi bi-send"></i>Gửi
                                </button>
                                <button id="reset-btn" type="reset" class="btn btn-magz btn-rounded">Hủy</button>
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
                    <div id="comment-list" class="comment-list">
                        <c:forEach var="comment" items="${comments}">
                            <div class="item">
                                <div class="user">
                                    <figure>
                                        <img src="${comment.user.avatar}">
                                    </figure>
                                    <div class="details">
                                        <h5 class="name">${comment.user.fullname}
                                            <c:if test="${session.user.id == comment.userId}">
                                                <a href="#cmt" title="Chỉnh sửa"
                                                   onclick="editCmt(${comment.id}, '${comment.content}') ">
                                                    <i class="bi bi-pencil-square"></i>
                                                </a>
                                                <a href="#cmt" onclick="deleteCmt(${comment.id})" title="Xóa">
                                                    <i class="bi bi-trash3"></i>
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
            $('#parentId').val('');
            $('#reply-section').hide();
        });
        $('#reset-btn').click(() => {
            $('#id').val('');
        });
        $('#comment-submit').click((e) => {
            e.preventDefault();
            let id = $('#id').val();
            let parentId = $('#parentId').val();
            let content = $('#comment-content').val();
            let formData = $('#comment-form').serializeArray();
            let data = {};
            formData.forEach(function (e) {
                data["" + e.name + ""] = e.value;
            });
            if (id !== '') {
                console.log(data);
                updateComment(JSON.stringify(data));
            } else {
                console.log(JSON.stringify(data));
                createComment(JSON.stringify(data));
            }
        });
    });

    function replyCmt(id, content) {
        $('#parentId').val(id);
        $('#parent-content').text('\'' + content + '\'');
        $('#reply-section').show();
    }

    function editCmt(id, content) {
        console.log(content);
        $('#id').val(id);
        $('#comment-content').val(content);
    }

    function deleteCmt(id) {
        Swal.fire({
            title: 'Xóa bình luận?',
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'OK',
            cancelButtonText: 'Hủy',
        }).then((result) => {
            if (result.isConfirmed) {
                let ids = [];
                let data = {};
                ids.push(id);
                data["ids"] = ids;
                console.log(JSON.stringify(data));
                deleteComment(JSON.stringify(data));
            }
        });
    }

    function createComment(data) {
        const api = '/v1/api/comments/';
        $.ajax({
            url: api,
            type: 'POST',
            contentType: 'application/json',
            dataType: 'json',
            data: data,
            success: function (result) {
                window.location.reload();
            },
            error: function (error) {
                console.log(error);
            }

        });
    }

    function updateComment(data) {
        const api = '/v1/api/comments/';
        $.ajax({
            url: api,
            type: 'PUT',
            contentType: 'application/json',
            dataType: 'json',
            data: data,
            success: function (result) {
                window.location.reload();
            },
            error: function (error) {
                console.log(error);
            }

        });
    }

    function deleteComment(data) {
        const api = '/v1/api/comments/';
        $.ajax({
            url: api,
            type: 'DELETE',
            contentType: 'application/json',
            dataType: 'json',
            data: data,
            success: function (result) {
                window.location.reload();
            },
            error: function (error) {
                console.log(error);
            }
        });
    }
</script>
</body>
</html>
