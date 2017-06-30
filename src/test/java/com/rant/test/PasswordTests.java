package com.rant.test;

import static org.junit.Assert.assertTrue;

import java.security.NoSuchAlgorithmException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.amdelamar.jhash.Hash;
import com.amdelamar.jhash.algorithms.Type;
import com.amdelamar.jhash.exception.BadOperationException;
import com.amdelamar.jhash.exception.InvalidHashException;

@RunWith(JUnit4.class)
public class PasswordTests {

    @Test
    public void defaultTests()
            throws BadOperationException, InvalidHashException, NoSuchAlgorithmException {

        String password = "Hello World!";

        // sha1 no pepper
        String hash = Hash.create(password, Type.PBKDF2_SHA1);
        System.out.println(hash);
        assertTrue(Hash.verify(password, hash));

        // sha256 no pepper
        String hash2 = Hash.create(password, Type.PBKDF2_SHA256);
        System.out.println(hash2);
        assertTrue(Hash.verify(password, hash2));

        // sha512 no pepper
        String hash3 = Hash.create(password, Type.PBKDF2_SHA512);
        System.out.println(hash3);
        assertTrue(Hash.verify(password, hash3));
    }
}
