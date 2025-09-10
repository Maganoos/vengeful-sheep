package eu.magkari.mc.vengeful_sheep.consumable;

import eu.magkari.mc.vengeful_sheep.PartsHandler;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.consume.ConsumeEffect;
import net.minecraft.world.World;

public class RestoreLayersConsumeEffect implements ConsumeEffect {

    @Override
    public Type<? extends ConsumeEffect> getType() {
        return null;
    }

    @Override
    public boolean onConsume(World world, ItemStack stack, LivingEntity user) {
        if (PartsHandler.isSheared((PlayerEntity) user)) {
            PartsHandler.addBack((PlayerEntity) user);
            return true;
        }
        return false;
    }
}
