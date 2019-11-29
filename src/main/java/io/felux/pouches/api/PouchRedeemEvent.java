package io.felux.pouches.api;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PouchRedeemEvent extends Event implements Cancellable {
    private Player player;
    private Pouch pouch;
    private Long amount;
    private static final HandlerList HANDLERS_LIST = new HandlerList();
    private boolean cancelled;

    public PouchRedeemEvent(Player player, Pouch pouch, Long amount) {
        this.player = player;
        this.pouch = pouch;
        this.amount = amount;
    }

    public Player getPlayer() {
        return player;
    }

    public Pouch getPouch() {
        return pouch;
    }

    public Long getAmount() {
        return amount;
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

    public static HandlerList getHandlerList() {
        return HANDLERS_LIST;
    }
}
