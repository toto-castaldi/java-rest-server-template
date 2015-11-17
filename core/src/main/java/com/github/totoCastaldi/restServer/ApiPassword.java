package com.github.totoCastaldi.restServer;

import com.github.totoCastaldi.restServer.model.CustomerEntity;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;

/**
 * Created by github on 11/12/14.
 */
public class ApiPassword {

    private final String seed;

    public ApiPassword(String seed) {
        this.seed = seed;
    }

    public String encodePassword(String username, String password) {
        return DigestUtils.md5Hex(password + seed + username);
    }

    public boolean validate(CustomerEntity user, String password) {
        return validate(user.getUsername(), password, user.getEncodedPassword());
    }

    public boolean validate(String userName, String password, String encodedPassword) {
        return StringUtils.equals(encodePassword(userName, password), encodedPassword);
    }

}
