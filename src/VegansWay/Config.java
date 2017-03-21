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
