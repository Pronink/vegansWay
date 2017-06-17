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

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.material.MaterialData;

/**
 *
 * @author Pronink
 */
public class CraftingRecipes
{

    public void addAllCraftingRecipes()
    {
	ShapedRecipe shapedRecipe; // Con orden
	ShapelessRecipe shapelessRecipe; // Sin orden

	// Planta de algodon -> Lana
	MaterialData azureBluet = new ItemStack(Material.RED_ROSE, 1, (short) 3).getData();
	ItemStack wool = new ItemStack(Material.WOOL);
	shapedRecipe = new ShapedRecipe(wool);
	shapedRecipe.shape("aa", "aa");
	shapedRecipe.setIngredient('a', azureBluet);
	Bukkit.getServer().addRecipe(shapedRecipe);

	// Semillas de calabaza -> Leche de calabaza
	MaterialData bucket = new ItemStack(Material.BUCKET).getData();
	MaterialData pumpkinSeeds = new ItemStack(Material.PUMPKIN_SEEDS).getData();
	ItemStack milkBucket = new ItemStack(Material.MILK_BUCKET);
        if (Config.CONFIG_MODULE_ITEMS_RENAMING)
        {
            milkBucket = ItemRenaming.changeName(milkBucket, Config.CONFIG_PUMPKIN_SEED_DRINK_NAME);
        }
	shapelessRecipe = new ShapelessRecipe(milkBucket);
	shapelessRecipe.addIngredient(bucket);
	shapelessRecipe.addIngredient(pumpkinSeeds);
	Bukkit.getServer().addRecipe(shapelessRecipe);

	// Carbon -> Tinta negra
	MaterialData coal = new ItemStack(Material.COAL).getData();
	ItemStack inksac = new ItemStack(Material.INK_SACK);
	shapedRecipe = new ShapedRecipe(inksac);
	shapedRecipe.shape("c");
	shapedRecipe.setIngredient('c', coal);
	Bukkit.getServer().addRecipe(shapedRecipe);

	// Carbon vegetal -> Tinta negra
	MaterialData charcoal = new ItemStack(Material.COAL, 1, (short) 1).getData();
	shapedRecipe = new ShapedRecipe(inksac);
	shapedRecipe.shape("c");
	shapedRecipe.setIngredient('c', charcoal);
	Bukkit.getServer().addRecipe(shapedRecipe);

	// Full papel -> Libro
	MaterialData paper = new ItemStack(Material.PAPER).getData();
	ItemStack book = new ItemStack(Material.BOOK);
	shapedRecipe = new ShapedRecipe(book);
	shapedRecipe.shape("ppp", "ppp", "ppp");
	shapedRecipe.setIngredient('p', paper);
	Bukkit.getServer().addRecipe(shapedRecipe);

	// Palo + Papel -> Pluma
	MaterialData stick = new ItemStack(Material.STICK).getData();
	ItemStack feather = new ItemStack(Material.FEATHER);
	shapelessRecipe = new ShapelessRecipe(feather);
	shapelessRecipe.addIngredient(stick);
	shapelessRecipe.addIngredient(paper);
	Bukkit.getServer().addRecipe(shapelessRecipe);

	// Lana/Algodon -> Armadura de cuero
	Color[] colorList =
	{
	    Color.fromRGB(0xDDDDDD), Color.fromRGB(0xDB7D3E), Color.fromRGB(0xB350BC), Color.fromRGB(0x6B8AC9), Color.fromRGB(0xB1A627), Color.fromRGB(0x41AE38), Color.fromRGB(0xD08499), Color.fromRGB(0x404040), Color.fromRGB(0x9AA1A1), Color.fromRGB(0x2E6E89), Color.fromRGB(0x7E3DB5), Color.fromRGB(0x2E388D), Color.fromRGB(0x4F321F), Color.fromRGB(0x35461B), Color.fromRGB(0x963430), Color.fromRGB(0x191616)
	};
	for (int i = 0; i < 16; i++)
	{
	    // Crear lana dependiendo de i (i es el numero de color de la lana)
	    ItemStack coloredWool = new ItemStack(Material.WOOL, 1, (byte) i);
	    MaterialData mwool = coloredWool.getData();

	    // Crear armadura dependiendo de i (i corresponde a la lista creada arriba)
	    ItemStack lhelmet = changeLeatherColor(new ItemStack(Material.LEATHER_HELMET), colorList[i]);
	    ItemStack lchestplate = changeLeatherColor(new ItemStack(Material.LEATHER_CHESTPLATE), colorList[i]);
	    ItemStack lleggings = changeLeatherColor(new ItemStack(Material.LEATHER_LEGGINGS), colorList[i]);
	    ItemStack lboots = changeLeatherColor(new ItemStack(Material.LEATHER_BOOTS), colorList[i]);
	    
	    if (Config.CONFIG_MODULE_ITEMS_RENAMING) // Cambio de nombre
	    {
		lhelmet = ItemRenaming.changeName(lhelmet, Config.CONFIG_WOOL_CAP_NAME);
		lchestplate = ItemRenaming.changeName(lchestplate, Config.CONFIG_WOOL_TUNIC_MAME);
		lleggings = ItemRenaming.changeName(lleggings, Config.CONFIG_WOOL_PANTS_NAME);
		lboots = ItemRenaming.changeName(lboots, Config.CONFIG_WOOL_BOOTS_NAME);
	    }

	    shapedRecipe = new ShapedRecipe(lhelmet);
	    shapedRecipe.shape("www", "w w");
	    shapedRecipe.setIngredient('w', mwool);
	    Bukkit.getServer().addRecipe(shapedRecipe);

	    shapedRecipe = new ShapedRecipe(lchestplate);
	    shapedRecipe.shape("w w", "www", "www");
	    shapedRecipe.setIngredient('w', mwool);
	    Bukkit.getServer().addRecipe(shapedRecipe);

	    shapedRecipe = new ShapedRecipe(lleggings);
	    shapedRecipe.shape("www", "w w", "w w");
	    shapedRecipe.setIngredient('w', mwool);
	    Bukkit.getServer().addRecipe(shapedRecipe);

	    shapedRecipe = new ShapedRecipe(lboots);
	    shapedRecipe.shape("w w", "w w");
	    shapedRecipe.setIngredient('w', mwool);
	    Bukkit.getServer().addRecipe(shapedRecipe);
	}
	
	// Tarta sin huevo
	MaterialData mmilkbucket = milkBucket.getData();
	MaterialData sugar = new ItemStack(Material.SUGAR).getData();
	MaterialData wheat = new ItemStack(Material.WHEAT).getData();
	ItemStack cake = new ItemStack(Material.CAKE);
	shapedRecipe = new ShapedRecipe(cake);
	shapedRecipe.shape("mmm", "sss", "www");
	shapedRecipe.setIngredient('m', mmilkbucket);
	shapedRecipe.setIngredient('s', sugar);
	shapedRecipe.setIngredient('w', wheat);
	Bukkit.getServer().addRecipe(shapedRecipe);

	// Pastel de calabaza sin huevo
	MaterialData pumpkin = new ItemStack(Material.PUMPKIN).getData();
	ItemStack pumpkinPie = new ItemStack(Material.PUMPKIN_PIE);
	shapelessRecipe = new ShapelessRecipe(pumpkinPie);
	shapelessRecipe.addIngredient(pumpkin);
	shapelessRecipe.addIngredient(sugar);
	shapelessRecipe.addIngredient(sugar);
	Bukkit.getServer().addRecipe(shapelessRecipe);

	// Palos y Papel -> Item Frame
	ItemStack itemFrame = new ItemStack(Material.ITEM_FRAME);
	shapedRecipe = new ShapedRecipe(itemFrame);
	shapedRecipe.shape("sss", "sps", "sss");
	shapedRecipe.setIngredient('s', stick);
	shapedRecipe.setIngredient('p', paper);
	Bukkit.getServer().addRecipe(shapedRecipe);

	// Madera y heno -> Cama
        ItemStack bed = new ItemStack(Material.BED, 1, (short) 4);
	MaterialData hay = new ItemStack(Material.HAY_BLOCK).getData();
	MaterialData wood;
	for (int i = 0; i < 6; i++) // Para agregar todos los tipos de madera
	{
	    wood = new ItemStack(Material.WOOD, 1, (short) i).getData();
	    shapedRecipe = new ShapedRecipe(bed);
	    shapedRecipe.shape("hhh", "www");
	    shapedRecipe.setIngredient('h', hay);
	    shapedRecipe.setIngredient('w', wood);
	    Bukkit.getServer().addRecipe(shapedRecipe);
	}
        
    }
    private ItemStack changeLeatherColor(ItemStack armor, Color color)
    {
	LeatherArmorMeta lam = (LeatherArmorMeta) armor.getItemMeta();
	lam.setColor(color);
	armor.setItemMeta(lam);
	return armor;
    }

}
