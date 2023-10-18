<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@include file="/common/taglib.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>User profile - ${session.user.username}</title>
</head>
<body>
<div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <section class="content-header">
        <div class="container-fluid">
            <div class="row mb-2">
                <div class="col-sm-6">
                    <h1>Profile</h1>
                </div>
                <div class="col-sm-6">
                    <ol class="breadcrumb float-sm-right">
                        <li class="breadcrumb-item"><a href="/admin/home">Home</a></li>
                        <li class="breadcrumb-item active">User Profile</li>
                    </ol>
                </div>
            </div>
        </div><!-- /.container-fluid -->
    </section>

    <!-- Main content -->
    <section class="content">
        <div class="container-fluid">
            <div class="row">
                <div class="col-md-3">

                    <!-- Profile Image -->
                    <div class="card card-primary card-outline">
                        <div class="card-body box-profile">
                            <div class="text-center">
                                <img class="profile-user-img img-fluid img-circle"
                                     src="${model.user.avatar}"
                                     alt="user-avatar">
                            </div>

                            <h3 class="profile-username text-center">${model.user.fullname}</h3>

                            <p class="text-muted text-center">${model.user.role.name}</p>

                            <ul class="list-group list-group-unbordered mb-3">
                                <li class="list-group-item">
                                    <b>Bài viết</b> <a class="float-right">${totalArticles}</a>
                                </li>
                                <li class="list-group-item">
                                    <b>Bình luận</b> <a class="float-right">${totalComments}</a>
                                </li>
                            </ul>
                        </div>
                        <!-- /.card-body -->
                    </div>
                    <!-- /.card -->
                </div>
                <!-- /.col -->
                <div class="col-md-9">
                    <form id="form">
                        <div class="row">
                            <div class="col-md-6 col-sm-12">
                                <div class="card">
                                    <div class="card-header">
                                        <h3 class="card-title">Thông tin chung</h3>
                                    </div>
                                    <div class="card-body">
                                        <div class="form-group">
                                            <label for="fullname">Họ tên</label>
                                            <input type="text" id="fullname" name="fullname" class="form-control"
                                                   value="${model.user.fullname}">
                                        </div>

                                        <div class="form-group">
                                            <label for="role">Vai trò</label>
                                            <select id="role" name="roleId" class="form-control custom-select">
                                                <option selected>--Chọn vai trò--</option>
                                                <c:forEach var="role" items="${model.roles}">
                                                    <option
                                                            <c:if test="${role.id == model.user.roleId}">selected
                                                    </c:if>
                                                            value="${role.id}">${role.name}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                        <div class="form-group">
                                            <label for="avatar">Ảnh đại diện</label>
                                            <input type="text" name="avatar" id="avatar"
                                                   class="form-control" value="${model.user.avatar}"
                                                   placeholder="Dán đường dẫn vào đây">
                                        </div>
                                        <input type="hidden" id="id" value="${model.user.id}" name="id">
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-6 col-sm-12">
                                <div class="card">
                                    <div class="card-header">
                                        <h3 class="card-title">Thông tin đăng nhập</h3>
                                    </div>
                                    <div class="card-body">
                                        <div class="form-group">
                                            <label for="username">Tên đăng nhập</label>
                                            <input type="text" id="username" name="username" class="form-control"
                                                   disabled
                                                   value="${model.user.username}">
                                        </div>
                                        <div class="form-group">
                                            <label for="email">Email</label>
                                            <input type="text" id="email" name="email" class="form-control"
                                                   value="${model.user.email}">
                                        </div>
                                        <div class="form-group">
                                            <label for="password">Password</label>
                                            <input type="password" id="password" name="password" class="form-control"
                                                   value="${model.user.password}" placeholder="Bỏ trống để giữ nguyên">
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-12 text-right">
                                        <button type="submit" class="btn btn-app" id="submitBtn">
                                            <i class="fas fa-save"></i>Lưu thay đổi
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
                <!-- /.col -->
            </div>
            <!-- /.row -->
        </div><!-- /.container-fluid -->
    </section>
    <!-- /.content -->
</div>
<script>
    $(document).ready(() => {
        var id = $('#id').val();
        if (id === '') {
            $('#username').prop('disabled', false);
        }
        //
        var url = '/v1/api/admin/user';
        var data = {};
        $('#submitBtn').click((e) => {
            e.preventDefault();
            var formData = $('#form').serializeArray();
            console.log(formData);
            $.each(formData, (i, e) => {
                data["" + e.name + ""] = e.value;
            });
            console.log(data);
            if (id === '') {
                createUser(data);
            } else {
                updateUser(data);
            }
        });

        function createUser(data) {
            $.ajax({
                url: url,
                type: 'POST',
                contentType: 'application/json',
                dataType: 'json',
                data: JSON.stringify(data),
                success: function (rs) {
                    showAlert('Thành công!', 'Thêm mới thành công!', 'success', '?tab=edit&id=' + rs.id);
                },
                error: function (error) {
                    showAlert('Thất bại!', 'Thêm mới Thất bại!', 'error');
                }
            });
        }

        function updateUser(data) {
            $.ajax({
                url: url,
                type: 'PUT',
                contentType: 'application/json',
                dataType: 'json',
                data: JSON.stringify(data),
                success: function (rs) {
                    console.log(rs);
                    showAlert('Thành công!', 'Cập nhật thành công!', 'success', '?tab=edit&id=' + id);
                },
                error: function (error) {
                    console.log(error);
                    showAlert('Thất bại!', 'Cập nhật Thất bại!', 'error', '?tab=edit&id=' + id + '&msg=update_failed');
                }
            });
        }

        function showAlert(title, text, icon, url) {
            Swal.fire({
                title: title,
                text: text,
                icon: icon,
                confirmButtonText: 'OK!',
                allowOutsideClick: false
            }).then((result) => {
                if (result.isConfirmed) {
                    if (url !== undefined) {
                        window.location.href = url;
                    }
                }
            });
        }
    })
</script>
</body>
</html>
