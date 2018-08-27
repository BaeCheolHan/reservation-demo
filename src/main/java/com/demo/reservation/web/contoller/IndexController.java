package com.demo.reservation.web.contoller;

import com.demo.reservation.web.entity.Room;
import com.demo.reservation.web.entity.User;
import com.demo.reservation.web.exception.NoContentException;
import com.demo.reservation.web.pojo.Calendar;
import com.demo.reservation.web.service.CalenderService;
import com.demo.reservation.web.service.RoomService;
import com.demo.reservation.web.service.UserService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequestMapping(path = { "/view/index", "/" })
public class IndexController {

    private CalenderService calenderService;
    private RoomService     roomService;
    private UserService     userService;

    public IndexController(CalenderService calenderService, RoomService roomService, UserService userService) {

        this.calenderService = calenderService;
        this.roomService = roomService;
        this.userService = userService;
    }

    @GetMapping
    public String main(Model model, @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) throws NoContentException {

        Calendar calendar;

        if (date == null) {
            calendar = calenderService.findDailyCalenderByDay(LocalDate.now());
            model.addAttribute("current", LocalDate.now().format(DateTimeFormatter.ISO_DATE));

        } else {

            calendar = calenderService.findDailyCalenderByDay(date);
            model.addAttribute("current", date.format(DateTimeFormatter.ISO_DATE));
        }

        List<Room> rooms = roomService.findAll();
        List<User> users = userService.findAll();

        model.addAttribute("calender", calendar);
        model.addAttribute("rooms", rooms);
        model.addAttribute("users", users);

        return "/index";
    }
}
