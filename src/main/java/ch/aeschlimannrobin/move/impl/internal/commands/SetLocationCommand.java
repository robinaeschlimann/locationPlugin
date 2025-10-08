package ch.aeschlimannrobin.move.impl.internal.commands;

import ch.aeschlimannrobin.move.api.ILocation;
import ch.aeschlimannrobin.move.api.ILocationService;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetLocationCommand implements CommandExecutor
{
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if( !(sender instanceof Player player) )
        {
            return false;
        }

        ILocationService locationService = Bukkit.getServicesManager().getRegistration( ILocationService.class ).getProvider();

        if( !locationService.isPlayerAllowedToSetLocations( player ) )
        {
            player.sendMessage(ChatColor.RED + "Du bist nicht bereichtigt neue Locations zu setzen" );
            return false;
        }

        if( args.length != 1 )
        {
            player.sendMessage(ChatColor.RED + "Bitte gib dem gesetzten Punkt noch einen Titel" );
            return false;
        }

        String locationName = args[0];
        String worldName = player.getWorld().getName();
        double xCoordinate = player.getLocation().getX();
        double yCoordinate = player.getLocation().getY();
        double zCoordinate = player.getLocation().getZ();
        float yaw = player.getLocation().getYaw();
        float pitch = player.getLocation().getPitch();

        ILocation location = locationService.addLocation( locationName, worldName, xCoordinate, yCoordinate, zCoordinate, yaw, pitch );

        if( location != null )
        {
            player.sendMessage(ChatColor.DARK_GREEN + "Diese Location wurde erfolgreich gespeichert!");
        }
        else
        {
            player.sendMessage( ChatColor.RED + "Dieser Name ist bereits vergeben. Bitte wähle einen anderen!" );
        }

        return false;
    }
}
