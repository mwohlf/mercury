package net.wohlfart.mercury.controller;

import io.swagger.annotations.Api;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import static net.wohlfart.mercury.SecurityConstants.CATCH_ALL;
import static net.wohlfart.mercury.SecurityConstants.ROOT;


// @Api
@Controller
public class IndexController {
// serve index.html for all routes, see: https://stackoverflow.com/questions/31415052/angular-2-0-router-not-working-on-reloading-the-browser

    /*
     * angular single page subpages mapping
     */
    @GetMapping(path = {ROOT, "/login", "/label", "/settings", "/admin", "/home" })
    public String index() {
        return "forward:index.html";
    }

}