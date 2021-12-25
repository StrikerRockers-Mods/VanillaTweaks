package io.github.strikerrocker.vt;

import io.github.strikerrocker.vt.base.FabricModule;
import io.github.strikerrocker.vt.content.ClientContentModule;
import io.github.strikerrocker.vt.tweaks.ClientTweaksModule;
import net.fabricmc.api.ClientModInitializer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class VanillaTweaksClient implements ClientModInitializer {
    private static final List<FabricModule> clientModules = new ArrayList<>();

    static {
        Collections.addAll(clientModules, new ClientContentModule(), new ClientTweaksModule());
    }

    @Override
    public void onInitializeClient() {
        clientModules.forEach(FabricModule::initialize);
    }
}