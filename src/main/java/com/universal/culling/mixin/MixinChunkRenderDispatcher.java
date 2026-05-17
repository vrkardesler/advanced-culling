package com.universal.culling.mixin;

import net.minecraft.client.renderer.chunk.SectionRenderDispatcher;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SectionRenderDispatcher.RenderSection.class)
public class MixinChunkRenderDispatcher {
    @Inject(method = "compileKeyframe", at = @At("HEAD"), cancellable = true, remap = false)
    private void onCompile(CallbackInfoReturnable<Boolean> cir) {
        // Multi-Threaded Akıllı Culling Algoritması Kancası
    }
}