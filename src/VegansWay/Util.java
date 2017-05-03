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

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonValue;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author Pronink
 */
public class Util
{

    public static void quitOneItemFromHand(Player player)
    {
	ItemStack itemStack = player.getInventory().getItemInMainHand();
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
    }

    public static Location getMiddlePoint(Location l1, Location l2)
    {
	int x1 = l1.getBlockX();
	int x2 = l2.getBlockX();
	int y1 = l1.getBlockY();
	int y2 = l2.getBlockY();
	int z1 = l1.getBlockZ();
	int z2 = l2.getBlockZ();

	int x3 = (x1 + x2) / 2;
	int y3 = (y1 + y2) / 2;
	int z3 = (z1 + z2) / 2;

	return new Location(l1.getWorld(), x3, y3, z3);
    }

    public static String mergeNames(String s1, String s2)
    {
	char mid1 = s1.toLowerCase().charAt((s1.length() - 1) / 2);
	boolean mid1vocal = isVocal(mid1);

	int i = (s2.length() - 1) / 2;
	while (i < 100)
	{
	    char mid2 = s2.toLowerCase().charAt(i);
	    boolean mid2vocal = isVocal(mid2);
	    System.out.println(mid1 + " " + mid2);
	    if (mid1vocal != mid2vocal)
	    {
		// System.out.println(s1.substring(0, ((s1.length() - 1) / 2) + 1) + s2.substring(i));
		return s1.substring(0, ((s1.length() - 1) / 2) + 1) + s2.substring(i);
	    }
	    /*else Muestra los nombres que rechaza (si las dos son minusculas o las dos son mayusculas)
	    {
		System.out.println(s1.substring(0, ((s1.length() - 1) / 2) + 1) + s2.substring(i) + " <- BAD, Testing more");
	    }*/
	    if (i < s2.length() - 1)
	    {
		i++;
	    }
	    else
	    {
		break;
	    }
	}
	// Mezcla los nombres partiendolos por la mitad, salga lo que salga
	return s1.substring(0, s1.length() / 2) + s2.substring(s2.length() / 2);
    }

    private static boolean isVocal(char ch)
    {
	return (ch == 'a' || ch == 'e' || ch == 'i' || ch == 'o' || ch == 'u');
    }

    public static String getLatestVersionName()
    {
	try
	{
	    URL oracle = new URL("https://api.github.com/repos/Pronink/vegansWay/releases");
	    BufferedReader in = new BufferedReader(
		    new InputStreamReader(oracle.openStream()));

	    String inputLine;
	    StringBuffer sb = new StringBuffer("");
	    while ((inputLine = in.readLine()) != null)
	    {
		sb.append(inputLine);
	    }
	    in.close();
	    //System.out.println(sb);
	    String json = sb.toString();
	    JsonArray items = Json.parse(json).asArray();
	    for (JsonValue item : items)
	    {
		String name = item.asObject().getString("tag_name", "");
		return name; // Retorno el primero, ya que es la ultima version
	    }
	}
	catch (Exception e)
	{
	    //System.out.println("No hay conexion a internet?");
	    return "";
	}
	return "";
    }
}
