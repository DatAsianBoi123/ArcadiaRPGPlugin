package com.datasiqn.arcadia.commands.arguments;

import com.datasiqn.arcadia.Arcadia;
import com.datasiqn.arcadia.dungeons.DungeonInstance;
import com.datasiqn.arcadia.enchants.EnchantType;
import com.datasiqn.arcadia.entities.EntityType;
import com.datasiqn.arcadia.loottables.LootTables;
import com.datasiqn.arcadia.guis.GUIType;
import com.datasiqn.arcadia.items.materials.ArcadiaMaterial;
import com.datasiqn.arcadia.recipes.ArcadiaRecipe;
import com.datasiqn.commandcore.ArgumentParseException;
import com.datasiqn.commandcore.arguments.ArgumentType;

public interface ArcadiaArgumentType {
    ArgumentType<ArcadiaMaterial> ITEM = new ArgumentType.EnumArgumentType<>(ArcadiaMaterial.class);

    ArgumentType<EnchantType> ENCHANT = new ArgumentType.EnumArgumentType<>(EnchantType.class);

    ArgumentType<LootTables> LOOT_TABLE = new ArgumentType.EnumArgumentType<>(LootTables.class);

    ArgumentType<ArcadiaRecipe> RECIPE = new ArgumentType.EnumArgumentType<>(ArcadiaRecipe.class);

    ArgumentType<EntityType> ENTITY = new ArgumentType.EnumArgumentType<>(EntityType.class);

    ArgumentType<GUIType> GUI = new ArgumentType.EnumArgumentType<>(GUIType.class);

    ArgumentType<DungeonInstance> DUNGEON = new ArgumentType.CustomArgumentType<>(str -> {
        DungeonInstance instance = Arcadia.getPlugin(Arcadia.class).getDungeonManager().getCreatedDungeon(str);
        if (instance == null) throw new ArgumentParseException("The dungeon '" + str + "' does not exist.");
        return instance;
    }, () -> Arcadia.getPlugin(Arcadia.class).getDungeonManager().getAllDungeonInstances().stream().map(DungeonInstance::id).toList());
}
