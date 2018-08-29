package com.demo.reservation.web.resource;

import com.demo.reservation.web.entity.Reservation;
import com.demo.reservation.web.exception.ConflictException;
import com.demo.reservation.web.exception.IllegalBodyException;
import com.demo.reservation.web.exception.InternalException;
import com.demo.reservation.web.pojo.request.ReservationCreateBody;
import com.demo.reservation.web.service.ReservationService;
import org.hamcrest.CoreMatchers;
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
import java.time.LocalTime;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@RunWith(SpringRunner.class)
@WebMvcTest(ReservationResource.class)
public class ReservationResourceTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ReservationService service;

    private ReservationResource   resource;
    private String                testCreateBodyJson;
    private ReservationCreateBody testCreateBodyPojo;

    @Before
    public void setUp() {

        MockitoAnnotations.initMocks(this);

        testCreateBodyJson = "{\n"
                + "  \"roomId\": 1,\n"
                + "  \"userId\": 11,\n"
                + "  \"startTime\": \"01:30\",\n"
                + "  \"endTime\": \"02:30\",\n"
                + "  \"day\": \"2018-08-27\",\n"
                + "  \"repeatCount\": 1\n"
                + "}";

        testCreateBodyPojo = new ReservationCreateBody();
        testCreateBodyPojo.setRoomId(1L);
        testCreateBodyPojo.setUserId(11L);
        testCreateBodyPojo.setStartTime(LocalTime.of(1, 30));
        testCreateBodyPojo.setEndTime(LocalTime.of(2, 30));
        testCreateBodyPojo.setDay(LocalDate.of(2018, 8, 27));
        testCreateBodyPojo.setRepeatCount(1);

        resource = new ReservationResource(service);
    }

    @Test(expected = IllegalBodyException.class)
    public void test_UnprocessableEntityByInvalidTimeFormat() {

        testCreateBodyPojo.setStartTime(LocalTime.of(1, 31));
        resource.create(testCreateBodyPojo);
    }

    @Test(expected = IllegalBodyException.class)
    public void test_UnprocessableEntityByInvalidTimeRange() {

        testCreateBodyPojo.setStartTime(LocalTime.of(1, 30));
        testCreateBodyPojo.setEndTime(LocalTime.of(0, 30));
        resource.create(testCreateBodyPojo);
    }

    @Test
    public void test_created() throws Exception {

        mvc.perform(MockMvcRequestBuilders.post("/api/reservation")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(testCreateBodyJson)
        )
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(print());

    }

    @Test
    public void test_conflict() throws Exception {

        given(service.create(testCreateBodyPojo)).willThrow(new ConflictException(1, Reservation.class));

        mvc.perform(MockMvcRequestBuilders.post("/api/reservation")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(testCreateBodyJson)
        )
                .andExpect(MockMvcResultMatchers.status().isConflict())
                .andDo(print());
    }

    @Test
    public void test_internalServerError() throws Exception {

        Exception[] exceptions = { new InternalException("error test!@!@SDAFASDF"), new IllegalStateException("error test,,,,,,,,,,!!") };

        given(service.create(testCreateBodyPojo)).willThrow(exceptions);

        for (Exception exception : exceptions) {
            mvc.perform(MockMvcRequestBuilders.post("/api/reservation")
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(testCreateBodyJson)
            ).andExpect(MockMvcResultMatchers.status().isInternalServerError())
                    .andExpect(MockMvcResultMatchers.content().string(CoreMatchers.equalTo("An unrecognized error occurred.")))
                    .andDo(print());
        }
    }

    @Test
    public void test_badRequest() throws Exception {
        // override repeatCount : 1 -> 0

        testCreateBodyJson = "{\n"
                + "  \"roomId\": 1,\n"
                + "  \"userId\": 11,\n"
                + "  \"startTime\": \"01:30\",\n"
                + "  \"endTime\": \"02:30\",\n"
                + "  \"day\": \"2018-08-27\",\n"
                + "  \"repeatCount\": 0\n"
                + "}";

        mvc.perform(MockMvcRequestBuilders.post("/api/reservation")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(testCreateBodyJson)
        )
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(print());
    }

}