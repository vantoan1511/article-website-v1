<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/common/taglib.jsp" %>

<!DOCTYPE html>
<html>
<head>
    <title>Đăng ký</title>
</head>
<body>
<section class="login first grey">
    <div class="container">
        <div class="box-wrapper">
            <div class="box box-border">
                <div class="box-body">
                    <h4>Đăng ký</h4>
                    <form action="/register" method="post" accept-charset="UTF-8">
                        <c:if test="${not empty msg }">
                            <div class="alert alert-${type}" role="alert">${msg}</div>
                        </c:if>
                        <div class="form-group">
                            <label for="email">Email</label>
                            <input id="email" type="email" name="email" class="form-control" required>
                        </div>
                        <div class="form-group">
                            <label for="fullname">Họ và tên</label>
                            <input id="fullname" type="text" name="fullname" class="form-control" required>
                        </div>
                        <div class="form-group">
                            <label for="username">Tên đăng nhập</label>
                            <input id="username" type="text" name="username" class="form-control" required>
                        </div>
                        <div class="form-group">
                            <label class="fw" for="password">Mật khẩu</label>
                            <input id="password" type="password" name="password" class="form-control" required>
                        </div>
                        <div class="form-group text-right">
                            <button class="btn btn-primary btn-block">Đăng ký</button>
                        </div>
                        <div class="form-group text-center">
                            <span class="text-muted">Đã có tài khoản?</span>
                            <a href="/login">Đăng nhập</a>
                        </div>
                        <div class="title-line">
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</section>
</body>
</html>
