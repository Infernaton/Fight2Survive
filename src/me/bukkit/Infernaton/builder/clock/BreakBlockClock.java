package me.bukkit.Infernaton.builder.clock;

import org.bukkit.Material;
import org.bukkit.block.Block;

/**
 * A clock which is called right after a player breaks a block,
 * when the clock stop, the game reset the broken block to its original state
 */
public class BreakBlockClock extends CountDown {

    private final Block block;
    private final Material blockType;
    private final byte blockData;

    public BreakBlockClock(long startTime, Block block) {
        newCountDown(this, startTime);
        this.block = block;
        this.blockType = block.getType();
        this.blockData = block.getData();
    }

    @Override
    public void run() {
        if (time == 0) {
            block.setType(blockType);
            block.setData(blockData);
        }
    }
}
