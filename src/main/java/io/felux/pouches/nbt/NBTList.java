package io.felux.pouches.nbt;

import io.felux.pouches.Pouches;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Method;
import java.util.AbstractList;

public class NBTList {

    private static final String version = "net.minecraft.server." + Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
    //private static final String cbVersion = "org.bukkit.craftbukkit." + Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
    private static Class<?> tagListClass;
    //private static Class<?> nbtBaseClass;

    static {
        try {
            tagListClass = Class.forName(version + ".NBTTagList");
            //nbtBaseClass = Class.forName(version + ".NBTBase");
        } catch (Exception ex) {
            ex.printStackTrace();
            Bukkit.getPluginManager().disablePlugin(JavaPlugin.getPlugin(Pouches.class));
        }
    }

    private final Object tagList;

    public NBTList() {
        this(null);
    }

    public NBTList(Object tagCompound) {
        Object toSet = tagCompound;
        if (tagCompound == null) {
            try {
                toSet = tagListClass.newInstance();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        this.tagList = toSet;
    }

    public Object getTagList() {
        return tagList;
    }

    public boolean isEmpty() {
        try {
            Method m = tagListClass.getMethod("isEmpty");
            m.setAccessible(true);
            Object r = m.invoke(this.tagList);
            m.setAccessible(false);
            return r instanceof Boolean ? (Boolean) r : true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return true;
        }
    }

    public void add(NBT value) {
        add(value.getTagCompund());
    }

    public <T> void add(NBTBaseType type, T value) {
        add(type.make(value));
    }

    public <T> void add(NBTBaseType type, T[] values) {
        for (T value : values) {
            add(type, value);
        }
    }

    private void add(Object nbt) {
        try {
            Method m = AbstractList.class.getMethod("add", Object.class);
            m.setAccessible(true);
            m.invoke(tagList, nbt);
            m.setAccessible(false);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
