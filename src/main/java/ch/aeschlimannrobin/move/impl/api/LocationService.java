package ch.aeschlimannrobin.move.impl.api;

import ch.aeschlimannrobin.move.api.ILocation;
import ch.aeschlimannrobin.move.api.ILocationService;
import ch.aeschlimannrobin.move.spi.ILocationData;
import ch.aeschlimannrobin.move.spi.ILocationStore;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.stream.Collectors;

public class LocationService implements ILocationService
{
    @Override
    public ILocation getLocation( String name )
    {
        ILocationData locationData = Bukkit.getServicesManager()
                .getRegistration( ILocationStore.class )
                .getProvider()
                .getLocation( name );

        return new Location( locationData );
    }

    @Override
    public List<ILocation> getLocations()
    {
        List<ILocationData> locationDataList = Bukkit.getServicesManager()
                .getRegistration( ILocationStore.class )
                .getProvider()
                .getLocations();

        return locationDataList.stream()
                .map( Location::new )
                .collect( Collectors.toList() );
    }

    @Override
    public ILocation addLocation( String name, String world, double xCoordinate, double yCoordinate, double zCoordinate, float yaw, float pitch )
    {
        return new Location( Bukkit.getServicesManager()
                .getRegistration( ILocationStore.class )
                .getProvider()
                .addLocation( name, world, xCoordinate, yCoordinate, zCoordinate, yaw, pitch ) );
    }

    @Override
    public void teleportPlayer( Player player, String locationName )
    {
        ILocation location = getLocation( locationName );

        if( location != null )
        {
            World world = Bukkit.getWorld( location.getWorld() );
            player.teleport( new org.bukkit.Location( world, location.getXCoordinate(), location.getYCoordinate(), location.getZCoordinate(),
                    location.getYaw(), location.getPitch() ) );

            player.sendMessage( ChatColor.DARK_GREEN + "Du wurdest erfolgreich zur Location " + ChatColor.RED + locationName + ChatColor.DARK_GREEN + " teleportiert" );
        }
        else
        {
            player.sendMessage( ChatColor.RED + "Für diesen Namen gibt es keine Location. Benutzer /locations um dir alle Möglichkeiten anzuzeigen" );
        }
    }
}
