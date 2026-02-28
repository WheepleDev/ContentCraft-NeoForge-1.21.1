package xyz.wheeple.contentcraft.event;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import xyz.wheeple.contentcraft.init.ModItems;

import java.util.Set;

@EventBusSubscriber(modid = "contentcraft")
public class XaenonArmorEvents {

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        if (player.level().isClientSide || !(player instanceof ServerPlayer serverPlayer)) return;
        applyLeggingsLogic(serverPlayer);
        applyBootsLogic(player);
    }

    @SubscribeEvent
    public static void applyChestplateLogic(MobEffectEvent.Applicable event) {
        if (event.getEntity() instanceof Player player && event.getEffectInstance().getEffect().is(MobEffects.LEVITATION)) {
            if (player.getItemBySlot(EquipmentSlot.CHEST).is(ModItems.XAENON_CHESTPLATE.get())) {
                if (player.getRandom().nextFloat() < 0.70f) {
                    event.setResult(MobEffectEvent.Applicable.Result.DO_NOT_APPLY);
                }
            }
        }
    }

    private static void applyLeggingsLogic(ServerPlayer player) {
        if (player.getCooldowns().isOnCooldown(ModItems.XAENON_LEGGINGS.get())) return;

        ItemStack leggings = player.getItemBySlot(EquipmentSlot.LEGS);
        if (leggings.is(ModItems.XAENON_LEGGINGS.get()) && player.getY() < -2) {
            int pearlSlot = player.getInventory().findSlotMatchingItem(new ItemStack(Items.ENDER_PEARL));
            if (pearlSlot != -1) {
                BlockPos safePos = findSafeLandingPos(player);
                if (safePos != null) {
                    player.getInventory().removeItem(pearlSlot, 1);
                    if (player.getRandom().nextFloat() < 0.20f) {
                        player.getCooldowns().addCooldown(ModItems.XAENON_LEGGINGS.get(), 100);
                        player.serverLevel().playSound(null, player.getX(), player.getY(), player.getZ(),
                                SoundEvents.GLASS_BREAK, SoundSource.PLAYERS, 1.5F, 1.2F);
                        player.serverLevel().sendParticles(player, ParticleTypes.POOF, true,
                                player.getX(), player.getY() + 1.0, player.getZ(),
                                50, 0.3, 0.3, 0.3, 0.1);
                        return;
                    }
                    player.getCooldowns().addCooldown(ModItems.XAENON_LEGGINGS.get(), 60);
                    executeVoidRescue(player, safePos);
                }
            }
        }
    }

    private static void applyBootsLogic(Player player) {
        ItemStack boots = player.getItemBySlot(EquipmentSlot.FEET);
        if (boots.is(ModItems.XAENON_BOOTS.get()) && player.isCrouching()) {
            if (player.level().getBlockState(player.blockPosition().below()).is(Blocks.END_STONE)) {
                Vec3 movement = player.getDeltaMovement();
                player.setDeltaMovement(movement.x * 1.5, movement.y, movement.z * 1.5);
                player.hurtMarked = true;
            }
        }
    }

    private static BlockPos findSafeLandingPos(ServerPlayer player) {
        ServerLevel level = player.serverLevel();
        int px = player.getBlockX();
        int pz = player.getBlockZ();
        for (int y = 30; y < 130; y++) {
            for (int r = 0; r <= 32; r++) {
                for (int x = -r; x <= r; x++) {
                    for (int z = -r; z <= r; z++) {
                        if (Math.abs(x) == r || Math.abs(z) == r) {
                            BlockPos checkPos = new BlockPos(px + x, y, pz + z);
                            if (level.getBlockState(checkPos).isSolid() &&
                                    level.isEmptyBlock(checkPos.above()) &&
                                    level.isEmptyBlock(checkPos.above(2))) {
                                return checkPos;
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    private static void executeVoidRescue(ServerPlayer player, BlockPos target) {
        ServerLevel level = player.serverLevel();
        double tx = target.getX() + 0.5;
        double ty = target.getY() + 1.1;
        double tz = target.getZ() + 0.5;
        level.playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.ENDER_PEARL_THROW, SoundSource.PLAYERS, 1.0F, 1.0F);
        player.connection.teleport(tx, ty, tz, player.getYRot(), player.getXRot(), Set.of());
        player.setDeltaMovement(Vec3.ZERO);
        player.fallDistance = 0;
        player.hurtMarked = true;
        level.playSound(null, tx, ty, tz, SoundEvents.ENDERMAN_TELEPORT, SoundSource.PLAYERS, 1.0F, 1.0F);
        level.sendParticles(player, ParticleTypes.PORTAL, true, tx, ty + 1.0, tz, 200, 0.5, 0.5, 0.5, 1.5);
        level.sendParticles(player, ParticleTypes.WITCH, true, tx, ty + 1.0, tz, 100, 0.3, 0.8, 0.3, 0.1);
        level.sendParticles(player, ParticleTypes.DRAGON_BREATH, true, tx, ty + 0.5, tz, 50, 0.2, 0.2, 0.2, 0.05);
        level.sendParticles(player, ParticleTypes.REVERSE_PORTAL, true, tx, ty + 1.0, tz, 100, 0.5, 1.0, 0.5, 0.2);
    }
}