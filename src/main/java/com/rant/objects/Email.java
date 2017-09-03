package com.rant.objects;
/**
 * Email message class is used for queuing emails for the daemon service to send.
 * 
 * @author Austin Delamar
 * @date 11/9/2015
 */
public class Email {
    private String to;
    private String from;
    private String subject;
    private String message;

    public Email() {
        // default constructor
    }

    public Email(String to, String from, String subject, String message) {
        this.to = to;
        this.from = from;
        this.subject = subject;
        this.message = message;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
