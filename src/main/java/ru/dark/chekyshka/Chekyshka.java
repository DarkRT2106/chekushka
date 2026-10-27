package ru.dark.chekyshka;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Inventory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Chekyshka implements ModInitializer {
    public static final String MOD_ID = "chekyshka";
    public static final String FINISHED_TAG = "chekyshka_finished";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static final ResourceKey<Item> CHEKUSHKA_KEY = ResourceKey.create(Registries.ITEM, id("chekushka_xxl"));
    public static final Item CHEKUSHKA = new ChekushkaItem(new Item.Properties().setId(CHEKUSHKA_KEY).stacksTo(1));

    @Override
    public void onInitialize() {
        BuiltInRegistries.ITEM.getClass(); // Ensure registries are available before registration.
        net.minecraft.core.Registry.register(BuiltInRegistries.ITEM, CHEKUSHKA_KEY, CHEKUSHKA);
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            ServerPlayer player = handler.player;
            if (!player.getTags().contains(FINISHED_TAG) && !hasBottle(player.getInventory())) {
                ItemStack bottle = new ItemStack(CHEKUSHKA);
                if (!player.getInventory().add(bottle)) {
                    player.drop(bottle, false);
                }
            }
        });
        LOGGER.info("Chekyshka is ready. Good luck, player.");
    }

    public static boolean hasBottle(Inventory inventory) {
        for (ItemStack stack : inventory.items) if (stack.is(CHEKUSHKA)) return true;
        for (ItemStack stack : inventory.offhand) if (stack.is(CHEKUSHKA)) return true;
        return false;
    }

    public static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(MOD_ID, path);
    }
}
