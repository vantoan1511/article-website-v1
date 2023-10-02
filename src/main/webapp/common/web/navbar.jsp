<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/common/taglib.jsp" %>
<nav class="menu">
    <div class="container">
        <div class="brand">
            <a href="/home">
                <img src="/template/web/images/logo.png" alt="Magz Logo">
            </a>
        </div>
        <div class="mobile-toggle">
            <a href="#" data-toggle="menu" data-target="#menu-list">
                <i class="ion-navicon-round"></i>
            </a>
        </div>
        <div class="mobile-toggle">
            <a href="#" data-toggle="sidebar" data-target="#sidebar">
                <i class="ion-ios-arrow-left"></i>
            </a>
        </div>
        <div id="menu-list">
            <ul class="nav-list">
                <li class="for-tablet nav-title"><a>Menu</a></li>
                <c:if test="${not empty session }">
                    <li class="for-tablet"><a><i class="ion-person"></i> Xin
                        chào, ${session.user.username}</a></li>
                    <li class="for-tablet"><a href="/logout"><i
                            class="ion-log-out"></i> Thoát</a></li>
                </c:if>
                <c:if test="${ empty session }">
                    <li class="for-tablet"><a href="/login">Đăng nhập</a></li>
                    <li class="for-tablet"><a href="/register">Đăng ký</a></li>
                </c:if>
                <li class="dropdown magz-dropdown magz-dropdown-megamenu">
                    <a href="#">Tất cả <i class="ion-ios-arrow-right"></i></a>
                    <div class="dropdown-menu megamenu">
                        <div class="megamenu-inner">
                            <div class="row">
                                <div class="col-md-3">
                                    <ul class="vertical-menu">
                                        <li><a href="/categories/kinh-te">Kinh tế</a></li>
                                        <li><a href="/categories/thoi-su">Thời sự</a></li>
                                        <li><a href="/categories/khoa-hoc">Khoa học</a></li>
                                    </ul>
                                </div>
                                <div class="col-md-3">
                                    <ul class="vertical-menu">
                                        <li><a href="/categories/the-thao">Thể thao</a></li>
                                        <li><a href="/categories/phap-luat">Pháp luật</a></li>
                                    </ul>
                                </div>
                                <div class="col-md-3">
                                    <ul class="vertical-menu">
                                        <li><a href="/categories/giao-duc">Giáo dục</a></li>
                                        <li><a href="/categories/doi-song">Đời sống</a></li>
                                    </ul>
                                </div>
                                <div class="col-md-3">
                                    <ul class="vertical-menu">
                                        <li><a href="/categories/thoi-trang">Thời trang</a></li>
                                        <li><a href="/categories/the-thao-dien-tu">Thể thao điện tử</a></li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </div>
                </li>
                <li><a href="/categories/kinh-te">Kinh tế</a></li>
                <li><a href="/categories/the-thao">Thể thao</a></li>
                <li><a href="/categories/giao-duc">Giáo dục</a></li>
            </ul>
        </div>
    </div>
</nav>
<!-- End nav -->