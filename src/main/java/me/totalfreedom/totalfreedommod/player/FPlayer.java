package me.totalfreedom.totalfreedommod.player;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import me.totalfreedom.totalfreedommod.TotalFreedomMod;
import me.totalfreedom.totalfreedommod.caging.CageData;
import me.totalfreedom.totalfreedommod.config.ConfigEntry;
import me.totalfreedom.totalfreedommod.freeze.FreezeData;
import me.totalfreedom.totalfreedommod.util.FUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class FPlayer
{

    public static final long AUTO_PURGE_TICKS = 5L * 60L * 20L;

    @Getter
    private final TotalFreedomMod plugin;
    @Getter
    private final String name;
    @Getter
    private final String ip;
    //
    @Setter
    private Player player;
    //
    private BukkitTask unmuteTask;
    @Getter
    private final FreezeData freezeData = new FreezeData(this);
    @Getter
    private double fuckoffRadius = 0;
    private int messageCount = 0;
    private int totalBlockDestroy = 0;
    private int totalBlockPlace = 0;
    private int freecamDestroyCount = 0;
    private int freecamPlaceCount = 0;
    @Getter
    private final CageData cageData = new CageData(this);
    private boolean isOrbiting = false;
    private double orbitStrength = 10.0;
    private BukkitTask lockupScheduleTask = null;
    private boolean lockedUp = false;
    private String lastMessage = "";
    private boolean inStaffchat = false;
    private boolean allCommandsBlocked = false;
    @Getter
    @Setter
    private boolean superadminIdVerified = false;
    private String lastCommand = "";
    private boolean cmdspyEnabled = false;
    private String tag = null;
    private int warningCount = 0;
    @Getter
    @Setter
    private boolean editBlocked = false;
    @Getter
    @Setter
    private boolean pvpBlocked = false;
    @Getter
    @Setter
    private boolean invSee = false;

    public FPlayer(TotalFreedomMod plugin, Player player)
    {
        this(plugin, player.getName(), FUtil.getIp(player));
    }

    private FPlayer(TotalFreedomMod plugin, String name, String ip)
    {
        this.plugin = plugin;
        this.name = name;
        this.ip = ip;
    }

    public Player getPlayer()
    {
        if (player != null && !player.isOnline())
        {
            player = null;
        }

        if (player == null)
        {
            for (Player onlinePlayer : Bukkit.getOnlinePlayers())
            {
                if (FUtil.getIp(onlinePlayer).equals(ip))
                {
                    player = onlinePlayer;
                    break;
                }
            }
        }

        return player;
    }

    public boolean isOrbiting()
    {
        return isOrbiting;
    }

    public void startOrbiting(double strength)
    {
        this.isOrbiting = true;
        this.orbitStrength = strength;
    }

    public void stopOrbiting()
    {
        this.isOrbiting = false;
    }

    public double orbitStrength()
    {
        return orbitStrength;
    }

    public boolean isFuckOff()
    {
        return fuckoffRadius > 0;
    }

    public void setFuckoff(double radius)
    {
        this.fuckoffRadius = radius;
    }

    public void disableFuckoff()
    {
        this.fuckoffRadius = 0;
    }

    public void resetMsgCount()
    {
        this.messageCount = 0;
    }

    public int incrementAndGetMsgCount()
    {
        return this.messageCount++;
    }

    public int incrementAndGetBlockDestroyCount()
    {
        return this.totalBlockDestroy++;
    }

    public void resetBlockDestroyCount()
    {
        this.totalBlockDestroy = 0;
    }

    public int incrementAndGetBlockPlaceCount()
    {
        return this.totalBlockPlace++;
    }

    public void resetBlockPlaceCount()
    {
        this.totalBlockPlace = 0;
    }

    public int incrementAndGetFreecamDestroyCount()
    {
        return this.freecamDestroyCount++;
    }

    public void resetFreecamDestroyCount()
    {
        this.freecamDestroyCount = 0;
    }

    public int incrementAndGetFreecamPlaceCount()
    {
        return this.freecamPlaceCount++;
    }

    public void resetFreecamPlaceCount()
    {
        this.freecamPlaceCount = 0;
    }

    public boolean isMuted()
    {
        return unmuteTask != null;
    }

    public void setMuted(boolean muted)
    {
        FUtil.cancel(unmuteTask);
        plugin.mu.MUTED_PLAYERS.remove(getPlayer().getName());
        unmuteTask = null;

        if (!muted)
        {
            return;
        }

        if (getPlayer() == null)
        {
            return;
        }

        plugin.mu.MUTED_PLAYERS.add(getPlayer().getName());

        unmuteTask = new BukkitRunnable()
        {
            @Override
            public void run()
            {
                if (getPlayer() != null)
                {
                    FUtil.staffAction(ConfigEntry.SERVER_NAME.getString(), "Unmuting " + getPlayer().getName(), false);
                    setMuted(false);
                }
                else
                {
                    FUtil.staffAction(ConfigEntry.SERVER_NAME.getString(), "Unmuting " + getName(), false);
                    plugin.mu.MUTED_PLAYERS.remove(getName());
                }
            }
        }.runTaskLater(plugin, AUTO_PURGE_TICKS);
    }

    public BukkitTask getLockupScheduleID()
    {
        return this.lockupScheduleTask;
    }

    public void setLockupScheduleId(BukkitTask id)
    {
        this.lockupScheduleTask = id;
    }

    public boolean isLockedUp()
    {
        return this.lockedUp;
    }

    public void setLockedUp(boolean lockedUp)
    {
        this.lockedUp = lockedUp;
    }

    public void setLastMessage(String message)
    {
        this.lastMessage = message;
    }

    public String getLastMessage()
    {
        return lastMessage;
    }

    public void setStaffChat(boolean inStaffchat)
    {
        this.inStaffchat = inStaffchat;
    }

    public boolean inStaffChat()
    {
        return this.inStaffchat;
    }

    public boolean allCommandsBlocked()
    {
        return this.allCommandsBlocked;
    }

    public void setCommandsBlocked(boolean commandsBlocked)
    {
        this.allCommandsBlocked = commandsBlocked;
    }

    public String getLastCommand()
    {
        return lastCommand;
    }

    public void setLastCommand(String lastCommand)
    {
        this.lastCommand = lastCommand;
    }

    public void setCommandSpy(boolean enabled)
    {
        this.cmdspyEnabled = enabled;
    }

    public boolean cmdspyEnabled()
    {
        return cmdspyEnabled;
    }

    public void setTag(String tag)
    {
        if (tag == null)
        {
            this.tag = null;
        }
        else
        {
            this.tag = FUtil.colorize(tag) + ChatColor.WHITE;
        }
    }

    public String getTag()
    {
        return this.tag;
    }

    public int getWarningCount()
    {
        return this.warningCount;
    }

    public void incrementWarnings()
    {
        this.warningCount++;

        if (this.warningCount % 2 == 0)
        {
            Player p = getPlayer();
            p.getWorld().strikeLightning(p.getLocation());
            FUtil.playerMsg(p, ChatColor.RED + "You have been warned at least twice now, make sure to read the rules at " + ConfigEntry.SERVER_BAN_URL.getString());
        }
    }

    private class ArrowShooter extends BukkitRunnable
    {

        private final Player player;

        private ArrowShooter(Player player)
        {
            this.player = player;
        }

        @Override
        public void run()
        {
            Arrow shot = player.launchProjectile(Arrow.class);
            shot.setVelocity(shot.getVelocity().multiply(2.0));
        }
    }
}
