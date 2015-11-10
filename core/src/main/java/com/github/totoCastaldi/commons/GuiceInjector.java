package com.github.totoCastaldi.commons;

import com.google.inject.Injector;

/**
 * Created by github on 05/12/14.
 */
public class GuiceInjector {

    private static Injector istance;

    public static void setIstance(Injector istance) {
        GuiceInjector.istance = istance;
    }

    public static Injector getIstance() {
        return istance;
    }
}
