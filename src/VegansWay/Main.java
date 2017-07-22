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
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Bat;
import org.bukkit.entity.CaveSpider;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Cow;
import org.bukkit.entity.Donkey;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Llama;
import org.bukkit.entity.Mule;
import org.bukkit.entity.Ocelot;
import org.bukkit.entity.Parrot;
import org.bukkit.entity.Pig;
import org.bukkit.entity.Player;
import org.bukkit.entity.Rabbit;
import org.bukkit.entity.Sheep;
import org.bukkit.entity.Spider;
import org.bukkit.entity.Squid;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Wolf;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.plugin.java.JavaPlugin;
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
        
        Location location = event.getEntity().getLocation();
        World world = event.getEntity().getWorld();
        Entity entity = event.getEntity();
        if(isFriendlyMob(entity))
        {
            world.playEffect(location, Effect.STEP_SOUND, 214);
            world.playEffect(location, Effect.STEP_SOUND, 233);
            world.playEffect(location, Effect.STEP_SOUND, 152);
        }
        else if(entity instanceof Zombie)
        {
            location = location.add(0, 1, 0);
            world.playEffect(location, Effect.STEP_SOUND, 165);
        }
        else if(entity instanceof Spider || entity instanceof CaveSpider)
        {
            world.playEffect(location, Effect.STEP_SOUND, 165);
        }
    }

    private boolean isFriendlyMob(Entity e)
    {
        if(e instanceof Player || e instanceof Bat || e instanceof Chicken || e instanceof Cow || e instanceof Pig || e instanceof Rabbit || e instanceof Sheep || e instanceof Squid || e instanceof Villager || e instanceof Donkey || e instanceof Horse || e instanceof Llama || e instanceof Mule || e instanceof Ocelot || e instanceof Parrot || e instanceof Wolf)
        {
            return true;
        }
        return false;
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
