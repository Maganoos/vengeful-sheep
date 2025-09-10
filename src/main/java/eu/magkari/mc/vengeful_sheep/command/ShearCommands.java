package eu.magkari.mc.vengeful_sheep.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import eu.magkari.mc.vengeful_sheep.PartsHandler;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import java.util.Collection;

public class ShearCommands {
    public static void registerCommands() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(CommandManager.literal("unshear")
                    .requires(source -> source.hasPermissionLevel(2))
                    .then(CommandManager.argument("players", EntityArgumentType.players())
                            .executes(ShearCommands::executeRestoreParts))
            );
        });

        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(CommandManager.literal("shear")
                    .requires(source -> source.hasPermissionLevel(2))
                    .then(CommandManager.argument("players", EntityArgumentType.players())
                            .executes(ShearCommands::executeShear))
            );
        });
    }

    private static int executeRestoreParts(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerCommandSource source = context.getSource();
        Collection<ServerPlayerEntity> players = EntityArgumentType.getPlayers(context, "players");

        int restoredCount = 0;

        for (ServerPlayerEntity player : players) {
            if (PartsHandler.isSheared(player)) {
                PartsHandler.addBack(player);
                restoredCount++;
            } else {
                source.sendFeedback(() -> Text.translatable(
                        "commands.vengeful_sheep.unshear.player_none",
                        player.getDisplayName()
                ), true);
            }
        }

        int finalRestoredCount = restoredCount;
        source.sendFeedback(() -> Text.translatable(
                "commands.vengeful_sheep.unshear.summary",
                finalRestoredCount
        ), true);

        return finalRestoredCount > 0 ? Command.SINGLE_SUCCESS : 0;
    }

    private static int executeShear(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerCommandSource source = context.getSource();
        Collection<ServerPlayerEntity> players = EntityArgumentType.getPlayers(context, "players");

        int shearedCount = 0;

        for (ServerPlayerEntity player : players) {
            if (!PartsHandler.isSheared(player)) {
                PartsHandler.removeParts(player);
                shearedCount++;
            } else {
                source.sendFeedback(() -> Text.translatable(
                        "commands.vengeful_sheep.shear.player_none",
                        player.getDisplayName()
                ), true);
            }
        }

        int finalShearedCount = shearedCount;
        source.sendFeedback(() -> Text.translatable(
                "commands.vengeful_sheep.shear.summary",
                finalShearedCount
        ), true);

        return finalShearedCount > 0 ? Command.SINGLE_SUCCESS : 0;
    }
}
