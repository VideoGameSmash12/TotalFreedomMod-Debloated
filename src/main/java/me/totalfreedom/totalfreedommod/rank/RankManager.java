package me.totalfreedom.totalfreedommod.rank;

import me.totalfreedom.totalfreedommod.FreedomService;
import me.totalfreedom.totalfreedommod.config.ConfigEntry;
import me.totalfreedom.totalfreedommod.player.FPlayer;
import me.totalfreedom.totalfreedommod.player.PlayerData;
import me.totalfreedom.totalfreedommod.staff.StaffMember;
import me.totalfreedom.totalfreedommod.util.FUtil;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class RankManager extends FreedomService
{
    @Override
    public void onStart()
    {
    }

    @Override
    public void onStop()
    {
    }

    public Displayable getDisplay(CommandSender sender)
    {
        if (!(sender instanceof Player))
        {
            return getRank(sender); // Consoles don't have display ranks
        }

        final Player player = (Player)sender;

        // Display impostors
        if (plugin.sl.isStaffImpostor(player))
        {
            return Rank.IMPOSTOR;
        }

        // If the player's an owner, display that
        if (ConfigEntry.SERVER_OWNERS.getList().contains(player.getName()))
        {
            return Title.OWNER;
        }

        // Developers always show up
        if (FUtil.isDeveloper(player))
        {
            return Title.DEVELOPER;
        }

        if (ConfigEntry.SERVER_EXECUTIVES.getList().contains(player.getName()) && plugin.sl.isStaff(player))
        {
            return Title.EXECUTIVE;
        }

        if (plugin.sl.isVerifiedStaff(player))
        {
            return Title.VERIFIED_STAFF;
        }

        return getRank(player);
    }

    public Displayable getDisplay(StaffMember staffMember)
    {
        // If the player's an owner, display that
        if (ConfigEntry.SERVER_OWNERS.getList().contains(staffMember.getName()))
        {
            return Title.OWNER;
        }

        // Developers always show up
        if (FUtil.isDeveloper((Player)staffMember))
        {
            return Title.DEVELOPER;
        }

        if (ConfigEntry.SERVER_EXECUTIVES.getList().contains(staffMember.getName()))
        {
            return Title.EXECUTIVE;
        }

        return staffMember.getRank();
    }

    public Rank getRank(CommandSender sender)
    {
        if (sender instanceof Player)
        {
            return getRank((Player)sender);
        }

        // CONSOLE?
        if (sender.getName().equals("CONSOLE"))
        {
            return ConfigEntry.STAFFLIST_CONSOLE_IS_ADMIN.getBoolean() ? Rank.SENIOR_CONSOLE : Rank.ADMIN_CONSOLE;
        }

        // Console admin, get by name
        StaffMember staffMember = plugin.sl.getEntryByName(sender.getName());

        // Unknown console: RCON?
        if (staffMember == null)
        {
            return Rank.SENIOR_CONSOLE;
        }

        Rank rank = staffMember.getRank();

        // Get console
        if (rank.hasConsoleVariant())
        {
            rank = rank.getConsoleVariant();
        }
        return rank;
    }

    public Rank getRank(Player player)
    {
        if (plugin.sl.isStaffImpostor(player))
        {
            return Rank.IMPOSTOR;
        }

        final StaffMember entry = plugin.sl.getAdmin(player);
        if (entry != null)
        {
            return entry.getRank();
        }

        return player.isOp() ? Rank.OP : Rank.NON_OP;
    }

    public String getTag(Player player, String defaultTag)
    {
        String tag = defaultTag;

        PlayerData playerData = plugin.pl.getData(player);
        String t = playerData.getTag();
        if (t != null && !t.isEmpty())
        {
            tag = t;
        }

        return tag;
    }

    public void updateDisplay(Player player)
    {
        if (!player.isOnline())
        {
            return;
        }
        FPlayer fPlayer = plugin.pl.getPlayer(player);
        PlayerData data = plugin.pl.getData(player);
        Displayable display = getDisplay(player);
        if (plugin.sl.isStaff(player) || FUtil.isDeveloper(player))
        {
            String displayName = display.getColor() + player.getName();
            player.setPlayerListName(displayName);
        }
        else
        {
            fPlayer.setTag(null);
            player.setPlayerListName(null);
        }
        fPlayer.setTag(getTag(player, display.getColoredTag()));
        updatePlayerTeam(player);
        plugin.pem.setPermissions(player);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerJoin(PlayerJoinEvent event)
    {
        final Player player = event.getPlayer();
        final FPlayer fPlayer = plugin.pl.getPlayer(player);
        PlayerData target = plugin.pl.getData(player);

        // Unban staff
        boolean isStaff = plugin.sl.isStaff(player);
        if (isStaff)
        {
            // Verify strict IP match
            if (!plugin.sl.isIdentityMatched(player))
            {
                FUtil.bcastMsg("Warning: " + player.getName() + " is a staff member, but is using an account not registered to one of their ip-list.", ChatColor.RED);
                fPlayer.setSuperadminIdVerified(false);
            }
            else
            {
                fPlayer.setSuperadminIdVerified(true);
                plugin.sl.updateLastLogin(player);
            }
        }

        if (plugin.sl.isVerifiedStaff(player))
        {
            FUtil.bcastMsg("Warning: " + player.getName() + " is a staff member, but does not have any staff permissions.", ChatColor.RED);
        }

        // Handle impostors
        boolean isImpostor = plugin.sl.isStaffImpostor(player);
        if (isImpostor)
        {
            FUtil.bcastMsg(ChatColor.AQUA + player.getName() + " is " + Rank.IMPOSTOR.getColoredLoginMessage());
            if (plugin.sl.isStaffImpostor(player))
            {
                FUtil.bcastMsg("Warning: " + player.getName() + " has been flagged as a staff impostor and has been frozen!", ChatColor.RED);
            }
            String displayName = Rank.IMPOSTOR.getColor() + player.getName();
            player.setPlayerListName(StringUtils.substring(displayName, 0, 16));
            player.getInventory().clear();
            player.setOp(false);
            player.setGameMode(GameMode.SURVIVAL);
            plugin.pl.getPlayer(player).getFreezeData().setFrozen(true);
            player.sendMessage(ChatColor.RED + "You are marked as an impostor, please verify yourself!");
            return;
        }

        // Broadcast login message
        if (isStaff || FUtil.isDeveloper(player) || plugin.pl.getData(player).hasLoginMessage())
        {
            if (!plugin.sl.isVanished(player.getName()))
            {
                FUtil.bcastMsg(craftLoginMessage(player, null));
            }
        }

        // Set display
        updateDisplay(player);
    }

    public String craftLoginMessage(Player player, String message)
    {
        Displayable display = plugin.rm.getDisplay(player);
        PlayerData playerData = plugin.pl.getData(player);
        if (message == null)
        {
            if (playerData.hasLoginMessage())
            {
                message = playerData.getLoginMessage();
            }
            else
            {
                if (display.hasDefaultLoginMessage())
                {
                    message = "%name% is %art% %coloredrank%";
                }
            }
        }
        if (message != null)
        {
            String loginMessage = FUtil.colorize(ChatColor.AQUA + (message.contains("%name%") ? "" : player.getName() + " is ")
                    + FUtil.colorize(message).replace("%name%", player.getName())
                    .replace("%rank%", display.getName())
                    .replace("%coloredrank%", display.getColoredName())
                    .replace("%art%", display.getArticle()));
            return loginMessage;
        }

        return null;
    }

    public void updatePlayerTeam(Player player)
    {
        Displayable display = getDisplay(player);
        Scoreboard scoreboard = server.getScoreboardManager().getMainScoreboard();
        Team team = scoreboard.getPlayerTeam(player);
        if (!display.hasTeam())
        {
            if (team != null)
            {
                team.removePlayer(player);
            }
            return;
        }
        String name = StringUtils.substring(display.toString(), 0, 16);
        team = scoreboard.getTeam(name);
        if (team == null)
        {
            team = scoreboard.registerNewTeam(name);
            team.setColor(display.getTeamColor());
        }
        if (!team.hasPlayer(player))
        {
            team.addPlayer(player);
        }
    }
}
