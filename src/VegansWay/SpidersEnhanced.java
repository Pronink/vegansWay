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

import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.entity.CaveSpider;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Spider;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author Pronink
 */
public class SpidersEnhanced
{

    void testSpiderWebAttack(EntityDamageByEntityEvent event)
    {
	if (event.getDamager() instanceof Spider || event.getDamager() instanceof CaveSpider)
	{
	    if ((int) (Math.random() * 4 + 1) == 1) // 1/4 parte de las veces entra
	    {
		Block block = event.getEntity().getLocation().getBlock();
		Entity spider = event.getDamager();
		block.getWorld().spawnParticle(Particle.EXPLOSION_NORMAL, block.getLocation(), 10, 0.2, 0.2, 0.2);
		block.getWorld().spawnParticle(Particle.EXPLOSION_NORMAL, spider.getLocation(), 10, 0.2, 0.2, 0.2);
		if (block.getType().equals(Material.AIR))
		{
		    block.setType(Material.WEB);
		}
	    }
	}
    }

    void addSpiderDrops(EntityDeathEvent event)
    {
	if (event.getEntity() instanceof Spider || event.getEntity() instanceof CaveSpider)
	{
	    int nStrings = (int) (Math.random() * 4); // De 0 a 3 drops de cuerdas extra
	    event.getDrops().add(new ItemStack(Material.STRING, nStrings));
	}
    }
    

    /*private void launchWeb(Spider spider) // Disparo de telas de araña. Se buggea
    {
	Arrow arrow = spider.launchProjectile(Arrow.class);
	Vector aVel = arrow.getVelocity();
	arrow.remove();
	aVel = aVel.multiply((double) Math.random());
	FallingBlock fb = spider.getWorld().spawnFallingBlock(spider.getLocation(), new MaterialData(Material.WEB));
	fb.setVelocity(aVel);
	fb.setTicksLived(20 * 5); // Vive 5 segundos
    }*/
}
