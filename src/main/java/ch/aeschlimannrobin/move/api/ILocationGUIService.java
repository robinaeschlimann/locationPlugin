package ch.aeschlimannrobin.move.api;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public interface ILocationGUIService
{
    void showGUI( Player player );

    List<ItemStack> getItems();
}
