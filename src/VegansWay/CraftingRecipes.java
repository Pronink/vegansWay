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
import java.util.HashMap;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.ItemMeta;
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
	ItemStack bed = new ItemStack(Material.BED);
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
