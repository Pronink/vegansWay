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
    public static boolean CONFIG_MODULE_MOBS_BLEEDING;
    public static boolean CONFIG_SHOWLOGO;
    public static int CONFIG_OCELOT_TO_CAT_PERCENTAGE;
    public static int CONFIG_SPIDERS_GENERATE_WEB_PERCENTAGE;
    public static int CONFIG_SPIDERS_EXTRA_STRING_DROP;
    public static String CONFIG_CATNIP_NAME;
    public static String CONFIG_WOOLPLANT_NAME;
    public static String CONFIG_WOOL_CAP_NAME;
    public static String CONFIG_WOOL_TUNIC_MAME;
    public static String CONFIG_WOOL_PANTS_NAME;
    public static String CONFIG_WOOL_BOOTS_NAME;
    public static String CONFIG_PUMPKIN_SEED_DRINK_NAME;
    
    public static void load(Configuration c)
    {
	CONFIG_MODULE_CRAFTING_RECIPES = c.getBoolean("Crafting_Recipes");
	CONFIG_MODULE_HEALING_AND_TAMING = c.getBoolean("Healing_And_Taming");
	CONFIG_MODULE_SPIDERS_ENHANCED = c.getBoolean("Spiders_Enhanced");
	CONFIG_MODULE_ITEMS_RENAMING = c.getBoolean("Items_Renaming");
        CONFIG_MODULE_MOBS_BLEEDING = c.getBoolean("Mobs_Bleeding");
	CONFIG_CATNIP_NAME = c.getString("Catnip_Name");
	CONFIG_WOOLPLANT_NAME = c.getString("WoolPlant_Name");
	CONFIG_SHOWLOGO = c.getBoolean("Show_Logo");
	CONFIG_OCELOT_TO_CAT_PERCENTAGE = c.getInt("Ocelote_To_Cat_Percentage");
	CONFIG_SPIDERS_GENERATE_WEB_PERCENTAGE = c.getInt("Spiders_Generate_Web_Percentage");
	CONFIG_SPIDERS_EXTRA_STRING_DROP = c.getInt("Spiders_Extra_String_Drop");
	CONFIG_WOOL_CAP_NAME = c.getString("Wool_Cap_Name");
	CONFIG_WOOL_TUNIC_MAME = c.getString("Wool_Tunic_Name");
	CONFIG_WOOL_PANTS_NAME = c.getString("Wool_Pants_Name");
	CONFIG_WOOL_BOOTS_NAME = c.getString("Wool_Boots_Name");
        CONFIG_PUMPKIN_SEED_DRINK_NAME = c.getString("Pumpkin_Seed_Drink_Name");
    }
}
