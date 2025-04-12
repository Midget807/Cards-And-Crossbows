package net.midget807.cardsncrossbows.mixin.client;

import net.midget807.cardsncrossbows.effect.ModEffects;
import net.midget807.cardsncrossbows.util.ManicFadeState;
import net.midget807.cardsncrossbows.util.ModTextureIdentifiers;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public abstract class InGameHudMixin {
    @Unique
    private int schizophreniaTicks = 0;

    @Unique
    private int fadeTime = this.client != null && this.client.player != null ? ManicFadeState.getServerState(this.client.player.getServer()).getFadeTime() : 2 * 20;

    @Shadow @Final private MinecraftClient client;

    @Shadow protected abstract void renderOverlay(DrawContext context, Identifier texture, float opacity);

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;getFrozenTicks()I", shift = At.Shift.BEFORE))
    private void cardsncrossbows$renderOverlays(DrawContext context, float tickDelta, CallbackInfo ci) {
        if (this.client.player != null && this.client.getServer() != null && this.client.player.hasStatusEffect(ModEffects.SCHIZOPHRENIA) && this.client.player.getStatusEffect(ModEffects.SCHIZOPHRENIA) != null) {
            if (this.client.player.getStatusEffect(ModEffects.SCHIZOPHRENIA).getDuration() >= fadeTime) {
                this.schizophreniaTicks++;
                this.renderOverlay(context, ModTextureIdentifiers.MANIC_TEXTURE, this.getManicScale(schizophreniaTicks));
            } else {
                this.schizophreniaTicks = 0;
                this.renderOverlay(context, ModTextureIdentifiers.MANIC_TEXTURE, this.getManicScale(this.client.player.getStatusEffect(ModEffects.SCHIZOPHRENIA).getDuration()));
            }
        }

    }

    @Unique
    private float getManicScale(float tick) {
        return (float) Math.min(tick, this.fadeTime)/ this.fadeTime;
    }
}
