package io.felux.pouches.title;

import net.minecraft.server.v1_10_R1.IChatBaseComponent;
import net.minecraft.server.v1_10_R1.Packet;
import net.minecraft.server.v1_10_R1.PacketPlayOutTitle;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class Title_v1_10_R1 implements Title {
    public Title_v1_10_R1() {
        super();
    }

    @Override
    public void sendTitle(final Player player, final String message) {
        final PacketPlayOutTitle title2 = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, IChatBaseComponent.ChatSerializer.a(message));
        final PacketPlayOutTitle length = new PacketPlayOutTitle(0, 11, 10);
        ((CraftPlayer) player.getPlayer()).getHandle().playerConnection.sendPacket((Packet) title2);
        ((CraftPlayer) player.getPlayer()).getHandle().playerConnection.sendPacket((Packet) length);
    }

    @Override
    public void sendSubtitle(final Player player, final String message) {
        final PacketPlayOutTitle title2 = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, IChatBaseComponent.ChatSerializer.a(message));
        ((CraftPlayer) player.getPlayer()).getHandle().playerConnection.sendPacket((Packet) title2);
    }
}
