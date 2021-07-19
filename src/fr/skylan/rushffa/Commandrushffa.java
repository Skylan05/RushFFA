package fr.skylan.rushffa;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import net.md_5.bungee.api.ChatColor;

public class Commandrushffa implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player && args.length == 1)
            if (args[0].equals("join")) {
                Main.arena.addPlayer((Player) sender);
            }
        if (args.length == 0) {
            sender.sendMessage("RushFFA v" + Main.version + " Creer par Skylan");
        }
        if (sender.isOp()) {
            if (sender instanceof Player) {
                Player p = (Player) sender;
                if (args.length == 1) {
                    if (args[0].equals("help")) {
                        sender.sendMessage("RushFFA help page :");
                    }
                    if (args[0].equals("reload")) {
                        sender.sendMessage("Reload de la config");
                        Main.conf.load(Main.instance);
                    }
                    if (args[0].equals("setlobby")) {
                        if (p.getLocation().getWorld().equals(Bukkit.getWorld("RushFFA"))) {
                            sender.sendMessage("Emplacement du lobby");
                            Main.instance.getConfig().set("lobbyloc", p.getLocation());
                            Main.instance.saveConfig();
                            ConfigManager.conf.set("lobbyloc", p.getLocation());
                        } else
                            p.sendMessage("Impossibe d'utiliser cette command dans ce monde faites /rushffa tp");
                    }
                    if (args[0].equals("lobby")) {
                        if (!ConfigManager.conf.get("lobbyloc").equals("null")) {
                            sender.sendMessage("teleportation au lobby");
                            p.teleport((Location) ConfigManager.conf.get("lobbyloc"));
                        } else
                            p.sendMessage("Le lobby na pas étais set !");
                    }
                    if (args[0].equals("tp")) {
                        Location loc = p.getLocation();
                        loc.setWorld(Bukkit.getWorld("RushFFA"));
                        p.teleport(loc);
                    }
                    if (args[0].equals("setinv")) {
                        YamlConfiguration inv = new YamlConfiguration();
                        File file = new File(Main.instance.getDataFolder(), "inventory.yml");
                        try {
                            inv.load(file);
                        } catch (IOException | InvalidConfigurationException e) {
                            e.printStackTrace();
                        }
                        PlayerInventory i = p.getInventory();
                        inv.set("inv", Utils.toBase64(p.getInventory()));
                        inv.set("armor", (p.getInventory().getArmorContents()));
                        Map<String, ItemStack> armor = new HashMap<String, ItemStack>();
                        armor.put("h", i.getHelmet());
                        armor.put("c", i.getChestplate());
                        armor.put("l", i.getLeggings());
                        armor.put("b", i.getBoots());
                        inv.set("armor", armor);
                        try {
                            inv.save(file);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Main.conf.load(Main.instance);
                        p.sendMessage(ChatColor.GREEN + "Inventaire sauvegarder.");
                    }
                } else if (args.length == 2) {
                    if (args[0].equals("addloc")) {
                        if (p.getLocation().getWorld().equals(Bukkit.getWorld("RushFFA"))) {
                            Main.arena.addLocation(p.getLocation(), args[1]);
                            p.sendMessage(ChatColor.GREEN + "Point de Spawn ajouter.");
                        } else
                            p.sendMessage("Impossibe d'utiliser cette command dans ce monde faites /rushffa tp");
                    }
                }
            } else
                sender.sendMessage(ChatColor.RED + "Cet command et utilisable que pas un joueur");

        }
        return true;
    }
}