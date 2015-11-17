package com.github.totoCastaldi.restServer.response;


import com.github.totoCastaldi.restServer.ApiCurrentExecution;
import com.github.totoCastaldi.restServer.AuthenticationType;
import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

public class ApiResponse {

    private static final String SLASH = "/";
    private static final String HEADER_LOCATION = "Location";
    private final ErrorResponseEntry.Factory errorResponseFactory;
    private final ApiCurrentExecution.Factory apiCurrentExecutionFactory;

    @Inject
    public ApiResponse (
            ErrorResponseEntry.Factory errorResponseFactory,
            ApiCurrentExecution.Factory apiCurrentExecutionFactory
    ) {
        this.errorResponseFactory = errorResponseFactory;
        this.apiCurrentExecutionFactory = apiCurrentExecutionFactory;
    }

    /**
     * 200
     * @param entity
     * @return
     */
    public Response ok(Object entity) {
        if (entity instanceof  Iterable) {
            return contentType(Response.ok(Iterables.toArray((Iterable<?>) entity, Object.class))).build();
        } else {
            return contentType(Response.ok(entity)).build();
        }

    }

    private Response.ResponseBuilder contentType(Response.ResponseBuilder responseBuilder) {
        return responseBuilder.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON + "; charset=" + Charset.defaultCharset().displayName());
    }

    /**
     * 200
     * @return
     */
    public Response ok() {
        return contentType(Response.ok()).build();
    }

    /**
     * 201
     * @param path
     * @return
     */
    public Response createdReturns(HttpServletRequest httpServletRequest, String... path) {

        URI location = getUri(httpServletRequest, path);

        return contentType(Response.created(location).entity(new String("{}"))).build();
    }


    /**
     * 201
     * @param entity
     * @param path
     * @return
     */
    public Response createdReturns(HttpServletRequest httpServletRequest, Object entity, String... path) {
        URI location = getUri(httpServletRequest, path);

        return contentType(Response.status(201).header(HEADER_LOCATION, location).entity(entity)).build();
    }

    /**
     * 204
     * @return
     */
    public Response noContent() {
        return contentType(Response.status(Response.Status.NO_CONTENT)).build();
    }

    /**
     * 400
     */
    public Response badResponse(final HttpServletRequest httpServletRequest, ErrorResponseCode... errors) {
        return contentType(Response.status(400).entity(toBody(errors, httpServletRequest))).build();
    }

    /**
     * 400
     */
    public Response badResponse(Throwable t) {
        return contentType(Response.status(400).entity(toBody(t))).build();
    }

    /**
     * 401
     * @param entity
     * @return
     */
    public Response authenticationRequired(Object entity) {
        return contentType(Response.status(401).entity(entity)).build();
    }

    /**
     * 403
     */
    public Response forbidden(final HttpServletRequest httpServletRequest, ErrorResponseCode... errors) {
        return contentType(Response.status(403).entity(toBody(errors, httpServletRequest))).build();
    }

    /**
     * 404
     * @return
     */
    public Response notFound() {
        throw new NotFoundException();
    }

    public Response notImplemented() {
        return contentType(Response.status(Response.Status.NOT_IMPLEMENTED)).build();
    }

    private ErrorResponse toBody(ErrorResponseCode[] errors, final HttpServletRequest httpServletRequest) {
        return new ErrorResponse(Iterables.toArray(Iterables.transform(Arrays.asList(errors), new Function<ErrorResponseCode, ErrorResponseEntry>() {
            @Nullable
            @Override
            public ErrorResponseEntry apply(@Nullable ErrorResponseCode errorResponseCode) {
                return errorResponseFactory.create(httpServletRequest, errorResponseCode);
            }
        }), ErrorResponseEntry.class));
    }

    private ErrorResponse toBody(Throwable t) {
        return new ErrorResponse(errorResponseFactory.create(t));
    }

    /**
     * 401
     * @param authorizationRequestes
     * @param errors
     * @return
     */
    public Response unauthorize(final HttpServletRequest httpServletRequest, Iterable<AuthenticationType> authorizationRequestes, ErrorResponseCode... errors) {
        Object responseBody = toBody(errors, httpServletRequest);
        Response.ResponseBuilder responseBuilder = Response.status(Response.Status.UNAUTHORIZED);
        if (Iterables.any(authorizationRequestes, new Predicate<AuthenticationType>() {

            @Override
            public boolean apply(@Nullable AuthenticationType authorizationRequest) {
                return authorizationRequest == AuthenticationType.BASIC;
            }
        }) || Iterables.isEmpty(authorizationRequestes)) {
            responseBuilder.header(HttpHeaders.WWW_AUTHENTICATE, "Basic realm=\"HF Channel\"");
        } else {
            responseBuilder.header(HttpHeaders.WWW_AUTHENTICATE, "Chili realm=\"HF Channel\"");
        };



        if (responseBody != null) {
            responseBuilder.entity(responseBody);
        }


        return contentType(responseBuilder).build();
    }


    private URI getUri(HttpServletRequest httpServletRequest, String... subpath) {
        final ApiCurrentExecution currentExecution = apiCurrentExecutionFactory.create(httpServletRequest);
        final String protocol = currentExecution.getHeader(ApiCurrentExecution.HEADER.X_FORWARDED_PROTO).or(StringUtils.lowerCase(StringUtils.substringBefore(httpServletRequest.getProtocol(), SLASH)));
        String uri = protocol +  "://";
        uri = uri +  httpServletRequest.getServerName();
        final int port = httpServletRequest.getServerPort();
        if (port != 80) {
            uri = uri + ":";
            uri = uri + httpServletRequest.getServerPort();
        }
        List<String> paths = Lists.newArrayList();
        String contextPath = httpServletRequest.getContextPath();
        if (StringUtils.isNotBlank(contextPath)) {
            paths.add(StringUtils.substringAfter(contextPath,SLASH));
        }
        paths.addAll(Arrays.asList(subpath));
        uri = uri +  SLASH + Joiner.on(SLASH).join(paths);
        return URI.create(uri);
    }




}
