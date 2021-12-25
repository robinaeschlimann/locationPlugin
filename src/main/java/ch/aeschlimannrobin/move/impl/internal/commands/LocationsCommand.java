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
        FileConfiguration fileConfiguration = MoveModule.getInstance().getConfig();

        String locationsMessage = ChatColor.BLUE + "";

        boolean isFirst = true;

        if( sender instanceof Player )
        {
            ILocationGUIService locationGUIService = Bukkit.getServicesManager()
                    .getRegistration( ILocationGUIService.class )
                    .getProvider();

            locationGUIService.showGUI( (Player) sender );
        }
        else
        {
            ILocationService locationService = Bukkit.getServicesManager()
                    .getRegistration( ILocationService.class )
                    .getProvider();

            for ( ILocation location : locationService.getLocations() )
            {
                if ( !isFirst )
                {
                    locationsMessage += "\n";
                }

                locationsMessage += "- " + location.getName();

                isFirst = false;
            }

            sender.sendMessage( ChatColor.GREEN + "Folgende Locations wurden bereits gesetzt: \n" + locationsMessage );
        }

        return false;
    }
}
