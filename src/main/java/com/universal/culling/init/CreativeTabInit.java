package com.universal.culling.init;

import com.universal.culling.AdvancedCullingMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class CreativeTabInit {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, AdvancedCullingMod.MOD_ID);
    
    public static final RegistryObject<CreativeModeTab> ADVANCED_CULLING_TAB = CREATIVE_TABS.register("advculling_tab",
        () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.advculling_tab"))
            .icon(() -> new ItemStack(BlockInit.COSMIC_BLOCK.get()))
            .displayItems((parameters, output) -> {
                output.accept(ItemInit.COSMIC_BLOCK_ITEM.get());
            })
            .build());
}