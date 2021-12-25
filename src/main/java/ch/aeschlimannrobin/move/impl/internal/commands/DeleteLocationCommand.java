package ch.aeschlimannrobin.move.impl.internal.commands;

import ch.aeschlimannrobin.move.impl.MoveModule;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

public class DeleteLocationCommand implements CommandExecutor
{
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if( args.length == 1 )
        {
            String locationToDelete = args[0];

            FileConfiguration fileConfiguration = MoveModule.getInstance().getConfig();

            fileConfiguration.set( "command.locations." + locationToDelete, null );

            MoveModule.getInstance().saveConfig();
        }
        else
        {
            sender.sendMessage(ChatColor.RED + "Bitte gib die Location an, welche gelöscht werden muss" );
        }

        return false;
    }
}
