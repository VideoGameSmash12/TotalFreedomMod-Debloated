package me.totalfreedom.totalfreedommod;

import java.util.Arrays;
import java.util.List;
import me.totalfreedom.totalfreedommod.util.FLog;
import me.totalfreedom.totalfreedommod.util.FUtil;
import net.minecraft.server.v1_16_R3.EntityPlayer;
import net.minecraft.server.v1_16_R3.MinecraftServer;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_16_R3.CraftServer;

public class ServerInterface extends FreedomService
{
    public static final String COMPILE_NMS_VERSION = "v1_16_R3";

    @Override
    public void onStart()
    {
    }

    @Override
    public void onStop()
    {
    }

    public static void warnVersion()
    {
        final String nms = FUtil.getNMSVersion();

        if (!COMPILE_NMS_VERSION.equals(nms))
        {
            FLog.warning(TotalFreedomMod.pluginName + " is compiled for " + COMPILE_NMS_VERSION + " but the server is running version " + nms + "!");
            FLog.warning("This might result in unexpected behaviour!");
        }
    }

    public void setOnlineMode(boolean mode)
    {
        getServer().setOnlineMode(mode);
    }

    public int purgeWhitelist()
    {
        String[] whitelisted = getServer().getPlayerList().getWhitelisted();
        int size = whitelisted.length;
        for (EntityPlayer player : getServer().getPlayerList().players)
        {
            getServer().getPlayerList().getWhitelist().remove(player.getProfile());
        }

        try
        {
            getServer().getPlayerList().getWhitelist().save();
        }
        catch (Exception ex)
        {
            FLog.warning("Could not purge the whitelist!");
            FLog.warning(ex);
        }
        return size;
    }

    public boolean isWhitelisted()
    {
        return getServer().getPlayerList().getHasWhitelist();
    }

    public List<?> getWhitelisted()
    {
        return Arrays.asList(getServer().getPlayerList().getWhitelisted());
    }

    public String getVersion()
    {
        return getServer().getVersion();
    }

    private MinecraftServer getServer()
    {
        return ((CraftServer)Bukkit.getServer()).getServer();
    }

}
