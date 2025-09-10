package eu.magkari.mc.vengeful_sheep.mixin;

import eu.magkari.mc.vengeful_sheep.PartsHandler;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public class MixinPlayerEntity {

    @Inject(method="tick", at = @At("HEAD"))
    private void vengeful_sheep$lockParts(CallbackInfo ci) {
        PlayerEntity player = (PlayerEntity) (Object) this;
        if (PartsHandler.playerParts.containsKey(player.getUuid())) {
            player.getDataTracker().set(((PlayerEntityAccessor) player).getPlayerModelParts(), (byte) 0);
        }
    }

    @Mixin(PlayerEntity.class)
    public interface PlayerEntityAccessor {
        @Accessor("PLAYER_MODEL_PARTS")
        TrackedData<Byte> getPlayerModelParts();
    }
}
