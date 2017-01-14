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
