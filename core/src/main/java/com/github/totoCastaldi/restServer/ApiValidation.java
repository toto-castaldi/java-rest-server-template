package com.github.totoCastaldi.restServer;

import com.github.totoCastaldi.restServer.model.CustomerEntity;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;

/**
 * Created by github on 11/12/14.
 */
public class ApiValidation {

    private final String seed;

    public ApiValidation(String seed) {
        this.seed = seed;
    }

    public String getPassword(String username, String password) {
        return DigestUtils.md5Hex(password + seed + username);
    }

    public boolean validCustomerPassword(CustomerEntity user, String password) {
        return StringUtils.equals(getPassword(user.getUsername(), password), user.getPassword());
    }

}
