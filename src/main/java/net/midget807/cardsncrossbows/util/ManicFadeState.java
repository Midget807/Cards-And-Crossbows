package net.midget807.cardsncrossbows.util;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.PersistentState;
import net.minecraft.world.PersistentStateManager;

public class ManicFadeState extends PersistentState {
    public static final String FADE_TIME_KEY = "FadeTime";
    public int fadeTime = 2 * 20; /**Default time - 2 seconds*/

    @Override
    public NbtCompound writeNbt(NbtCompound nbt) {
        nbt.putInt(FADE_TIME_KEY, this.fadeTime);
        return nbt;
    }

    public static ManicFadeState createFromNbt(NbtCompound nbt) {
        ManicFadeState state = new ManicFadeState();
        state.fadeTime = nbt.getInt(FADE_TIME_KEY);
        return state;
    }
    public static ManicFadeState createNew() {
        ManicFadeState state = new ManicFadeState();
        state.fadeTime = 2 * 20;
        return state;
    }
    public static ManicFadeState getServerState(MinecraftServer server) {
        PersistentStateManager persistentStateManager = server.getOverworld().getPersistentStateManager();
        ManicFadeState state = persistentStateManager.getOrCreate(ManicFadeState::createFromNbt, ManicFadeState::createNew, "fadeTime");
        state.markDirty();
        return state;
    }

    public int getFadeTime() {
        return this.fadeTime;
    }
    public void setFadeTime(int fadeTime) {
        this.fadeTime = fadeTime;
    }
}
