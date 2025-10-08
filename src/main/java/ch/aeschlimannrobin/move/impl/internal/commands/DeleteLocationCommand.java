package ch.aeschlimannrobin.move.impl.internal.commands;

import ch.aeschlimannrobin.move.api.ILocationService;
import ch.aeschlimannrobin.move.impl.MoveModule;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class DeleteLocationCommand implements CommandExecutor
{
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if( sender instanceof Player player && !Bukkit.getServicesManager().getRegistration( ILocationService.class ).getProvider().isPlayerAllowedToSetLocations( player ) )
        {
            sender.sendMessage(ChatColor.RED + "Du bist nicht berechtigt eine Location zu löschen" );
        }

        if( args.length != 1 )
        {
            sender.sendMessage(ChatColor.RED + "Bitte gib die Location an, welche gelöscht werden muss" );
        }

        String locationToDelete = args[0];

        FileConfiguration fileConfiguration = MoveModule.getInstance().getConfig();

        fileConfiguration.set( "command.locations." + locationToDelete, null );

        MoveModule.getInstance().saveConfig();

        return false;
    }
}
