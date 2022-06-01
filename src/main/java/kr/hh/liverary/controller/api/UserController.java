package kr.hh.liverary.controller.api;

import kr.hh.liverary.config.auth.dto.SessionUser;
import kr.hh.liverary.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@RestController
public class UserController {
    private final HttpSession httpSession;

    @GetMapping("/api/v1/user")
    public SessionUser getUserName() {
        SessionUser user = (SessionUser) httpSession.getAttribute("user");

        if(user == null) throw new UserNotFoundException("User is not found.");

        return user;
    }
}
