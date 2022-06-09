package kr.hh.liverary.controller.servlet;

import kr.hh.liverary.config.auth.LoginUser;
import kr.hh.liverary.config.auth.dto.SessionUser;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class ServletController implements ErrorController {
    private final HttpSession httpSession;

    @GetMapping({"/", "/error"})
    public String index(@LoginUser SessionUser user) {
        if (user != null) {
        }
        return "index.html";
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
