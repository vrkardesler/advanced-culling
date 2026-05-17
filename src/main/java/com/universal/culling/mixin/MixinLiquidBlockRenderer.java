package com.universal.culling.mixin;

import com.universal.culling.CullingEngine;
import net.minecraft.client.renderer.block.LiquidBlockRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import com.mojang.blaze3d.vertex.VertexConsumer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LiquidBlockRenderer.class)
public class MixinLiquidBlockRenderer {

    @Inject(method = "tesselate", at = @At("HEAD"), cancellable = true)
    private void onTesselate(BlockAndTintGetter level, BlockPos pos, VertexConsumer vertexConsumer, 
                             BlockState blockState, FluidState fluidState, CallbackInfo ci) {
        
        boolean internalLiquid = true;
        
        for (Direction direction : Direction.values()) {
            BlockPos adjacentPos = pos.relative(direction);
            FluidState adjacentFluid = level.getFluidState(adjacentPos);
            
            if (adjacentFluid.isEmpty()) {
                internalLiquid = false;
                break;
            }
        }

        if (internalLiquid) {
            ci.cancel();
        }
    }
}