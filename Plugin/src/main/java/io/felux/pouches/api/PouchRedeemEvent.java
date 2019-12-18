package io.felux.pouches.api;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

public class PouchRedeemEvent extends Event implements Cancellable {
    private static final HandlerList HANDLERS_LIST = new HandlerList();
    private Player player;
    private Pouch pouch;
    private Long amount;
    private ItemStack item;
    private PouchSlot slot;
    private boolean cancelled;

    public PouchRedeemEvent(Player player, Pouch pouch, Long amount, ItemStack item, PouchSlot slot) {
        this.player = player;
        this.pouch = pouch;
        this.amount = amount;
        this.item = item;
        this.slot = slot;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS_LIST;
    }

    public Player getPlayer() {
        return player;
    }

    public Pouch getPouch() {
        return pouch;
    }

    public void setPouch(Pouch pouch) {
        this.pouch = pouch;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public ItemStack getItem() {
        return item;
    }

    public void setItem(ItemStack item) {
        this.item = item;
    }

    public PouchSlot getSlot() {
        return slot;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public HandlerList getHandlers() {
        return HANDLERS_LIST;
    }
}
