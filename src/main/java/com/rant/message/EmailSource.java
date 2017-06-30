package com.rant.message;

import com.rant.model.Email;

/**
 * A blueprint to communicate to a Email Service.
 * 
 * @author Austin Delamar
 * @date 3/8/2017
 */
public abstract class EmailSource {

    public abstract void init();

    public abstract void destroy();

    public abstract boolean sendEmail(Email email);
}
