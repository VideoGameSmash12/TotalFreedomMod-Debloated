package me.totalfreedom.totalfreedommod.bridge;

import de.myzelyam.api.vanish.PlayerHideEvent;
import de.myzelyam.api.vanish.PlayerShowEvent;
import me.totalfreedom.totalfreedommod.FreedomService;
import me.totalfreedom.totalfreedommod.player.PlayerData;
import me.totalfreedom.totalfreedommod.rank.Displayable;
import me.totalfreedom.totalfreedommod.util.FUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;

public class VanishBridge extends FreedomService
{
    @Override
    public void onStart()
    {
    }

    @Override
    public void onStop()
    {
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerUnvanish(PlayerShowEvent event)
    {
        Player player = event.getPlayer();
        Displayable display = plugin.rm.getDisplay(player);
        String tag = display.getColoredTag();

        FUtil.bcastMsg(plugin.rm.craftLoginMessage(event.getPlayer(), null));
        plugin.dc.messageChatChannel("**" + player.getName() + " joined the server" + "**");
        PlayerData playerData = plugin.pl.getData(player);
        if (playerData.getTag() != null)
        {
            tag = FUtil.colorize(playerData.getTag());
        }
        playerData.setTag(tag);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerVanish(PlayerHideEvent event)
    {
        Player player = event.getPlayer();
        plugin.dc.messageChatChannel("**" + player.getName() + " left the server" + "**");
    }
}