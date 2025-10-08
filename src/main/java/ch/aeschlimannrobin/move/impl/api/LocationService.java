package ch.aeschlimannrobin.move.impl.api;

import ch.aeschlimannrobin.move.api.ILocation;
import ch.aeschlimannrobin.move.api.ILocationService;
import ch.aeschlimannrobin.move.spi.ILocationData;
import ch.aeschlimannrobin.move.spi.ILocationStore;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class LocationService implements ILocationService
{
    @Override
    public boolean isPlayerAllowedToSetLocations( Player player )
    {
        return getLocationStore()
                .map( ILocationStore::getLocationAdmins )
                .map( admins -> admins.isEmpty() || admins.contains( player.getUniqueId().toString() ) )
                .orElse( false );
    }

    @Override
    public ILocation getLocation( String name )
    {
        return getLocationStore()
                .map( store -> store.getLocation( name ) )
                .map( Location::new )
                .orElse( null );
    }

    @Override
    public List<ILocation> getLocations()
    {
        List<ILocationData> locationDataList = getLocationStore()
                .map( ILocationStore::getLocations )
                .orElse( Collections.emptyList() );

        return locationDataList.stream()
                .map( Location::new )
                .collect( Collectors.toList() );
    }

    @Override
    public ILocation addLocation( String name, String world, double xCoordinate, double yCoordinate, double zCoordinate, float yaw, float pitch )
    {
        return new Location( getLocationStore()
                .map( store -> store.addLocation( name, world, xCoordinate, yCoordinate, zCoordinate, yaw, pitch ) )
                .orElse( null ) );
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

    private static Optional<ILocationStore> getLocationStore()
    {
        return Optional.ofNullable( Bukkit.getServicesManager()
                        .getRegistration( ILocationStore.class ) )
                .map( RegisteredServiceProvider::getProvider );
    }
}
