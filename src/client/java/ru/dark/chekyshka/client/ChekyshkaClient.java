package ru.dark.chekyshka.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.ItemStack;
import ru.dark.chekyshka.Chekyshka;

public class ChekyshkaClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null || client.screen != null) return;
            boolean locked = Chekyshka.hasBottle(client.player.getInventory());
            if (!locked) return;
            // Keep the player in place until the bottle has been consumed.
            client.player.input.forwardImpulse = 0.0F;
            client.player.input.leftImpulse = 0.0F;
            client.player.input.jumping = false;
            client.player.input.shiftKeyDown = false;
            client.options.keyAttack.setDown(false);
            client.options.keyPickItem.setDown(false);
            // Keep use available only while the bottle is selected, so the player can drink it.
            ItemStack held = client.player.getMainHandItem();
            if (!held.is(Chekyshka.CHEKUSHKA)) client.options.keyUse.setDown(false);
        });
    }
}
