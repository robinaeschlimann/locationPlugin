package ch.aeschlimannrobin.move.spi;

import org.bukkit.World;

public interface ILocationData
{
    String getName();

    String getWorld();

    double getXCoordinate();

    double getYCoordinate();

    double getZCoordinate();

    float getYaw();

    float getPitch();
}
