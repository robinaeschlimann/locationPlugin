package ch.aeschlimannrobin.move.api;

import org.bukkit.entity.Player;

import java.util.List;

public interface ILocationService
{
    ILocation getLocation( String name );

    List<ILocation> getLocations();

    ILocation addLocation( String name, String world, double xCoordinate, double yCoordinate, double zCoordinate, float yaw, float pitch );

    void teleportPlayer( Player player, String location );
}
