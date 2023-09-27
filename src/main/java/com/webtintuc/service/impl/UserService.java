package com.webtintuc.service.impl;

import com.webtintuc.constant.SystemConstant;
import com.webtintuc.dao.IRoleDao;
import com.webtintuc.dao.IUserDao;
import com.webtintuc.model.User;
import com.webtintuc.service.IUserService;
import com.webtintuc.sqlbuilder.Pageable;
import org.mindrot.jbcrypt.BCrypt;
import org.simplejavamail.api.email.Email;
import org.simplejavamail.api.mailer.Mailer;
import org.simplejavamail.api.mailer.config.TransportStrategy;
import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.mailer.MailerBuilder;

import javax.inject.Inject;
import java.util.List;
import java.util.UUID;

public class UserService implements IUserService {

    @Inject
    private IUserDao userDao;

    @Inject
    private IRoleDao roleDao;

    private User user = new User();

    @Override
    public List<User> findAll(Pageable pageable) {
        List<User> users = userDao.findAll(pageable);
        users.forEach(item -> item.setRole(roleDao.findById(item.getRoleId())));
        return users;
    }

    @Override
    public User findById(Long id) {
        user = userDao.findById(id).setPassword("").setRole(roleDao.findById(user.getRoleId()));
        return user;
    }

    @Override
    public User findByUsername(String username) {
        return userDao.findByUsername(username).setPassword("");
    }

    @Override
    public User findByEmail(String email) {
        return userDao.findByEmail(email).setPassword("");
    }

    @Override
    public User findByToken(String token) {
        user = userDao.findByToken(token);
        return user != null ? user.setPassword("") : null;
    }

    public User validate(String username, String password) {
        User user = userDao.findByUsername(username);
        if (user != null) {
            if (!BCrypt.checkpw(password, user.getPassword())) {
                user = null;
            }
        }
        return user;
    }

    @Override
    public User register(User user) {
        if (userDao.findByEmail(user.getEmail()) != null || userDao.findByUsername(user.getUsername()) != null) {
            return null;
        }
        return userDao.save(user
                .setAvatar("/template/admin/dist/img/user2-160x160.jpg")
                .setPassword(BCrypt.hashpw(user.getPassword(), SystemConstant.SALT)));
    }

    @Override
    public User update(User user) {
        userDao.update(user);
        return findById(user.getId());
    }

    @Override
    public void resetPassword(String email) {
        User user = userDao.findByEmail(email);
        if (user != null) {
            String token = UUID.randomUUID().toString();
            userDao.update(user.setToken(token));
            //send an email contains the token
            Email mail = EmailBuilder.startingBlank().from("Web-tin-tuc", "vantoan1517@gmail.com")
                    .to(user.getUsername(), email)
                    .withSubject("Reset password")
                    .withHTMLText("Click vào <a href='http://localhost:8080/reset?token=" + token + "'>đây</a> để reset password!")
                    .buildEmail();
            Mailer mailer = MailerBuilder
                    .withSMTPServer("smtp.gmail.com", 587, "vantoan1517@gmail.com", "kjjmdtdufyrgfsqz")
                    .withTransportStrategy(TransportStrategy.SMTP_TLS)
                    .buildMailer();
            mailer.sendMail(mail);
        }
    }

    @Override
    public Integer getTotalItems() {
        return userDao.countAll();
    }
}
