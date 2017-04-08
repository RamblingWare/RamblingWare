package org.rw.message;

import org.rw.action.model.Email;

/**
 * A blueprint to communicate to a Email Service.
 * @author Austin Delamar
 * @date 3/8/2017
 */
public abstract class EmailSource {

	public abstract void sendEmail(Email email);
}
