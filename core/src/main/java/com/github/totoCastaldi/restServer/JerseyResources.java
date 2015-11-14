package com.github.totoCastaldi.restServer;

import com.google.common.collect.Lists;

import javax.ws.rs.container.ContainerRequestFilter;
import java.util.List;

/**
 * Created by toto on 11/11/15.
 */
public class JerseyResources {

    private static List<Package> packageCollections = Lists.newCopyOnWriteArrayList();
    private static List<Class<? extends ContainerRequestFilter>> containerRequestFilters = Lists.newCopyOnWriteArrayList();

    public static void add(Package aPackage) {
        packageCollections.add(aPackage);
    }

    public static void add(Class<? extends ContainerRequestFilter> containerRequestFilter) {
        containerRequestFilters.add(containerRequestFilter);
    }

    public static void addPackages(List<Package> list) {
        packageCollections.addAll(list);
    }

    public static void addContainerRequestFilters(List<Class<? extends ContainerRequestFilter>> list) {
        containerRequestFilters.addAll(list);
    }

    public static List<Package> getPackageCollections() {
        return packageCollections;
    }

    public static List<Class<? extends ContainerRequestFilter>> getContainerRequestFilters() {
        return containerRequestFilters;
    }

}
