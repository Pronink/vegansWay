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
