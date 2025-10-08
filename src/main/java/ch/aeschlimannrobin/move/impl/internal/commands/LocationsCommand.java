package ch.aeschlimannrobin.move.impl.internal.commands;

import ch.aeschlimannrobin.move.api.ILocation;
import ch.aeschlimannrobin.move.api.ILocationGUIService;
import ch.aeschlimannrobin.move.api.ILocationService;
import ch.aeschlimannrobin.move.impl.MoveModule;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class LocationsCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if( sender instanceof Player player )
        {
            showGui( player );
        }
        else
        {
            showLocationsOnConsole( sender );
        }

        return false;
    }

    private static void showLocationsOnConsole( CommandSender sender )
    {
        ILocationService locationService = Bukkit.getServicesManager()
                .getRegistration( ILocationService.class )
                .getProvider();

        StringBuilder locationsMessage = new StringBuilder( ChatColor.BLUE + "" );
        boolean isFirst = true;
        for ( ILocation location : locationService.getLocations() )
        {
            if ( !isFirst )
            {
                locationsMessage.append( "\n" );
            }

            locationsMessage.append( "- " );
            locationsMessage.append( location.getName() );

            isFirst = false;
        }

        sender.sendMessage( ChatColor.GREEN + "Folgende Locations wurden bereits gesetzt: \n" + locationsMessage.toString() );
    }

    private static void showGui( Player sender )
    {
        ILocationGUIService locationGUIService = Bukkit.getServicesManager()
                .getRegistration( ILocationGUIService.class )
                .getProvider();

        locationGUIService.showGUI( sender );
    }
}
