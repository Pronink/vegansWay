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
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 *
 * @author Pronink
 */
public class ItemModify
{
    public void modifyDrop(BlockBreakEvent event)
    {
	Block block = event.getBlock();
	ItemStack newItem = testItem(block.getDrops().toArray());
	if (newItem != null)
	{
	    event.setCancelled(true);
	    Location centerLocation = block.getLocation();
	    centerLocation.setX(centerLocation.getX() + 0.5D); // Para centrar de donde aparece el nuevo item
	    centerLocation.setZ(centerLocation.getZ() + 0.5D);
	    block.getWorld().dropItemNaturally(centerLocation, newItem);
	    event.getBlock().setType(Material.AIR);
	}
    }
    
    private ItemStack testItem(Object[] dropList)
    {
	for (Object obItemStack : dropList)
	{
	    ItemStack itemStack = (ItemStack) obItemStack;
	    if (itemStack.getData().toString().equals("RED_ROSE(7)"))
	    {
		ItemStack newItem = new ItemStack(Material.RED_ROSE, 1, (short)7);
		ItemMeta meta = newItem.getItemMeta();
		meta.setDisplayName(ChatColor.RESET+"Nepeta Cataria");
		newItem.setItemMeta(meta);
		return newItem;
	    }
	}
	return null;
    }
}
