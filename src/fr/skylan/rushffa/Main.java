package fr.skylan.rushffa;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.WorldCreator;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    public static Main instance;
    public static final String version = "1.0.0";
    public static ConfigManager conf;
    public static Arena arena;
    public static boolean upToDate;

    @Override
    public void onEnable() {
        Bukkit.getConsoleSender().sendMessage(ChatColor.BLUE + "Démarage RushFFA");
        getServer().createWorld(new WorldCreator("RushFFA"));
        instance = this;
        conf = new ConfigManager();
        conf.load(instance);
        this.getCommand("RushFFA").setExecutor(new Commandrushffa());
        RushFFAListener list = new RushFFAListener();
        list.load();
        getServer().getPluginManager().registerEvents(list, this);
        arena = new Arena();
        arena.load();
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Plugin RushFFA lancé avec succés.");
    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage(ChatColor.BLUE + "Fin Plugin RushFFA");
    }
}