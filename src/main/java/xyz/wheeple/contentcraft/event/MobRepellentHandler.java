package xyz.wheeple.contentcraft.event;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import xyz.wheeple.contentcraft.Contentcraft;
import xyz.wheeple.contentcraft.util.ModTags;

@EventBusSubscriber(modid = Contentcraft.MOD_ID)
public class MobRepellentHandler {

    @SubscribeEvent
    public static void onEntityTick(EntityTickEvent.Post event) {
        if (!(event.getEntity() instanceof LivingEntity living) || !(living instanceof Enemy)) {
            return;
        }

        if (!living.level().isClientSide) {
            BlockPos pos = living.blockPosition();
            int radius = 5;

            for (BlockPos checkPos : BlockPos.betweenClosed(pos.offset(-radius, -1, -radius), pos.offset(radius, 1, radius))) {
                if (living.level().getBlockState(checkPos).is(ModTags.Blocks.HOSTILE_MOB_REPELLANT_BLOCKS)) {

                    Vec3 repelDir = living.position().subtract(Vec3.atCenterOf(checkPos)).normalize();
                    living.setDeltaMovement(living.getDeltaMovement().add(repelDir.scale(0.15)));
                    living.hurtMarked = true;

                    AABB blockBounds = new AABB(checkPos);
                    if (living.getBoundingBox().intersects(blockBounds)) {
                        living.hurt(living.level().damageSources().magic(), 2.0F);
                    }
                    break;
                }
            }
        }
    }
}