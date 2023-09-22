<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/common/taglib.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>Đăng nhập</title>
</head>
<body>
<section class="login first grey">
    <div class="container">
        <div class="box-wrapper">
            <div class="box box-border">
                <div class="box-body">
                    <h4>Đăng nhập</h4>
                    <form action="/login" method="post">
                        <input type="hidden" value="${next}" name="next">
                        <c:if test="${not empty msg }">
                            <div class="alert alert-${type}" role="alert">${msg}</div>
                        </c:if>
                        <div class="form-group">
                            <label>Tên đăng nhập</label>
                            <input type="text" name="username" class="form-control" required>
                        </div>
                        <div class="form-group">
                            <label class="fw">Mật khẩu
                                <a href="/forgot" class="pull-right">Quên mật khẩu?</a>
                            </label>
                            <input type="password" name="password" class="form-control" required>
                        </div>
                        <div class="form-group text-right">
                            <button class="btn btn-primary btn-block">Đăng nhập</button>
                        </div>
                        <div class="form-group text-center">
                            <span class="text-muted">Chưa có tài khoản?</span>
                            <a href="/register">Đăng ký</a>
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
