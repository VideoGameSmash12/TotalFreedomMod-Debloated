package me.totalfreedom.totalfreedommod.config;

import java.util.List;
import me.totalfreedom.totalfreedommod.TotalFreedomMod;


public enum ConfigEntry
{
    FORCE_IP_ENABLED(Boolean.class, "forceip.enabled"),
    FORCE_IP_PORT(Integer.class, "forceip.port"),
    FORCE_IP_KICKMSG(String.class, "forceip.kickmsg"),
    //
    ALLOW_EXPLOSIONS(Boolean.class, "allow.explosions"),
    ALLOW_FIRE_PLACE(Boolean.class, "allow.fire_place"),
    ALLOW_FIRE_SPREAD(Boolean.class, "allow.fire_spread"),
    ALLOW_FLUID_SPREAD(Boolean.class, "allow.fluid_spread"),
    ALLOW_LAVA_DAMAGE(Boolean.class, "allow.lava_damage"),
    ALLOW_LAVA_PLACE(Boolean.class, "allow.lava_place"),
    ALLOW_TNT_MINECARTS(Boolean.class, "allow.tnt_minecarts"),
    ALLOW_WATER_PLACE(Boolean.class, "allow.water_place"),
    ALLOW_REDSTONE(Boolean.class, "allow.redstone"),
    ALLOW_FIREWORK_EXPLOSION(Boolean.class, "allow.fireworks"),
    ALLOW_FROSTWALKER(Boolean.class, "allow.frostwalker"),
    ALLOW_UNSAFE_ENCHANTMENTS(Boolean.class, "allow.unsafe_enchantments"),
    ALLOW_BELLS(Boolean.class, "allow.bells"),
    ALLOW_ARMOR_STANDS(Boolean.class, "allow.armorstands"),
    ALLOW_MINECARTS(Boolean.class, "allow.minecarts"),
    ALLOW_STRUCTURE_BLOCKS(Boolean.class, "allow.structureblocks"),
    ALLOW_JIGSAWS(Boolean.class, "allow.jigsaws"),
    ALLOW_GRINDSTONES(Boolean.class, "allow.grindstones"),
    ALLOW_JUKEBOXES(Boolean.class, "allow.jukeboxes"),
    ALLOW_SPAWNERS(Boolean.class, "allow.spawners"),
    ALLOW_BEEHIVES(Boolean.class, "allow.beehives"),
    ALLOW_RESPAWN_ANCHORS(Boolean.class, "allow.respawnanchors"),
    AUTO_TP(Boolean.class, "allow.auto_tp"),
    AUTO_CLEAR(Boolean.class, "allow.auto_clear"),
    //
    BLOCKED_CHATCODES(String.class, "blocked_chatcodes"),
    //
    MOB_LIMITER_ENABLED(Boolean.class, "moblimiter.enabled"),
    MOB_LIMITER_MAX(Integer.class, "moblimiter.max"),
    MOB_LIMITER_DISABLE_DRAGON(Boolean.class, "moblimiter.disable.dragon"),
    MOB_LIMITER_DISABLE_GHAST(Boolean.class, "moblimiter.disable.ghast"),
    MOB_LIMITER_DISABLE_GIANT(Boolean.class, "moblimiter.disable.giant"),
    MOB_LIMITER_DISABLE_SLIME(Boolean.class, "moblimiter.disable.slime"),
    //
    HTTPD_ENABLED(Boolean.class, "httpd.enabled"),
    HTTPD_HOST(String.class, "httpd.host"),
    HTTPD_PORT(Integer.class, "httpd.port"),
    HTTPD_PUBLIC_FOLDER(String.class, "httpd.public_folder"),
    //
    SERVER_COLORFUL_MOTD(Boolean.class, "server.colorful_motd"),
    SERVER_NAME(String.class, "server.name"),
    SERVER_ADDRESS(String.class, "server.address"),
    SERVER_MOTD(String.class, "server.motd"),
    SERVER_LOGIN_TITLE(String.class, "server.login_title.title"),
    SERVER_LOGIN_SUBTITLE(String.class, "server.login_title.subtitle"),
    SERVER_OWNERS(List.class, "server.owners"),
    SERVER_EXECUTIVES(List.class, "server.executives"),
    SERVER_BAN_URL(String.class, "server.ban_url"),
    SERVER_INDEFBAN_URL(String.class, "server.indefban_url"),
    SERVER_TABLIST_HEADER(String.class, "server.tablist_header"),
    SERVER_TABLIST_FOOTER(String.class, "server.tablist_footer"),
    //
    SERVER_BAN_MOTD(String.class, "server.motds.ban"),
    SERVER_STAFFMODE_MOTD(String.class, "server.motds.staffmode"),
    SERVER_LOCKDOWN_MOTD(String.class, "server.motds.lockdown"),
    SERVER_WHITELIST_MOTD(String.class, "server.motds.whitelist"),
    SERVER_FULL_MOTD(String.class, "server.motds.full"),
    //
    STAFFLIST_CLEAN_THESHOLD_HOURS(Integer.class, "stafflist.clean_threshold_hours"),
    STAFFLIST_CONSOLE_IS_ADMIN(Boolean.class, "stafflist.console_is_admin"),
    //
    COREPROTECT_MYSQL_ENABLED(Boolean.class, "coreprotect.enabled"),
    COREPROTECT_MYSQL_HOST(String.class, "coreprotect.host"),
    COREPROTECT_MYSQL_PORT(String.class, "coreprotect.port"),
    COREPROTECT_MYSQL_USERNAME(String.class, "coreprotect.username"),
    COREPROTECT_MYSQL_PASSWORD(String.class, "coreprotect.password"),
    COREPROTECT_MYSQL_DATABASE(String.class, "coreprotect.database"),
    //
    DISABLE_NIGHT(Boolean.class, "disable.night"),
    DISABLE_WEATHER(Boolean.class, "disable.weather"),
    //
    ENABLE_PREPROCESS_LOG(Boolean.class, "preprocess_log"),
    ENABLE_PET_PROTECT(Boolean.class, "petprotect.enabled"),
    //
    AUTOKICK_ENABLED(Boolean.class, "autokick.enabled"),
    //
    NUKE_MONITOR_ENABLED(Boolean.class, "nukemonitor.enabled"),
    NUKE_MONITOR_COUNT_BREAK(Integer.class, "nukemonitor.count_break"),
    NUKE_MONITOR_COUNT_PLACE(Integer.class, "nukemonitor.count_place"),
    NUKE_MONITOR_RANGE(Double.class, "nukemonitor.range"),
    //
    AUTOKICK_THRESHOLD(Double.class, "autokick.threshold"),
    AUTOKICK_TIME(Integer.class, "autokick.time"),
    //
    LOGS_SECRET(String.class, "logs.secret"),
    LOGS_URL(String.class, "logs.url"),
    //
    FLATLANDS_GENERATE(Boolean.class, "flatlands.generate"),
    FLATLANDS_GENERATE_PARAMS(String.class, "flatlands.generate_params"),
    //
    ANNOUNCER_ENABLED(Boolean.class, "announcer.enabled"),
    ANNOUNCER_INTERVAL(Integer.class, "announcer.interval"),
    ANNOUNCER_PREFIX(String.class, "announcer.prefix"),
    ANNOUNCER_ANNOUNCEMENTS(List.class, "announcer.announcements"),
    //
    EXPLOSIVE_RADIUS(Double.class, "explosive_radius"),
    FREECAM_TRIGGER_COUNT(Integer.class, "freecam_trigger_count"),
    SERVICE_CHECKER_URL(String.class, "service_checker_url"),
    BLOCKED_COMMANDS(List.class, "blocked_commands"),
    HOST_SENDER_NAMES(List.class, "host_sender_names"),
    FAMOUS_PLAYERS(List.class, "famous_players"),
    STAFF_ONLY_MODE(Boolean.class, "staff_only_mode"),
    STAFF_INFO(List.class, "staffinfo"),
    AUTO_ENTITY_WIPE(Boolean.class, "auto_wipe"),
    TOGGLE_CHAT(Boolean.class, "toggle_chat"),
    DEVELOPER_MODE(Boolean.class, "developer_mode");
    //
    private final Class<?> type;
    private final String configName;

    ConfigEntry(Class<?> type, String configName)
    {
        this.type = type;
        this.configName = configName;
    }

    public Class<?> getType()
    {
        return type;
    }

    public String getConfigName()
    {
        return configName;
    }

    public String getString()
    {
        return getConfig().getString(this);
    }

    public String setString(String value)
    {
        getConfig().setString(this, value);
        return value;
    }

    public Double getDouble()
    {
        return getConfig().getDouble(this);
    }

    public Double setDouble(Double value)
    {
        getConfig().setDouble(this, value);
        return value;
    }

    public Boolean getBoolean()
    {
        return getConfig().getBoolean(this);
    }

    public Boolean setBoolean(Boolean value)
    {
        getConfig().setBoolean(this, value);
        return value;
    }

    public Integer getInteger()
    {
        return getConfig().getInteger(this);
    }

    public Integer setInteger(Integer value)
    {
        getConfig().setInteger(this, value);
        return value;
    }

    public List<?> getList()
    {
        return getConfig().getList(this);
    }

    @SuppressWarnings("unchecked")
    public List<String> getStringList()
    {
        return (List<String>)getList();
    }

    private MainConfig getConfig()
    {
        return TotalFreedomMod.plugin().config;
    }

    public static ConfigEntry findConfigEntry(String name)
    {
        name = name.toLowerCase().replace("_", "");
        for (ConfigEntry entry : values())
        {
            if (entry.toString().toLowerCase().replace("_", "").equals(name))
            {
                return entry;
            }
        }
        return null;
    }
}
