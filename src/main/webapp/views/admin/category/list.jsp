<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/common/taglib.jsp" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>Quản lý thể loại - ${session.user.username}</title>
</head>

<body>
<!-- Content Wrapper. Contains page content -->
<div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <section class="content-header">
        <div class="container-fluid">
            <div class="row mb-2">
                <div class="col-sm-6">
                    <h1>Quản lý thể loại</h1>
                </div>
                <div class="col-sm-6">
                    <ol class="breadcrumb float-sm-right">
                        <li class="breadcrumb-item"><a href="/admin/home">Home</a></li>
                        <li class="breadcrumb-item active">Quản lý thể loại</li>
                    </ol>
                </div>
            </div>
        </div>
        <!-- /.container-fluid -->
    </section>

    <!-- Main content -->
    <section class="content">
        <div class="row">
            <div class="col-md-4">
                <div class="card card-primary">
                    <div class="card-header">
                        <h3 class="card-title">Chỉnh sửa/Thêm mới</h3>
                        <div class="card-tools">
                            <button type="button" class="btn btn-tool" data-card-widget="collapse"
                                    title="Collapse">
                                <i class="fas fa-minus"></i>
                            </button>
                        </div>
                    </div>
                    <div class="card-body">
                        <form id="submit-form">
                            <div class="form-group">
                                <label for="name">Tên thể loại</label>
                                <input type="text" id="name" name="name" class="form-control" required
                                       value="${category.name}">
                            </div>
                            <div class="form-group">
                                <label for="name">Mã thể loại</label>
                                <input type="text" id="code" name="code" class="form-control" required
                                       value="${category.code}">
                            </div>
                            <input type="hidden" id="id" value="${category.id}" name="id">
                            <button type="button" class="btn btn-app" id="submit-btn">
                                <i class="fas fa-save"></i>Lưu thay đổi
                            </button>
                        </form>
                    </div>
                </div>
            </div>
            <div class="col-md-8">
                <div class="card">
                    <div class="card-header">
                        <h3 class="card-title">Danh sách thể loại</h3>
                        <div class="card-tools">
                            <button type="button" class="btn btn-tool"
                                    data-card-widget="collapse" title="Collapse">
                                <i class="fas fa-minus"></i>
                            </button>
                        </div>
                    </div>
                    <div class="card-header">
                        <div class="row">
                            <div class="col">
                                <a href="/admin/category" class="btn btn-app"
                                   title="Thêm thể loại"> <i class="fas fa-plus"></i> Thêm mới
                                </a>
                                <button disabled id="delete-btn" type="button"
                                        class="btn btn-app bg-danger" title="Xóa">
                                    <i class="fas fa-trash"></i> Xóa
                                </button>
                            </div>
                        </div>
                    </div>
                    <div class="card-body p-0">
                        <table id="myTable" class="table table-striped table-hover projects">
                            <thead>
                            <tr>
                                <th>
                                    <div class="form-check">
                                        <input class="form-check-input" type="checkbox" id="select-all">
                                    </div>
                                </th>
                                <th>STT</th>
                                <th>Mã</th>
                                <th>Tên</th>
                                <th>Thao tác</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach varStatus="loop" var="category" items="${categories}">
                                <tr>
                                    <td>
                                        <div class="form-check">
                                            <input class="form-check-input check-box" type="checkbox"
                                                   id="${category.id}">
                                        </div>
                                    </td>
                                    <td>${loop.index+1}</td>
                                    <td>${category.code}</td>
                                    <td>${category.name}</td>
                                    <td>
                                        <a class="btn btn-outline-success btn-sm"
                                           href="/admin/category?id=${category.id}">
                                            <i class="fas fa-pencil-alt"></i> Sửa
                                        </a>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </section>
</div>
<script>
    $(document).ready(() => {
        $('#select-all').change(function () {
            $('.check-box').prop('checked', this.checked);
        });
        $('.check-box').change(function () {
            if ($('.check-box:checked').length === $('.check-box').length) {
                $('#select-all').prop('checked', true);
            } else {
                $('#select-all').prop('checked', false);
            }
        });
        $('.form-check-input').change(function () {
            if ($('.form-check-input:checked').length === 0) {
                $('#delete-btn').prop('disabled', true);
            } else {
                $('#delete-btn').prop('disabled', false);
            }
        });

        $('#submit-btn').click((e) => {
            e.preventDefault();
            url = '/api/admin/v1/category';
            var formData = $('#submit-form').serializeArray();
            var data = {};
            var id = $('#id').val();
            console.log(formData);
            $.each(formData, (i, e) => {
                data["" + e.name + ""] = e.value;
            });
            if (id === '') {
                createCategory(url, data);
            } else {
                updateCategory(url, data);
            }
        });

        $('#delete-btn').click((e) => {
            e.preventDefault();
            const url = '/v1/api/admin/category'
            var data = {};
            var ids = [];
            $('tbody .form-check-input:checked').each((i, e) => {
                ids.push(e.id);
            });
            data['ids'] = ids;
            console.log(JSON.stringify(data));
            Swal.fire({
                title: 'Chắc chưa?',
                text: "Thể loại và các bài viết của nó sẽ bị xóa vĩnh viễn!",
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#3085d6',
                cancelButtonColor: '#d33',
                confirmButtonText: 'Xóa luôn!',
                cancelButtonText: 'Hủy',
                allowOutsideClick: false
            }).then((result) => {
                if (result.isConfirmed) {
                    deleteCategory(url, data);
                }
            });
        });

        function deleteCategory(url, data) {
            $.ajax({
                url: url,
                type: 'DELETE',
                contentType: 'application/json',
                dataType: 'json',
                data: JSON.stringify(data),
                success: function (result) {
                    Swal.fire({
                        title: 'Đã xóa!',
                        icon: 'success',
                        confirmButtonText: 'OK',
                        allowOutsideClick: false
                    }).then((result) => {
                        if (result.isConfirmed) {
                            location.reload();
                        }
                    });
                },
                error: function (error) {
                    console.log(error);
                }
            });
        }

        function createCategory(url, data) {
            $.ajax({
                url: url,
                type: 'POST',
                contentType: 'application/json',
                dataType: 'json',
                data: JSON.stringify(data),
                success: function (rs) {
                    showAlert('Thành công!', 'Thêm mới thành công!', 'success', '?id=' + rs.id);
                },
                error: function (error) {
                    showAlert('Thất bại!', 'Thêm mới Thất bại!', 'error', '');
                }
            });
        }

        function updateCategory(url, data) {
            $.ajax({
                url: url,
                type: 'PUT',
                contentType: 'application/json',
                dataType: 'json',
                data: JSON.stringify(data),
                success: function (rs) {
                    showAlert('Thành công!', 'Cập nhật thành công!', 'success', '?id=' + rs.id);
                },
                error: function (error) {
                    showAlert('Thất bại!', 'Cập nhật Thất bại!', 'error', '');
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
                    window.location.href = url;
                }
            });
        }
    });
</script>
</body>
</html>