package com.motorider.motobase.filter;

import com.motorider.motobase.controller.UserController;
import com.motorider.motobase.dto.UserDTO;
import com.motorider.motobase.enums.TypeAccount;
import com.motorider.motobase.exception.DataBaseResultNotFoundException;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.motorider.motobase.constant.SecurityConstant.*;
import static com.motorider.motobase.constant.UserConstant.TYPE_ACCOUNT;
import static com.motorider.motobase.enums.Rol.BIKER;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    @Autowired
    private UserController userController;

    public JWTAuthorizationFilter(AuthenticationManager authManager) {
        super(authManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws IOException, ServletException {
        String header = req.getHeader(HEADER_STRING);

        if (header == null || !header.startsWith(TOKEN_PREFIX)) {
            chain.doFilter(req, res);
            return;
        }

        if (userController == null){
            ServletContext servletContext = req.getServletContext();
            WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
            userController = webApplicationContext.getBean(UserController.class);
        }


        UsernamePasswordAuthenticationToken authentication = null;
        try {
            authentication = getAuthentication(req);
        } catch (DataBaseResultNotFoundException e) {
            e.printStackTrace();
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(req, res);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) throws DataBaseResultNotFoundException {
        String token = request.getHeader(HEADER_STRING);
        List<GrantedAuthority> authorities = null;
        if (token != null) {
            String email = Jwts.parser()
                    .setSigningKey(SECRET.getBytes())
                    .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                    .getBody()
                    .getSubject();

            String typeAccount = Jwts.parser()
                    .setSigningKey(SECRET.getBytes())
                    .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                    .getBody().get(TYPE_ACCOUNT, String.class);


            if (email != null) {
                UserDTO user = userController.findByEmail(email, TypeAccount.valueOf(typeAccount)).getBody();
                if(user != null) {
                    authorities = getGrantedAuthorities(Arrays.asList(BIKER.name()));
                }
                return new UsernamePasswordAuthenticationToken(email, null, authorities);
            }
            return null;
        }
        return null;
    }
    private List<GrantedAuthority> getGrantedAuthorities(List<String> privileges) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String privilege : privileges) {
            authorities.add(new SimpleGrantedAuthority(privilege));
        }
        return authorities;
    }
}