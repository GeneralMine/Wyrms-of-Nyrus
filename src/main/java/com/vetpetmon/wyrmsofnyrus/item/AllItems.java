package com.vetpetmon.wyrmsofnyrus.item;

import net.minecraft.item.Item;

import java.util.ArrayList;
import java.util.List;

public class AllItems {
    public static final List<Item> ALL_ITEMS = new ArrayList<Item>();

    public static final Item creepshard = new ItemBase("creepshard", true);
    public static final Item metalcomb_array = new ItemBase("metalcomb_array", true);
    public static final Item wyrmarmorfrag = new ItemBase("wyrmarmorfrag", true);
    public static final Item wyrmico = new ItemBase("wyrmico", false);
}
