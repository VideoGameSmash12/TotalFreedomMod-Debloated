package me.totalfreedom.totalfreedommod.command;

import me.totalfreedom.totalfreedommod.rank.Rank;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandPermissions(level = Rank.OP, source = SourceType.ONLY_IN_GAME, blockHostConsole = true)
@CommandParameters(description = "Report a player for all staff to see.", usage = "/<command> <player> <reason>")
public class Command_report extends FreedomCommand
{

    @Override
    public boolean run(CommandSender sender, Player playerSender, Command cmd, String commandLabel, String[] args, boolean senderIsConsole)
    {
        if (args.length < 2)
        {
            return false;
        }

        Player player = getPlayer(args[0], true);

        if (player == null)
        {
            msg(PLAYER_NOT_FOUND);
            return true;
        }

        if (sender instanceof Player)
        {
            if (player.equals(playerSender))
            {
                msg(ChatColor.RED + "Please, don't try to report yourself.");
                return true;
            }
        }

        if (plugin.sl.isStaff(player))
        {
            msg(ChatColor.RED + "You can not report server staff.");
            return true;
        }

        String report = StringUtils.join(ArrayUtils.subarray(args, 1, args.length), " ");
        plugin.cm.reportAction(playerSender, player, report);

        boolean logged = false;

        msg(ChatColor.GREEN + "Thank you, your report has been successfully logged.");

        return true;
    }
}