package com.manager.hotel.web.handler;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import static jakarta.servlet.RequestDispatcher.ERROR_STATUS_CODE;

@Slf4j
public class WebErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(
            final Model model,
            final HttpServletRequest request) {
        Object status = request.getAttribute(ERROR_STATUS_CODE);
        log.error(status.toString());
        try {
            int statusCode = Integer.parseInt(status.toString());
            model.addAttribute("status", statusCode);
        } catch (Exception e) {
            log.error(e.getMessage());
            model.addAttribute("status", 500);
        }
        return "error";
    }
}
