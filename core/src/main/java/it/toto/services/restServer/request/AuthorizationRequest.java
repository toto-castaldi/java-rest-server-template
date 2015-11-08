package it.toto.services.restServer.request;

/**
 * Created by toto on 11/12/14.
 */
public interface AuthorizationRequest {

    boolean isPassed();
    String getUsername();
    String getRequest();

}
