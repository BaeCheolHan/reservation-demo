package com.demo.reservation.web.contoller;

import com.demo.reservation.web.service.CalenderService;
import com.demo.reservation.web.service.RoomService;
import com.demo.reservation.web.service.UserService;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RunWith(SpringRunner.class)
@WebMvcTest(IndexController.class)
public class IndexControllerTest {

    @MockBean
    private CalenderService calenderService;

    @MockBean
    private RoomService roomService;

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mvc;

    @Before
    public void setUp() {

        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void test_currentDateView() throws Exception {

        String expectDateValue = LocalDate.now().format(DateTimeFormatter.ISO_DATE);

        mvc.perform(MockMvcRequestBuilders.get("/").accept(MediaType.TEXT_HTML))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString(expectDateValue)));
    }

    @Test
    public void test_specificDateView() throws Exception {

        String expectDateValue = LocalDate.of(1989, 9, 11).format(DateTimeFormatter.ISO_DATE);

        mvc.perform(MockMvcRequestBuilders.get("/").param("date", expectDateValue).accept(MediaType.TEXT_HTML))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString(expectDateValue)));
    }
}