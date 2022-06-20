package com.newjumper.taloi.screen.slot;

import com.newjumper.taloi.util.TaloiTags;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

public class SeparatorOreSlot extends SlotItemHandler {
    public SeparatorOreSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
        super(itemHandler, index, xPosition, yPosition);
    }

    @Override
    public boolean mayPlace(@NotNull ItemStack pStack) {
        return pStack.is(TaloiTags.Items.SEPARATOR_ORES);
    }
}
