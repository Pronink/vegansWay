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
    public static int CONFIG_VERSION;
    public static boolean CONFIG_FIND_UPDATES_PERIODICLY;//
    public static boolean CONFIG_ALLOW_METRICS;//
    public static boolean CONFIG_SHOWLOGO;//
    public static boolean CONFIG_MODULE_CRAFTING_RECIPES;
    public static boolean CONFIG_MODULE_HEALING_AND_TAMING;
    public static boolean CONFIG_MODULE_SPIDERS_ENHANCED;
    public static boolean CONFIG_MODULE_ITEMS_RENAMING;
    public static boolean CONFIG_MODULE_MOBS_BLEEDING;
    public static boolean CONFIG_MODULE_BETTER_WORLD;
    public static int CONFIG_OCELOT_TO_CAT_PERCENTAGE;
    public static int CONFIG_SPIDERS_GENERATE_WEB_PERCENTAGE;
    public static int CONFIG_SPIDERS_EXTRA_STRING_DROP;
    public static String CONFIG_CATNIP_NAME;
    public static String CONFIG_FIBERPLANT_NAME;
    public static String CONFIG_WOOL_CAP_NAME;
    public static String CONFIG_WOOL_TUNIC_MAME;
    public static String CONFIG_WOOL_PANTS_NAME;
    public static String CONFIG_WOOL_BOOTS_NAME;
    public static String CONFIG_PUMPKIN_SEED_DRINK_NAME;
    
    public static boolean CONFIG_GENERATE_FIBERPLANT;
    public static int CONFIG_BONEMEAL_ON_FIBERPLANT;
    public static boolean CONFIG_GENERATE_CATNIP;
    public static int CONFIG_BONEMEAL_ON_CATNIP;
    public static int CONFIG_GENERATE_FIBERFLOWER;
    public static int CONFIG_BONEMEAL_ON_CACTUS;
    
    public static void load(Configuration c)
    {
        CONFIG_VERSION = c.getInt("Config_V");
        CONFIG_FIND_UPDATES_PERIODICLY = c.getBoolean("Find_Updates_Periodicly");
	CONFIG_SHOWLOGO = c.getBoolean("Show_Logo");
        CONFIG_ALLOW_METRICS = c.getBoolean("Allow_Metrics");
	CONFIG_MODULE_CRAFTING_RECIPES = c.getBoolean("Crafting_Recipes");
	CONFIG_MODULE_HEALING_AND_TAMING = c.getBoolean("Healing_And_Taming");
	CONFIG_MODULE_SPIDERS_ENHANCED = c.getBoolean("Spiders_Enhanced");
	CONFIG_MODULE_ITEMS_RENAMING = c.getBoolean("Items_Renaming");
        CONFIG_MODULE_MOBS_BLEEDING = c.getBoolean("Mobs_Bleeding");
        CONFIG_MODULE_BETTER_WORLD = c.getBoolean("Better_World");
	CONFIG_CATNIP_NAME = c.getString("Catnip_Name");
	CONFIG_FIBERPLANT_NAME = c.getString("FiberPlant_Name");
	CONFIG_OCELOT_TO_CAT_PERCENTAGE = c.getInt("Ocelote_To_Cat_Percentage");
	CONFIG_SPIDERS_GENERATE_WEB_PERCENTAGE = c.getInt("Spiders_Generate_Web_Percentage");
	CONFIG_SPIDERS_EXTRA_STRING_DROP = c.getInt("Spiders_Extra_String_Drop");
	CONFIG_WOOL_CAP_NAME = c.getString("Wool_Cap_Name");
	CONFIG_WOOL_TUNIC_MAME = c.getString("Wool_Tunic_Name");
	CONFIG_WOOL_PANTS_NAME = c.getString("Wool_Pants_Name");
	CONFIG_WOOL_BOOTS_NAME = c.getString("Wool_Boots_Name");
        CONFIG_PUMPKIN_SEED_DRINK_NAME = c.getString("Pumpkin_Seed_Drink_Name");
        CONFIG_GENERATE_FIBERPLANT = c.getBoolean("Generate_FiberPlant");
        CONFIG_BONEMEAL_ON_FIBERPLANT = c.getInt("BoneMeal_On_FiberPlant");
        CONFIG_GENERATE_CATNIP = c.getBoolean("Generate_Catnip");
        CONFIG_BONEMEAL_ON_CATNIP = c.getInt("BoneMeal_On_Catnip");
        CONFIG_GENERATE_FIBERFLOWER = c.getInt("Generate_FiberFlower");
        CONFIG_BONEMEAL_ON_CACTUS = c.getInt("BoneMeal_On_Cactus");
    }
}
