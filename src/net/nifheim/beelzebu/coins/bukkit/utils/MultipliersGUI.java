/*
 * This file is part of Coins.
 *
 * Copyright © 2017 Beelzebu
 * Coins is licensed under the GNU General Public License.
 *
 * Coins is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Coins is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package net.nifheim.beelzebu.coins.bukkit.utils;

import net.nifheim.beelzebu.coins.CoinsAPI;
import net.nifheim.beelzebu.coins.core.multiplier.MultiplierType;
import net.nifheim.beelzebu.coins.bukkit.utils.gui.BaseGUI;
import net.nifheim.beelzebu.coins.core.Core;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 *
 * @author Beelzebu
 */
public class MultipliersGUI extends BaseGUI {

    private final Core core = Core.getInstance();
    private final Player p;

    public MultipliersGUI(Player player, String name) {
        super(54, name);
        p = player;
        setItems();
    }

    private void setItems() {
        if (p != null && CoinsAPI.getMultiplier().getMultipliersFor(p.getUniqueId()).size() > 0) {
            int pos = -1;
            for (int j : CoinsAPI.getMultiplier().getMultipliersFor(p.getUniqueId())) {
                pos++;
                ItemStack item = new ItemStack(Material.NETHER_STAR);
                ItemMeta meta = item.getItemMeta();
                meta.setDisplayName("" + j);
                item.setItemMeta(meta);
                setItem(pos, item, player -> {
                    CoinsAPI.getMultiplier().useMultiplier(player.getUniqueId(), j, MultiplierType.SERVER);
                    player.sendMessage("Has usado el multiplicador con id: " + j);
                });
            }
        }
        short id = (short) 1;
        for (int i = 36; i < 45; i++) {
            setItem(i, new ItemStack(Material.WOOD_STEP, 1, id));
        }
        setItem(49, new ItemStack(Material.REDSTONE_BLOCK), p -> {
            p.playSound(p.getLocation(), Sound.valueOf(core.getConfig().getString("Multipliers.GUI.Close.Sound", "CLICK")), 10, core.getConfig().getInt("Multipliers.GUI.Close.Pitch", 1));
            p.closeInventory();
        });
    }
}
