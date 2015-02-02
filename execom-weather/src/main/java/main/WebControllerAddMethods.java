package main;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class WebControllerAddMethods {

    public String getAuthenticatedUsersName() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}