package eu.magkari.mc.vengeful_sheep.mixin;

import eu.magkari.mc.vengeful_sheep.PartsHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.event.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(SheepEntity.class)
public class MixinSheepEntity {
	@Redirect(
			method = "interactMob",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/entity/passive/SheepEntity;emitGameEvent(Lnet/minecraft/registry/entry/RegistryEntry;Lnet/minecraft/entity/Entity;)V"
			)
	)
	private void vengeful_sheep$onShear(SheepEntity sheep, RegistryEntry<GameEvent> event, Entity entity) {
		if (event == GameEvent.SHEAR && entity instanceof PlayerEntity) {
			PartsHandler.removeParts((PlayerEntity) entity);
		}
		sheep.emitGameEvent(event, entity);

	}
}