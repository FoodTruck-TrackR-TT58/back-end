package com.lambda.foodtruck.controllers;

import com.lambda.foodtruck.models.Diner;
import com.lambda.foodtruck.models.Operator;
import com.lambda.foodtruck.models.UserMinimum;
import com.lambda.foodtruck.services.DinerServices;
import com.lambda.foodtruck.services.OperatorServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class Oauthendpoints
{
    @Autowired
    private OperatorServices operatorServices;

    @Autowired
    private DinerServices dinerServices;

    @Autowired
    private TokenStore tokenStore;

    @PostMapping(value = "/register", consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<?> addSelf(HttpServletRequest httpServletRequest, @Valid @RequestBody UserMinimum minuser)
        throws
        URISyntaxException
    {
        System.out.println(minuser.getRole().toLowerCase());
        HttpHeaders responseHeaders = new HttpHeaders();

        if(minuser.getRole().equalsIgnoreCase("operator"))
        {
            Operator newoperator = new Operator();

            newoperator.setUsername(minuser.getUsername());
            newoperator.setPassword(minuser.getPassword());
            newoperator.setEmail(minuser.getEmail());
            newoperator = operatorServices.save(newoperator);

            URI newUserURI = ServletUriComponentsBuilder.fromUriString(httpServletRequest.getServerName() + ":" + httpServletRequest.getLocalPort() + "/operators/operator/{operId}")
                .buildAndExpand(newoperator.getUserid())
                .toUri();
            responseHeaders.setLocation(newUserURI);
        }
        else if(minuser.getRole().equalsIgnoreCase("diner"))
        {
            Diner newdiner = new Diner();

            newdiner.setUsername(minuser.getUsername());
            newdiner.setPassword(minuser.getPassword());
            newdiner.setEmail(minuser.getEmail());
            newdiner = dinerServices.save(newdiner);

            URI newUserURI = ServletUriComponentsBuilder.fromUriString(httpServletRequest.getServerName() + ":" + httpServletRequest.getLocalPort() + "/diners/diner/{dinerId}")
                .buildAndExpand(newdiner.getDinerid())
                .toUri();
            responseHeaders.setLocation(newUserURI);
        }

            // return the access token
            // To get the access token, surf to the endpoint /login (which is always on the server where this is running)
            // just as if a client had done this.
            RestTemplate restTemplate = new RestTemplate();
            String requestURI = "http://localhost" + ":" + httpServletRequest.getLocalPort() + "/login";

            List<MediaType> acceptableMediaTypes = new ArrayList<>();
            acceptableMediaTypes.add(MediaType.APPLICATION_JSON);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            headers.setAccept(acceptableMediaTypes);
            headers.setBasicAuth(System.getenv("OAUTHCLIENTID"),
                System.getenv("OAUTHCLIENTSECRET"));

            MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
            map.add("grant_type",
                "password");
            map.add("scope",
                "read write trust");
            map.add("username",
                minuser.getUsername());
            map.add("password",
                minuser.getPassword());

            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map,
                headers);

            String theToken = restTemplate.postForObject(requestURI,
                request,
                String.class);

            return new ResponseEntity<>(theToken,
                responseHeaders,
                HttpStatus.CREATED);

    }

    @GetMapping(value = {"/oauth/revoke-token", "/logout"},
        produces = "application/json")
    public ResponseEntity<?> logoutSelf(HttpServletRequest request)
    {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null)
        {
            // find the token
            String tokenValue = authHeader.replace("Bearer",
                "")
                .trim();
            // and remove it!
            OAuth2AccessToken accessToken = tokenStore.readAccessToken(tokenValue);
            tokenStore.removeAccessToken(accessToken);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
