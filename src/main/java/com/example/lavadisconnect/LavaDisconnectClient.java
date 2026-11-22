package com.example.lavadisconnect;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.MessageScreen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.text.Text;

public class LavaDisconnectClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null || client.world == null) return;
            if (client.player.isInLava()) {
                disconnect(client);
            }
        });
    }

    // To samo, co robi przycisk "Disconnect" w menu pauzy (Esc)
    private void disconnect(MinecraftClient client) {
        boolean singleplayer = client.isInSingleplayer();
        client.world.disconnect();
        if (singleplayer) {
            client.disconnect(new MessageScreen(Text.translatable("menu.savingLevel")));
        } else {
            client.disconnect();
        }
        client.setScreen(new TitleScreen());
    }
}
