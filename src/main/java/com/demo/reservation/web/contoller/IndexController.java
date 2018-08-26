package com.demo.reservation.web.contoller;

import com.demo.reservation.web.service.CalenderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;

@Controller
@RequestMapping(path = {"/view/index", "/"})
public class IndexController {

    CalenderService calenderService;

    public IndexController(CalenderService calenderService) {
        this.calenderService = calenderService;
    }

    @GetMapping
    public String main(Model model) {


        model.addAttribute("calender", calenderService.findMonthlyMapByDate(new Date()));
        calenderService.findMonthlyMapByDate(new Date()).forEach(System.out::println);

        model.addAttribute("title", "my title! yo----man!");
        return "/index";
    }
}
