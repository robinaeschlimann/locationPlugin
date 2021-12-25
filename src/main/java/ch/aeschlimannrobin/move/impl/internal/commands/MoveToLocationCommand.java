package ch.aeschlimannrobin.move.impl.internal.commands;

import ch.aeschlimannrobin.move.api.ILocation;
import ch.aeschlimannrobin.move.api.ILocationGUIService;
import ch.aeschlimannrobin.move.api.ILocationService;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MoveToLocationCommand implements CommandExecutor
{
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if( sender instanceof Player)
        {
            Player player = (Player) sender;

            if( args.length == 1 )
            {
                String locationName = args[0];

                ILocationService locationService = Bukkit.getServicesManager().getRegistration( ILocationService.class ).getProvider();

                locationService.teleportPlayer( player, locationName );
            }
            else if ( args.length == 0 )
            {
                Bukkit.getServicesManager().getRegistration( ILocationGUIService.class ).getProvider().showGUI( player );
            }
            else
            {
                player.sendMessage( ChatColor.RED + "too many arguments" );
            }
        }

        return false;
    }
}
