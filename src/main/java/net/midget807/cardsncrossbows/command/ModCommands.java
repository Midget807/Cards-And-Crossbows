package net.midget807.cardsncrossbows.command;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.midget807.cardsncrossbows.CardsNCrossbowsMain;
import net.midget807.cardsncrossbows.util.ManicFadeState;
import net.minecraft.text.Text;

import static com.mojang.brigadier.arguments.IntegerArgumentType.*;
import static net.minecraft.server.command.CommandManager.*;

public class ModCommands {
    public static void registerCommands() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(literal("cardsncrossbows")
                    .requires(source -> source.hasPermissionLevel(2))
                    .then(literal("manicFadeTime")
                            .then(argument("time", integer(0, 72000))
                                    .executes(context -> {
                                        ManicFadeState state = ManicFadeState.getServerState(context.getSource().getServer());
                                        state.setFadeTime(getInteger(context, "time"));
                                        state.markDirty();
                                        context.getSource().sendFeedback(() -> Text.literal("Schizophrenia fade time is set to: " + getInteger(context, "time")), true);
                                        return 1;
                                    })
                            )
                    )
            );
        });
    }
    public static void registerModCommands() {
        CardsNCrossbowsMain.LOGGER.info("Registering Mod Commands");
        //registerCommands();
    }
}
