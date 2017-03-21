/*
 * The MIT License
 *
 * Copyright 2017 Pronink.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
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

    private final String NEPETA = ChatColor.RESET + Config.CONFIG_CATNIP_NAME;
    private final String COTTON = ChatColor.RESET + Config.CONFIG_COTTONPLANT_NAME;

    public void modifyItemGround(ItemSpawnEvent event)
    {
	Item item = event.getEntity();
	String fullName = item.getItemStack().getData().toString();
	//Bukkit.broadcastMessage("ItemStack data: "+fullName+"    ItemStack material+data: "+item.getItemStack().getType());
	if (fullName.equals("RED_ROSE(7)")) // PINK TULIP
	{
	    item.setItemStack(changeName(item.getItemStack(), NEPETA));
	}
	else if (fullName.equals("RED_ROSE(3)")) // AZURE BLUET
	{
	    item.setItemStack(changeName(item.getItemStack(), COTTON)); // COTTON
	}
    }

    //ItemStack newItem = new ItemStack(Material.RED_ROSE, 1, (short) 7);


    private ItemStack changeName(ItemStack itemStack, String name)
    {
	ItemMeta meta = itemStack.getItemMeta();
	meta.setDisplayName(ChatColor.RESET + name);
	itemStack.setItemMeta(meta);
	return itemStack;
    }
}
