<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/common/taglib.jsp" %>

<!DOCTYPE html>
<html>
<head>
    <title>Reset mật khẩu</title>
</head>
<body>
<section class="login first grey">
    <div class="container">
        <div class="box-wrapper">
            <div class="box box-border">
                <div class="box-body">
                    <h4>Reset mật khẩu</h4>
                    <form action="/forgot" method="post">
                        <c:if test="${not empty msg }">
                            <div class="alert alert-${type}" role="alert">${msg}</div>
                        </c:if>
                        <div class="form-group">
                            <label>Nhập email đã đăng ký</label>
                            <input type="email" name="email" class="form-control" required>
                        </div>
                        <div class="form-group text-right">
                            <button class="btn btn-primary btn-block">Gửi</button>
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
