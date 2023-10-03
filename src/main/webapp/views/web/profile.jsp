<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@include file="/common/taglib.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>${user.fullname}</title>
</head>
<body>
<section class="home">
    <div class="container">
        <div class="row ">
            <div class="col-xs-12 col-md-4">
                <aside>
                    <div class="aside-body">
                        <div class="featured-author">
                            <div class="featured-author-inner">
                                <div class="featured-author-cover"
                                     style="background-image: url('/template/web/images/news/img15.jpg');">
                                    <div class="badges">
                                        <div class="badge-item">
                                            <i class="ion-star"></i>
                                        </div>
                                    </div>
                                    <div class="featured-author-center">
                                        <figure class="featured-author-picture">
                                            <img src="${user.avatar}" alt="Avatar">
                                        </figure>
                                        <div class="featured-author-info">
                                            <h2 class="name">${user.fullname}</h2>
                                            <div class="desc">@${user.username}</div>
                                        </div>
                                    </div>
                                </div>
                                <div class="featured-author-body">
                                    <div class="featured-author-count">
                                        <div class="item">
                                            <a>
                                                <div class="name">Bài viết</div>
                                                <div class="value">${articles.size()}</div>
                                            </a>
                                        </div>
                                        <div class="item">
                                            <a>
                                                <div class="name">Bình luận</div>
                                                <div class="value">${comments.size()}</div>
                                            </a>
                                        </div>
                                        <div class="item">
                                            <a>
                                                <div class="name">Đã tham gia</div>
                                                <div class="value">01/01/2023</div>
                                            </a>
                                        </div>
                                    </div>
                                    <div class="featured-author-footer">
                                        <c:if test="${user.username == session.user.username}">
                                            <a href="/settings">Cập nhật</a>
                                        </c:if>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </aside>
            </div>
            <div class="col-xs-12 col-md-8">
                <div class="line">
                    <div>Gần đây</div>
                </div>
                <div class="row">
                    <c:forEach var="comment" items="${comments}">
                        <article class="article-mini">
                            <div class="inner">
                                <figure>
                                    <a href="/article?id=${comment.article.id}#cmt-${comment.id}">
                                        <img src="${comment.article.thumbnail}" alt="">
                                    </a>
                                </figure>
                                <div class="padding">
                                    <h1>
                                        <a href="/article?id=${comment.article.id}#cmt-${comment.id}">
                                            "${comment.content}"
                                        </a>
                                    </h1>
                                    <div class="detail">
                                            <%--<div class="category"><a href="category.html">Lifestyle</a></div>--%>
                                        <div class="time">
                                            <fmt:formatDate value="${comment.createdDate}"/>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </article>
                    </c:forEach>
                </div>
            </div>
        </div>
    </div>
</section>
</body>
</html>
