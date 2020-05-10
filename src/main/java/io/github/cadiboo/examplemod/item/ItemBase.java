package io.github.cadiboo.examplemod.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;

public class ItemBase extends Item {
    public ItemBase() {
        super(new  Properties().group(ItemGroup.FOOD));
    }
}
