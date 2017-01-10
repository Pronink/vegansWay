/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VegansWay;

import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Ocelot;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 *
 * @author Pronink
 */
public class CatTaming
{

    public void testOcelotFeeding()
    {
	for (Player player : Bukkit.getOnlinePlayers())
	{
	    ItemStack itemStack = player.getInventory().getItemInMainHand();
	    if (itemStack.getData().toString().equals("RED_ROSE(7)"))
	    {
		List<Entity> nearbyEntities = player.getNearbyEntities(20, 20, 20); //Busco bichos dentro de un radiod el jugador
		for (Entity nearbyEntitie : nearbyEntities)
		{
		    if (nearbyEntitie instanceof Ocelot)
		    {
			Ocelot myOcelot = (Ocelot) nearbyEntitie;
			Chicken chicken = createOrMoveNamedChicken(player);
			myOcelot.setTarget(chicken);
			if (myOcelot.getLocation().distance(player.getLocation()) < 1) // Si el gato esta cerca tuya se inicia la conversion en gato
			{
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
			    myOcelot.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 200, 1, false, true), true);
			    myOcelot.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 200, 2, false, false), true);
			    myOcelot.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 200, 2, false, false), true);

			    World myWorld = player.getWorld();
			    myWorld.spawnParticle(Particle.CRIT, myOcelot.getEyeLocation(), 10, 0.2, 0.2, 0.2);
			    myWorld.playSound(myOcelot.getLocation(), Sound.ENTITY_CAT_HURT, 1, 1);
			    myWorld.playSound(myOcelot.getLocation(), Sound.BLOCK_GRASS_FALL, 1, 1);

			    if (myOcelot.getCatType().equals(Ocelot.Type.WILD_OCELOT))
			    {
				int random = (int) (Math.random() * 101); // De 0 a 100
				if (random <= 40)
				{
				    convertToCat(myOcelot, player);
				    safelyKillChicken(chicken);
				    return;
				}
			    }
			}
		    }
		}
	    }
	}
    }

    private Chicken createOrMoveNamedChicken(Player player)
    {
	Location playerLoc = player.getLocation();
	playerLoc.setY(playerLoc.getY() + 2);
	// Checkear si ya existe una gallina
	List<Entity> nearbyEntities = player.getNearbyEntities(20, 20, 20); // Busca una gallina cercana al jugador
	for (Entity entity : nearbyEntities)
	{
	    if (entity instanceof Chicken)
	    {
		Chicken chicken = (Chicken) entity;
		if ((!chicken.hasAI()) && chicken.getCustomName().equals(player.getName()))
		{
		    chicken.teleport(playerLoc); // Y la teletransporta al jugador
		    chicken.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 100, 0, false, false), true);
		    return chicken;
		}
	    }
	}
	// Si no se ha encontrado gallina, crear una nueva
	World myWorld = player.getWorld();
	Chicken chicken = (Chicken) myWorld.spawnEntity(playerLoc, EntityType.CHICKEN);
	chicken.setCustomName(player.getName());
	chicken.setCollidable(false);
	chicken.setInvulnerable(true);
	chicken.setGravity(false);
	chicken.setAI(false);
	chicken.setSilent(true);
	chicken.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 100, 0, false, false), true);
	return chicken;
    }

    private void convertToCat(Ocelot myOcelot, Player owner)
    {
	int random = (int) (Math.random() * 3 + 1);
	switch (random)
	{
	    case 1:
		myOcelot.setCatType(Ocelot.Type.BLACK_CAT);
		break;
	    case 2:
		myOcelot.setCatType(Ocelot.Type.RED_CAT);
		break;
	    case 3:
		myOcelot.setCatType(Ocelot.Type.SIAMESE_CAT);
		break;
	    default:
		break;
	}
	myOcelot.setTarget(null);
	myOcelot.setOwner(owner);
	World myWorld = owner.getWorld();
	myWorld.playSound(myOcelot.getLocation(), Sound.ENTITY_CAT_AMBIENT, 1, 1);
	Thread thread = new Thread(new Runnable()
	{
	    @Override
	    public void run()
	    {
		for (int i = 0; i < 10; i++)
		{
		    try
		    {
			Thread.sleep(750);
		    }
		    catch (InterruptedException ex)
		    {
		    }
		    Location heartLocation = myOcelot.getLocation();
		    heartLocation.setY(heartLocation.getY() + 1);
		    myWorld.spawnParticle(Particle.HEART, heartLocation, 1, 0, 0, 0);
		}
	    }
	});
	thread.start();

    }

    public void removeInvulnerableChickens()
    {
	for (World myWorld : Bukkit.getWorlds())
	{
	    for (LivingEntity livingEntity : myWorld.getLivingEntities())
	    {
		if (livingEntity instanceof Chicken)
		{
		    Chicken chicken = (Chicken) livingEntity;
		    if (!chicken.hasAI()) // Si se cumple se inicia el borrado de gallinas y pasa por varias pruebas
		    {
			Player player = null;
			for (Player players : Bukkit.getOnlinePlayers())
			{
			    if (players.getName().equals(chicken.getCustomName()))
			    {
				player = players;
			    }
			}
			if (player == null) // Si el jugador de la gallina no esta conectado, se muere la gallina
			{
			    safelyKillChicken(chicken);
			    Bukkit.broadcastMessage(ChatColor.RED + "Una gallina se ha borrado porque no hay jugador");
			    return;
			}

			if (!player.getInventory().getItemInMainHand().getData().toString().equals("RED_ROSE(7)")) // Si el jugador ya no tiene la Nepeta, se muere la gallina
			{
			    safelyKillChicken(chicken);
			    Bukkit.broadcastMessage(ChatColor.RED + "Una gallina se ha borrado porque no hay item en la mano");
			    return;
			}

			List<Entity> livingThings = player.getNearbyEntities(20, 20, 20);
			for (Entity livingThing : livingThings) // Si ya no hay Ocelotes cerca, se muere la gallina
			{
			    if (livingThing instanceof Ocelot)
			    {
				return;
			    }
			}
			safelyKillChicken(chicken);
			Bukkit.broadcastMessage(ChatColor.RED + "Una gallina se ha borrado");
		    }
		}
	    }
	}
    }

    private void safelyKillChicken(Chicken chicken)
    {
	// Ahora la mato
	chicken.remove();
	// Primero quito a los ocelotes de alrededor su tarjet al pollo
	List<Entity> nearbyEntities = chicken.getNearbyEntities(20, 20, 20);
	for (Entity entity : nearbyEntities)
	{
	    if (entity instanceof Ocelot)
	    {
		Ocelot myOcelot = (Ocelot) entity;
		myOcelot.setTarget(null);
	    }
	}

    }
}
