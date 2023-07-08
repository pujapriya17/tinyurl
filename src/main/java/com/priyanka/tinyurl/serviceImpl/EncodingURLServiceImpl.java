package com.priyanka.tinyurl.serviceImpl;

import com.datastax.driver.core.exceptions.AlreadyExistsException;
import com.priyanka.tinyurl.dao.UrlDao;
import com.priyanka.tinyurl.dao.UserDao;
import com.priyanka.tinyurl.entity.URL;
import com.priyanka.tinyurl.entity.User;
import com.priyanka.tinyurl.request.CreateURLRequest;
import com.priyanka.tinyurl.request.DeleteURLRequest;
import com.priyanka.tinyurl.service.URLService;
import com.priyanka.tinyurl.service.UserService;
import com.priyanka.tinyurl.utils.ArgumentChecks;
import com.priyanka.tinyurl.utils.DateUtils;
import com.priyanka.tinyurl.utils.MessageDigestAlgorithm;
import com.priyanka.tinyurl.utils.URLUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.sql.Date;
import java.util.Calendar;
import java.util.Optional;

@Service("encodingURLService")
public class EncodingURLServiceImpl implements URLService {

    private static final int DEFAULT_VALIDITY = 2;
    @Autowired
    private Environment env;

    @Autowired
    private UrlDao urlDao;

    @Autowired
    private UserService userService;

    Logger logger = LoggerFactory.getLogger(EncodingURLServiceImpl.class);
    @Override
    public String getShortURL(CreateURLRequest createURLRequest) throws NoSuchAlgorithmException {
        String res = null;
        try {
            String algorithm = env.getProperty("url.shortening.algorithm");
            int length = Integer.parseInt(env.getProperty("shortened.url.length"));

            if(createURLRequest.getCustom_alias() == null || createURLRequest.getCustom_alias().isEmpty()) {
                //hash
                Optional<String> hashedValue = Optional.of(URLUtils.getEncryptedString(createURLRequest.getOriginal_url(), MessageDigestAlgorithm.getAlgorithm(algorithm)));
                logger.info("Hashed value: {}", hashedValue);

                //encode
                if (hashedValue.isPresent()) {
                    res = Base64.getEncoder().encodeToString(hashedValue.get().getBytes());
                    res = res.substring(0, length);
                }
            }else{
                res = createURLRequest.getCustom_alias();
                URL url = urlDao.getURL(res);
                System.out.println(url);
                if(url != null){
                    if(!url.getOriginalURL().equals(createURLRequest.getOriginal_url())){
                        throw new IllegalArgumentException("The short URL is already in use for a different URL");
                    }else{
                        return res;
                    }
                }
            }

            //persist
            java.util.Date currentDate = new java.util.Date();
            Date creationDate = new Date(currentDate.getTime());
            Date expirationDate = ArgumentChecks.nullOrEmpty(createURLRequest.getExpire_date())
                                    ? new Date(DateUtils.addToDate(currentDate, DEFAULT_VALIDITY, Calendar.YEAR).getTime())
                                    : Date.valueOf(createURLRequest.getExpire_date());

            User user = userService.getUser(createURLRequest.getUser_id());

            urlDao.addUrl(new URL(res, createURLRequest.getOriginal_url(), creationDate, expirationDate, user.getUserId()));
        } catch (NoSuchAlgorithmException e){
            logger.error("Incorrect Algorithm");
            throw e;
        } catch (AlreadyExistsException e){
            if(createURLRequest.getCustom_alias() != null && !createURLRequest.getCustom_alias().isEmpty()) {
                logger.error("Custom Short URL already exists for some URL");
                throw e;
            }else{
                logger.debug("The URL has already been shortened");
            }
        }

        return res;
    }

    @Override
    public Optional<String> getLongURL(String shortURL) {
        logger.debug("Getting long URL for {}", shortURL);
        URL url = urlDao.getURL(shortURL);
        logger.debug("URL for hash {} : {}", shortURL, url);
        java.util.Date currentDate = new java.util.Date();
        if(url != null && url.getExpirationDate().compareTo(currentDate) > 0) {
            logger.debug("Expiration date {} is after the current date {}", url.getExpirationDate(), currentDate);
            return Optional.of(url.getOriginalURL());
        }else{
            return Optional.empty();
        }
    }

    @Override
    public void deleteURL(DeleteURLRequest deleteURLRequest) {
        urlDao.deleteUrl(deleteURLRequest.getUrl_key());
    }
}
