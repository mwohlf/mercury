package net.wohlfart.mercury.controller;

import io.swagger.annotations.Api;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import static net.wohlfart.mercury.SecurityConstants.CATCH_ALL;
import static net.wohlfart.mercury.SecurityConstants.ROOT;


@Api
@Controller
public class IndexController {

    @GetMapping(path = {ROOT})
    public String index() {
        return "forward:index.html";
    }

}