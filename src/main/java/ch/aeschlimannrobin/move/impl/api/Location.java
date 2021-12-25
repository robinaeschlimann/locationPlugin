package ch.aeschlimannrobin.move.impl.api;

import ch.aeschlimannrobin.move.api.ILocation;
import ch.aeschlimannrobin.move.spi.ILocationData;

public class Location implements ILocation
{
    private ILocationData locationData;

    public Location( ILocationData locationData )
    {
        this.locationData = locationData;
    }

    @Override
    public String getName()
    {
        return locationData.getName();
    }

    @Override
    public String getWorld()
    {
        return locationData.getWorld();
    }

    @Override
    public double getXCoordinate()
    {
        return locationData.getXCoordinate();
    }

    @Override
    public double getYCoordinate()
    {
        return locationData.getYCoordinate();
    }

    @Override
    public double getZCoordinate()
    {
        return locationData.getZCoordinate();
    }

    @Override
    public float getYaw()
    {
        return locationData.getYaw();
    }

    @Override
    public float getPitch()
    {
        return locationData.getPitch();
    }
}
