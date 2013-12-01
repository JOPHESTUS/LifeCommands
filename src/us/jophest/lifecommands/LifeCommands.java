package us.jophest.lifecommands;

import java.util.List;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;


public class LifeCommands extends JavaPlugin implements Listener {
	Logger log = this.getLogger();
	PluginDescriptionFile pdfFile;
	public static LifeCommands plugin;


	public void onEnable() {
		getServer().getPluginManager().registerEvents(this, this);

		setupConfig();
	}

	public void mbaxterIsTheBest() {

	}
	public void gomeowIsABabe() {

	}

	
	private void setupConfig() {
		// TODO Auto-generated method stub
		getConfig().options().copyDefaults(true);
		saveDefaultConfig();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		// TODO Auto-generated method stub
		

		if (command.getName().equalsIgnoreCase("lifecommands")) {
			 StringBuilder b = new StringBuilder();
             for (int i = 1; i < args.length; i++) {
                 if (i != 1)
                     b.append(" ");
                 b.append(args[i]);
             }
             List<String> cmd = getConfig().getStringList("commands");
		if (args.length < 1) {
			sender.sendMessage(ChatColor.RED + "[LifeCommands]");
			sender.sendMessage(ChatColor.GOLD + "Version: 1.0 ");
			sender.sendMessage(ChatColor.GOLD + "By JOPHESTUS ");
			sender.sendMessage(ChatColor.GOLD + "/lifecommands reload" + ChatColor.GREEN + " to reload config");
			sender.sendMessage(ChatColor.GOLD + "/lifecommands add <command>" + ChatColor.GREEN + " to add new command to config");
			sender.sendMessage(ChatColor.GOLD + "/lifecommands del <command>" + ChatColor.GREEN + " to delete command from config");
			sender.sendMessage(ChatColor.GOLD + "/lifecommands list" + ChatColor.GREEN + " to list all commands");
			sender.sendMessage(ChatColor.GOLD + "/lifecommands reset <player>" + ChatColor.GREEN + " to reset a player's commandlife");
		} else if (args[0].equalsIgnoreCase("add")){
			if (sender.hasPermission("lifecommands.add")){
							cmd.add(b.toString());
			getConfig().set("commands", cmd);
			saveConfig();
			reloadConfig();
			sender.sendMessage(ChatColor.RED + "[LifeCommands] "
					+ ChatColor.GREEN + '"'+b.toString() + '"' + " has been added");
				
				
				} else {
				sender.sendMessage(ChatColor.RED + "[LifeCommands] "
						+ ChatColor.GREEN + "You don't have permission to do that");
			}
			} else if (args[0].equalsIgnoreCase("del")){
				if (sender.hasPermission("lifecommands.del")){
					
			cmd.remove(b.toString());
			getConfig().set("commands", cmd);
			saveConfig();
			reloadConfig();
			sender.sendMessage(ChatColor.RED + "[LifeCommands] "
					+ ChatColor.GREEN + '"' + b.toString() + '"' + " has been removed");
			
					
						
						
					
					}else {
					sender.sendMessage(ChatColor.RED + "[LifeCommands] "
							+ ChatColor.GREEN + "You don't have permission to do that");
				}
		}else if (args[0].equalsIgnoreCase("reload")){
			if (sender.hasPermission("lifecommands.reload")){
			reloadConfig();
			sender.sendMessage(ChatColor.RED + "[LifeCommands] "
					+ ChatColor.GREEN + "Config reloaded");
			}else {
				sender.sendMessage(ChatColor.RED + "[LifeCommands] "
						+ ChatColor.GREEN + "You don't have permission to do that");
			}
		}else if (args[0].equalsIgnoreCase("list")){
			if (sender.hasPermission("lifecommands.list")){
			reloadConfig();
			sender.sendMessage(ChatColor.RED + "++++++++[LifeCommands]++++++++");
			for (String s : cmd) {
				sender.sendMessage(ChatColor.GREEN + s);
			}
			sender.sendMessage(ChatColor.RED + "++++++++[LifeCommands]++++++++");
			}else {
				sender.sendMessage(ChatColor.RED + "[LifeCommands] "
						+ ChatColor.GREEN + "You don't have permission to do that");
			}
		}else if (args[0].equalsIgnoreCase("reset")){
			if(sender.hasPermission("lifecommands.reset")){
				getConfig().set("players." + args[1], null);
				sender.sendMessage(ChatColor.RED + "[LifeCommands] "
						+ ChatColor.GREEN + args[1] + "'s command life has been reset");
				saveConfig();
				reloadConfig();
			}else {
				sender.sendMessage(ChatColor.RED + "[LifeCommands] "
						+ ChatColor.GREEN + "You don't have permission to do that");
			}
		}
		}
				

		return super.onCommand(sender, command, label, args);

	}

    @EventHandler
    public void onCommandPreprocess(PlayerCommandPreprocessEvent event) {
         Player player = event.getPlayer();
           List<String> playercmd = getConfig().getStringList("players." + event.getPlayer().getName());
           reloadConfig(); 
           if(playercmd.contains(event.getMessage())){
        	   
            	event.setCancelled(true);
            	player.sendMessage(ChatColor.RED + "[LifeCommands] "
						+ ChatColor.GREEN + "You've already used this command in this lifetime");
        	   
            }else{
            	reloadConfig();
            	if (!player.hasPermission("lifecommands.exempt")){
            	if (getConfig().getStringList("commands").contains(event.getMessage())){
            		
            	playercmd.add(event.getMessage());
            	getConfig().set("players." + event.getPlayer().getName(), playercmd);
            	saveConfig();
            	reloadConfig();
            	event.setCancelled(false);
            	player.sendMessage(ChatColor.RED + "[LifeCommands] "
						+ ChatColor.GREEN + "For the duration of your current lifetime, access to this command will now be denied");
            
            	} }
            }
        		   
        		   
                         
           }
          
	@EventHandler
	public void onDie(PlayerDeathEvent event){
		
		getConfig().set("players." + event.getEntity().getName(), null);
    	saveConfig();
    	reloadConfig();         
    	  
	}
    }

