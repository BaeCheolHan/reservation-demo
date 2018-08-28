package com.demo.reservation.web.service;

import com.demo.reservation.web.dao.RoomDAO;
import com.demo.reservation.web.exception.NoContentException;
import com.demo.reservation.web.exception.NotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
public class RoomServiceTest {

    @MockBean
    private RoomDAO     dao;
    private RoomService service;

    @Before
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);
        service = new RoomService(dao);
    }

    @Test(expected = NoContentException.class)
    public void test_noContent() throws NoContentException {

        given(dao.findAll()).willReturn(null);
        service.findAll();
    }

    @Test(expected = NotFoundException.class)
    public void test_notFound() throws NotFoundException {

        given(dao.findById(any())).willReturn(Optional.empty());

        service.findById(99L);
    }
}