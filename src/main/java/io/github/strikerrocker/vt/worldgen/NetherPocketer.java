package io.github.strikerrocker.vt.worldgen;

import io.github.strikerrocker.vt.handlers.ConfigHandler;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static net.minecraftforge.event.terraingen.PopulateChunkEvent.Populate.EventType.NETHER_LAVA;
import static net.minecraftforge.event.terraingen.PopulateChunkEvent.Populate.EventType.NETHER_LAVA2;

public class NetherPocketer {
    @SubscribeEvent
    public void onPopulateChunk(PopulateChunkEvent.Populate event) {
        if (ConfigHandler.miscellanious.noMoreLavaPocketGen) {
            if (event.getType() == NETHER_LAVA || event.getType() == NETHER_LAVA2) {
                event.setResult(Event.Result.DENY);
            }
        }
    }
}