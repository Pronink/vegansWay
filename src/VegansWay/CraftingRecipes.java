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
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;

/**
 *
 * @author Pronink
 */
public class CraftingRecipes
{

    void addAllCraftingRecipes()
    {
	ShapedRecipe shapedRecipe; // Con orden
	ShapelessRecipe shapelessRecipe; // Sin orden
	
	// Planta de algodon -> Lana
	Material azureBluet = new ItemStack(Material.RED_ROSE, 1, (short)3).getType();
	ItemStack wool = new ItemStack(Material.WOOL);
	shapedRecipe = new ShapedRecipe(wool);
	shapedRecipe.shape("aa","aa");
	shapedRecipe.setIngredient('a', azureBluet);
	Bukkit.getServer().addRecipe(shapedRecipe);
	
	// Semillas de calabaza -> Leche de calabaza
	Material bucket = new ItemStack(Material.BUCKET).getType();
	Material pumpkinSeeds = new ItemStack(Material.PUMPKIN_SEEDS).getType();
	ItemStack milkBucket = new ItemStack(Material.MILK_BUCKET);
	shapelessRecipe = new ShapelessRecipe(milkBucket);
	shapelessRecipe.addIngredient(bucket);
	shapelessRecipe.addIngredient(pumpkinSeeds);
	Bukkit.getServer().addRecipe(shapelessRecipe);
	
	// Carbon -> Tinta negra
	Material coal = new ItemStack(Material.COAL).getType();
	ItemStack inksac = new ItemStack(Material.INK_SACK);
	shapedRecipe = new ShapedRecipe(inksac);
	shapedRecipe.shape("c");
	shapedRecipe.setIngredient('c', coal);
	Bukkit.getServer().addRecipe(shapedRecipe);
	
	// Carbon vegetal -> Tinta negra
	Material charcoal = new ItemStack(Material.COAL, 1, (short)1).getType();
	shapedRecipe = new ShapedRecipe(inksac);
	shapedRecipe.shape("c");
	shapedRecipe.setIngredient('c', charcoal);
	Bukkit.getServer().addRecipe(shapedRecipe);
	
	// Full papel -> Libro
	Material paper = new ItemStack(Material.PAPER).getType();
	ItemStack book = new ItemStack(Material.BOOK);
	shapedRecipe = new ShapedRecipe(book);
	shapedRecipe.shape("ppp","ppp","ppp");
	shapedRecipe.setIngredient('p', paper);
	Bukkit.getServer().addRecipe(shapedRecipe);
	
	// Palo + Papel -> Pluma
	Material stick = new ItemStack(Material.STICK).getType();
	ItemStack feather = new ItemStack(Material.FEATHER);
	shapelessRecipe = new ShapelessRecipe(feather);
	shapelessRecipe.addIngredient(stick);
	shapelessRecipe.addIngredient(paper);
	Bukkit.getServer().addRecipe(shapelessRecipe);
	
	// Lana/Algodon -> Armadura de cuero
	Material mwool = wool.getType();
	ItemStack lhelmet = new ItemStack(Material.LEATHER_HELMET);
	ItemStack lechestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
	ItemStack lleggings = new ItemStack(Material.LEATHER_LEGGINGS);
	ItemStack lboots = new ItemStack(Material.LEATHER_BOOTS);
	
	shapedRecipe = new ShapedRecipe(lhelmet);
	shapedRecipe.shape("www","w w");
	shapedRecipe.setIngredient('w', mwool);
	Bukkit.getServer().addRecipe(shapedRecipe);
	
	shapedRecipe = new ShapedRecipe(lechestplate);
	shapedRecipe.shape("w w","www","www");
	shapedRecipe.setIngredient('w', mwool);
	Bukkit.getServer().addRecipe(shapedRecipe);
	
	shapedRecipe = new ShapedRecipe(lleggings);
	shapedRecipe.shape("www","w w","w w");
	shapedRecipe.setIngredient('w', mwool);
	Bukkit.getServer().addRecipe(shapedRecipe);
	
	shapedRecipe = new ShapedRecipe(lboots);
	shapedRecipe.shape("w w","w w");
	shapedRecipe.setIngredient('w', mwool);
	Bukkit.getServer().addRecipe(shapedRecipe);
	
	// Tarta sin huevo
	Material mmilkbucket = milkBucket.getType();
	Material sugar = new ItemStack(Material.SUGAR).getType();
	Material wheat = new ItemStack(Material.WHEAT).getType();
	ItemStack cake = new ItemStack(Material.CAKE);
	shapedRecipe = new ShapedRecipe(cake);
	shapedRecipe.shape("mmm","sss","www");
	shapedRecipe.setIngredient('m', mmilkbucket);
	shapedRecipe.setIngredient('s', sugar);
	shapedRecipe.setIngredient('w', wheat);
	Bukkit.getServer().addRecipe(shapedRecipe);
	
	// Pastel de calabaza sin huevo
	Material pumpkin = new ItemStack(Material.PUMPKIN).getType();
	ItemStack pumpkinPie = new ItemStack(Material.PUMPKIN_PIE);
	shapelessRecipe = new ShapelessRecipe(pumpkinPie);
	shapelessRecipe.addIngredient(pumpkin);
	shapelessRecipe.addIngredient(sugar);
	shapelessRecipe.addIngredient(sugar);
	Bukkit.getServer().addRecipe(shapelessRecipe);
	
	// Palos -> Item Frame
	ItemStack itemFrame = new ItemStack(Material.ITEM_FRAME);
	shapedRecipe = new ShapedRecipe(itemFrame);
	shapedRecipe.shape("sss","sps","sss");
	shapedRecipe.setIngredient('s', stick);
	shapedRecipe.setIngredient('p', paper);
	Bukkit.getServer().addRecipe(shapedRecipe);
    }
    
}
