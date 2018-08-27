package com.demo.reservation.web.service;

import com.demo.reservation.web.dao.UserDAO;
import com.demo.reservation.web.entity.User;
import com.demo.reservation.web.exception.NoContentException;
import com.demo.reservation.web.exception.NotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class UserService {

    private UserDAO dao;

    public UserService(UserDAO dao) {

        this.dao = dao;
    }

    public User findById(Long id) {

        return dao.findById(id).orElseThrow(() -> new NotFoundException(id, User.class));
    }

    public List<User> findAll() throws NoContentException {

        List<User> result = dao.findAll();
        if (result.isEmpty()) {
            throw new NoContentException("User is Empty.");
        }

        return result;
    }

    @PostConstruct
    public void initUsers() {
        // set Room
        String[] usernames = { "Hans", "Key", "Kakao", "Pay" };
        for (String username : usernames) {
            User user = new User();
            user.setName(username);
            dao.save(user);
        }

    }
}
