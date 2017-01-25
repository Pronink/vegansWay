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

import java.util.ArrayList;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import jdk.nashorn.internal.runtime.regexp.joni.constants.RegexState;
import org.bukkit.entity.Entity;
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

	private Wolf entityDog;
	private boolean lovingNow;
	private boolean breakTime;

	public LovingDog(Wolf entityDog)
	{
	    this.entityDog = entityDog;
	}

	public Wolf getEntityDog()
	{
	    return entityDog;
	}

	public void setEntityDog(Wolf entityDog)
	{
	    this.entityDog = entityDog;
	}

	public boolean isLovingNow()
	{
	    return lovingNow;
	}

	public void setLovingNow(boolean lovingNow)
	{
	    this.lovingNow = lovingNow;
	}

	public boolean isBreakTime()
	{
	    return breakTime;
	}

	public void setBreakTime(boolean breakTime)
	{
	    this.breakTime = breakTime;
	}

	public boolean isSitting()
	{
	    return entityDog.isSitting();
	}

	public UUID getUniqueId()
	{
	    return entityDog.getUniqueId();
	}
    }

    private class LovingCat
    {

	private Ocelot entityCat;
	private boolean lovingNow;
	private boolean breakTime;

	public LovingCat(Ocelot entityCat)
	{
	    this.entityCat = entityCat;
	}

	public Ocelot getEntityCat()
	{
	    return entityCat;
	}

	public void setEntityCat(Ocelot entityCat)
	{
	    this.entityCat = entityCat;
	}

	public boolean isLovingNow()
	{
	    return lovingNow;
	}

	public void setLovingNow(boolean lovingNow)
	{
	    this.lovingNow = lovingNow;
	}

	public boolean isBreakTime()
	{
	    return breakTime;
	}

	public void setBreakTime(boolean breakTime)
	{
	    this.breakTime = breakTime;
	}

	public boolean isSitting()
	{
	    return entityCat.isSitting();
	}

	public UUID getUniqueId()
	{
	    return entityCat.getUniqueId();
	}
    }

    private ArrayList<LovingDog> dogList;
    private ArrayList<LovingCat> catList;

    public LovingPets()
    {
	this.dogList = new ArrayList<>();
	this.catList = new ArrayList<>();
    }

    public void addPet(Entity entity)
    {
	if (entity instanceof Wolf)
	{
	    Wolf myDog = (Wolf) entity;
	    if (myDog.isTamed())
	    {
		UUID uuidDog = myDog.getUniqueId();
		for (LovingDog ld : dogList)
		{
		    if (uuidDog.compareTo(ld.getUniqueId()) == 0)
		    {
			return; // Si lo encuentro en la lista, significa que ya esta amando
		    }
		}
		tryDogLoving(myDog);
	    }
	}
	else if (entity instanceof Ocelot)
	{
	    Ocelot myCat = (Ocelot) entity;
	    if (myCat.isTamed())
	    {
		UUID uuidCat = myCat.getUniqueId();
		for (LovingCat lc : catList)
		{
		    if (uuidCat.compareTo(lc.getUniqueId()) == 0)
		    {
			return; // Si lo encuentro en la lista, significa que ya esta amando
		    }
		}
		tryCatLoving(myCat);
	    }
	}
    }

    /* Los métodos try[Dog/Cat]Loving agregan una entidad a su lista durante 10 segundos y compara a miembros de
    su lista cada segundo. Solo entran los miembros que ya no existan dentro de la lista */
    private void tryDogLoving(Wolf myDog)
    {
	Thread thread = new Thread(new Runnable()
	{
	    @Override
	    public void run()
	    {
		try
		{
		    int SEARCHING = 0, LOVING = 1, FINAL = 2;
		    int state = SEARCHING;
		    while (state != FINAL)
		    {
			if (state == SEARCHING)
			{
			    // WIP
			    // TODO: Buscar perros cerca y si existe pasar al estado LOVING
			}
			if (state == LOVING)
			{
			    // TODO: Si estan suficientemente cerca, generar un perrito y pasar el estado a FINAL
			}
			Thread.sleep(1000);
		    }
		}
		catch (InterruptedException ex)
		{
		    Logger.getLogger(LovingPets.class.getName()).log(Level.SEVERE, null, ex);
		}
	    }
	});
	thread.start();
    }

    private void tryCatLoving(Ocelot myCat)
    {
	Thread thread = new Thread(new Runnable()
	{
	    @Override
	    public void run()
	    {
		// TODO: Copiar de tryDogLoving pero con el gato
	    }
	});
	thread.start();
    }

}
