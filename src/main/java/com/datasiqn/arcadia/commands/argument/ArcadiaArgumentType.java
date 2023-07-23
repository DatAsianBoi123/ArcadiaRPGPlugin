package com.datasiqn.arcadia.commands.argument;

import com.datasiqn.arcadia.dungeon.DungeonInstance;
import com.datasiqn.arcadia.enchants.EnchantType;
import com.datasiqn.arcadia.entities.EntityType;
import com.datasiqn.arcadia.item.material.ArcadiaMaterial;
import com.datasiqn.arcadia.loottable.LootTable;
import com.datasiqn.arcadia.menu.MenuType;
import com.datasiqn.arcadia.player.PlayerData;
import com.datasiqn.arcadia.recipe.ArcadiaRecipe;
import com.datasiqn.arcadia.upgrade.UpgradeType;
import com.datasiqn.commandcore.argument.type.ArgumentType;

public interface ArcadiaArgumentType {
    ArgumentType<PlayerData> PLAYER = new PlayerArgumentType();

    ArgumentType<ArcadiaMaterial> ITEM = new ArgumentType.EnumArgumentType<>(ArcadiaMaterial.class);

    ArgumentType<EnchantType> ENCHANT = new ArgumentType.EnumArgumentType<>(EnchantType.class);

    ArgumentType<LootTable> LOOT_TABLE = new ArgumentType.EnumArgumentType<>(LootTable.class);

    ArgumentType<ArcadiaRecipe> RECIPE = new ArgumentType.EnumArgumentType<>(ArcadiaRecipe.class);

    ArgumentType<UpgradeType> UPGRADE = new ArgumentType.EnumArgumentType<>(UpgradeType.class);

    ArgumentType<EntityType> ENTITY = new ArgumentType.EnumArgumentType<>(EntityType.class);

    ArgumentType<MenuType> GUI = new ArgumentType.EnumArgumentType<>(MenuType.class);

    ArgumentType<DungeonInstance> DUNGEON = new DungeonArgumentType();

    ArgumentType<UpgradeType> UPGRADE_TYPE = new ArgumentType.EnumArgumentType<>(UpgradeType.class);
}
