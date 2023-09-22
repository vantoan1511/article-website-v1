<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/common/taglib.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Chỉnh sửa bài viết</title>
</head>
<body>
<!-- Content Wrapper. Contains page content -->
<div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <section class="content-header">
        <div class="container-fluid">
            <div class="row mb-2">
                <div class="col-sm-6">
                    <h1>Chỉnh sửa bài viết</h1>
                </div>
                <div class="col-sm-6">
                    <ol class="breadcrumb float-sm-right">
                        <li class="breadcrumb-item"><a href="/admin/home">Home</a></li>
                        <li class="breadcrumb-item active">Chỉnh sửa bài viết</li>
                    </ol>
                </div>
            </div>
        </div>
        <!-- /.container-fluid -->
    </section>

    <!-- Main content -->
    <section class="content">
        <form id="form">
            <div class="row">
                <div class="col-md-4">
                    <div class="card card-primary">
                        <div class="card-header">
                            <h3 class="card-title">Tổng quan</h3>
                            <div class="card-tools">
                                <button type="button" class="btn btn-tool" data-card-widget="collapse"
                                        title="Collapse">
                                    <i class="fas fa-minus"></i>
                                </button>
                            </div>
                        </div>
                        <div class="card-body">
                            <div class="form-group">
                                <label for="title">Tên bài viết</label>
                                <input type="text" id="title" name="title" class="form-control"
                                       value="${model.article.title}">
                            </div>
                            <div class="form-group">
                                <label for="description">Mô tả ngắn</label>
                                <textarea id="description" name="description"
                                          class="form-control">${model.article.description}</textarea>
                            </div>
                            <div class="form-group">
                                <label for="category">Thể loại</label> <select id="category"
                                                                               name="categoryId"
                                                                               class="form-control custom-select">
                                <option selected>--Chọn thể loại--</option>
                                <c:forEach var="category" items="${model.categories}">
                                    <option
                                            <c:if test="${category.id == model.article.categoryId}">selected
                                    </c:if>
                                            value="${category.id}">${category.name}</option>
                                </c:forEach>
                            </select>
                            </div>
                            <div class="form-group">
                                <label for="thumbnail">Ảnh bìa</label>
                                <input type="text" name="thumbnail" id="thumbnail"
                                       class="form-control" value="${model.article.thumbnail}"
                                       placeholder="Dán đường dẫn vào đây">
                            </div>
                            <div class="form-group">
                                <label for="author">Tác giả</label>
                                <input type="text" id="author" class="form-control"
                                       value="${model.article.createdBy}" disabled>
                            </div>
                            <div class="form-group">
                                <label for="created-date">Ngày tạo</label>
                                <input type="datetime" id="created-date" class="form-control"
                                       value=" <fmt:formatDate value="${model.article.createdDate}"
                                       pattern="EEEE, HH:mm dd/MM/yyyy"/> "
                                       disabled>
                            </div>
                            <div class="form-group">
                                <label for="author">Cập nhật lần cuối</label>
                                <input type="datetime" id="last-modified-date" class="form-control"
                                       value=" <fmt:formatDate value="${model.article.modifiedDate}"
                                       pattern="EEEE, HH:mm dd/MM/yyyy" /> "
                                       disabled>
                            </div>
                            <div class="form-group">
                                <label for="created-date">Bởi</label>
                                <input type="datetime" id="last-modified-by" class="form-control"
                                       value="${model.article.modifiedBy}" disabled>
                            </div>
                            <input type="hidden" id="id" value="${model.article.id}" name="id">
                        </div>
                    </div>
                </div>
                <div class="col-md-8 col-sm-12">
                    <div class="card card-secondary">
                        <div class="card-header">
                            <h3 class="card-title">Nội dung</h3>
                            <div class="card-tools">
                                <button type="button" class="btn btn-tool" data-card-widget="collapse"
                                        title="Collapse">
                                    <i class="fas fa-minus"></i>
                                </button>
                            </div>
                        </div>
                        <div class="card-body">
                            <div class="form-group">
                                <label for="content">Nội dung</label>
                                <textarea id="content" name="content"
                                          class="form-control">${model.article.content}</textarea>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-2">
                            <button type="submit" class="btn btn-app" id="submitBtn">
                                <i class="fas fa-save"></i>Lưu thay đổi
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </form>
    </section>
</div>
<script>
    $(document).ready(() => {
        CKEDITOR.replace('content');
        var url = '/v1/api/admin/article';
        var data = {};
        var id = $('#id').val();
        $('#submitBtn').click((e) => {
            e.preventDefault();
            var formData = $('#form').serializeArray();
            var contentData = CKEDITOR.instances.content.getData();
            console.log(formData);
            $.each(formData, (i, e) => {
                data["" + e.name + ""] = e.value;
            });
            data["content"] = contentData;
            if (id === '') {
                createNews(data);
            } else {
                updateNews(data);
            }
        });

        function createNews(data) {
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
                    showAlert('Thất bại!', 'Thêm mới Thất bại!', 'error', '&msg=add_failed');
                }
            });
        }

        function updateNews(data) {
            $.ajax({
                url: url,
                type: 'PUT',
                contentType: 'application/json',
                dataType: 'json',
                data: JSON.stringify(data),
                success: function (rs) {
                    showAlert('Thành công!', 'Cập nhật thành công!', 'success', '?tab=edit&id=' + rs.id);
                },
                error: function (error) {
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
                    window.location.href = url;
                }
            });
        }
    })
</script>
</body>
</html>