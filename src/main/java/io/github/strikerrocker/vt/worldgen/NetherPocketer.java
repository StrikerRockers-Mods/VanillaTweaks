package io.github.strikerrocker.vt.worldgen;

import io.github.strikerrocker.vt.handlers.VTConfigHandler;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static net.minecraftforge.event.terraingen.PopulateChunkEvent.Populate.EventType.*;

public class NetherPocketer {
    @SubscribeEvent
    public void onPopulateChunk(PopulateChunkEvent.Populate event) {
        if ((VTConfigHandler.noMoreLavaPocketGen) && event.getType() == NETHER_LAVA) {
            event.setResult(Event.Result.DENY);
        } else if ((VTConfigHandler.noMoreLavaPocketGen) && event.getType() == NETHER_LAVA2) {
            event.setResult(Event.Result.DENY);
        }
    }
}
