package ch.aeschlimannrobin.move.spi;

import java.util.List;

public interface ILocationStore
{
    ILocationData getLocation( String name );

    List<ILocationData> getLocations();

    ILocationData addLocation( String name, String world, double xCoordinate, double yCoordinate, double zCoordinate, float yaw, float pitch );
}
