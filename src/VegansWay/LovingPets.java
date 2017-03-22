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

import java.util.ArrayList;
import java.util.UUID;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Ocelot;
import org.bukkit.entity.Wolf;

/**
 *
 * @author Pronink
 */
public class LovingPets
{

    private class LovingDog
    {

	public Wolf entityDog;
	public int lovingTime;
	public int breakTime;

	public LovingDog(Wolf entityDog)
	{
	    this.entityDog = entityDog;
	    this.lovingTime = 10;
	    this.breakTime = 0;
	}
    }

    private class LovingCat
    {

	public Ocelot entityCat;
	public int lovingTime;
	public int breakTime;

	public LovingCat(Ocelot entityCat)
	{
	    this.entityCat = entityCat;
	    this.lovingTime = 10;
	    this.breakTime = 0;
	}
    }
    private ArrayList<LovingDog> dogList;
    private ArrayList<LovingCat> catList;

    public LovingPets()
    {
	this.dogList = new ArrayList<>();
	this.catList = new ArrayList<>();
    }

    public void testLovingPets() // Llamado desde el main cada 1 segundo para restar tiempo en las listas
    {
	for (LovingDog ld : dogList)
	{
	    if (ld.lovingTime > 0)
	    {
		ld.lovingTime--;
	    }
	    if (ld.breakTime > 0)
	    {
		ld.breakTime--;
	    }
	    if (ld.lovingTime > 0 && ld.breakTime == 0)
	    {
		ld.entityDog.getWorld().spawnParticle(Particle.HEART, ld.entityDog.getLocation().add(0, 1, 0), 1, 0, 0, 0);
	    }
	    if (ld.lovingTime > 0 && ld.breakTime > 0)
	    {
		ld.entityDog.getWorld().spawnParticle(Particle.VILLAGER_ANGRY, ld.entityDog.getLocation().add(0, 1, 0), 1, 0, 0, 0);
	    }
	    if (ld.lovingTime > 0 && ld.breakTime == 0) // Si el perro puede amar...
	    {
		for (LovingDog ld2 : dogList) // ... busca mas perros en la lista
		{
		    if (ld2.lovingTime > 0 && ld2.breakTime == 0) // Si el segundo perro puede amar...
		    {
			if (ld.entityDog.getUniqueId().compareTo(ld2.entityDog.getUniqueId()) != 0) // Si no son el mismo perro
			{
			    if (ld.entityDog.getLocation().distance(ld2.entityDog.getLocation()) < 10) // Si estan a menos de 10 metros
			    {
				if (!ld.entityDog.isSitting() || !ld2.entityDog.isSitting()) // Si alguno de los dos esta de pie
				{
				    ld.entityDog.setTarget(ld2.entityDog); // Ahora solo tengo que esperar a que los perros se peguen y ...
				    ld2.entityDog.setTarget(ld.entityDog); // ... lancen el evento que los hagan tener una cria. Luego los target desaparecen
				    //Bukkit.broadcastMessage(ChatColor.LIGHT_PURPLE + "Hay 2 perros que se aman " + ChatColor.RED + "<3");
				}
			    }
			}
		    }
		}
	    }
	}
	// TODO: Hacer lo mismo con la lista de Ocelotes
    }

    public void addPet(Entity entity) // Llamado desde FeedingPets
    {
	if (entity instanceof Wolf)
	{
	    Wolf myDog = (Wolf) entity;
	    if (myDog.isTamed() && myDog.isAdult())
	    {
		UUID uuidDog = myDog.getUniqueId();
		for (LovingDog ld : dogList)
		{
		    if (uuidDog.compareTo(ld.entityDog.getUniqueId()) == 0)
		    {
			ld.lovingTime = 10; // Si lo encuentro en la lista, reinicio el contador de amar a 10
			return;
		    }
		} // Si no lo encuentro en la lista, lo agrego
		dogList.add(new LovingDog(myDog));
	    }
	}
	// TODO: Añadir lo mismo con el ocelot
    }

    public void testNewDogOrCatBaby(Entity e1, Entity e2) // Llamado desde el main cada vez que dos pets se pegan (target)
    {
	if (e1 instanceof Wolf && e2 instanceof Wolf)
	{
	    Wolf dog1 = (Wolf) e1;
	    Wolf dog2 = (Wolf) e2;
	    if (dog1.isTamed() && dog2.isTamed() && dog1.isAdult() && dog2.isAdult())
	    {
		boolean dog1exists = false, dog2exists = false;
		LovingDog lovingDog1 = null;
		LovingDog lovingDog2 = null;
		for (LovingDog ld : dogList)
		{
		    if (dog1.getUniqueId().compareTo(ld.entityDog.getUniqueId()) == 0)
		    {
			dog1exists = true;
			lovingDog1 = ld;
		    }
		    if (dog2.getUniqueId().compareTo(ld.entityDog.getUniqueId()) == 0)
		    {
			dog2exists = true;
			lovingDog2 = ld;
		    }
		}
		if (dog1exists && dog2exists)
		{
		    if (lovingDog1.breakTime == 0 && lovingDog2.breakTime == 0)
		    {
			Wolf dogBaby = (Wolf) dog1.getWorld().spawnEntity(Util.getMiddlePoint(dog1.getLocation(), dog2.getLocation()), EntityType.WOLF);
			dogBaby.setBaby();
			dogBaby.setTamed(true);
			dogBaby.setOwner(dog1.getOwner());

			Wolf dogNew1 = (Wolf) dog1.getWorld().spawnEntity(dog1.getLocation(), EntityType.WOLF);
			Wolf dogNew2 = (Wolf) dog2.getWorld().spawnEntity(dog2.getLocation(), EntityType.WOLF);
			dogNew1.setTamed(true);
			dogNew1.setOwner(dog1.getOwner());
			dogNew1.setCustomName(dog1.getCustomName());
			dogNew1.setCustomNameVisible(dog1.isCustomNameVisible());
			dogNew1.setHealth(20);
			dogNew1.setVelocity(dog1.getVelocity());
			dogNew2.setTamed(true);
			dogNew2.setOwner(dog2.getOwner());
			dogNew2.setCustomName(dog2.getCustomName());
			dogNew2.setCustomNameVisible(dog2.isCustomNameVisible());
			dogNew2.setHealth(20);
			dogNew2.setVelocity(dog2.getVelocity());

			lovingDog1.entityDog = dogNew1;
			lovingDog2.entityDog = dogNew2;
			lovingDog1.lovingTime = 0;
			lovingDog2.lovingTime = 0;
			lovingDog1.breakTime = 60;
			lovingDog2.breakTime = 60;

			dog1.remove();
			dog2.remove();
		    }
		}

	    }
	}
	// TODO: Añadir lo mismo con el ocelot
    }
}
