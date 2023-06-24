package io.github.strikerrocker.vt.world;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.strikerrocker.vt.VanillaTweaks;
import io.github.strikerrocker.vt.base.ForgeFeature;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ModifiableBiomeInfo;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;

public class NoMoreLavaPocketGen extends ForgeFeature {
    // Version of forge stock biome modifier with config
    public static final DeferredRegister<Codec<? extends BiomeModifier>> BIOME_MODIFIER_SERIALIZERS = DeferredRegister.create(ForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS, VanillaTweaks.MOD_ID);
    public static final RegistryObject<Codec<RemoveFeaturesBiomeModifier>> REMOVE_FEATURES_BIOME_MODIFIER_TYPE = BIOME_MODIFIER_SERIALIZERS.register("remove_features", () ->
            RecordCodecBuilder.create(builder -> builder.group(
                    Biome.LIST_CODEC.fieldOf("biomes").forGetter(RemoveFeaturesBiomeModifier::biomes),
                    PlacedFeature.LIST_CODEC.fieldOf("features").forGetter(RemoveFeaturesBiomeModifier::features),
                    new ExtraCodecs.EitherCodec<>(GenerationStep.Decoration.CODEC.listOf(), GenerationStep.Decoration.CODEC).xmap(
                            either -> either.map(Set::copyOf, Set::of), // convert list/singleton to set when decoding
                            set -> set.size() == 1 ? Either.right(set.toArray(GenerationStep.Decoration[]::new)[0]) : Either.left(List.copyOf(set))
                    ).optionalFieldOf("steps", EnumSet.allOf(GenerationStep.Decoration.class)).forGetter(RemoveFeaturesBiomeModifier::steps)
            ).apply(builder, RemoveFeaturesBiomeModifier::new))
    );
    private static ForgeConfigSpec.BooleanValue disableLavaPocketGen;

    public record RemoveFeaturesBiomeModifier(HolderSet<Biome> biomes, HolderSet<PlacedFeature> features,
                                              Set<GenerationStep.Decoration> steps) implements BiomeModifier {

        @Override
        public void modify(Holder<Biome> biome, Phase phase, ModifiableBiomeInfo.BiomeInfo.Builder builder) {
            if (disableLavaPocketGen.get() && phase == Phase.REMOVE && this.biomes.contains(biome)) {
                BiomeGenerationSettingsBuilder generationSettings = builder.getGenerationSettings();
                for (GenerationStep.Decoration step : this.steps) {
                    generationSettings.getFeatures(step).removeIf(this.features::contains);
                }
            }
        }

        @Override
        public Codec<? extends BiomeModifier> codec() {
            return REMOVE_FEATURES_BIOME_MODIFIER_TYPE.get();
        }
    }

    @Override
    public void setupConfig(ForgeConfigSpec.Builder builder) {
        disableLavaPocketGen = builder
                .translation("config.vanillatweaks:disableLavaPocketGen")
                .comment("Don't want that that pesky lava pockets in nether to kill you?")
                .define("disableLavaPocketGen", true);
    }
}