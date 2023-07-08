package com.priyanka.tinyurl.controller;

import com.datastax.driver.core.exceptions.AlreadyExistsException;
import com.priyanka.tinyurl.request.CreateURLRequest;
import com.priyanka.tinyurl.request.DeleteURLRequest;
import com.priyanka.tinyurl.service.RateLimitingService;
import com.priyanka.tinyurl.service.URLService;
import com.priyanka.tinyurl.utils.ArgumentChecks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingRequestValueException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.NoSuchAlgorithmException;
import java.sql.Date;

@RestController
@RequestMapping("short-URL")
public class ShortURLController {

    @Autowired
    @Qualifier("encodingURLService")
    private URLService encodingURLService;

    @Autowired
    private RateLimitingService rateLimitingService;

    /**
     * Given a URL, this generates a shorter and unique alias of it.
     * This is called a short link. This link is short enough to be easily copied and pasted into applications.
     *
     * @param createURLRequest
     * @return A successful creation returns the shortened URL; otherwise, it returns an error code
     */
    @PostMapping
    public ResponseEntity<String> createShortURL(@RequestBody CreateURLRequest createURLRequest) {
        if(ArgumentChecks.nullOrEmpty(createURLRequest.getOriginal_url())){
            return new ResponseEntity<>("Original URL to be shortened must be provided", HttpStatus.BAD_REQUEST);
        }

        String expiryDateInRequest = createURLRequest.getExpire_date();
        if(expiryDateInRequest != null){
            Date expiryDate = Date.valueOf(expiryDateInRequest);
            if(!expiryDate.after(new Date(new java.util.Date().getTime()))){
                return new ResponseEntity<>("Expiration date for the shortened URL must be after today", HttpStatus.BAD_REQUEST);
            }
        }

        try {
            String shortURL = encodingURLService.getShortURL(createURLRequest);
            return new ResponseEntity<>("http://localhost:8080/" + shortURL, HttpStatus.OK);
        }catch (AlreadyExistsException e){
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Custom alias requested already exists");
        } catch (NoSuchAlgorithmException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    /**
     *
     * @param deleteURLRequest
     * @return A successful deletion returns ‘URL Removed’.
     * @throws MissingRequestValueException
     */
    @DeleteMapping
    public ResponseEntity<String> deleteURL(@RequestBody DeleteURLRequest deleteURLRequest) throws MissingRequestValueException {
        if(deleteURLRequest.getUrl_key() == null || deleteURLRequest.getUrl_key().isEmpty()){
            throw new MissingRequestValueException("Shortened URL to be removed must be provided");
        }
        encodingURLService.deleteURL(deleteURLRequest);
        return new ResponseEntity<>("URL Removed", HttpStatus.OK);
    }
}
