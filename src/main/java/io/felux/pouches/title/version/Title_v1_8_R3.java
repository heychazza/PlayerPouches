package io.felux.pouches.title.version;

import io.felux.pouches.title.Title;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class Title_v1_8_R3 implements Title {
    public Title_v1_8_R3() {
        super();
    }

    @Override
    public void sendTitle(final Player player, final String message) {
        final PacketPlayOutTitle title2 = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, IChatBaseComponent.ChatSerializer.a(message));
        final PacketPlayOutTitle length = new PacketPlayOutTitle(0, 11, 10);
        ((CraftPlayer) player.getPlayer()).getHandle().playerConnection.sendPacket(title2);
        ((CraftPlayer) player.getPlayer()).getHandle().playerConnection.sendPacket(length);
    }

    @Override
    public void sendSubtitle(final Player player, final String message) {
        final PacketPlayOutTitle title2 = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, IChatBaseComponent.ChatSerializer.a(message));
        ((CraftPlayer) player.getPlayer()).getHandle().playerConnection.sendPacket(title2);
    }
}