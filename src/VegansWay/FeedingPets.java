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

import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Ocelot;
import org.bukkit.entity.Wolf;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

/**
 *
 * @author Pronink
 */
public class FeedingPets
{

    private boolean nowFeeding = false; // Necesario porque el evento se ejecuta dos veces
    private LovingPets lovingPets;
    
    public FeedingPets(LovingPets lovingPets)
    {
	this.lovingPets = lovingPets;
    }

    
    public void testPetFeeding(PlayerInteractAtEntityEvent event)
    {
	if (!nowFeeding)
	{
	    if ((event.getRightClicked() instanceof Wolf || event.getRightClicked() instanceof Ocelot) && isFood(event.getPlayer().getInventory().getItemInMainHand().getType()))
	    {
		setTrueThenFalse(); // Pongo el semaforo en true para que no vuelva a pasar la segunda vez
		if (event.getRightClicked() instanceof Wolf)
		{
		    Wolf dog = (Wolf) event.getRightClicked();
		    if (dog.isTamed())
		    {
			double health = dog.getHealth();
			if (health <= 17.0)
			{
			    dog.setHealth(dog.getHealth() + 3.0);
			}
			else if (health > 17.0 && health <= 20.0)
			{
			    dog.setHealth(20D);
			    dog.getWorld().spawnParticle(Particle.HEART, dog.getLocation().add(0, 1, 0), 4, 0.4, 0.4, 0.4);
			    lovingPets.addPet(dog);
			}
			Util.quitOneItemFromHand(event.getPlayer());
		    }
		}
		else if (event.getRightClicked() instanceof Ocelot)
		{
		    Ocelot cat = (Ocelot) event.getRightClicked();
		    if (cat.isTamed())
		    {
			double health = cat.getHealth();
			if (health <= 7.0)
			{
			    cat.setHealth(cat.getHealth() + 3.0);
			}
			else if (health > 7.0 && health <= 10.0)
			{
			    cat.setHealth(10D);
			    cat.getWorld().spawnParticle(Particle.HEART, cat.getLocation().add(0, 1, 0), 4, 0.4, 0.4, 0.4);
			}
			Util.quitOneItemFromHand(event.getPlayer());
			// TODO: lovingPets.addPet(cat);
		    }
		}
	    }
	}
    }

    private void setTrueThenFalse()
    {
	nowFeeding = true; // Set true
	Thread thread = new Thread(new Runnable()
	{
	    @Override
	    public void run()
	    {
		try
		{
		    Thread.sleep(200);
		    nowFeeding = false; // Set false then 200 miliseconds
		}
		catch (InterruptedException ex)
		{
		    Logger.getLogger(FeedingPets.class.getName()).log(Level.SEVERE, null, ex);
		}
	    }
	});
	thread.start();
    }

    private boolean isFood(Material material)
    {
	switch (material)
	{
	    case BREAD:
		return true;
	    case APPLE:
		return true;
	    case GOLDEN_APPLE:
		return true;
	    case CAKE:
		return true;
	    case PUMPKIN_PIE:
		return true;
	    case COOKIE:
		return true;
	    case MELON:
		return true;
	    case CARROT_ITEM:
		return true;
	    case GOLDEN_CARROT:
		return true;
	    case POTATO_ITEM:
		return true;
	    case BAKED_POTATO:
		return true;
	    case BEETROOT:
		return true;
	    default:
		return false;
	}
    }

}
