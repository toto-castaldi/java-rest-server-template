package com.github.totoCastaldi.restServer;

import com.google.common.collect.Lists;
import lombok.Getter;

import java.util.List;

/**
 * Created by toto on 11/11/15.
 */
public class Resources {

    private static List<Package> packageCollections = Lists.newCopyOnWriteArrayList();

    public static void add(Package aPackage) {
        packageCollections.add(aPackage);
    }

    public static List<Package> getPackageCollections() {
        return packageCollections;
    }
}
