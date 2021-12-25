package ch.aeschlimannrobin.move.impl.api;

import ch.aeschlimannrobin.move.api.ILocation;
import ch.aeschlimannrobin.move.api.ILocationGUIService;
import ch.aeschlimannrobin.move.api.ILocationService;
import com.sun.istack.internal.NotNull;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class LocationGUIService implements ILocationGUIService, Listener
{
    private Inventory inventory;

    @Override
    public void showGUI( Player player )
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

    @NotNull
    @Override
    public List<ItemStack> getItems()
    {
        List<ItemStack> returnValue = new ArrayList<>();
        ILocationService locationService = Bukkit.getServicesManager().getRegistration( ILocationService.class ).getProvider();

        if( locationService != null )
        {
            for( ILocation location : locationService.getLocations() )
            {
                List<String> lore = new ArrayList<>();
                lore.add( "World: " + location.getWorld() );
                lore.add( "x: " + location.getXCoordinate() );
                lore.add( "y: " + location.getYCoordinate() );
                lore.add( "z: " + location.getZCoordinate() );

                ItemStack itemStack = new ItemStack( Material.FILLED_MAP );
                ItemMeta itemMeta = itemStack.getItemMeta();
                itemMeta.setDisplayName( ChatColor.GREEN + location.getName() );
                itemMeta.setLocalizedName( location.getName() );
                itemMeta.setLore( lore );

                itemStack.setItemMeta( itemMeta );

                returnValue.add( itemStack );
            }
        }

        return returnValue;
    }

    @EventHandler
    public void onLocationInventoryClick( InventoryClickEvent event )
    {
        ItemStack currentItem = event.getCurrentItem();

        if( event.getInventory().equals( inventory ) && currentItem != null && currentItem.hasItemMeta() )
        {
            event.setCancelled( true );
            Player player = (Player) event.getWhoClicked();


            Bukkit.getServicesManager().getRegistration( ILocationService.class ).getProvider().teleportPlayer( player, currentItem.getItemMeta().getLocalizedName() );
        }
    }
}
