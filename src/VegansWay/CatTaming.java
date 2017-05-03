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

import java.util.List;
import java.util.Random;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Ocelot;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 *
 * @author Pronink
 */
public class CatTaming
{

    public void testOcelotTaming()
    {
	for (Player player : Bukkit.getOnlinePlayers())
	{
	    ItemStack itemStack = player.getInventory().getItemInMainHand();
	    // Si tienes nepeta cataria en la mano
	    if (itemStack.getData().toString().equals("RED_ROSE(7)"))
	    {
		List<Entity> nearbyEntities = player.getNearbyEntities(20, 20, 20); //Busco bichos dentro de un radio del jugador
		for (Entity nearbyEntitie : nearbyEntities)
		{
		    if (nearbyEntitie instanceof Ocelot)
		    {
			Ocelot myOcelot = (Ocelot) nearbyEntitie;
			if (myOcelot.getCatType().equals(Ocelot.Type.WILD_OCELOT))
			{
			    myOcelot.setTarget(player); // Cuando el ocelot dañe al jugador, el evento del main llamará a testConvertToCat()
			}
			// Como en creativo el ocelot no va a llamar a la funcion al no poder dañar al jugador, detecto si esta cerca y lo convierto directamente
			// ya que SI tiene nepeta en la mano
			if (player.getGameMode().equals(GameMode.CREATIVE) && myOcelot.getCatType().equals(Ocelot.Type.WILD_OCELOT) && myOcelot.getLocation().distance(player.getLocation()) < 3)
			{
			    convertOcelotToCat(myOcelot, player);
			}
		    }
		}
	    }
	    // Si no tienes nepeta cataria en la mano y eres modo creativo
	    if (!itemStack.getData().toString().equals("RED_ROSE(7)") && player.getGameMode().equals(GameMode.CREATIVE))
	    {
		List<Entity> nearbyEntities = player.getNearbyEntities(1,1,1);
		for (Entity nearbyEntitie : nearbyEntities){
		    if (nearbyEntitie instanceof Ocelot)
		    {
			Ocelot myOcelot = (Ocelot) nearbyEntitie;
			if (myOcelot.getCatType().equals(Ocelot.Type.WILD_OCELOT) && myOcelot.getTarget() != null)
			{
			    // Como en creativo el ocelot no va a llamar a la funcion al no poder dañar al jugador, detecto si esta cerca y lo anulo directamente
			    // ya que NO tiene nepeta en la mano
			    disappointedOcelot(myOcelot);
			}
		    }
		}
	    }
	}
    }

    public void testConvertToCat(EntityDamageByEntityEvent event) // Llamado desde el main cada vez que un ocelot pega a un player
    {
	Entity damager = event.getDamager();
	Entity entity = event.getEntity();
	if (damager instanceof Ocelot && entity instanceof Player)
	{
	    Ocelot ocelot = (Ocelot) damager;
	    Player player = (Player) entity;
	    if (ocelot.getCatType().equals(Ocelot.Type.WILD_OCELOT))
	    {
		World myWorld = player.getWorld();
		ItemStack itemStack = player.getInventory().getItemInMainHand();
		if (itemStack.getData().toString().equals("RED_ROSE(7)"))
		{
		    event.setCancelled(true);
		    Util.quitOneItemFromHand(player);
		    Random r = new Random();
		    if (r.nextInt(100) < Config.CONFIG_OCELOT_TO_CAT_PERCENTAGE)
		    {
			convertOcelotToCat(ocelot, player);
		    }
		    else
		    {
			myWorld.spawnParticle(Particle.CRIT, ocelot.getEyeLocation(), 10, 0.2, 0.2, 0.2);
			myWorld.playSound(ocelot.getLocation(), Sound.ENTITY_CAT_HURT, 1, 1);
			myWorld.playSound(ocelot.getLocation(), Sound.BLOCK_GRASS_FALL, 1, 1);
		    }
		}
		else
		{
		    event.setCancelled(true);
		    disappointedOcelot(ocelot);
		}
	    }
	}
    }

    private void convertOcelotToCat(Ocelot ocelot, Player player)
    {
	Ocelot newCat = (Ocelot) ocelot.getWorld().spawnEntity(ocelot.getLocation(), EntityType.OCELOT);
	Random r = new Random();
	switch (r.nextInt(3) + 1)
	{
	    case 1:
		newCat.setCatType(Ocelot.Type.BLACK_CAT);
		break;
	    case 2:
		newCat.setCatType(Ocelot.Type.RED_CAT);
		break;
	    case 3:
		newCat.setCatType(Ocelot.Type.SIAMESE_CAT);
		break;
	    default:
		break;
	}
	newCat.setTamed(true);
	newCat.setOwner(player);
	if (!ocelot.isAdult())
	{
	    newCat.setBaby();
	}
	newCat.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 200, 1, false, true), true);
	newCat.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 200, 2, false, false), true);
	newCat.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 200, 1, false, false), true);
	World myWorld = newCat.getWorld();
	myWorld.playSound(newCat.getLocation(), Sound.ENTITY_CAT_AMBIENT, 1, 1);
	myWorld.playSound(newCat.getLocation(), Sound.BLOCK_GRASS_FALL, 1, 1);
	myWorld.spawnParticle(Particle.HEART, newCat.getLocation().add(0, 1, 0), 5, 0.2, 0.2, 0.2);
	ocelot.remove();
    }

    private void disappointedOcelot(Ocelot ocelot)
    {
	ocelot.setTarget(null);
	ocelot.getWorld().playSound(ocelot.getLocation(), Sound.ENTITY_CAT_HURT, 1, 1);
	ocelot.getWorld().spawnParticle(Particle.VILLAGER_ANGRY, ocelot.getLocation().add(0, 1, 0), 5, 0.2, 0.2, 0.2);
    }
}
