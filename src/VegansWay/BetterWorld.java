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

import java.util.Random;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockGrowEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.world.ChunkPopulateEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;
import org.bukkit.util.EulerAngle;

/**
 *
 * @author Ismael
 */
public class BetterWorld
{

    // MÉTODOS QUE CREAN FLORES AL GENERAR EL MUNDO O AL CRECER LOS CACTUS (TENGO QUE HACER QUE CON BONE MEAL CREES LANA EN LOS FIBER PLANTS)
    
    public void testGeneration(ChunkPopulateEvent event)
    {
        Random r = new Random();
        int chunkRandom = r.nextInt(100); // No en todos los chunks se van a generar cosas
        Biome biome = event.getChunk().getBlock(7, 0, 7).getBiome();
        boolean isDesert = biome.equals(Biome.DESERT) || biome.equals(Biome.DESERT_HILLS) || biome.equals(Biome.MUTATED_DESERT);

        int x = event.getChunk().getBlock(7, 0, 7).getX();
        int z = event.getChunk().getBlock(7, 0, 7).getZ();
        World world = event.getWorld();
        int y = world.getHighestBlockYAt(x, z);

        // Recorrer la zona centro de cada chunk
        if (!world.getBlockAt(x, y, z).getRelative(BlockFace.DOWN).getType().equals(Material.STATIONARY_WATER))
        {
            int initX = x - 6, initY = y - 14, initZ = z - 6;
            int relX, relY, relZ;
            for (int i = 0; i <= 12; i++)
            {
                relX = initX + i;
                for (int j = 0; j <= 28; j++)
                {
                    relY = initY + j;
                    for (int k = 0; k <= 12; k++)
                    {
                        relZ = initZ + k;

                        Block block = world.getBlockAt(relX, relY, relZ);

                        if (!isDesert && chunkRandom < 25 && r.nextInt(100) < 5
                                && block.getType().equals(Material.AIR))
                        {
                            if (block.getRelative(BlockFace.DOWN).getType().equals(Material.GRASS))
                            {
                                makeFiberPlant(block);
                            }
                        }
                        if (isDesert && chunkRandom < 75 && r.nextInt(100) < 50
                                && block.getType().equals(Material.AIR)
                                && block.getRelative(BlockFace.DOWN).getRelative(BlockFace.DOWN).getRelative(BlockFace.DOWN).getType().equals(Material.CACTUS)
                                && block.getRelative(BlockFace.DOWN).getRelative(BlockFace.DOWN).getRelative(BlockFace.DOWN).getRelative(BlockFace.DOWN).getType().equals(Material.SAND))
                        {

                            block.getRelative(BlockFace.DOWN).setType(Material.CACTUS);
                            block.getRelative(BlockFace.DOWN).getRelative(BlockFace.DOWN).setType(Material.CACTUS);
                            double flowerX = relX + 0.5d;
                            double flowerY = relY - 1.4d;
                            double flowerZ = relZ + 0.5d;

                            makeCactusFlower(world, flowerX, flowerY, flowerZ);
                        }
                    }
                }
            }
        }
    }

    public void testCactusGrow(BlockGrowEvent event)
    {
        if (event.getNewState().getBlock().getRelative(BlockFace.DOWN).getType().equals(Material.CACTUS)
                && event.getNewState().getBlock().getRelative(BlockFace.DOWN).getRelative(BlockFace.DOWN).getType().equals(Material.CACTUS))
        {
            World world = event.getNewState().getWorld();
            double flowerX = event.getNewState().getBlock().getX() + 0.5d;
            double flowerY = event.getNewState().getBlock().getY() - 0.4d;
            double flowerZ = event.getNewState().getBlock().getZ() + 0.5d;

            makeCactusFlower(world, flowerX, flowerY, flowerZ);
        }
    }

    

    // MÉTODOS QUE DETECTAN SI SE DEBE ROMPER UNA FLOR
    
    public void findFloatingFlowers()
    {
        int entidades = 0;
        for (Player player : Bukkit.getOnlinePlayers())
        {
            for (Entity entity : player.getNearbyEntities(10, 10, 10))
            {
                entidades++;
                if (isCactusFlower(entity)
                        && !entity.getLocation().getBlock().getRelative(BlockFace.UP).getType().equals(Material.CACTUS))
                {
                    breakCactusFlower((ArmorStand) entity);
                }
            }
        }
        Bukkit.broadcastMessage(entidades + "");
    }
    
    public void testCactusBreak(BlockBreakEvent event)
    {
        if (event.getBlock().getType().equals(Material.CACTUS))
        {
            Location cactusLocation = event.getBlock().getLocation().add(0.5d, 0, 0.5d);
            double x = cactusLocation.getX();
            double y = cactusLocation.getY();
            double z = cactusLocation.getZ();
            Entity[] entities = event.getBlock().getChunk().getEntities();
            for (Entity entity : entities)
            {
                if (isCactusFlower(entity)
                        && entity.getLocation().getX() == x
                        && entity.getLocation().getZ() == z
                        && Math.abs(entity.getLocation().getY() - y) < 3d)
                {
                    breakCactusFlower((ArmorStand) entity);
                }
            }
        }
    }

    public void testCactusFlowerDamaged(EntityDamageByEntityEvent event)
    {
        if (event.getEntity() instanceof ArmorStand)
        {
            Location firstASLocation = event.getEntity().getLocation(); // Armor stand que ha recibido el golpe y sus coordenadas
            double x = firstASLocation.getX();
            double y = firstASLocation.getY();
            double z = firstASLocation.getZ();
            Entity[] entities = event.getEntity().getLocation().getChunk().getEntities(); // Armor stands cercanos
            for (Entity entity : entities)
            {
                if (isCactusFlower(entity)
                        && entity.getLocation().getX() == x
                        && entity.getLocation().getZ() == z
                        && Math.abs(entity.getLocation().getY() - y) < 1d)
                {
                    breakCactusFlower((ArmorStand) entity);
                }
            }
        }
    }

    
    
    // MÉTODOS AYUDANTES
    
    private void makeCactusFlower(World world, double flowerX, double flowerY, double flowerZ)
    {
        Random r = new Random();
        double randomAngleX = r.nextDouble();
        double randomAngleY = r.nextDouble();
        double randomAngleZ = r.nextDouble();
        int color = r.nextInt(16);

        for (int a = 0; a < 360; a = a + 60)
        {
            ArmorStand as = (ArmorStand) world.spawnEntity(new Location(world, flowerX, flowerY, flowerZ, a, a), EntityType.ARMOR_STAND);
            as.setGravity(false);
            as.setCollidable(false);
            as.setAI(false);
            as.setInvulnerable(true);
            as.setHeadPose(new EulerAngle(randomAngleX, randomAngleY, randomAngleZ));
            as.setHelmet(new ItemStack(Material.CARPET, 1, (short) 1, (byte) color));
            as.setVisible(false);
            as.setCustomName("vegansWay_CactusFlower");
            as.setCustomNameVisible(false);
        }

        Bukkit.broadcastMessage("Cactus lana en: " + flowerX + " " + flowerZ);
    }

    private void breakCactusFlower(ArmorStand armorStand)
    {
        Byte color = ((ArmorStand) armorStand).getHelmet().getData().getData();
        armorStand.getWorld().dropItemNaturally(armorStand.getLocation().add(0, 1.5d, 0), new ItemStack(Material.WOOL, 1, (short) 0, color));
        armorStand.getWorld().spawnParticle(Particle.BLOCK_CRACK, armorStand.getLocation().add(0, 1.5d, 0), 5, 0.2f, 0.2f, 0.2f, 0.001f, new MaterialData(Material.CARPET, color));
        armorStand.remove();
    }

    private boolean isCactusFlower(Entity entity)
    {
        return entity != null
                && !entity.isDead()
                && entity instanceof ArmorStand
                && entity.getCustomName().equals("vegansWay_CactusFlower");
    }

    private void makeFiberPlant(Block block)
    {
        block.setType(Material.RED_ROSE);
        block.setData((byte) 3);
    }

    
}
