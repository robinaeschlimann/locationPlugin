package ch.aeschlimannrobin.move.impl;

import ch.aeschlimannrobin.move.api.ILocationGUIService;
import ch.aeschlimannrobin.move.api.ILocationService;
import ch.aeschlimannrobin.move.impl.api.LocationGUIService;
import ch.aeschlimannrobin.move.impl.internal.commands.DeleteLocationCommand;
import ch.aeschlimannrobin.move.impl.internal.commands.SetLocationCommand;
import ch.aeschlimannrobin.move.impl.internal.commands.LocationsCommand;
import ch.aeschlimannrobin.move.impl.internal.commands.MoveToLocationCommand;
import ch.aeschlimannrobin.move.impl.api.LocationService;
import ch.aeschlimannrobin.move.impl.spi.LocationStore;
import ch.aeschlimannrobin.move.spi.ILocationStore;
import org.bukkit.Bukkit;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

public final class MoveModule extends JavaPlugin {

    private static MoveModule instance;

    public static MoveModule getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;

        LocationGUIService locationGUIService = new LocationGUIService();

        Bukkit.getServicesManager().register( ILocationStore.class, new LocationStore(), this, ServicePriority.High );
        Bukkit.getServicesManager().register( ILocationService.class, new LocationService(), this, ServicePriority.High );
        Bukkit.getServicesManager().register( ILocationGUIService.class, locationGUIService, this, ServicePriority.High );

        Bukkit.getPluginManager().registerEvents( locationGUIService, this );

        getCommand( "setlocation" ).setExecutor( new SetLocationCommand() );
        getCommand( "movetolocation" ).setExecutor( new MoveToLocationCommand() );
        getCommand( "locations" ).setExecutor( new LocationsCommand() );
        getCommand( "deletelocation" ).setExecutor( new DeleteLocationCommand() );
    }

    @Override
    public void onDisable() {
    }
}
