package fi.gosu.mc.plugins.SleepDemocracy;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by Aikain on 7.5.2016.
 */
public class SleepDemocracy extends JavaPlugin implements Listener {
    private boolean SDEnable;
    private int SDPercent;

    @Override
    public void onEnable() {
        this.loadConfig();
        this.getServer().getPluginManager().registerEvents(this, this);
        this.getCommand("sdtoggle").setExecutor(this);
        this.getCommand("sdset").setExecutor(this);
        super.onEnable();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    public void loadConfig() {
        this.getConfig().options().copyDefaults(true);
        this.saveConfig();
        this.SDEnable = this.getConfig().getBoolean("SDEnable");
        this.SDPercent = this.getConfig().getInt("SDPercent");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        switch (cmd.getName()) {
            case "sdtoggle":
                if (sender.hasPermission("sleepdemocracy.sdtoggle")) {
                    if (args.length == 0) {
                        this.SDEnable = !this.SDEnable;
                    } else if (args.length == 1) {
                        if (args[0].equals("ON")) {
                            this.SDEnable = true;
                        } else if (args[1].equals("OFF")) {
                            this.SDEnable = false;
                        } else {
                            sender.sendMessage(cmd.getUsage());
                            return false;
                        }
                    } else {
                        sender.sendMessage(cmd.getUsage());
                        return false;
                    }
                    this.getConfig().set("SDEnable", Boolean.valueOf(this.SDEnable));
                    this.saveConfig();
                    sender.sendMessage((this.SDEnable ? "Enabled" : "Disabled") + " SleepDemocracy");
                    return true;
                }
                sender.sendMessage(cmd.getPermissionMessage());
                break;
            case "sdset":
                if (sender.hasPermission("sleepdemocracy.sdset")) {
                    if (args.length == 1) {
                        try {
                            int a = Integer.parseInt(args[0]);
                            if (a < 0 || a > 100) {
                                throw new Exception();
                            }
                            this.SDPercent = a;
                            this.getConfig().set("SDPercent", Integer.valueOf(a));
                            this.saveConfig();
                            sender.sendMessage("Set percent to " + args[0]);
                            return true;
                        } catch (Exception e) {
                            sender.sendMessage("Number invalid. Please enter a number 0 - 100.");
                        }
                    } else {
                        sender.sendMessage(cmd.getUsage());
                    }
                } else {
                    sender.sendMessage(cmd.getPermissionMessage());
                }
                break;
        }
        return false;
    }
}