package com.priyanka.tinyurl.entity;

import java.util.Date;
import java.util.UUID;

public class URL {
    private String hash;

    private String originalURL;

    private Date creationDate;

    private Date expirationDate;

    private UUID userId;

    public URL() {
    }

    public URL(String hash, String originalURL, Date creationDate, Date expirationDate, UUID userId) {
        this.hash = hash;
        this.originalURL = originalURL;
        this.creationDate = creationDate;
        this.expirationDate = expirationDate;
        this.userId = userId;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getOriginalURL() {
        return originalURL;
    }

    public void setOriginalURL(String originalURL) {
        this.originalURL = originalURL;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "URL{" +
                "hash='" + hash + '\'' +
                ", originalURL='" + originalURL + '\'' +
                ", creationDate=" + creationDate +
                ", expirationDate=" + expirationDate +
                ", userId=" + userId +
                '}';
    }
}
