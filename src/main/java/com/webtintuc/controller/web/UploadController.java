package com.webtintuc.controller.web;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

@WebServlet("/upload")
@MultipartConfig(maxFileSize = 5 * 1024 * 1024, maxRequestSize = 1024 * 1024 * 5)
public class UploadController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/views/web/upload.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uploadPath = getServletContext().getRealPath("/static/images");
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        Part filePart = req.getPart("file");
        String fileName = filePart.getSubmittedFileName();
        String fileExtension = fileName.substring(fileName.lastIndexOf("."));
        String newFileName = req.getParameter("fileName") + fileExtension;

        if (!fileExtension.equalsIgnoreCase(".png") && fileExtension.equalsIgnoreCase(".jpg")) {
            resp.getWriter().println("Dinh dang khong ho tro!");
            return;
        }

        InputStream fileContent = filePart.getInputStream();

        File file = new File(uploadPath + File.separator + newFileName);
        Files.copy(fileContent, file.toPath(), StandardCopyOption.REPLACE_EXISTING);

        resp.getWriter().println("Tai len thanh cong!");
    }
}
