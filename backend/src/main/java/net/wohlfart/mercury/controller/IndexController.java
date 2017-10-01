package net.wohlfart.mercury.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import static net.wohlfart.mercury.SecurityConstants.ROOT;


@Controller
public class IndexController {

    @GetMapping(path = {ROOT})
    public String index() {
        return "forward:index.html";
    }

}