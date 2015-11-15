package com.github.totoCastaldi.restServer;

import com.github.totoCastaldi.restServer.model.CustomerDao;
import com.github.totoCastaldi.restServer.model.DummyCustomerDao;
import com.google.common.collect.Lists;
import com.google.inject.AbstractModule;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang.StringUtils;

import javax.ws.rs.container.ContainerRequestFilter;
import java.util.List;

/**
 * Created by toto on 14/11/15.
 */
@AllArgsConstructor (staticName = "of", access = AccessLevel.PRIVATE)
public class RestServerConf {

    @Getter
    private List<Package> packages;
    @Getter
    private List<AbstractModule> modules;
    @Getter
    private Class<? extends CustomerDao> customerDao;
    @Getter
    private String seed;
    @Getter
    private List<Class<? extends ContainerRequestFilter>> filters;

    public static Builder builder() {
        return new RestServerConf.Builder();
    }

    @NoArgsConstructor (access = AccessLevel.PRIVATE)
    public static class Builder {

        private List<Package> packages = Lists.newArrayList();
        private List<AbstractModule> modules = Lists.newArrayList();
        private Class<? extends CustomerDao> customerDao = DummyCustomerDao.class;
        private String seed = StringUtils.EMPTY;
        private List<Class<? extends ContainerRequestFilter>> filters = Lists.newArrayList();

        public Builder add(Package aPackage) {
            this.packages.add(aPackage);
            return this;
        }

        public Builder add(Class<? extends ContainerRequestFilter> containerRequestFilter) {
            this.filters.add(containerRequestFilter);
            return this;
        }

        public Builder add(AbstractModule abstractModule) {
            this.modules.add(abstractModule);
            return this;
        }

        public RestServerConf build() {
            return RestServerConf.of(packages, modules, customerDao, seed, filters);
        }

        public Builder setCustomerDao(Class<? extends CustomerDao> customerDao) {
            this.customerDao = customerDao;
            return this;
        }

        public Builder setPassworSeed(String seed) {
            this.seed = seed;
            return this;
        }
    }


}
