package com.motorider.motobase.controller;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.motorider.motobase.enums.TypeAccount;
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
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.motorider.motobase.constant.SecurityConstant.*;

@RestController
@RequestMapping("/signin/google")
public class GoogleSignInController {

    @RequestMapping(path = { "", "/" }, method = RequestMethod.POST)
    public void signIn(@RequestParam(name = "idToken") String idToken, HttpServletResponse response) throws GeneralSecurityException, IOException {
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(GoogleNetHttpTransport.newTrustedTransport(), JacksonFactory.getDefaultInstance())
                .setAudience(Collections.singletonList("391677982885-78iis2nefchdonb2id0j089jjtrjb5oc.apps.googleusercontent.com"))
                // Or, if multiple clients access the backend:
                //.setAudience(Arrays.asList(CLIENT_ID_1, CLIENT_ID_2, CLIENT_ID_3))
                .build();

        // (Receive idTokenString by HTTPS POST)

        GoogleIdToken googleIdToken = verifier.verify(idToken);
        if (googleIdToken != null) {
            Map<String, Object> claims;
            GoogleIdToken.Payload payload = googleIdToken.getPayload();

            // Print user identifier
            String userId = payload.getSubject();
            System.out.println("User ID: " + userId);

            // Get profile information from payload
            String email = payload.getEmail();
            boolean emailVerified = Boolean.valueOf(payload.getEmailVerified());
            String name = (String) payload.get("name");
            String pictureUrl = (String) payload.get("picture");
            String locale = (String) payload.get("locale");
            String familyName = (String) payload.get("family_name");
            String givenName = (String) payload.get("given_name");


            claims = new HashMap<>();
            claims.put("id", userId);
            claims.put("name", name);
            claims.put("pictureUrl", pictureUrl);
            claims.put("locale", locale);
            claims.put("familyName", familyName);
            claims.put("source", TypeAccount.GMAIL);
            String token = Jwts.builder()
                    .setSubject(email)
                    .setClaims(claims)
                    .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                    .signWith(SignatureAlgorithm.HS512, SECRET.getBytes())
                    .compact();
            response.setStatus(HttpStatus.SC_OK);
            response.addHeader(HEADER_STRING, TOKEN_PREFIX + token);
        } else {
            response.setStatus(HttpStatus.SC_BAD_REQUEST);
        }
    }

}
