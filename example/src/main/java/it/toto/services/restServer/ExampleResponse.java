package it.toto.services.restServer;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by toto on 08/11/15.
 */
@AllArgsConstructor (staticName = "of")
public class ExampleResponse {

    @Getter
    private boolean status;
}
