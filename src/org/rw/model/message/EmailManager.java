package org.rw.model.message;

import org.rw.bean.Email;

/**
 * An interface to communicate to a Email Service.
 * @author Austin Delamar
 * @date 3/8/2017
 */
public interface EmailManager {

	public void sendEmail(Email email);
}
