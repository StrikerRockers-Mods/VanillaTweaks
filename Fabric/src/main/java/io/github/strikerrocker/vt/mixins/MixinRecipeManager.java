package io.github.strikerrocker.vt.mixins;

import com.google.gson.JsonElement;
import io.github.strikerrocker.vt.RecipeModule;
import io.github.strikerrocker.vt.VanillaTweaks;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.item.crafting.RecipeManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(RecipeManager.class)
public class MixinRecipeManager {
    /**
     * Injects recipes generated in code
     */
    @Inject(method = "apply*", at = @At("HEAD"))
    public void interceptApply(Map<ResourceLocation, JsonElement> map, ResourceManager resourceManager, ProfilerFiller profiler, CallbackInfo info) {
        RecipeModule.recipes.forEach((identifier, jsonObject) -> {
            map.put(new ResourceLocation(VanillaTweaks.MOD_ID, "vanilla_recipes/" + identifier.getPath()), jsonObject);
            VanillaTweaks.LOGGER.info("Added " + identifier + " recipe");
        });
    }
}
