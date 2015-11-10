package com.github.totoCastaldi.commons;

import com.google.common.collect.Lists;
import lombok.Getter;

import javax.inject.Singleton;
import java.util.List;

/**
 * Created by github on 05/12/14.
 */
@Singleton
public class MemoryShutdownableRepository implements ShutdownableRepository {

    @Getter
    List<Shutdownable> services = Lists.newCopyOnWriteArrayList();

    @Override
    public void register(Shutdownable shutdownable) {
        services.add(shutdownable);
    }

}
