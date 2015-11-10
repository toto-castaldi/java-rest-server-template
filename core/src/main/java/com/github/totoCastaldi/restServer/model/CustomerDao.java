package com.github.totoCastaldi.restServer.model;

import com.google.common.base.Optional;

/**
 * Created by github on 08/11/15.
 */
public interface CustomerDao {
    Optional<CustomerEntity> findByUsername(String username);
}
