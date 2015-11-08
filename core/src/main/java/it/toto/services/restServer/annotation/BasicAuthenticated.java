package it.toto.services.restServer.annotation;

import javax.ws.rs.NameBinding;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by toto on 08/11/15.
 */
@NameBinding
@Retention(RetentionPolicy.RUNTIME)
public @interface BasicAuthenticated {

}
