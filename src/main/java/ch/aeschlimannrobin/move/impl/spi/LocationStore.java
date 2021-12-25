package ch.aeschlimannrobin.move.impl.spi;

import ch.aeschlimannrobin.move.impl.MoveModule;
import ch.aeschlimannrobin.move.spi.ILocationData;
import ch.aeschlimannrobin.move.spi.ILocationStore;
import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;

public class LocationStore implements ILocationStore
{
    private FileConfiguration fileConfiguration;

    public LocationStore()
    {
        this.fileConfiguration = MoveModule.getInstance().getConfig();
    }

    @Nullable
    @Override
    public ILocationData getLocation( String name )
    {
        ILocationData returnValue = null;

        if( fileConfiguration.contains( "command.locations." + name ) )
        {
            String worldName = fileConfiguration.getString( "command.locations." + name + ".world" );
            double xCoordinate = fileConfiguration.getDouble( "command.locations." + name + ".x" );
            double yCoordinate = fileConfiguration.getDouble( "command.locations." + name + ".y" );
            double zCoordinate = fileConfiguration.getDouble( "command.locations." + name + ".z" );
            float yaw = (float) fileConfiguration.getDouble( "command.locations." + name + ".yaw" );
            float pitch = (float) fileConfiguration.getDouble( "command.locations." + name + ".pitch" );

            returnValue = new LocationData( name, worldName, xCoordinate, yCoordinate, zCoordinate, yaw, pitch );
        }

        return returnValue;
    }

    @NotNull
    @Override
    public List<ILocationData> getLocations()
    {
        List<ILocationData> returnValue = new ArrayList<>();

        for( String name :  fileConfiguration.getConfigurationSection( "command.locations" ).getKeys( false ) )
        {
            ILocationData locationData = getLocation( name );

            if( locationData != null )
            {
                returnValue.add( locationData );
            }
        }

        return returnValue;
    }

    @Override
    public ILocationData addLocation( String name, String world, double xCoordinate, double yCoordinate, double zCoordinate, float yaw, float pitch )
    {
        ILocationData returnValue = null;

        FileConfiguration fileConfiguration = MoveModule.getInstance().getConfig();

        if( !fileConfiguration.contains( "command.locations." + name ) )
        {
            fileConfiguration.set("command.locations." + name + ".world", world);
            fileConfiguration.set("command.locations." + name + ".x", xCoordinate);
            fileConfiguration.set("command.locations." + name + ".y", yCoordinate);
            fileConfiguration.set("command.locations." + name + ".z", zCoordinate);
            fileConfiguration.set("command.locations." + name + ".yaw", yaw);
            fileConfiguration.set("command.locations." + name + ".pitch", pitch);

            MoveModule.getInstance().saveConfig();

            returnValue = getLocation( name );
        }

        return returnValue;
    }
}
