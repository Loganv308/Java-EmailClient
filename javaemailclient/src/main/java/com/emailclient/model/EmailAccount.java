package com.emailclient.model;

import java.util.Properties;

import javax.mail.Store;

public class EmailAccount {

    private String address;
    private String password;
    private Properties properties;
    // To store and retrieve messages
    private Store store;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public EmailAccount(String address, String password) {
        this.address = address;
        this.password = password;
        properties = new Properties();
        // For sending emails
        properties.put("incomingHost", "imap.gmail.com");
        properties.put("mail.store.protocol", "imaps");

        // For retreiving emails
        properties.put("mail.transport.protocol", "smtps");
        properties.put("mail.smtps.host", "smtp.gmail.com");
        properties.put("mail.smtps.auth", "true");   
        properties.put("outgoingHost", "smtp.gmail.com");
    }
}
