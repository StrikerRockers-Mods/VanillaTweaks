package io.github.strikerrocker.vt.tweaks.silkspawner;

import io.github.strikerrocker.vt.base.Feature;
import io.github.strikerrocker.vt.tweaks.TweaksClientImpl;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;

public class SilkSpawnerClient extends Feature {
    @Override
    public void initialize() {
        ItemTooltipCallback.EVENT.register((stack, tooltipContext, list) -> TweaksClientImpl.addSilkSpawnerTooltip(stack, list));
    }
}
