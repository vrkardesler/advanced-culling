package com.universal.culling.mixin;

import com.universal.culling.CullingEngine;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlockRenderDispatcher.class)
public class MixinBlockRenderDispatcher {
    
    @Inject(method = "renderBatched", at = @At("HEAD"), cancellable = true)
    private void onRenderBatched(BlockState state, BlockPos pos, BlockGetter level, PoseStack poseStack, 
                                 VertexConsumer vertexConsumer, boolean checkSides, net.minecraft.util.RandomSource random, 
                                 CallbackInfo ci) {
        if (checkSides) {
            boolean visible = false;
            for (Direction direction : Direction.values()) {
                if (CullingEngine.shouldRenderFace(state, level, pos, direction, pos.relative(direction))) {
                    visible = true;
                    break;
                }
            }
            if (!visible) {
                ci.cancel();
            }
        }
    }
}