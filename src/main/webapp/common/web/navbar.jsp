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
                <li class="dropdown magz-dropdown"><a href="category.html">Thể
                    loại <i class="ion-ios-arrow-right"></i>
                </a>
                    <ul class="dropdown-menu">
                        <li><a href="#">Thể thao</a></li>
                        <li><a href="#">Khoa học - Công nghệ</a></li>
                        <li><a href="#">Chính trị</a></li>
                        <li><a href="#">Kinh tế</a></li>
                        <li><a href="#">Văn hóa</a></li>
                        <li><a href="#">Thời trang</a></li>
                        <li><a href="#">Ẩm thực</a></li>
                        <li><a href="#">Giáo dục</a></li>
                    </ul>
                </li>

                <li class="dropdown magz-dropdown"><a href="#">Cá nhân<i
                        class="ion-ios-arrow-right"></i>
                </a>
                    <ul class="dropdown-menu">
                        <li><a href="/profile/${session.user.username}"><i
                                class="icon ion-person"></i>Tài khoản của tôi</a></li>
                        <li><a href="#"><i class="icon ion-chatbox"></i> Bình
                            luận</a></li>
                        <li><a href="#"><i class="icon ion-key"></i> Đổi mật khẩu</a></li>

                        <li class="divider"></li>
                        <li><a href="/logout"><i class="icon ion-log-out"></i>
                            Đăng xuất</a></li>
                    </ul>
                </li>
            </ul>
        </div>
    </div>
</nav>
<!-- End nav -->