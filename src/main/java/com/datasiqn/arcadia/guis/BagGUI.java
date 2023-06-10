package com.datasiqn.arcadia.guis;

import com.datasiqn.arcadia.Arcadia;
import com.datasiqn.arcadia.dungeons.DungeonPlayer;
import com.datasiqn.arcadia.upgrades.Upgrade;
import com.datasiqn.arcadia.util.ItemUtil;
import com.datasiqn.menuapi.inventory.MenuHandler;
import com.datasiqn.menuapi.inventory.item.MenuButton;
import com.datasiqn.menuapi.inventory.item.StaticMenuItem;
import com.datasiqn.schedulebuilder.ScheduleBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class BagGUI extends MenuHandler {
    private final Arcadia plugin;

    public BagGUI(Arcadia plugin) {
        this.plugin = plugin;
    }

    @Override
    public void onClick(@NotNull InventoryClickEvent event) {
        super.onClick(event);

        event.setCancelled(true);
    }

    @Override
    public void onDrag(@NotNull InventoryDragEvent event) {
        super.onDrag(event);

        event.setCancelled(true);
    }

    @Override
    public void populate(@NotNull HumanEntity humanEntity) {
        DungeonPlayer dungeonPlayer = plugin.getDungeonManager().getDungeonPlayer(humanEntity.getUniqueId());
        if (dungeonPlayer == null) return;

        ItemStack empty = ItemUtil.createEmpty(Material.GRAY_STAINED_GLASS_PANE);
        for (int i = 0; i < 9; i++) setItem(53 - i, new StaticMenuItem(empty));

        ItemStack closeItem = new ItemStack(Material.BARRIER);
        ItemMeta meta = closeItem.getItemMeta();
        if (meta == null) return;
        meta.setDisplayName(ChatColor.RED + "Close");
        closeItem.setItemMeta(meta);
        setItem(49, new MenuButton(closeItem).onClick(event -> ScheduleBuilder.create().executes(runnable -> event.getWhoClicked().closeInventory()).run(plugin)));

        List<Upgrade> upgrades = dungeonPlayer.getUpgrades();
        for (int i = 0; i < upgrades.size(); i++) {
            setItem(i, new StaticMenuItem(upgrades.get(i).toItemStack()));
        }
    }

    @Override
    public @NotNull Inventory createInventory() {
        return Bukkit.createInventory(null, 54, "Item Bag");
    }
}
