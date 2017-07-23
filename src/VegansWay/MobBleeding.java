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

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.*;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

/**
 *
 * @author Ismael
 */


public class MobBleeding {
    
    public void testMobBleeding(EntityDamageByEntityEvent event)
    {
        Entity entity = event.getEntity();
        World world = entity.getWorld();
        if (isMob(entity))
        {
            if (isFriendlyMob(entity))
            {
                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Location nowLocation = ((LivingEntity)event.getEntity()).getEyeLocation();
                        world.spawnParticle(Particle.ITEM_CRACK, nowLocation, 50, 0.2f, 0.2f, 0.2f, 0.15f, new ItemStack(Material.RAW_BEEF));
                        for (int i = 0; i < 5*5; i++)
                        {
                            nowLocation = (((LivingEntity)event.getEntity()).getEyeLocation()).add(0, -0.4f, 0);
                            world.spawnParticle(Particle.BLOCK_CRACK, nowLocation, 5, 0.2f, 0.2f, 0.2f, 0.001f, new MaterialData(Material.NETHER_WART_BLOCK));
                            if (event.getEntity().isDead())
                            {
                                break;
                            }
                            try { Thread.sleep(200); } catch (InterruptedException ex) {}
                        }
                    }
                });
                t.start();
            }
            else
            {
                Location location = ((LivingEntity)entity).getEyeLocation();
                world.spawnParticle(Particle.BLOCK_CRACK, location, 5, 0.2f, 0.2f, 0.2f, 0.1f, new MaterialData(Material.SLIME_BLOCK));
            }
        }
    }
    
    private boolean isFriendlyMob(Entity e)
    {
        return (e instanceof Player || 
                e instanceof Bat || 
                e instanceof Chicken || 
                e instanceof Cow || 
                e instanceof Pig || 
                e instanceof Rabbit || 
                e instanceof Sheep || 
                e instanceof Squid || 
                e instanceof Villager || 
                e instanceof Donkey || 
                e instanceof Horse || 
                e instanceof Llama || 
                e instanceof Mule || 
                e instanceof Ocelot || 
                e instanceof Parrot || 
                e instanceof Wolf || 
                e instanceof Squid );
    }

    private boolean isMob(Entity e) {
        return (e instanceof AbstractHorse ||
                e instanceof Bat ||
                e instanceof Blaze ||
                e instanceof CaveSpider ||
                e instanceof ChestedHorse ||
                e instanceof Chicken ||
                e instanceof Cow ||
                e instanceof Creeper ||
                e instanceof Donkey ||
                e instanceof ElderGuardian ||
                e instanceof EnderDragon ||
                e instanceof Enderman ||
                e instanceof Endermite ||
                e instanceof Evoker ||
                e instanceof Ghast ||
                e instanceof Giant ||
                e instanceof Golem ||
                e instanceof Guardian ||
                e instanceof Horse ||
                e instanceof HumanEntity ||
                e instanceof Husk ||
                e instanceof Illager ||
                e instanceof Illusioner ||
                e instanceof IronGolem ||
                e instanceof Llama ||
                e instanceof MagmaCube ||
                e instanceof Mule ||
                e instanceof MushroomCow ||
                e instanceof NPC ||
                e instanceof Ocelot ||
                e instanceof Parrot ||
                e instanceof Pig ||
                e instanceof PigZombie ||
                e instanceof Player ||
                e instanceof PolarBear ||
                e instanceof Rabbit ||
                e instanceof Sheep ||
                e instanceof Shulker ||
                e instanceof Silverfish ||
                e instanceof Skeleton ||
                e instanceof SkeletonHorse ||
                e instanceof Slime ||
                e instanceof Snowman ||
                e instanceof Spellcaster ||
                e instanceof Spider ||
                e instanceof Squid ||
                e instanceof Stray ||
                e instanceof Vex ||
                e instanceof Villager ||
                e instanceof Vindicator ||
                e instanceof Witch ||
                e instanceof Wither ||
                e instanceof WitherSkeleton ||
                e instanceof Wolf ||
                e instanceof Zombie ||
                e instanceof ZombieHorse ||
                e instanceof ZombieVillager);
    }
}
/*else if(entity instanceof Zombie)
            {
                world.spawnParticle(Particle.ITEM_CRACK, location.add(0, -0.5f, 0), 5, 0.2f, 0.5f, 0.2f, 0.1f, new ItemStack(Material.ROTTEN_FLESH));
                world.spawnParticle(Particle.BLOCK_CRACK, location.add(0, -0.5f, 0), 5, 0.2f, 0.5f, 0.2f, 0.1f, new MaterialData(Material.SLIME_BLOCK));
            }
            else if(entity instanceof Spider || entity instanceof CaveSpider)
            {
                world.spawnParticle(Particle.ITEM_CRACK, location, 5, 0.2f, 0.2f, 0.2f, 0.1f, new ItemStack(Material.SPIDER_EYE));
                world.spawnParticle(Particle.BLOCK_CRACK, location, 5, 0.2f, 0.2f, 0.2f, 0.1f, new MaterialData(Material.SLIME_BLOCK));
            }*/
