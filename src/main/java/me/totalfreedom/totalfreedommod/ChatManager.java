package me.totalfreedom.totalfreedommod;

import com.google.common.base.Strings;
import me.totalfreedom.totalfreedommod.config.ConfigEntry;
import me.totalfreedom.totalfreedommod.player.FPlayer;
import me.totalfreedom.totalfreedommod.player.PlayerData;
import me.totalfreedom.totalfreedommod.rank.Displayable;
import me.totalfreedom.totalfreedommod.staff.StaffMember;
import me.totalfreedom.totalfreedommod.util.FLog;
import me.totalfreedom.totalfreedommod.util.FSync;
import me.totalfreedom.totalfreedommod.util.FUtil;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import static me.totalfreedom.totalfreedommod.util.FUtil.playerMsg;

public class ChatManager extends FreedomService
{
    @Override
    public void onStart()
    {
    }

    @Override
    public void onStop()
    {
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onPlayerChatFormat(AsyncPlayerChatEvent event)
    {
        try
        {
            handleChatEvent(event);
        }
        catch (Exception ex)
        {
            FLog.severe(ex);
        }
    }

    private void handleChatEvent(AsyncPlayerChatEvent event)
    {
        final Player player = event.getPlayer();
        String message = event.getMessage().trim();

        // Format colors and strip &k
        message = FUtil.colorize(message);
        message = message.replaceAll(ChatColor.MAGIC.toString(), "&k");

        if (!ConfigEntry.TOGGLE_CHAT.getBoolean() && !plugin.sl.isStaff(player))
        {
            event.setCancelled(true);
            playerMsg(player, "Chat is currently disabled.", org.bukkit.ChatColor.RED);
            return;
        }

        // Truncate messages that are too long - 256 characters is vanilla client max
        if (message.length() > 256)
        {
            message = message.substring(0, 256);
            FSync.playerMsg(player, "Message was shortened because it was too long to send.");
        }

        final FPlayer fPlayer = plugin.pl.getPlayerSync(player);
        if (fPlayer.isLockedUp())
        {
            FSync.playerMsg(player, "You're locked up and cannot talk.");
            event.setCancelled(true);
            return;
        }

        // Check for staffchat
        if (fPlayer.inStaffChat())
        {
            FSync.staffChatMessage(player, message);
            event.setCancelled(true);
            return;
        }

        // Finally, set message
        event.setMessage(message);

        // Make format
        String format = "%1$s §8\u00BB §f%2$s";

        String tag = fPlayer.getTag();
        if (tag != null && !tag.isEmpty())
        {
            format = tag.replace("%", "%%") + " " + format;
        }

        // Check for mentions
        Boolean mentionEveryone = ChatColor.stripColor(message).toLowerCase().contains("@everyone") && plugin.sl.isStaff(player);
        for (Player p : server.getOnlinePlayers())
        {
            if (ChatColor.stripColor(message).toLowerCase().contains("@" + p.getName().toLowerCase()) || mentionEveryone)
            {
                p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, SoundCategory.MASTER, 1337F, 0.9F);
            }
        }

        // Set format
        event.setFormat(format);
    }

    public ChatColor getColor(Displayable display)
    {
        ChatColor color = display.getColor();
        return color;
    }

    public String getColoredTag(Displayable display)
    {
        ChatColor color = display.getColor();
        return color + display.getAbbr();
    }

    public void staffChat(CommandSender sender, String message)
    {
        Displayable display = plugin.rm.getDisplay(sender);
        FLog.info("[STAFF] " + sender.getName() + " " + display.getTag() + ": " + message, true);

        for (Player player : server.getOnlinePlayers())
        {
            if (plugin.sl.isStaff(player))
            {
                StaffMember staffMember = plugin.sl.getAdmin(player);
                if (!Strings.isNullOrEmpty(staffMember.getAcFormat()))
                {
                    String format = staffMember.getAcFormat();
                    ChatColor color = getColor(display);
                    String msg = format.replace("%name%", sender.getName()).replace("%rank%", display.getAbbr()).replace("%rankcolor%", color.toString()).replace("%msg%", message);
                    player.sendMessage(FUtil.colorize(msg));
                }
                else
                {
                    player.sendMessage("[" + ChatColor.AQUA + "STAFF" + ChatColor.WHITE + "] " + ChatColor.DARK_RED + sender.getName() + ChatColor.DARK_GRAY + " [" + getColoredTag(display) + ChatColor.DARK_GRAY + "]" + ChatColor.WHITE + ": " + ChatColor.GOLD + FUtil.colorize(message));
                }
            }
        }
    }

    public void reportAction(Player reporter, Player reported, String report)
    {
        for (Player player : server.getOnlinePlayers())
        {
            if (plugin.sl.isStaff(player))
            {
                playerMsg(player, ChatColor.RED + "[REPORTS] " + ChatColor.GOLD + reporter.getName() + " has reported " + reported.getName() + " for " + report);
            }
        }
        FLog.info("[REPORTS] " + reporter.getName() + " has reported " + reported.getName() + " for " + report);
    }
}