<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/common/taglib.jsp" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>Quản lý người dùng - ${session.user.username}</title>
</head>

<body>
<!-- Content Wrapper. Contains page content -->
<div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <section class="content-header">
        <div class="container-fluid">
            <div class="row mb-2">
                <div class="col-sm-6">
                    <h1>Quản lý người dùng</h1>
                </div>
                <div class="col-sm-6">
                    <ol class="breadcrumb float-sm-right">
                        <li class="breadcrumb-item"><a href="/admin/home">Home</a></li>
                        <li class="breadcrumb-item active">Quản lý người dùng</li>
                    </ol>
                </div>
            </div>
        </div>
        <!-- /.container-fluid -->
    </section>

    <!-- Main content -->
    <section class="content">
        <div class="card">
            <div class="card-header">
                <h3 class="card-title">Danh sách người dùng</h3>
                <div class="card-tools">
                    <button type="button" class="btn btn-tool"
                            data-card-widget="collapse" title="Collapse">
                        <i class="fas fa-minus"></i>
                    </button>
                </div>
            </div>
            <div class="card-header">
                <div class="row">
                    <div class="col-3">
                        <a href="/admin/article?tab=edit" class="btn btn-app"
                           title="Thêm bài viết"> <i class="fas fa-plus"></i> Thêm mới
                        </a>
                        <button disabled id="delete-btn" type="button"
                                class="btn btn-app bg-danger" title="Xóa">
                            <i class="fas fa-trash"></i> Xóa
                        </button>
                    </div>
                    <div class="col-3">
                        <div class="form-group row">
                            <label class="col-sm-auto" for="sort-by">Xếp theo</label>
                            <select name="sort-by" id="sort-by" class="col form-control">
                                <option value="fullname">Họ tên</option>
                                <option value="username">Tên đăng nhập</option>
                                <option value="email">Email</option>
                                <option value="roleid">Vai trò</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group col-md-auto">
                        <div class="row">
                            <label class="col-sm-auto" for="sort-order">Thứ tự</label>
                            <select name="sort-order" id="sort-order" class="col form-control">
                                <option value="ASC">Tăng dần</option>
                                <option value="DESC">Giảm dần</option>
                            </select>
                        </div>
                    </div>
                </div>
            </div>
            <div class="card-body p-0">
                <table id="myTable" class="table table-striped table-hover projects">
                    <thead>
                    <tr>
                        <th>
                            <div class="form-check">
                                <input class="form-check-input check-all" type="checkbox" id="select-all">
                            </div>
                        </th>
                        <th>STT</th>
                        <th>Họ tên</th>
                        <th>Tên người dùng</th>
                        <th>Email</th>
                        <th>Vai trò</th>
                        <th>Thao tác</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach varStatus="loop" var="user" items="${model.users}">
                        <tr>
                            <td>
                                <div class="form-check">
                                    <input class="form-check-input check-box" type="checkbox" id="${user.id}">
                                </div>
                            </td>
                            <td>${loop.index+1}</td>
                            <td>${user.fullname}</td>
                            <td>${user.username}</td>
                            <td>${user.email}</td>
                            <td>${user.role.name}</td>
                            <td class="project-actions text-right">
                                <a target="_blank" class="btn btn-info btn-sm" href="/article?id=${user.id}">
                                    <i class="fas fa-folder"></i> Xem
                                </a>
                                <a class="btn btn-success btn-sm"
                                   href="/admin/article?tab=edit&id=${user.id}">
                                    <i class="fas fa-pencil-alt"></i> Sửa
                                </a>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
            <div class="card-footer clear-fix">
                <div class="row">
                    <div class="col"><label for="limit-select">Số dòng trên trang</label></div>
                    <div class="col">
                        <select id="limit-select" class="form-select form-select-sm">
                            <option selected>${model.limit}</option>
                            <c:forEach begin="2" end="5" varStatus="i">
                                <c:if test="${model.limit != i.index }">
                                    <option value="${i.index }">${i.index }</option>
                                </c:if>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col-9">
                        <ul id="pagination" class="pagination-md justify-content-center"></ul>
                    </div>
                </div>
            </div>
        </div>
    </section>
</div>
<script>
    $(document).ready(() => {

        articlePaginate();

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

        $('#delete-btn').click((e) => {
            e.preventDefault();
            const url = '/api/admin/v1/article'
            var data = {};
            var ids = [];
            $('tbody .form-check-input:checked').each((i, e) => {
                ids.push(e.id);
            });
            data['ids'] = ids;
            console.log(JSON.stringify(data));
            Swal.fire({
                title: 'Chắc chưa?',
                text: "Bài viết sẽ bị xóa vĩnh viễn!",
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#3085d6',
                cancelButtonColor: '#d33',
                confirmButtonText: 'Xóa luôn!',
                cancelButtonText: 'Hủy',
                allowOutsideClick: false
            }).then((result) => {
                if (result.isConfirmed) {
                    deleteArticle(url, data);
                }
            });
        });

        function deleteArticle(url, data) {
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

        function articlePaginate() {
            const url = '/admin/users?tab=list';
            var startPage = ${model.page};
            var totalPages = ${model.totalPages};
            var limit = $('#limit-select');
            var sortBy = '${model.sortBy}';
            var sortOrder = '${model.sortOrder}';

            $('#sort-by option').each(function () {
                var value = $(this).attr('value');
                if (sortBy === value) {
                    $(this).attr('selected', true);
                }
            });

            $('#sort-order option').each(function () {
                if (sortOrder === $(this).attr('value')) {
                    $(this).attr('selected', true);
                }
            });

            $('#sort-by').on('change', function () {
                window.location.href = url + '&page=1&limit=' + limit.val() + '&sortBy='
                    + $(this).val() + '&sortOrder=' + sortOrder;
            });

            $('#sort-order').on('change', function () {
                window.location.href = url + '&page=1&limit=' + limit.val() + '&sortBy='
                    + sortBy + '&sortOrder=' + $(this).val();
            });

            limit.change(() => {
                window.location.href = url + '&page=1&limit=' + limit.val() + '&sortBy='
                    + sortBy + '&sortOrder=' + sortOrder;
            });

            $('#pagination').twbsPagination({
                startPage: startPage,
                totalPages: totalPages,
                visiblePages: 7,
                first: 'Đầu',
                last: 'Cuối',
                next: 'Tiếp',
                prev: 'Lùi',
                onPageClick: function (event, page) {
                    if (page !== startPage) {
                        window.location.href = url + '&page=' + page + '&limit=' + limit.val()
                            + '&sortBy=' + sortBy + '&sortOrder=' + sortOrder;
                    }
                }
            });
        }
    });
</script>
</body>
</html>