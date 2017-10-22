package net.wohlfart.mercury.admin;

import lombok.extern.slf4j.Slf4j;
import net.wohlfart.mercury.model.User;
import net.wohlfart.mercury.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import static net.wohlfart.mercury.SecurityConstants.USERS_ENDPOINT;

@Slf4j
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(USERS_ENDPOINT)
    @PreAuthorize("hisAuthenticated()")
    public ResponseEntity<Page<User>> findPage() throws AuthenticationException {
        Pageable pageable = new PageRequest(0, 100);
        Page<User> page = userService.findAll(pageable);
        log.info("found userpage " + page);
        return ResponseEntity.ok(page);
    }

}
