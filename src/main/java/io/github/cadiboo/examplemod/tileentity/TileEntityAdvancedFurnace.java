package io.github.cadiboo.examplemod.tileentity;

import net.minecraft.tileentity.TileEntity;

public class TileEntityAdvancedFurnace extends TileEntity {

    /**
     * If your TileEntity's TESR implementation is a FastTESR then this method needs to return true.
     */
    @Override
    public boolean hasFastRenderer()
    {
        return true;
    }
}
