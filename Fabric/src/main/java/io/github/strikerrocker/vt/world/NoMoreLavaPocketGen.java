package io.github.strikerrocker.vt.world;

import io.github.strikerrocker.vt.VanillaTweaks;
import io.github.strikerrocker.vt.VanillaTweaksFabric;
import io.github.strikerrocker.vt.base.Feature;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

public class NoMoreLavaPocketGen extends Feature {
    @Override
    public void initialize() {
        if (VanillaTweaksFabric.config.world.disableLavaPockets) {
            BiomeModifications.create(new ResourceLocation(VanillaTweaks.MOD_ID, "lava_pocket_removal")).
                    add(ModificationPhase.REMOVALS,
                            BiomeSelectors.foundInTheNether(),
                            ctx -> ctx.getGenerationSettings()
                                    .removeFeature(ResourceKey.create(Registry.PLACED_FEATURE_REGISTRY, new ResourceLocation("spring_closed"))));
            BiomeModifications.create(new ResourceLocation(VanillaTweaks.MOD_ID, "lava_pocket_removal_double")).
                    add(ModificationPhase.REMOVALS,
                            BiomeSelectors.foundInTheNether(),
                            ctx -> ctx.getGenerationSettings()
                                    .removeFeature(ResourceKey.create(Registry.PLACED_FEATURE_REGISTRY, new ResourceLocation("spring_closed_double"))));
        }
    }
}