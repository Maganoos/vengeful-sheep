package eu.magkari.mc.vengeful_sheep;

import eu.magkari.mc.vengeful_sheep.mixin.MixinPlayerEntity.PlayerEntityAccessor;
import net.minecraft.entity.player.PlayerEntity;

import java.util.Map;
import java.util.UUID;
import java.util.WeakHashMap;

public class PartsHandler {
    public static Map<UUID, Byte> playerParts = new WeakHashMap<>();

    public static void removeParts(PlayerEntity player) {
        UUID id = player.getUuid();

        byte originalParts = player.getDataTracker().get(((PlayerEntityAccessor) player).getPlayerModelParts());
        playerParts.put(id, originalParts);

        player.getDataTracker().set(((PlayerEntityAccessor) player).getPlayerModelParts(), (byte) 0);
    }

    public static void addBack(PlayerEntity player) {
        player.getDataTracker().set(((PlayerEntityAccessor) player).getPlayerModelParts(), playerParts.get(player.getUuid()));
        playerParts.remove(player.getUuid());
    }

    public static boolean isSheared(PlayerEntity player) {
        return playerParts.containsKey(player.getUuid());
    }
}
