package com.bryce;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang3.StringUtils;




@Controller
class SessionController {

    private static final Logger logger = LoggerFactory.getLogger(SessionController.class);
    
    @Value("${logging.file.name}")
    private String logFilePath;
    
    @GetMapping("/")
    public String home(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(true);
        Integer counter = (Integer) session.getAttribute("counter");
        if (counter == null) {
            counter = 1;
        } else {
            counter++;
        }
        session.setAttribute("counter", counter);
        String logMessage = String.format("Session ID: %s, Refresh Counter: %d, Log File Path: %s",
                session.getId(), counter, logFilePath);
        
        logMessage = StringUtils.upperCase(logMessage);
        
        logger.info(logMessage);

        model.addAttribute("sessionId", session.getId());
        model.addAttribute("counter", counter);
        model.addAttribute("logFilePath", logFilePath);

        return "count";
    }
}
