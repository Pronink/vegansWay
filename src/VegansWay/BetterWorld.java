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
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import org.bukkit.event.player.PlayerInteractEvent;
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
    public void modifyGeneration(ChunkPopulateEvent event)
    {
        Random r = new Random();
        int chunkRandom = r.nextInt(100); // No en todos los chunks se van a generar cosas
        Biome biome = event.getChunk().getBlock(7, 0, 7).getBiome();
        boolean isDesert = biome.equals(Biome.DESERT) || biome.equals(Biome.DESERT_HILLS) || biome.equals(Biome.MUTATED_DESERT);
        boolean isJungle = biome.equals(Biome.JUNGLE) || biome.equals(Biome.JUNGLE_HILLS) || biome.equals(Biome.JUNGLE_EDGE) || biome.equals(Biome.MUTATED_JUNGLE) || biome.equals(Biome.MUTATED_JUNGLE_EDGE);

        int x = event.getChunk().getBlock(7, 0, 7).getX();
        int z = event.getChunk().getBlock(7, 0, 7).getZ();
        World world = event.getWorld();
        int y = world.getHighestBlockYAt(x, z);

        // Recorrer la zona centro de cada chunk
        if (!world.getBlockAt(x, y, z).getRelative(BlockFace.DOWN).getType().equals(Material.STATIONARY_WATER)) // Si no es océano
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

                        Block block = world.getBlockAt(relX, relY, relZ); // Este bloque recorre la zona (cuadrado de 12x28x12) de la superficie de cada chunk

                        // GENERACIÓN EN TODO
                        if (Config.CONFIG_GENERATE_FIBERPLANT
                                && !isDesert && !isJungle
                                && chunkRandom < 15 && r.nextInt(100) < 5
                                && block.getType().equals(Material.AIR)
                                && block.getRelative(BlockFace.DOWN).getType().equals(Material.GRASS))
                        {
                            makeFiberPlant(block);
                        }

                        // GENERACIÓN EN JUNGLA
                        if (Config.CONFIG_GENERATE_CATNIP
                                && isJungle
                                && chunkRandom < 30 && r.nextInt(100) < 10
                                && (block.getType().equals(Material.AIR))
                                && block.getRelative(BlockFace.DOWN).getType().equals(Material.GRASS))
                        {
                            makeCatnip(block);
                        }

                        // GENERACIÓN EN DESIERTOS
                        if (Config.CONFIG_GENERATE_FIBERFLOWER > 0
                                && isDesert
                                && chunkRandom < 75 && r.nextInt(100) < 50
                                && block.getType().equals(Material.AIR)
                                && block.getRelative(BlockFace.DOWN).getRelative(BlockFace.DOWN).getRelative(BlockFace.DOWN).getType().equals(Material.CACTUS)
                                && block.getRelative(BlockFace.DOWN).getRelative(BlockFace.DOWN).getRelative(BlockFace.DOWN).getRelative(BlockFace.DOWN).getType().equals(Material.SAND))
                        {

                            block.getRelative(BlockFace.DOWN).setType(Material.CACTUS);
                            block.getRelative(BlockFace.DOWN).getRelative(BlockFace.DOWN).setType(Material.CACTUS);
                            double flowerX = relX + 0.5d;
                            double flowerY = relY - 1.4d;
                            double flowerZ = relZ + 0.5d;

                            // Solo crece en cactus completamente crecidos. Aqui el porcentaje siempre sera 100%
                            makeCactusFlower(world, flowerX, flowerY, flowerZ);
                        }
                    }
                }
            }
        }
    }

    public void testCactusGrow(BlockGrowEvent event)
    {
        Random random = new Random();
        if (random.nextInt(100) < Config.CONFIG_GENERATE_FIBERFLOWER)
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
    }

    // MÉTODOS QUE DETECTAN SI SE DEBE ROMPER UNA FLOR
    public void findFloatingFlowers()
    {
        for (Player player : Bukkit.getOnlinePlayers())
        {
            for (Entity entity : player.getNearbyEntities(10, 10, 10))
            {
                if (isCactusFlower(entity)
                        && !entity.getLocation().getBlock().getRelative(BlockFace.UP).getType().equals(Material.CACTUS))
                {
                    breakCactusFlower((ArmorStand) entity);
                }
            }
        }
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

    public void testFertilize(PlayerInteractEvent event) {
        if (isBoneMeal(event.getItem())) {
            Block clickedBlock = event.getClickedBlock();
            World world = clickedBlock.getWorld();
            Random random = new Random();
            // SI ES CACTUS
            if (clickedBlock.getType().equals(Material.CACTUS)) {
                // Prepara algunas cosas
                double flowerX = clickedBlock.getX() + 0.5d;
                double flowerY = clickedBlock.getY() - 0.4d;
                double flowerZ = clickedBlock.getZ() + 0.5d;
                generateGrowParticle(clickedBlock);
                Util.quitOneItemFromHand(event.getPlayer());
                // Hacer config para los porcentajes de crecer cactus de manera natural y cuando le das con bonemeal
                // Hacer que se puedan cambiar los colores de la lana en la mesa de crafteo
                if (random.nextInt(100) < Config.CONFIG_BONEMEAL_ON_CACTUS)
                {
                    // Caso SAND-(CACTUS)-AIR
                    if (clickedBlock.getRelative(BlockFace.DOWN).getType().equals(Material.SAND)
                            && clickedBlock.getRelative(BlockFace.UP).getType().equals(Material.AIR)) 
                    {
                        clickedBlock.getRelative(BlockFace.UP).setType(Material.CACTUS);
                    } // Caso SAND-(CACTUS)-CACTUS-AIR
                    else if (clickedBlock.getRelative(BlockFace.DOWN).getType().equals(Material.SAND)
                            && clickedBlock.getRelative(BlockFace.UP).getType().equals(Material.CACTUS)
                            && clickedBlock.getRelative(BlockFace.UP).getRelative(BlockFace.UP).getType().equals(Material.AIR)) 
                    {
                        clickedBlock.getRelative(BlockFace.UP).getRelative(BlockFace.UP).setType(Material.CACTUS);
                        if (random.nextInt(100) < Config.CONFIG_GENERATE_FIBERFLOWER)
                            makeCactusFlower(clickedBlock.getWorld(), flowerX, flowerY + 2f, flowerZ);
                    } 
                    // Caso SAND-CACTUS-(CACTUS)-AIR
                    else if (clickedBlock.getRelative(BlockFace.DOWN).getType().equals(Material.CACTUS)
                            && clickedBlock.getRelative(BlockFace.DOWN).getRelative(BlockFace.DOWN).getType().equals(Material.SAND)
                            && clickedBlock.getRelative(BlockFace.UP).getType().equals(Material.AIR)) 
                    {
                        clickedBlock.getRelative(BlockFace.UP).setType(Material.CACTUS);
                        if (random.nextInt(100) < Config.CONFIG_GENERATE_FIBERFLOWER)
                            makeCactusFlower(clickedBlock.getWorld(), flowerX, flowerY + 1f, flowerZ);
                    } 
                    // Caso SAND-(CACTUS)-CACTUS-CACTUS-AIR
                    else if (clickedBlock.getRelative(BlockFace.DOWN).getType().equals(Material.SAND)
                            && clickedBlock.getRelative(BlockFace.UP).getType().equals(Material.CACTUS)
                            && clickedBlock.getRelative(BlockFace.UP).getRelative(BlockFace.UP).getType().equals(Material.CACTUS)
                            && clickedBlock.getRelative(BlockFace.UP).getRelative(BlockFace.UP).getRelative(BlockFace.UP).getType().equals(Material.AIR)) 
                    {
                        if (random.nextInt(100) < Config.CONFIG_GENERATE_FIBERFLOWER)
                            makeCactusFlower(clickedBlock.getWorld(), flowerX, flowerY + 2f, flowerZ);
                    } 
                    // Caso SAND-CACTUS-(CACTUS)-CACTUS-AIR
                    else if (clickedBlock.getRelative(BlockFace.DOWN).getRelative(BlockFace.DOWN).getType().equals(Material.SAND)
                            && clickedBlock.getRelative(BlockFace.DOWN).getType().equals(Material.CACTUS)
                            && clickedBlock.getRelative(BlockFace.UP).getType().equals(Material.CACTUS)
                            && clickedBlock.getRelative(BlockFace.UP).getRelative(BlockFace.UP).getType().equals(Material.AIR)) 
                    {
                        if (random.nextInt(100) < Config.CONFIG_GENERATE_FIBERFLOWER)
                            makeCactusFlower(clickedBlock.getWorld(), flowerX, flowerY + 1f, flowerZ);
                    } 
                    // Caso SAND-CACTUS-CACTUS-(CACTUS)-AIR
                    else if (clickedBlock.getRelative(BlockFace.DOWN).getRelative(BlockFace.DOWN).getRelative(BlockFace.DOWN).getType().equals(Material.SAND)
                            && clickedBlock.getRelative(BlockFace.DOWN).getRelative(BlockFace.DOWN).getType().equals(Material.CACTUS)
                            && clickedBlock.getRelative(BlockFace.DOWN).getType().equals(Material.CACTUS)
                            && clickedBlock.getRelative(BlockFace.UP).getType().equals(Material.AIR)) 
                    {
                        if (random.nextInt(100) < Config.CONFIG_GENERATE_FIBERFLOWER)
                            makeCactusFlower(clickedBlock.getWorld(), flowerX, flowerY, flowerZ);
                    }
                }
            }
            // Si es fiber plant... o nepeta cataria...
            if (clickedBlock.getType().equals(Material.RED_ROSE) && (clickedBlock.getData() == (byte)3 || clickedBlock.getData() == (byte)7)) {
                int initX = clickedBlock.getX()-2, initY = clickedBlock.getY()-2, initZ = clickedBlock.getZ()-2;
                int relX, relY, relZ;
                ArrayList<Block> places = new ArrayList();
                for (int i = 0; i <= 4; i++)
                {
                    relX = initX + i;
                    for (int j = 0; j <= 4; j++)
                    {
                        relY = initY + j;
                        for (int k = 0; k <= 4; k++)
                        {
                            relZ = initZ + k;
                            Block relBlock = world.getBlockAt(relX, relY, relZ);
                            if (relBlock.getType().equals(Material.AIR) && relBlock.getRelative(BlockFace.DOWN).getType().equals(Material.GRASS))
                            {
                                places.add(relBlock);
                            }
                        }
                    }
                }
                if (places.size() > 0)
                {
                    int flowerType = clickedBlock.getData();
                    int numberNewFlowers = flowerType == 3 ? Config.CONFIG_BONEMEAL_ON_FIBERPLANT : Config.CONFIG_BONEMEAL_ON_CATNIP; // Si agrego nuevas plantas este trozo no funcionará
                    for (int i = 0; i < numberNewFlowers; i++)
                    {
                        Block selectedPlace = places.get(random.nextInt(places.size()));
                        selectedPlace.setType(Material.RED_ROSE);
                        selectedPlace.setData((byte)flowerType);

                        generateLineOfParticles(clickedBlock, selectedPlace);
                    }
                }
                Util.quitOneItemFromHand(event.getPlayer());
            }
        }
    }

    // MÉTODOS AYUDANTES
    private void makeCactusFlower(World world, double flowerX, double flowerY, double flowerZ)
    {
        boolean existsCactusFlower = false;
        for (Entity entity : world.getNearbyEntities(new Location(world, flowerX, flowerY, flowerZ), 0.5f, 0.5f, 0.5f)) 
        {
            if (isCactusFlower(entity)) {
                existsCactusFlower = true;
            }
        }
        if (!existsCactusFlower)
        {
            Random r = new Random();
            double randomAngleX = r.nextDouble();
            double randomAngleY = r.nextDouble();
            double randomAngleZ = r.nextDouble();
            int color = r.nextInt(16);

            if (r.nextInt(200) < 1)// Easter egg, muy raro 0.5%
            {
                byte rainbow[] = {14, 1, 4, 5, 3, 10};
                int counter = 0;
                for (int a = 0; a < 360; a = a + 60)
                {
                    ArmorStand as = (ArmorStand) world.spawnEntity(new Location(world, flowerX, flowerY, flowerZ, a, a), EntityType.ARMOR_STAND);
                    as = setInvulnerableArmorStand(as);
                    as.setHeadPose(new EulerAngle(randomAngleX, randomAngleY, randomAngleZ));
                    as.setHelmet(new ItemStack(Material.CARPET, 1, (short) 1, (byte) rainbow[counter]));
                    counter++;
                }
            }
            else // Lo que ocurre normalmente
            {
                for (int a = 0; a < 360; a = a + 60)
                {
                    ArmorStand as = (ArmorStand) world.spawnEntity(new Location(world, flowerX, flowerY, flowerZ, a, a), EntityType.ARMOR_STAND);
                    as = setInvulnerableArmorStand(as);
                    as.setHeadPose(new EulerAngle(randomAngleX, randomAngleY, randomAngleZ));
                    as.setHelmet(new ItemStack(Material.CARPET, 1, (short) 1, (byte) color));
                }
                world.spawnParticle(Particle.BLOCK_CRACK, new Location(world, flowerX, flowerY, flowerZ).add(0, 2f, 0), 5, 0.2f, 0.2f, 0.2f, 0.001f, new MaterialData(Material.CARPET, (byte)color));
            }
        }
    }

    private ArmorStand setInvulnerableArmorStand(ArmorStand as)
    {
        as.setGravity(false);
        as.setCollidable(false);
        as.setAI(false);
        as.setInvulnerable(true);
        as.setVisible(false);
        as.setCustomName("vegansWay_CactusFlower");
        as.setCustomNameVisible(false);
        return as;
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

    private void makeCatnip(Block block)
    {
        block.setType(Material.RED_ROSE);
        block.setData((byte) 7);
    }

    private boolean isBoneMeal(ItemStack item)
    {
        return (item.getType().equals(Material.INK_SACK) && item.getDurability() == 15);
    }
    
    private void generateGrowParticle(Block block)
    {
        block.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, block.getLocation().add(0.5d, 0.5d, 0.5d), 20, 0.4f, 0.4f, 0.4f);
    }

    private void generateLineOfParticles(Block block, Block selectedPlace)
    {
        final double x1 = block.getX()+0.5d;
        final double y1 = block.getY()+0.1d;
        final double z1 = block.getZ()+0.5d;
        final double x2 = selectedPlace.getX()+0.5d;
        final double y2 = selectedPlace.getY()+0.1d;
        final double z2 = selectedPlace.getZ()+0.5d;
        final double modx = ((double)x2 - (double)x1)/10d;
        final double mody = ((double)y2 - (double)y1)/10d;
        final double modz = ((double)z2 - (double)z1)/10d;
        Thread thread = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                double x = x1;
                double y = y1;
                double z = z1;
                for (int i = 0; i < 11; i++)
                {
                    block.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, new Location(block.getWorld(),x,y,z), 1, 0d, 0d, 0d);
                    x += modx;
                    y += mody;
                    z += modz;
                    try
                    {
                        Thread.sleep(20);
                    }
                    catch (InterruptedException ex)
                    {
                        Logger.getLogger(BetterWorld.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                generateGrowParticle(selectedPlace);
            }
        });
        thread.start();
    }
}
