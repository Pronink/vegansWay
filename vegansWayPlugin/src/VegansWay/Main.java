/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VegansWay;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author Pronink
 */
public class Main extends JavaPlugin implements Listener
{

    int state = 0;
    CatTaming catTaming;

    @Override
    public void onEnable()
    {
	getServer().getPluginManager().registerEvents(this, this);
	catTaming = new CatTaming();
	startTimedEvents();
    }

    private void startTimedEvents()
    {
	Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable()
	{
	    @Override
	    public void run() // Añadir aqui los eventos que se ejecutaran cada segundo
	    {
		state = ++state % 120; // EL CICLO DURA 60 SEGUNDOS
		if (state % 1 == 0) // CADA 0.5 SEGUNDOS
		{
		    catTaming.testOcelotFeeding();
		}
		if (state % 6 == 0) // CADA 3 SEGUNDOS
		{
		    System.out.println("Removing chickens... Cause: Player logout");
		    catTaming.removeInvulnerableChickens();
		}
	    }
	}, 20 * 1, 20 * 1 / 2); // Cada 1/2 segundos, empezando desde el segundo 1
    }

    @Override
    public void onDisable()
    {

    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e)
    {
	//Player p = e.getPlayer();
	Thread thread = new Thread(new Runnable()
	{
	    @Override
	    public void run()
	    {
		try
		{
		    Thread.sleep(1000);
		}
		catch (InterruptedException ex)
		{
		    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
		}
		catTaming.removeInvulnerableChickens();
	    }
	});
    }
}
