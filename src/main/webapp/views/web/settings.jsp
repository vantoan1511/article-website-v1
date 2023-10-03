<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@include file="/common/taglib.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>Thiết lập tài khoản</title>
</head>
<body>
<section class="home">
    <div class="container">
        <div class="row">
            <div class="col-md-8 col-sm-12 col-xs-12">
                <div class="line">
                    <div>Thông tin tài khoản</div>
                </div>
                <form action="/register" method="post" accept-charset="UTF-8">
                    <c:if test="${not empty msg }">
                        <div class="alert alert-${type}" role="alert">${msg}</div>
                    </c:if>
                    <div class="row">
                        <div class="col-md-3 text-left">
                            <div class="form-group">
                                <label for="email">Email</label>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group">
                                <input id="email" type="email" name="email" class="form-control" required
                                       value="${user.email}">
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-3 text-left">
                            <div class="form-group">
                                <label for="fullname">Họ và tên</label>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group">
                                <input id="fullname" type="text" name="fullname" class="form-control" required
                                       value="${user.fullname}">
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-3 text-left">
                            <div class="form-group">
                                <label for="username">Tên đăng nhập</label>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group">
                                <input id="username" type="text" name="username" class="form-control" required
                                       value="${user.username}">
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-3 text-left">
                            <div class="form-group">
                                <label for="username">Ảnh đại diện</label>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group">
                                <input id="avatar" type="text" name="avatar" class="form-control" required
                                       placeholder="Dán đường dẫn vào đây" value="${user.avatar}">
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-3 text-left">
                            <div class="form-group">
                                <label class="fw" for="password">Mật khẩu</label>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group">
                                <input id="password" type="password" name="password" class="form-control" required>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-4"></div>
                        <div class="col-md-4">
                            <div class="form-group">
                                <button disabled class="btn btn-primary btn-block">Lưu</button>
                            </div>
                        </div>
                    </div>
                </form>
                <div class="title-line"></div>
            </div>
        </div>
    </div>
</section>
</body>
</html>
