package com.pvtoc.utils;


import com.pvtoc.constants.UserRole;
import com.pvtoc.dao.UserDao;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
@Data
public class AuthSuccessHandler implements AuthenticationSuccessHandler {
    @Autowired
    UserDao userDao;

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {

        String targetUrl = determineTargetUrl(authentication);

        if (response.isCommitted()) {
            System.out.println("Can't redirect");
            return;
        }
        redirectStrategy.sendRedirect(request, response, targetUrl);
    }


    protected String determineTargetUrl(Authentication authentication) {
        String url = "";
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        List<String> roles = new ArrayList<String>();
        for (GrantedAuthority a : authorities) {
            roles.add(a.getAuthority());
        }
        UserRole userRole = UserRole.valueOf(roles.get(0));
        String login = (( User ) authentication.getPrincipal()).getUsername();

        if (UserRole.ROLE_ADMIN.equals(userRole)) {
            url = "/admins/" + login;
        } else if (UserRole.ROLE_CLIENT.equals(userRole)) {
            url = "/clients/" + login;
        }
        return url;
    }
}
