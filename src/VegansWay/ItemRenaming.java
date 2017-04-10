/*
 * Copyright (C) 2017 Pronink
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package VegansWay;

import org.bukkit.ChatColor;
import org.bukkit.entity.Item;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 *
 * @author Pronink
 */
public class ItemRenaming
{

    public void modifyItemGround(ItemSpawnEvent event)
    {
	Item item = event.getEntity();
	String fullName = item.getItemStack().getData().toString();
	//Bukkit.broadcastMessage("ItemStack data: "+fullName+"    ItemStack material+data: "+item.getItemStack().getType());
	if (fullName.equals("RED_ROSE(7)")) // PINK TULIP
	{
	    item.setItemStack(changeName(item.getItemStack(), Config.CONFIG_CATNIP_NAME));
	}
	else if (fullName.equals("RED_ROSE(3)")) // AZURE BLUET
	{
	    item.setItemStack(changeName(item.getItemStack(), Config.CONFIG_WOOLPLANT_NAME)); // COTTON
	}
    }

    //ItemStack newItem = new ItemStack(Material.RED_ROSE, 1, (short) 7);


    public static ItemStack changeName(ItemStack itemStack, String name)
    {
	ItemMeta meta = itemStack.getItemMeta();
	meta.setDisplayName(ChatColor.RESET + name);
	itemStack.setItemMeta(meta);
	return itemStack;
    }
}
