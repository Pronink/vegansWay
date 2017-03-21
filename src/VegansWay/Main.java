/*
 * The MIT License
 *
 * Copyright 2017 Pronink.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package VegansWay;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author Pronink
 */
// TODO: Hacer que los perros y gatos se enamoren cuando los alimentas
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
	    lovingPets = new LovingPets();
	    feedingPets = new FeedingPets(lovingPets);
	}
	// REGISTRAR EVENTOS, INICIAR EVENTOS TEMPORIZADOS, INICIAR CRAFTEOS
	Bukkit.getServer().getPluginManager().registerEvents(this, this);
	startTimedEvents();

	// MISCELANEA
	if (Config.CONFIG_SHOWLOGO)
	{
	    showLogo();
	}
	CatTaming.removeInvulnerableChickens();
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
		if (state % 6 == 0) // CADA 3 SEGUNDOS
		{
		    CatTaming.removeInvulnerableChickens();
		}
	    }
	}, 20 * 1, 20 * 1 / 2); // Cada 1/2 segundos, empezando desde el segundo 1

    }

    @Override
    public void onDisable()
    {
	CatTaming.removeInvulnerableChickens();
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
	    lovingPets.testNewDogOrCatBaby(event.getDamager(), event.getEntity());
	}
	if (Config.CONFIG_MODULE_SPIDERS_ENHANCED)
	{
	    spidersEnhanced.testSpiderWebAttack(event);
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

    private void showLogo()
    {
	StringBuilder asciiLogo = new StringBuilder("");
	asciiLogo.append(ChatColor.GREEN).append("\n");
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
	asciiLogo.append("                    .                                                                              `.                  \n\n");
	Bukkit.getConsoleSender().sendMessage(asciiLogo.toString());
    }

}
