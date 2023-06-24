package io.github.strikerrocker.vt;

import java.util.ServiceLoader;

public interface VTServices {


    IVTService SERVICES = load(IVTService.class);

    static <T> T load(Class<T> clazz) {
        final T loadedService = ServiceLoader.load(clazz)
                .findFirst()
                .orElseThrow(() -> new NullPointerException("Failed to load service for " + clazz.getName()));
        VanillaTweaks.LOGGER.debug("Loaded {} for service {}", loadedService, clazz);
        return loadedService;
    }

}
