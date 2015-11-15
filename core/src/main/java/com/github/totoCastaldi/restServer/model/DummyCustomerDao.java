package com.github.totoCastaldi.restServer.model;

import com.google.common.base.Optional;

/**
 * Created by toto on 14/11/15.
 */
public class DummyCustomerDao implements CustomerDao{
    @Override
    public Optional<CustomerEntity> findByUsername(String username) {
        return Optional.absent();
    }
}
