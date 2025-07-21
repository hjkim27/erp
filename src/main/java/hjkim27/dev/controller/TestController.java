package hjkim27.dev.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/test")
public class TestController {

    private final String VIEW_FOLDER = "template/";
    private final String VIEW_PATH = VIEW_FOLDER + "path/";

    @GetMapping("/")
    public ModelAndView home() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("index");
        return mav;
    }

    @GetMapping("/test")
    public ModelAndView home1() {
        return new ModelAndView("index");
    }

    @RequestMapping("main")
    public ModelAndView main() {
        ModelAndView mav = new ModelAndView(VIEW_FOLDER + "main");
        return mav;
    }

    @RequestMapping("path/info")
    public ModelAndView info() {
        ModelAndView mav = new ModelAndView(VIEW_PATH + "info");
        return mav;
    }

    @RequestMapping("path/infoData")
    public ModelAndView infoData() {
        ModelAndView mav = new ModelAndView(VIEW_PATH + "infoData");
        return mav;
    }

    @RequestMapping("path/edit")
    public ModelAndView edit() {
        ModelAndView mav = new ModelAndView(VIEW_PATH + "edit");
        return mav;
    }
}
