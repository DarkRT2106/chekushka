package ru.dark.chekyshka;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

public class ChekushkaItem extends Item {
    public ChekushkaItem(Properties properties) { super(properties); }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) { return UseAnim.DRINK; }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) { return 100; }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        return ItemUtils.startUsing(player, hand, stack);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity user) {
        if (user instanceof ServerPlayer player) {
            player.addTag(Chekyshka.FINISHED_TAG);
            player.getInventory().items.removeIf(item -> item.is(Chekyshka.CHEKUSHKA));
            player.getInventory().offhand.removeIf(item -> item.is(Chekyshka.CHEKUSHKA));
            player.displayClientMessage(net.minecraft.network.chat.Component.literal("Чекушка выпита. Управление разблокировано."), true);
        }
        if (user instanceof Player) return ItemStack.EMPTY;
        return ItemStack.EMPTY;
    }

    private static final class ItemUtils {
        static InteractionResultHolder<ItemStack> startUsing(Player player, InteractionHand hand, ItemStack stack) {
            player.startUsingItem(hand);
            return InteractionResultHolder.consume(stack);
        }
    }
}
