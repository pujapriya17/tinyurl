package com.priyanka.tinyurl.controller;

import com.priyanka.tinyurl.service.URLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.Optional;

@RestController
@RequestMapping("/")
public class RedirectController {
    @Autowired
    private URLService urlService;

    /**
     * When users accesses a short link, this redirects them to the original link.
     * @param response
     * @param shortURL
     * @throws IOException
     */
    @GetMapping("{shortURL}")
    public void redirectShortURL(HttpServletResponse response, @PathVariable String shortURL) throws IOException {
        Optional<String> originalURL = urlService.getLongURL(shortURL);
        if(originalURL.isPresent()) {
            response.sendRedirect(originalURL.get());
        }else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
