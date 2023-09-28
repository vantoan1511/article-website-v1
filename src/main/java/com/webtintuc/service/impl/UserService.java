package com.webtintuc.service.impl;

import com.webtintuc.constant.SystemConstant;
import com.webtintuc.converter.UserConverter;
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
import java.lang.reflect.Field;
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
        user = userDao.findById(id);
        user.setPassword("");
        user.setRole(roleDao.findById(user.getRoleId()));
        return user;
    }

    @Override
    public User findByUsername(String username) {
        user = userDao.findByUsername(username);
        user.setPassword("");
        return user;
    }

    @Override
    public User findByEmail(String email) {
        user = userDao.findByEmail(email);
        user.setPassword("");
        return user;
    }

    @Override
    public User findByToken(String token) {
        user = userDao.findByToken(token);
        if (user != null) {
            user.setPassword("");
        }
        return user != null ? user : null;
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
        user.setAvatar("/template/admin/dist/img/user2-160x160.jpg");
        user.setPassword(BCrypt.hashpw(user.getPassword(), SystemConstant.SALT));
        return userDao.save(user);
    }

    @Override
    public User update(User user) {
        User oldUser = userDao.findById(user.getId());
        UserConverter.convert(oldUser, user);
        if (user.getPassword() != null) {
            oldUser.setPassword(BCrypt.hashpw(user.getPassword(), SystemConstant.SALT));
        }
        userDao.update(oldUser);
        return findById(user.getId());
    }

    @Override
    public void resetPassword(String email) {
        User user = userDao.findByEmail(email);
        if (user != null) {
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            userDao.update(user);
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
