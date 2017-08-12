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

import java.io.IOException;
import java.util.List;
import java.util.Random;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.SkullType;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Skull;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Skeleton;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.world.ChunkPopulateEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.EulerAngle;
import org.mcstats.Metrics;

/**
 *
 * @author Pronink
 */
public class Main extends JavaPlugin implements Listener
{

    int state = 0;
    CatTaming catTaming;
    ItemRenaming itemRenaming;
    CraftingRecipes craftingRecipes;
    SpidersEnhanced spidersEnhanced;
    LovingPets lovingPets;
    FeedingPets feedingPets;
    MobBleeding mobBleeding;

    @Override
    public void onEnable()
    {
	// INICIAR CONFIGURACIÓN
	saveDefaultConfig(); // Nunca sobreescribe si ya existe algo
	Config.load(getConfig());
	// INICIAR MÓDULOS
	if (Config.CONFIG_MODULE_ITEMS_RENAMING)
	{
	    itemRenaming = new ItemRenaming();
	}
	if (Config.CONFIG_MODULE_CRAFTING_RECIPES)
	{
	    craftingRecipes = new CraftingRecipes();
	    craftingRecipes.addAllCraftingRecipes();
	}
	if (Config.CONFIG_MODULE_SPIDERS_ENHANCED)
	{
	    spidersEnhanced = new SpidersEnhanced();
	}
	if (Config.CONFIG_MODULE_HEALING_AND_TAMING)
	{
	    catTaming = new CatTaming();
	    lovingPets = new LovingPets(); // El main lo llamará cada 1 segundo y tambien cuando dos mascotas se peguen
	    feedingPets = new FeedingPets(lovingPets); // Feedingpets llamará a LovingPets cada vez que se sobrealimenta una mascota
	}
        if (Config.CONFIG_MODULE_MOBS_BLEEDING)
        {
            mobBleeding = new MobBleeding();
        }
	// REGISTRAR EVENTOS, INICIAR EVENTOS TEMPORIZADOS, INICIAR CRAFTEOS
	Bukkit.getServer().getPluginManager().registerEvents(this, this);
	startTimedEvents();

	// MOSTRAR VERSIONES
	getVersionInfo(); // Muestro el logo (si esta habilitado), la version y busco actualizaciones (hilo)
        
        // INICIO METRICS
        try {
            Metrics metrics = new Metrics(this);
            metrics.start();
        } catch (IOException e) {
            // Failed to submit the stats :-(
            printC("Algo falló al iniciar Metrics");
        }
    }

    private void startTimedEvents()
    {
	Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable()
	{
	    @Override
	    public void run() // Añadir aqui los eventos que se ejecutaran cada segundo
	    {
		state = ++state % 120; // EL CICLO DURA 60 SEGUNDOS
		if (state % 2 == 0) // CADA 1 SEGUNDOS
		{
		    if (Config.CONFIG_MODULE_HEALING_AND_TAMING)
		    {
			catTaming.testOcelotTaming();
			lovingPets.testLovingPets();
		    }
		}
	    }
	}, 20 * 1, 20 * 1 / 2); // Cada 1/2 segundos, empezando desde el segundo 1

    }

    @Override
    public void onDisable()
    {

    }

    @EventHandler
    public void onItemSpawn(ItemSpawnEvent event)
    {
	if (Config.CONFIG_MODULE_ITEMS_RENAMING)
	{
	    itemRenaming.modifyItemGround(event);
	}
    }
    
    @EventHandler
    public void onCreatureSpawn(CreatureSpawnEvent event)
    {
        Entity entity = event.getEntity();
        if (entity instanceof Skeleton)
        {
            //Bukkit.broadcastMessage("Ha spawneado un esqueleto hermoso");
            Block block = entity.getLocation().getBlock();
            
            int initX = block.getX()-2, initY = block.getY()-1, initZ = block.getZ()-2;
            int relX, relY, relZ;
            for (int i = 0; i <= 4; i++) {
                relX = initX + i;
                for (int j = 0; j <= 2; j++) {
                    relY = initY + j;
                    for (int k = 0; k <= 4; k++) {
                        relZ = initZ + k;
                        
                        Block newBlock = entity.getWorld().getBlockAt(relX, relY, relZ);
                        if (newBlock.getType().equals(Material.AIR) && newBlock.getRelative(BlockFace.DOWN).getType().equals(Material.GRASS))
                        {
                            Random r = new Random();
                            if (r.nextInt(100) < 25)
                            {
                                newBlock.setType(Material.RED_ROSE);
                                newBlock.setData((byte)3);
                            }
                        }
                        
                    }
                }
            }
            
            
        }
    }
    
    @EventHandler
    public void onChunkPopulate(ChunkPopulateEvent event) {
        Random r = new Random();
        int chunkRandom = r.nextInt(100);
        Biome biome = event.getChunk().getBlock(7, 0, 7).getBiome();
        boolean isDesert = biome.equals(Biome.DESERT) || biome.equals(Biome.DESERT_HILLS) || biome.equals(Biome.MUTATED_DESERT);

        int x = event.getChunk().getBlock(7, 0, 7).getX();
        int z = event.getChunk().getBlock(7, 0, 7).getZ();
        World w = event.getWorld();
        int y = w.getHighestBlockYAt(x, z);

        if (!w.getBlockAt(x, y, z).getRelative(BlockFace.DOWN).getType().equals(Material.STATIONARY_WATER)) {
            int initX = x - 6, initY = y - 14, initZ = z - 6;
            int relX, relY, relZ;
            for (int i = 0; i <= 12; i++) {
                relX = initX + i;
                for (int j = 0; j <= 28; j++) {
                    relY = initY + j;
                    for (int k = 0; k <= 12; k++) {
                        relZ = initZ + k;

                        Block b = w.getBlockAt(relX, relY, relZ);

                        if (!isDesert && chunkRandom < 25 && r.nextInt(100) < 5
                                && b.getType().equals(Material.AIR)) {
                            if (b.getRelative(BlockFace.DOWN).getType().equals(Material.GRASS)) {
                                b.setType(Material.RED_ROSE);
                                b.setData((byte) 3);
                            }
                        }
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
                        
                        
                        if (isDesert && chunkRandom < 75 && r.nextInt(100) < 50
                                && b.getType().equals(Material.AIR)
                                //&& b.getRelative(BlockFace.DOWN).getType().equals(Material.AIR)
                                //&& b.getRelative(BlockFace.DOWN).getRelative(BlockFace.DOWN).getType().equals(Material.AIR)
                                && b.getRelative(BlockFace.DOWN).getRelative(BlockFace.DOWN).getRelative(BlockFace.DOWN).getType().equals(Material.CACTUS)
                                && b.getRelative(BlockFace.DOWN).getRelative(BlockFace.DOWN).getRelative(BlockFace.DOWN).getRelative(BlockFace.DOWN).getType().equals(Material.SAND)) {
                            /*b.setType(Material.WOOL);
                              b.setData((byte) r.nextInt(16));*/
                            
                            b.getRelative(BlockFace.DOWN).setType(Material.CACTUS);
                            b.getRelative(BlockFace.DOWN).getRelative(BlockFace.DOWN).setType(Material.CACTUS);

                            double rdX = r.nextDouble();
                            double rdY = r.nextDouble();
                            double rdZ = r.nextDouble();
                            int color = r.nextInt(16);

                            for (int a = 0; a < 360; a = a + 60) {
                                ArmorStand as = (ArmorStand) w.spawnEntity(new Location(w, relX+0.5f, relY-1.4f, relZ+0.5f, a, a), EntityType.ARMOR_STAND);
                                as.setGravity(false);
                                as.setCollidable(false);
                                as.setAI(false);
                                as.setInvulnerable(true);
                                as.setHeadPose(new EulerAngle(rdX, rdY, rdZ));
                                as.setHelmet(new ItemStack(Material.CARPET, 1, (short) 1, (byte) color));
                                as.setVisible(false);
                                as.setCustomName("vegansWay_CactusFlower");
                                as.setCustomNameVisible(false);
                            }
                            
                            Bukkit.broadcastMessage("Cactus lana en: " + relX + " " + relZ);
                        }
                    }
                }
            }
        }
    }
    
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (event.getBlock().getType().equals(Material.CACTUS)) {
            Location cactusLocation = event.getBlock().getLocation().add(0.5d, 0, 0.5d);
            Entity[] entities = event.getBlock().getChunk().getEntities();
            for (Entity entity : entities) {
                if (entity != null
                        && entity instanceof ArmorStand
                        && entity.getCustomName().equals("vegansWay_CactusFlower")
                        && entity.getLocation().getX() == cactusLocation.getX()
                        && entity.getLocation().getZ() == cactusLocation.getZ()
                        && Math.abs(entity.getLocation().getY() - cactusLocation.getY()) < 3) {
                    Byte color = ((ArmorStand)entity).getHelmet().getData().getData();
                    entity.getWorld().dropItemNaturally(entity.getLocation(), new ItemStack(Material.WOOL, 1, (short) 0, color));
                    entity.remove();
                }
            }
        }
    }
    
    public void generatePineapple(Block b, char bf)
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
    String cactus = "MHF_Cactus";
    
    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event)
    {
	if (Config.CONFIG_MODULE_HEALING_AND_TAMING)
	{
	    catTaming.testConvertToCat(event);
	    lovingPets.testNewDogOrCatBaby(event.getDamager(), event.getEntity());
	}
	if (Config.CONFIG_MODULE_SPIDERS_ENHANCED)
	{
	    spidersEnhanced.testSpiderWebAttack(event);
	}
        if (Config.CONFIG_MODULE_MOBS_BLEEDING)
        {
            mobBleeding.testMobBleeding(event);
        }
    }

    
    
    @EventHandler
    public void onEntityDeath(EntityDeathEvent event)
    {
	if (Config.CONFIG_MODULE_SPIDERS_ENHANCED)
	{
	    spidersEnhanced.addSpiderDrops(event);
	}
    }

    @EventHandler
    public void onRightClick(PlayerInteractAtEntityEvent event)
    {
	if (Config.CONFIG_MODULE_HEALING_AND_TAMING)
	{
	    feedingPets.testPetFeeding(event);
	}
    }

    // MÉTODOS DE LA CONSOLA
    private String getLogo()
    {
	StringBuilder asciiLogo = new StringBuilder("");
	asciiLogo.append(ChatColor.GREEN).append("\n\n");
	asciiLogo.append("                    ynn                                                                                                \n");
	asciiLogo.append(".s+`           -/oyhdN/                                                                                                \n");
	asciiLogo.append("  oNo        -mMmMMMMN`                                                                                                \n");
	asciiLogo.append("   +Md`      ds:dMMMMs                                                                                                 \n");
	asciiLogo.append("    oMm`     .:NMMNh/                                               :+syhddhhysso+++++                `:+sssssyyyo/`   \n");
	asciiLogo.append("     hMd     /N+.                                                 oNh+:-:oso/+ossyMMN-    .+o     `/yys/.`      `-oms  \n");
	asciiLogo.append("     `NM+   /M/                                                  sMo    /.sM`    yMN-  `/dMd`  `+hy/`               s. \n");
	asciiLogo.append("      +MN` :Mo                                                   .ymo/::+yy-    sMM: .smdMd` :yd+`                     \n");
	asciiLogo.append("       NMo.Nm                                        -/             .:::.      oMM:-ym++Mm`/dh:                        \n");
	asciiLogo.append("       oMNdM:  -syyds  `oyyhy:y/ .oyyds/h- `sNhomN/ `NNs`                     /MMshN+ /MNomd-`+yyhd:yo  /d/  /d/       \n");
	asciiLogo.append("       `MMMm .dd: :h+`sN/  `MN+.hm:  -Mm: :mh- .mm.-h+Mm                     -MMMNo` :MMMm/ oNo`  mMo .hm:  hN/        \n");
	asciiLogo.append("        hMM+ NMs/--`:hMy  :my.:mM+ `/Ns./yN/ `sNs+hM  :M/-o/                `mMMy.  .NMNo  /Md  -dd-:oNs` `Ny-/:       \n");
	asciiLogo.append("        /hh` /ydyso+::ydmMNho+-:yhs+hyo+hs`  yhs+::y/+hhs/`                 /yy-    sys`   `shy+yhso+hysdNNho+-        \n");
	asciiLogo.append("                     /+yMy`                                                                          /+hMs`            \n");
	asciiLogo.append("                   .o-hm:                                                                          .o-hm-              \n");
	asciiLogo.append("                   ysm+                                                                            ysm+                \n");
	asciiLogo.append("                    .                                                                              `.                  \n");
	return (asciiLogo.toString());
    }

    private void getVersionInfo()
    {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                String installedVersion = "v" + getDescription().getVersion();
                String latestVersion = Util.getLatestVersionName();
                String text = "";
                if (Config.CONFIG_SHOWLOGO)
                {
                    text = getLogo(); // Obtengo el logo
                }
                if (latestVersion.equals("")) // Si no hay conexion a github
                {
                    printC(text); // Imprime el logo, si este ha sido activado antes
                }
                else if (installedVersion.equals(latestVersion)) // Si esta actualizado
                {
                    printC(text + ChatColor.GREEN + "\nYou use the latest version of VegansWay: " + latestVersion);
                }
                else
                {
                    printC(text + ChatColor.RED + "\nYou do not use the latest version of VegansWay:" + ChatColor.DARK_RED + "\n\tINSTALLED VERSION -> " + installedVersion + "\n\tLATEST VERSION    -> " + latestVersion + ChatColor.RED + "\nDownload the latest version from: " + ChatColor.RESET + "https://www.spigotmc.org/resources/vegansway.40292" + ChatColor.RED + " or " + ChatColor.RESET + "https://github.com/Pronink/vegansWay/releases" + "\n");
                }
            }
        });
        t.start();
    }

    private void printC(String string)
    {
	Bukkit.getConsoleSender().sendMessage(string);
    }

}
