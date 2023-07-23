package net.itshamza.mgrr.item;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;

public class ModTabs {
    public static final CreativeModeTab MGRR_TAB = new CreativeModeTab("mgrr_tab") {
        @Override
        public @NotNull ItemStack m_6976_() {
            return new ItemStack(Items.DIAMOND_SWORD);
        }
    };
}
