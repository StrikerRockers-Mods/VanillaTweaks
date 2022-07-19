package io.github.strikerrocker.vt.enchantments;

import com.google.common.base.Suppliers;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

/**
 * Handles the functionality of Blazing enchantment
 */
class BlazingModifier extends LootModifier {

    public static final Supplier<Codec<BlazingModifier>> CODEC = Suppliers.memoize(() -> RecordCodecBuilder.create(inst -> codecStart(inst).apply(inst, BlazingModifier::new)));

    public BlazingModifier(LootItemCondition[] conditionsIn) {
        super(conditionsIn);
    }

    @Override
    protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> dropList, LootContext context) {
        ItemStack tool = context.getParamOrNull(LootContextParams.TOOL);
        Entity entity = context.getParam(LootContextParams.THIS_ENTITY);
        if (tool == null || !(entity instanceof Player)) return dropList;
        ObjectArrayList<ItemStack> newDropList = new ObjectArrayList<>();
        dropList.forEach(stack -> newDropList.add(EnchantmentImpl.smelt(stack, context.getLevel())));
        return newDropList;
    }

    @Override
    public Codec<? extends IGlobalLootModifier> codec() {
        return CODEC.get();
    }
}