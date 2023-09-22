<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<header class="primary">
    <div class="firstbar">
        <div class="container">
            <div class="row">
                <div class="col-md-3 col-sm-12">
                    <div class="brand">
                        <a href="/home">
                            <img src="/template/web/images/logo.png" alt="Magz Logo">
                        </a>
                    </div>
                </div>
                <div class="col-md-6 col-sm-12">
                    <form class="search" autocomplete="off" action="/search">
                        <div class="form-group">
                            <div class="input-group">
                                <input type="text" name="keyword" class="form-control" placeholder="Từ khóa...">
                                <div class="input-group-btn">
                                    <button class="btn btn-primary"><i class="ion-search"></i></button>
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
                <div class="col-md-3 col-sm-12 text-right">
                    <ul class="nav-icons">
                        <c:if test="${not empty session}">
                            <li>
                                <a href="/profile/${session.user.username}">
                                    <i class="bi bi-person"></i>
                                    <div>Welcome, ${session.user.username}</div>
                                </a>
                            </li>
                            <li>
                                <a href="/logout">
                                    <i class="bi bi-box-arrow-right"></i>
                                    <div>Đăng xuất</div>
                                </a>
                            </li>
                        </c:if>
                        <c:if test="${empty session}">
                            <li>
                                <a href="/register">
                                    <i class="bi bi-person-add"></i>
                                    <div>Đăng ký</div>
                                </a>
                            </li>
                            <li>
                                <a href="/login">
                                    <i class="bi bi-box-arrow-in-right"></i>
                                    <div>Đăng nhập</div>
                                </a>
                            </li>
                        </c:if>
                    </ul>
                </div>
            </div>
        </div>
    </div>

    <!-- Start nav -->
    <%@ include file="/common/web/navbar.jsp" %>
    <!-- End nav -->
</header>