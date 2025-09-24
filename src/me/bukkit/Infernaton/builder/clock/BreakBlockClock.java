package me.bukkit.Infernaton.builder.clock;

import me.bukkit.Infernaton.handler.BlockHandler;
import org.bukkit.block.Block;
import org.bukkit.material.MaterialData;

/**
 * A clock which is called right after a player breaks a block,
 * when the clock stop, the game reset the broken block to its original state
 */
public class BreakBlockClock extends CountDown {

    private final Block block;
    private final MaterialData material;

    public BreakBlockClock(long startTime, Block block) {
        newCountDown(this, startTime);

        this.block = block;
        this.material = new MaterialData(block.getType(), block.getData());
    }

    @Override
    public void run() {
        if (time == 0) {
            BlockHandler.setMaterial(block, material);
        }

        super.run();
    }
}
