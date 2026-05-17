package com.universal.culling.init;

import com.universal.culling.AdvancedCullingMod;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemInit {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, AdvancedCullingMod.MOD_ID);
    public static final RegistryObject<Item> COSMIC_BLOCK_ITEM = ITEMS.register("cosmic_block", 
        () -> new BlockItem(BlockInit.COSMIC_BLOCK.get(), new Item.Properties()));
}