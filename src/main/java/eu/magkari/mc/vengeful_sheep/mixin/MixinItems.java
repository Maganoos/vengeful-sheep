package eu.magkari.mc.vengeful_sheep.mixin;

import eu.magkari.mc.vengeful_sheep.consumable.RestoreLayersConsumeEffect;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.component.type.ConsumableComponent;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.Set;
import java.util.function.BiFunction;

@Mixin(Items.class)
public class MixinItems {
    @ModifyVariable(
            method = "register(Lnet/minecraft/block/Block;Ljava/util/function/BiFunction;Lnet/minecraft/item/Item$Settings;)Lnet/minecraft/item/Item;",
            at = @At(
                    value = "HEAD"
            ),
            index = 2,
            argsOnly = true)
    private static Item.Settings vengeful_sheep$makeGrassEdible(Item.Settings settings, Block block, BiFunction<Block, Item.Settings, Item> factory) {
        if (Set.of(Blocks.TALL_GRASS, Blocks.SHORT_GRASS, Blocks.SEAGRASS).contains(block)) {
            return settings.food(
                    new FoodComponent.Builder().nutrition(0).alwaysEdible().build(),
                    ConsumableComponent.builder().consumeEffect(new RestoreLayersConsumeEffect()).build()
            );
        }
        return settings;
    }
}
