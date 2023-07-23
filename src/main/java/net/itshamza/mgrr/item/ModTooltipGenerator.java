package net.itshamza.mgrr.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class ModTooltipGenerator {
    @SubscribeEvent
    @SuppressWarnings("unused")
    public static void tooltipGenerator(ItemTooltipEvent event){
        ItemStack itemStack = event.getItemStack();
        if(itemStack.getItem() instanceof MGRWeapon mgrWeapon && mgrWeapon.canParry()){
            event.getToolTip().add(
                    Component.translatable("desc.mgrr.general_parry").withStyle(ChatFormatting.YELLOW).withStyle(ChatFormatting.BOLD)
            );
        }
    }
}
