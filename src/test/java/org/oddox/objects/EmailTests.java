package org.oddox.objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.oddox.objects.Email;

/**
 * Unit tests for Email Object
 * 
 * @author amdelamar
 * @since 1.0.0
 */
@RunWith(JUnit4.class)
public class EmailTests {

    @Test
    public void email() {
        Email em = new Email();
        em.setTo("to");
        em.setFrom("from");
        em.setSubject("subject");
        em.setMessage("message");
        assertEquals("to", em.getTo());
        assertEquals("from", em.getFrom());
        assertEquals("subject", em.getSubject());
        assertEquals("message", em.getMessage());
        assertNotNull(em.toString());

        Email em2 = new Email("to", "from", "subject", "message");
        assertEquals("to", em2.getTo());
        assertEquals("from", em2.getFrom());
        assertEquals("subject", em2.getSubject());
        assertEquals("message", em2.getMessage());
    }

}
