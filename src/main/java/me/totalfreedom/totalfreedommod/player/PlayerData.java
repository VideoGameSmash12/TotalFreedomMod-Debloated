package me.totalfreedom.totalfreedommod.player;

import com.google.common.collect.Lists;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import me.totalfreedom.totalfreedommod.util.FLog;
import me.totalfreedom.totalfreedommod.util.FUtil;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class PlayerData
{
    @Getter
    @Setter
    private String name;
    private final List<String> ips = Lists.newArrayList();
    private final List<String> notes = Lists.newArrayList();
    @Getter
    @Setter
    private String tag = null;
    @Getter
    @Setter
    private String rideMode = "ask";
    @Getter
    @Setter
    private String loginMessage;

    public PlayerData(ResultSet resultSet)
    {
        try
        {
            name = resultSet.getString("username");
            ips.clear();
            ips.addAll(FUtil.stringToList(resultSet.getString("ips")));
            notes.clear();
            notes.addAll(FUtil.stringToList(resultSet.getString("notes")));
            tag = resultSet.getString("tag");
            rideMode = resultSet.getString("ride_mode");
            loginMessage = resultSet.getString("login_message");
        }
        catch (SQLException e)
        {
            FLog.severe("Failed to load player: " + e.getMessage());
        }
    }

    @Override
    public String toString()
    {
        final StringBuilder output = new StringBuilder();

        output.append("Player: ").append(name).append("\n")
                .append("- IPs: ").append(StringUtils.join(ips, ", ")).append("\n")
                .append("- Tag: ").append(FUtil.colorize(tag)).append(ChatColor.GRAY).append("\n")
                .append("- Ride Mode: ").append(rideMode).append("\n")
                .append("- Login Message: ").append(loginMessage);

        return output.toString();
    }

    public PlayerData(Player player)
    {
        this.name = player.getName();
    }

    public List<String> getIps()
    {
        return Collections.unmodifiableList(ips);
    }

    public boolean hasLoginMessage()
    {
        return loginMessage != null && !loginMessage.isEmpty();
    }

    public boolean addIp(String ip)
    {
        return !ips.contains(ip) && ips.add(ip);
    }

    public void removeIp(String ip)
    {
        ips.remove(ip);
    }

    public void clearIps()
    {
        ips.clear();
    }

    public void addIps(List<String> ips)
    {
        ips.addAll(ips);
    }

    public List<String> getNotes()
    {
        return Collections.unmodifiableList(notes);
    }

    public void clearNotes()
    {
        notes.clear();
    }

    public void addNote(String note)
    {
        notes.add(note);
    }

    public boolean removeNote(int id) throws IndexOutOfBoundsException
    {
        try
        {
            notes.remove(id);
        }
        catch (IndexOutOfBoundsException e)
        {
            return false;
        }
        return true;
    }

    public Map<String, Object> toSQLStorable()
    {
        Map<String, Object> map = new HashMap<String, Object>()
        {{
            put("username", name);
            put("ips", FUtil.listToString(ips));
            put("notes", FUtil.listToString(notes));
            put("tag", tag);
            put("ride_mode", rideMode);
            put("login_message", loginMessage);
        }};
        return map;
    }
}
