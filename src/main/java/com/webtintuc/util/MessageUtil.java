package com.webtintuc.util;

import javax.servlet.http.HttpServletRequest;

public class MessageUtil {
    public static void showMessage(HttpServletRequest req) {
        String type = req.getParameter("type");
        String msg = req.getParameter("msg");
        String msgContent = "";
        if (msg != null) {
            if (msg.equals("login_failed")) {
                msgContent = "Tên đăng nhập/Mật khẩu không chính xác!";
            } else if (msg.equals("email_username_existed")) {
                msgContent = "Email hoặc tên đăng nhập đã tồn tại!";
            } else if (msg.equals("register_success")) {
                msgContent = "Đăng ký thành công! Đăng nhập để tiếp tục.";
            } else if (msg.equals("email_confirmed")) {
                msgContent = "Đã gửi link reset mật khẩu đến email, nếu email có tồn tại trên hệ thống.";
            } else if (msg.equals("reset_success")) {
                msgContent = "Đổi mật khẩu thành công!";
            } else if (msg.equals("timeout")) {
                msgContent = "Phiên đăng nhập đã hết hạn!";
            } else if (msg.equals("login_required")) {
                msgContent = "Cần đăng nhập trước!";
            } else if (msg.equals("profile_updated")) {
                msgContent = "Cập nhật thành công. Đăng nhập lại để áp dụng thay đổi!";
            }
            req.setAttribute("type", type);
            req.setAttribute("msg", msgContent);
        }
    }
}
