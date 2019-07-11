package io.github.strikerrocker.vt.world;

import io.github.strikerrocker.vt.base.Feature;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static net.minecraftforge.event.terraingen.PopulateChunkEvent.Populate.EventType.NETHER_LAVA;
import static net.minecraftforge.event.terraingen.PopulateChunkEvent.Populate.EventType.NETHER_LAVA2;

public class NoMoreLavaGen extends Feature {
    private boolean noMoreLavaPocketGen;

    public NoMoreLavaGen() {
        MinecraftForge.TERRAIN_GEN_BUS.register(this);
    }

    @Override
    public void syncConfig(Configuration config, String category) {
        noMoreLavaPocketGen = config.get(category, "noMoreLavaPocketGen", true, "Annoyed of the pesky lava pockets in nether").getBoolean();
    }

    @SubscribeEvent
    public void onPopulateChunk(PopulateChunkEvent.Populate event) {
        if (noMoreLavaPocketGen) {
            if (event.getType() == NETHER_LAVA || event.getType() == NETHER_LAVA2) {
                event.setResult(Event.Result.DENY);
            }
        }
    }
}
