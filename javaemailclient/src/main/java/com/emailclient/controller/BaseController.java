package com.emailclient.controller;

import com.emailclient.EmailManager;
import com.emailclient.view.ViewFactory;

public abstract class BaseController {

    protected EmailManager emailManager;
    protected ViewFactory viewFactory;
    private String fxmlName;

    public BaseController(EmailManager emailManager, ViewFactory viewFactory, String fxmlName){
        this.emailManager = emailManager;
        this.viewFactory = viewFactory;
        this.fxmlName = fxmlName;
    }

    public String getFxmlName() {
        return fxmlName;
    };
}
