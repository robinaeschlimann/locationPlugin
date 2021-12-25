package ch.aeschlimannrobin.move.impl.spi;

import ch.aeschlimannrobin.move.spi.ILocationData;
import org.bukkit.World;

public class LocationData implements ILocationData
{
    private String name;
    private String world;
    private double xCoordinate;
    private double yCoordinate;
    private double zCoordinate;
    private float yaw;
    private float pitch;

    public LocationData( String name, String world, double xCoordinate, double yCoordinate, double zCoordinate, float yaw, float pitch )
    {
        this.name = name;
        this.world = world;
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        this.zCoordinate = zCoordinate;
        this.yaw = yaw;
        this.pitch = pitch;
    }

    @Override
    public String getWorld()
    {
        return world;
    }

    @Override
    public String getName()
    {
        return name;
    }

    @Override
    public double getXCoordinate()
    {
        return xCoordinate;
    }

    @Override
    public double getYCoordinate()
    {
        return yCoordinate;
    }

    @Override
    public double getZCoordinate()
    {
        return zCoordinate;
    }

    @Override
    public float getYaw()
    {
        return yaw;
    }

    @Override
    public float getPitch()
    {
        return pitch;
    }
}
