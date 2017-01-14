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

import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.CaveSpider;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Spider;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

/**
 *
 * @author Pronink
 */
// TODO: Que las arañas den más tela o disparen telas de araña
// TODO: Camas crafteadas con heno
// TODO: Alimentar a los perros con no-carne
// TODO: Comprarme un macbook
public class Main extends JavaPlugin implements Listener
{

    int state = 0;
    CatTaming catTaming;
    ItemModify itemModify;
    CraftingRecipes craftingRecipes;

    @Override
    public void onEnable()
    {
	catTaming = new CatTaming();
	itemModify = new ItemModify();
	craftingRecipes = new CraftingRecipes();
	// REGISTRAR EVENTOS, INICIAR EVENTOS TEMPORIZADOS, INICIAR CRAFTEOS
	Bukkit.getServer().getPluginManager().registerEvents(this, this);
	startTimedEvents();
	craftingRecipes.addAllCraftingRecipes();
    }

    private void startTimedEvents()
    {
	Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable()
	{
	    @Override
	    public void run() // Añadir aqui los eventos que se ejecutaran cada segundo
	    {
		state = ++state % 120; // EL CICLO DURA 60 SEGUNDOS
		if (state % 1 == 0) // CADA 0.5 SEGUNDOS
		{
		    catTaming.testOcelotFeeding();
		}
		if (state % 6 == 0) // CADA 3 SEGUNDOS
		{
		    catTaming.removeInvulnerableChickens();
		}
	    }
	}, 20 * 1, 20 * 1 / 2); // Cada 1/2 segundos, empezando desde el segundo 1

    }

    @Override
    public void onDisable()
    {
	catTaming.removeInvulnerableChickens();
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e)
    {
	catTaming.removeInvulnerableChickens();
    }

    @EventHandler
    public void onItemSpawn(ItemSpawnEvent event)
    {
	itemModify.modifyItemGround(event);
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event)
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

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event)
    {
	if (event.getEntity() instanceof Spider || event.getEntity() instanceof CaveSpider)
	{
	    Spider spider = (Spider)event.getEntity();
	    int nStrings = (int) (Math.random() * 4); // De 0 a 3 drops de cuerdas extra
	    event.getDrops().add(new ItemStack(Material.STRING, nStrings));
	}

    }

    /*private void launchWeb(Spider spider)
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
