package com.kidd.shopping.event;

import com.kidd.shopping.auth.entity.User;
import org.springframework.context.ApplicationEvent;

import java.util.Locale;

public class EmailVerificationEvent extends ApplicationEvent {
    private String appUrl;
    private User user;
    private Locale locale;

    public EmailVerificationEvent(User user, Locale locale, String appUrl) {
        super(user);
        this.appUrl = appUrl;
        this.locale = locale;
        this.user = user;
    }

    public String getAppUrl() {
        return appUrl;
    }

    public void setAppUrl(String appUrl) {
        this.appUrl = appUrl;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }
}
