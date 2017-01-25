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

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author Pronink
 */
public class Util
{
    public static void quitOneItemFromHand(Player player)
    {
	ItemStack itemStack = player.getInventory().getItemInMainHand();
	if (!player.getGameMode().equals(GameMode.CREATIVE))
	{
	    if (itemStack.getAmount() == 1)
	    {
		player.getInventory().setItemInMainHand(null);
	    }
	    else
	    {
		itemStack.setAmount(itemStack.getAmount() - 1);
	    }
	}
    }
    
    public static Location getMiddlePoint(Location l1, Location l2)
    {
	int x1 = l1.getBlockX();
	int x2 = l2.getBlockX();
	int y1 = l1.getBlockY();
	int y2 = l2.getBlockY();
	int z1 = l1.getBlockZ();
	int z2 = l2.getBlockZ();
	
	int x3 = (x1 + x2) / 2;
	int y3 = (y1 + y2) / 2;
	int z3 = (z1 + z2) / 2;
	
	return new Location(l1.getWorld(), x3, y3, z3);
    }
}
