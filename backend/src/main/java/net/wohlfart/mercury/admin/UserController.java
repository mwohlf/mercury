package net.wohlfart.mercury.admin;

import lombok.extern.slf4j.Slf4j;
import net.wohlfart.mercury.model.User;
import net.wohlfart.mercury.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static net.wohlfart.mercury.SecurityConstants.USERS_ENDPOINT;

@Slf4j
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(USERS_ENDPOINT)
    @PreAuthorize("isAuthenticated()") //
    public ResponseEntity<Page<User>> findPage(@RequestParam(name="page", defaultValue="0", required=false) Integer page,
                                               @RequestParam(name="size", defaultValue="25", required=false) Integer size) throws AuthenticationException {
        Pageable pageable = new PageRequest(page, size);
        Page<User> tablePage = userService.findAll(pageable);
        log.info("found userpage " + tablePage);
        return ResponseEntity.ok(tablePage);
    }

}
