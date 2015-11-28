package com.github.totoCastaldi.restServer;

import com.github.totoCastaldi.restServer.model.CustomerDao;
import com.github.totoCastaldi.restServer.model.DummyCustomerDao;
import com.github.totoCastaldi.restServer.plugin.Plugin;
import com.github.totoCastaldi.restServer.response.ApiErrorMessage;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.inject.AbstractModule;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang.StringUtils;

import javax.ws.rs.container.ContainerRequestFilter;
import java.util.Collection;
import java.util.List;

/**
 * Created by toto on 14/11/15.
 */
@NoArgsConstructor (access = AccessLevel.PRIVATE)
public class RestServerConf {

    @Getter
    private List<AppConfKey> confKeys = Lists.newArrayList();
    @Getter
    private List<Package> packages = Lists.newArrayList();
    @Getter
    private List<AbstractModule> modules = Lists.newArrayList();
    @Getter
    private Class<? extends CustomerDao> customerDao = DummyCustomerDao.class;
    @Getter
    private String seed = StringUtils.EMPTY;
    @Getter
    private List<Class<? extends ContainerRequestFilter>> filters = Lists.newArrayList();
    @Getter
    private ApiErrorMessage apiErrorMessage = new DummyApiErrorMessage();

    public static Builder builder() {
        return new RestServerConf.Builder();
    }

    @NoArgsConstructor (access = AccessLevel.PRIVATE)
    public static class Builder {

        private RestServerConf result = new RestServerConf();

        public Builder add(Package aPackage) {
            result.packages.add(aPackage);
            return this;
        }

        public Builder add(Plugin plugin) {
            plugin.config(this);
            return this;
        }

        public Builder add(Class<? extends ContainerRequestFilter> containerRequestFilter) {
            result.filters.add(containerRequestFilter);
            return this;
        }

        public Builder add(AbstractModule abstractModule) {
            result.modules.add(abstractModule);
            return this;
        }

        public RestServerConf build() {
            return result;
        }

        public Builder setCustomerDao(Class<? extends CustomerDao> customerDao) {
            result.customerDao = customerDao;
            return this;
        }

        public Builder setPassworSeed(String seed) {
            result.seed = seed;
            return this;
        }

        public Builder addStringConf(String key) {
            result.confKeys.add(AppConfKey.of(TYPE.STRING, key));
            return this;
        }
    }


    public static enum TYPE {
        STRING

    }

    @Getter
    @AllArgsConstructor (staticName = "of", access = AccessLevel.PRIVATE)
    public static class AppConfKey {
        TYPE clazz;
        String name;
    }
}
