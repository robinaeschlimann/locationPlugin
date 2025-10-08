package ch.aeschlimannrobin.move.impl.api;

import ch.aeschlimannrobin.move.api.ILocation;
import ch.aeschlimannrobin.move.api.ILocationGUIService;
import ch.aeschlimannrobin.move.api.ILocationService;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.RegisteredServiceProvider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class LocationGUIService implements ILocationGUIService, Listener
{
    private Inventory inventory;

    @Override
    public void
    showGUI( Player player )
    {
        if( inventory == null )
        {
            inventory = Bukkit.createInventory( null, 6*9, "Location auswählen" );
        }

        List<ItemStack> itemStacks = getItems();

        for( int i = 0; i < itemStacks.size(); i++ )
        {
            inventory.setItem( i, itemStacks.get( i ) );
        }

        player.openInventory( inventory );
    }

    @Override
    public List<ItemStack> getItems()
    {
       return getLocationService()
                .map( this::getLocations )
                .orElse( Collections.emptyList() );
    }

    private List<ItemStack> getLocations( ILocationService locationService )
    {
        return locationService.getLocations().stream()
                .map( this::mapToItemStack )
                .toList();
    }

    private ItemStack mapToItemStack( ILocation location )
    {
        List<String> lore = new ArrayList<>();
        lore.add( "World: " + location.getWorld() );
        lore.add( "x: " + location.getXCoordinate() );
        lore.add( "y: " + location.getYCoordinate() );
        lore.add( "z: " + location.getZCoordinate() );

        ItemStack itemStack = new ItemStack( Material.FILLED_MAP );
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName( location.getName() );
        itemMeta.setLocalizedName( location.getName() );
        itemMeta.setLore( lore );

        itemStack.setItemMeta( itemMeta );

        return itemStack;
    }

    @EventHandler
    public void onLocationInventoryClick( InventoryClickEvent event )
    {
        ItemStack currentItem = event.getCurrentItem();

        if( event.getInventory().equals( inventory ) && currentItem != null && currentItem.hasItemMeta() )
        {
            event.setCancelled( true );
            Player player = (Player) event.getWhoClicked();

            getLocationService().ifPresent( service -> service.teleportPlayer( player, Optional.ofNullable( currentItem.getItemMeta() ).map( ItemMeta::getLocalizedName ).orElse( "" ) ) );
        }
    }

    private static Optional<ILocationService> getLocationService()
    {
        return Optional.ofNullable( Bukkit.getServicesManager()
                        .getRegistration( ILocationService.class ) )
                .map( RegisteredServiceProvider::getProvider );
    }
}
