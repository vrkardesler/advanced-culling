package com.universal.culling;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;

public class CullingEngine {

    public static boolean shouldRenderFace(BlockState state, BlockGetter level, BlockPos pos, Direction face, BlockPos adjacentPos) {
        BlockState adjacentState = level.getBlockState(adjacentPos);
        FluidState adjacentFluid = level.getFluidState(adjacentPos);

        // --- 1. MAĞARA VE YERALTI CULLING (CAVE FILTER) ---
        // Oyuncu yeraltında değilse ve blok tamamen gömülüyse render yükünü kes
        if (pos.getY() < 63 && adjacentState.isSolidRender(level, adjacentPos)) {
            // Blok 6 yönden de kapalı mı kontrolü (Hızlı tarama)
            if (level.getBlockState(pos.above()).isSolidRender(level, pos.above()) &&
                level.getBlockState(pos.below()).isSolidRender(level, pos.below()) &&
                level.getBlockState(pos.north()).isSolidRender(level, pos.north()) &&
                level.getBlockState(pos.south()).isSolidRender(level, pos.south())) {
                return false; 
            }
        }

        // --- 2. AKIŞKAN / SIVI ÜST YÜZEY FİLTRESİ ---
        if (!adjacentFluid.isEmpty() && adjacentFluid.isSource()) {
            if (face == Direction.UP) {
                return false; 
            }
        }

        // --- 3. STANDART MAT KÜP ENGELLEME ---
        if (adjacentState.isSolidRender(level, adjacentPos) && adjacentState.isCollisionShapeFullBlock(level, adjacentPos)) {
            return false;
        }

        // --- 4. MATEMATİKSEL DINAMIK FOV VE VEKTÖR FRUSTUM CULLING ---
        Minecraft mc = Minecraft.getInstance();
        if (mc.cameraEntity != null) {
            Vec3 cameraPos = mc.gameRenderer.getMainCamera().getPosition();
            Vec3 blockCenter = new Vec3(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5);
            Vec3 lookVec = mc.cameraEntity.getLookAngle();
            
            Vec3 toBlockVec = blockCenter.subtract(cameraPos).normalize();
            double dotProduct = toBlockVec.dot(lookVec);
            
            // Hassas FOV Filtresi: Oyuncunun görüş açısının (O anki FOV ayarının) 
            // dış kenarlarında kalan blokları yakalamak için eşik değeri -0.4'e çekildi.
            if (dotProduct < -0.4 && cameraPos.distanceToSqr(blockCenter) > 16.0) {
                return false;
            }

            // Gelişmiş Arka Yüzey (Backface) Filtresi
            Vec3 faceNormal = new Vec3(face.getStepX(), face.getStepY(), face.getStepZ());
            if (toBlockVec.dot(faceNormal) > 0.0 && cameraPos.distanceToSqr(blockCenter) > 4.0) {
                return false;
            }
        }

        // --- 5. TRANSPARAN OVERDRAW ENGELLEME ---
        if (state.getBlock() == adjacentState.getBlock() && !state.canOcclude()) {
            return false; 
        }

        return true;
    }
}