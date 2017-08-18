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

/**
 *
 * @author Ismael
 */


public class RecycleBin {
    // Codigo fuente de la generacion de bellotas en los arboles
    /*@EventHandler
    public void onChunkPopulate(ChunkPopulateEvent event)
    {
        int x = event.getChunk().getBlock(7, 0, 7).getX();
        int z = event.getChunk().getBlock(7, 0, 7).getZ();
        World w = event.getWorld();
        int y = w.getHighestBlockYAt(x, z);
        //w.getBlockAt(x, y, z).getRelative(BlockFace.DOWN).setType(Material.TNT);
        if (!w.getBlockAt(x, y, z).getRelative(BlockFace.DOWN).getType().equals(Material.STATIONARY_WATER))
        {
            int initX = x-6, initY = y-14, initZ = z-6;
            int relX, relY, relZ;
            for (int i = 0; i <= 12; i++) {
                relX = initX + i;
                for (int j = 0; j <= 28; j++) {
                    relY = initY + j;
                    for (int k = 0; k <= 12; k++) {
                        relZ = initZ + k;

                        Random r = new Random();
                        if (r.nextBoolean() && r.nextBoolean() && r.nextBoolean())
                        {
                            Block b = w.getBlockAt(relX, relY, relZ);
                            if (b.getType().equals(Material.AIR))
                            {
                                if (b.getRelative(BlockFace.NORTH).getType().equals(Material.LEAVES) || b.getRelative(BlockFace.NORTH).getType().equals(Material.LEAVES_2))
                                {
                                    generatePineapple(b, 'n');
                                }
                                else if (b.getRelative(BlockFace.SOUTH).getType().equals(Material.LEAVES) || b.getRelative(BlockFace.SOUTH).getType().equals(Material.LEAVES_2))
                                {
                                    generatePineapple(b, 's');
                                }
                                else if (b.getRelative(BlockFace.EAST).getType().equals(Material.LEAVES) || b.getRelative(BlockFace.NORTH).getType().equals(Material.LEAVES_2))
                                {
                                    generatePineapple(b, 'e');
                                }
                                else if (b.getRelative(BlockFace.WEST).getType().equals(Material.LEAVES) || b.getRelative(BlockFace.NORTH).getType().equals(Material.LEAVES_2))
                                {
                                    generatePineapple(b, 'w');
                                }
                                else
                                {
                                    b.setType(Material.GLASS);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    public void generatePineapple(Block b, char bf)
    {
        b.setType(Material.SKULL);
        Skull cabeza = (Skull)b.getState();
        cabeza.setSkullType(SkullType.PLAYER);
        cabeza.setOwningPlayer(Bukkit.getOfflinePlayer(pina));
        switch (bf) {
            case 'n':
                cabeza.setRawData((byte)3);
                break;
            case 's':
                cabeza.setRawData((byte)2);
                break;
            case 'e':
                cabeza.setRawData((byte)4);
                break;
            case 'w':
                cabeza.setRawData((byte)5);
                break;
            default:
                break;
        }
        cabeza.update();
    }*/
    
    
    
    // CABEZAS DE CACTUS
    /*public void generatePineapple(Block b, char bf)
    {
        b.setType(Material.SKULL);
        Skull cabeza = (Skull)b.getState();
        cabeza.setSkullType(SkullType.PLAYER);
        cabeza.setOwningPlayer(Bukkit.getOfflinePlayer(cactus));
        switch (bf) {
            case 'n':
                cabeza.setRawData((byte)3);
                break;
            case 's':
                cabeza.setRawData((byte)2);
                break;
            case 'e':
                cabeza.setRawData((byte)4);
                break;
            case 'w':
                cabeza.setRawData((byte)5);
                break;
            default:
                break;
        }
        cabeza.update();
    }

    String manzana = "KylexDavis";
    String pina = "Rocket_Ash";
    String lanaBlanca = "ema";
    String lanaRoja = "wool";
    String lanaGris = "graywool";
    String lanaVerde = "Talia";
    String lanaAmarilla = "TNTniceman";
    String cactus = "MHF_Cactus";*/
    
                        /*if (isDesert && chunkRandom < 50 && r.nextInt(100) < 25
                                && b.getType().equals(Material.AIR)) {
                            if (b.getRelative(BlockFace.NORTH).getType().equals(Material.CACTUS)) {
                                generatePineapple(b, 'n');
                                Bukkit.broadcastMessage("Cactus pequeño en: " + relX + " " + relZ);
                            } else if (b.getRelative(BlockFace.SOUTH).getType().equals(Material.CACTUS)) {
                                generatePineapple(b, 's');
                                Bukkit.broadcastMessage("Cactus pequeño en: " + relX + " " + relZ);
                            } else if (b.getRelative(BlockFace.EAST).getType().equals(Material.CACTUS)) {
                                generatePineapple(b, 'e');
                                Bukkit.broadcastMessage("Cactus pequeño en: " + relX + " " + relZ);
                            } else if (b.getRelative(BlockFace.WEST).getType().equals(Material.CACTUS)) {
                                generatePineapple(b, 'w');
                                Bukkit.broadcastMessage("Cactus pequeño en: " + relX + " " + relZ);
                            }
                        }*/
                        // /summon armor_stand ~ ~ ~ {Invisible:1b,NoBasePlate:1b,NoGravity:1b,Rotation:[120f],ArmorItems:[{},{},{},{id:"171",Count:1b,Damage:1}],HandItems:[{},{}],Pose:{Head:[10f,15f,10f]}}
                        
              
    
    
    // QUE UN BLOQUE QUE SE ROMPE POR FISICAS ROMPA LAS FLORES (AHORA USO MEJOR DETECTAR ENTIDADES CERCANAS CADA CIERTO TIEMPO => findFloatingFlowers() <= )
    /*@EventHandler
    public void onBlockPhysicsEvent(BlockPhysicsEvent event)
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
                if (entity != null
                        && entity instanceof ArmorStand
                        && entity.getCustomName().equals("vegansWay_CactusFlower")
                        && entity.getLocation().getX() == x
                        && entity.getLocation().getZ() == z
                        && Math.abs(entity.getLocation().getY() - y) < 3d)
                {
                    Byte color = ((ArmorStand) entity).getHelmet().getData().getData();
                    if (!entity.isDead())
                    {
                        entity.getWorld().dropItemNaturally(entity.getLocation().add(0, 1.5d, 0), new ItemStack(Material.WOOL, 1, (short) 0, color));
                        entity.getWorld().spawnParticle(Particle.BLOCK_CRACK, entity.getLocation().add(0, 1.5d, 0), 5, 0.2f, 0.2f, 0.2f, 0.001f, new MaterialData(Material.CARPET, color));
                        entity.remove();
                    }
                }
            }
        }
    
    // QUE LOS ESQUELETOS GENEREN FLORES
    /*@EventHandler
    public void onCreatureSpawn(CreatureSpawnEvent event)
    {
        Entity entity = event.getEntity();
        if (entity instanceof Skeleton)
        {
            //Bukkit.broadcastMessage("Ha spawneado un esqueleto hermoso");
            Block block = entity.getLocation().getBlock();

            int initX = block.getX() - 2, initY = block.getY() - 1, initZ = block.getZ() - 2;
            int relX, relY, relZ;
            for (int i = 0; i <= 4; i++)
            {
                relX = initX + i;
                for (int j = 0; j <= 2; j++)
                {
                    relY = initY + j;
                    for (int k = 0; k <= 4; k++)
                    {
                        relZ = initZ + k;

                        Block newBlock = entity.getWorld().getBlockAt(relX, relY, relZ);
                        if (newBlock.getType().equals(Material.AIR) && newBlock.getRelative(BlockFace.DOWN).getType().equals(Material.GRASS))
                        {
                            Random r = new Random();
                            if (r.nextInt(100) < 25)
                            {
                                newBlock.setType(Material.RED_ROSE);
                                newBlock.setData((byte) 3);
                            }
                        }
                    }
                }
            }
        }
    }
    }*/
}
