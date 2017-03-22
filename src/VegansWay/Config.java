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

import org.bukkit.configuration.Configuration;

/**
 *
 * @author Pronink
 */
public class Config
{
    public static boolean CONFIG_MODULE_CRAFTING_RECIPES;
    public static boolean CONFIG_MODULE_HEALING_AND_TAMING;
    public static boolean CONFIG_MODULE_SPIDERS_ENHANCED;
    public static boolean CONFIG_MODULE_ITEMS_RENAMING;
    public static String CONFIG_CATNIP_NAME;
    public static String CONFIG_COTTONPLANT_NAME;
    public static boolean CONFIG_SHOWLOGO;
    
    public static void load(Configuration c)
    {
	CONFIG_MODULE_CRAFTING_RECIPES = c.getBoolean("Crafting_Recipes");
	CONFIG_MODULE_HEALING_AND_TAMING = c.getBoolean("Healing_And_Taming");
	CONFIG_MODULE_SPIDERS_ENHANCED = c.getBoolean("Spiders_Enhanced");
	CONFIG_MODULE_ITEMS_RENAMING = c.getBoolean("Items_Renaming");
	CONFIG_CATNIP_NAME = c.getString("Catnip_Name");
	CONFIG_COTTONPLANT_NAME = c.getString("CottonPlant_Name");
	CONFIG_SHOWLOGO = c.getBoolean("Show_Logo");
	// TODO: Testear que si algo me llega NULL, vuelva a generar el archivo
    }
}
