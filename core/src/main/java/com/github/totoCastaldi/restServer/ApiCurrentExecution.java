package com.github.totoCastaldi.restServer;

import com.google.common.base.Optional;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;
import lombok.Getter;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created by github on 11/12/14.
 */
@Slf4j
public class ApiCurrentExecution {

    public void setLocale(Locale locale) {
        setProperty(KEY.LOCALE, locale);
    }

    public Optional<Locale> getLocale() {
        return Optional.fromNullable((Locale) getProperty(KEY.LOCALE));
    }

    public void populateHttpHeaders(HttpServletRequest httpServletRequest) {
        Map<HEADER, String> headers = Maps.newHashMap();
        for (HEADER header : HEADER.values()) {
            String headerValue = null;
            try {
                headerValue = httpServletRequest.getHeader(header.getHttpHeader());
            } catch (Exception e) {
                log.warn(StringUtils.EMPTY, e);
            }
            if (StringUtils.isNotBlank(headerValue)) {
                headers.put(header, headerValue);
            }
        }
        setProperty(KEY.HEADERS, headers);
    }

    public void authenticationErrors(Iterable<String> errorMessages) {
        setProperty(ApiCurrentExecution.KEY.AUTHENTICATION_ERROR_MESSAGES, errorMessages);
    }

    public Iterable<String> getAuthenticationErrors() {
        Iterable<String> result = getProperty(KEY.AUTHENTICATION_ERROR_MESSAGES);
        if (result == null) {
            return Lists.newArrayList();
        } else {
            return result;
        }
    }


    private enum KEY {AUTHENTICATION_NOT_PASSED, USERNAME, USER_TYPE, HEADERS, LOCALE, AUTHENTICATION_ERROR_MESSAGES, AUTHENTICATION_PASSED};

    public static ApiCurrentExecution on(HttpServletRequest httpServletRequest) {
        return new ApiCurrentExecution(httpServletRequest);
    }

    public interface Factory {
        ApiCurrentExecution create(@Assisted("currentHttpRequest") HttpServletRequest currentHttpRequest);
    }

    public enum HEADER {
        X_FORWARDED_PROTO("X-Forwarded-Proto");

        @Getter
        private final String httpHeader;

        HEADER (String httpHeader) {
            this.httpHeader = httpHeader;
        }
    };

    @NonNull
    private final HttpServletRequest currentHttpRequest;

    @AssistedInject
    public ApiCurrentExecution(
            @Assisted("currentHttpRequest") HttpServletRequest currentHttpRequest
    ) {
        this.currentHttpRequest = currentHttpRequest;
    }


    @Override
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        for (KEY value : KEY.values()) {
            stringBuffer.append(value.name());
            stringBuffer.append("=");
            final Object property = getProperty(value);
            stringBuffer.append(String.valueOf(property));
            stringBuffer.append(";");
        }
        return stringBuffer.toString();
    }

    public Optional<String> getUsername() {
        return Optional.fromNullable((String) getProperty(KEY.USERNAME));
    }


    private <T> T getProperty(KEY key) {
        return (T) currentHttpRequest.getAttribute(key.name());
    }

    private void setProperty(KEY key, Object obj) {
        currentHttpRequest.setAttribute(key.name(), obj);
    }

    public boolean isAuthenticationPassed() {
        return Iterables.size(getAuthenticationPassedType()) > 0;
    }

    public Iterable<AuthenticationType> getAuthenticationPassedType() {
        Iterable<AuthenticationType> result = getProperty(KEY.AUTHENTICATION_PASSED);
        if (result == null) {
            result = Lists.newArrayList();
        }
        return result;
    }

    public void authenticationNotPassed(Iterable<AuthenticationType> authenticationType) {
        setProperty(ApiCurrentExecution.KEY.AUTHENTICATION_NOT_PASSED, authenticationType);
    }

    public void setUsername(String username) {
        setProperty(ApiCurrentExecution.KEY.USERNAME, username);
    }

    public void setUserType(UserType userType) {
        setProperty(ApiCurrentExecution.KEY.USER_TYPE, userType);
    }

    public void authenticationPassed(Iterable<AuthenticationType> authenticationType) {
        setProperty(ApiCurrentExecution.KEY.AUTHENTICATION_PASSED, authenticationType);
    }

    public Optional<String> getHeader(HEADER header) {
        Map<HEADER, String> headers = getProperty(KEY.HEADERS);
        return Optional.fromNullable(headers.get(header));
    }

    public Optional<UserType> getUserType() {
        return Optional.fromNullable((UserType) getProperty(KEY.USER_TYPE));
    }

}

