package com.github.totoCastaldi.restServer.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by github on 08/11/15.
 */
@AllArgsConstructor(staticName = "of")
public class CustomerEntity {

    @Getter
    private String username;
    @Getter
    private String encodedPassword;
    @Getter
    private Boolean admin;
    @Getter
    private Boolean customer;

}
