package com.motorider.motobase.controller;

import com.motorider.motobase.enums.TypeAccount;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.Version;
import com.restfb.types.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.motorider.motobase.constant.SecurityConstant.*;

@RestController
@RequestMapping("/signin/facebook")
public class FacebookSignInController {

    @RequestMapping(path = {"", "/"}, method = RequestMethod.POST)
    public void signIn(@RequestParam(name = "idToken") String idToken, HttpServletResponse response) throws GeneralSecurityException, IOException {
        Map<String, Object> claims;
        FacebookClient facebookClient = new DefaultFacebookClient(idToken, Version.VERSION_2_4);
        User user = facebookClient.fetchObject("me", User.class,
                Parameter.with("fields", "id,name,email,link,age_range,gender"));

        String email = user.getEmail();

        System.out.println(email);

        if (email != null) {
            claims = new HashMap<>();
            claims.put("id", user.getId());
            claims.put("name", user.getName());
            claims.put("link", user.getLink());
            claims.put("ageRange", user.getAgeRange());
            claims.put("gender", user.getGender());
            claims.put("source", TypeAccount.FACEBOOK);
            String token = Jwts.builder()
                    .setClaims(claims)
                    .setSubject(email)
                    .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                    .signWith(SignatureAlgorithm.HS512, SECRET.getBytes())
                    .compact();
            response.setStatus(HttpStatus.SC_OK);
            response.addHeader(HEADER_STRING, TOKEN_PREFIX + token);
        }
    }
}