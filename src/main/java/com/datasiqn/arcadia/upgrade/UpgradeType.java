package com.datasiqn.arcadia.upgrade;

import com.datasiqn.arcadia.Arcadia;
import com.datasiqn.arcadia.item.ItemRarity;
import com.datasiqn.arcadia.upgrade.listeners.BloodChaliceListener;
import com.datasiqn.arcadia.upgrade.listeners.UpgradeListener;
import com.datasiqn.arcadia.util.lorebuilder.Lore;
import it.unimi.dsi.fastutil.ints.Int2LongMap;
import it.unimi.dsi.fastutil.ints.Int2LongOpenHashMap;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import it.unimi.dsi.fastutil.objects.Object2DoubleLinkedOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2DoubleMap;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;

public enum UpgradeType {
    BLOOD_CHALICE("Blood Chalice", Lore.of("Killing enemies heal you"), Material.POTION, ItemRarity.RARE, new BloodChaliceListener()),
    ;

    private static final Object2DoubleMap<ItemRarity> RARITY_WEIGHTS = new Object2DoubleLinkedOpenHashMap<>();
    private static final double[] PROBABILITIES;
    private static final int[] ALIAS;
    static {
        RARITY_WEIGHTS.put(ItemRarity.COMMON, 100);     // ~76%
        RARITY_WEIGHTS.put(ItemRarity.RARE, 28);        // ~21%
        RARITY_WEIGHTS.put(ItemRarity.LEGENDARY, 2);    // ~2%
        RARITY_WEIGHTS.put(ItemRarity.MYTHIC, 1);       // ~1%

        int size = RARITY_WEIGHTS.size();
        double total = RARITY_WEIGHTS.values().doubleStream().sum();
        PROBABILITIES = new double[size];
        ALIAS = new int[size];
        // create alias to use when generating random weights

        IntList small = new IntArrayList();
        IntList large = new IntArrayList();
        {
            int i = 0;
            for (double value : RARITY_WEIGHTS.values()) {
                double mappedVal = value * (size / total);
                PROBABILITIES[i] = mappedVal;
                if (mappedVal < 1) small.add(i);
                else large.add(i);
                i++;
            }
        }

        while (small.size() > 0 && large.size() > 0) {
            ALIAS[small.getInt(0)] = large.getInt(0);
            PROBABILITIES[large.getInt(0)] += PROBABILITIES[small.getInt(0)] - 1;
            small.removeInt(0);
            if (PROBABILITIES[large.getInt(0)] > 1) large.add(large.removeInt(0));
            else if (PROBABILITIES[large.getInt(0)] < 1) small.add(large.removeInt(0));
        }

        while (small.size() > 0) {
            PROBABILITIES[small.removeInt(0)] = 1;
        }

        while (large.size() > 0) {
            PROBABILITIES[large.removeInt(0)] = 1;
        }
    }

    private final String displayName;
    private final Lore description;
    private final Material material;
    private final ItemRarity rarity;

    UpgradeType(String displayName, @NotNull Lore description, Material material, ItemRarity rarity, UpgradeListener listener) {
        this.displayName = displayName;
        this.description = description;
        this.material = material;
        this.rarity = rarity;

        Arcadia.getPlugin(Arcadia.class).getUpgradeEventManager().register(listener, this);
    }

    public String getDisplayName() {
        return displayName;
    }

    public Lore getDescription() {
        return description;
    }

    public Material getMaterial() {
        return material;
    }

    public ItemRarity getRarity() {
        return rarity;
    }

    public static void testRandom() {
        long times = 1_000_000;
        double total = RARITY_WEIGHTS.values().doubleStream().sum();
        System.out.println("Testing " + times + " times");
        Int2LongMap results = new Int2LongOpenHashMap();

        for (long i = 0; i < times; i++) {
            int random = (int) (Math.random() * RARITY_WEIGHTS.size());
            if (Math.random() < PROBABILITIES[random]) {
                results.putIfAbsent(random, 0);
                results.computeIfPresent(random, (key, occurrences) -> occurrences + 1);
            } else {
                results.putIfAbsent(ALIAS[random], 0);
                results.computeIfPresent(ALIAS[random], (key, occurrences) -> occurrences + 1);
            }
        }

        {
            int i = 0;
            for (double val : RARITY_WEIGHTS.values()) {
                double expected = val / total;
                System.out.println("Expected: " + expected + ".\nDeviation: " + ((results.get(i) / (double) times) - expected));
                i++;
            }
        }
    }
}