package com.universal.culling;

import com.universal.culling.init.BlockInit;
import com.universal.culling.init.CreativeTabInit;
import com.universal.culling.init.ItemInit;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(AdvancedCullingMod.MOD_ID)
public class AdvancedCullingMod {
    public static final String MOD_ID = "advculling";
    public static final Logger LOGGER = LogManager.getLogger();

    public AdvancedCullingMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        BlockInit.BLOCKS.register(modEventBus);
        ItemInit.ITEMS.register(modEventBus);
        CreativeTabInit.CREATIVE_TABS.register(modEventBus);

        modEventBus.addListener(this::clientSetup);
        LOGGER.info("Advanced Block Culling Modu Aktif Edildi (Forge 1.20.4)!");
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        LOGGER.info("Culling Motoru Istemci Optimizasyonları Tamamlandı.");
    }
}