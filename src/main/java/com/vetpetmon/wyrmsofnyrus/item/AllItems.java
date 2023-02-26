package com.vetpetmon.wyrmsofnyrus.item;

import com.vetpetmon.wyrmsofnyrus.entity.WyrmRegister;
import net.minecraft.item.Item;

import java.util.ArrayList;
import java.util.List;

public class AllItems {
    public static final List<Item> ALL_ITEMS = new ArrayList<Item>();

    public static final Item creepshard = new ItemBase("creepshard", true);
    public static final Item creepedflesh = new ItemBase("creepedflesh", true);
    public static final Item creepedbone = new ItemBase("creepedbone", true);
    public static final Item metalcomb_array = new ItemBase("metalcomb_array", true);
    public static final Item wyrmarmorfrag = new ItemBase("wyrmarmorfrag", true);
    public static final Item wyrmico = new ItemBase("wyrmico", false, true);
    public static final Item creepedbulb = new ItemBase("creepedbulb", true);
    public static final Item meatybase = new ItemBase("meatybase", true);
    public static final Item strykerspawner = new WyrmfollySpawner("strykerspawner", (byte) 1);
    private static int wyrmID = 0;
    public static final Item hexepodspawner = new WyrmSpawner(WyrmRegister.wyrmIDs[wyrmID][0]+"spawner", wyrmID++);
    public static final Item wyrmlingspawner = new WyrmSpawner(WyrmRegister.wyrmIDs[wyrmID][0]+"spawner", wyrmID++);
    public static final Item wyrmproberspawner = new WyrmSpawner(WyrmRegister.wyrmIDs[wyrmID][0]+"spawner", wyrmID++);
    public static final Item thevisitorspawner = new WyrmSpawner(WyrmRegister.wyrmIDs[wyrmID][0]+"spawner", wyrmID++);
    public static final Item wyrmworkerspawner = new WyrmSpawner(WyrmRegister.wyrmIDs[wyrmID][0]+"spawner", wyrmID++);
    public static final Item wyrmroverspawner = new WyrmSpawner(WyrmRegister.wyrmIDs[wyrmID][0]+"spawner", wyrmID++);
    public static final Item wyrmroveruraniumspawner = new WyrmSpawner(WyrmRegister.wyrmIDs[wyrmID][0]+"spawner", wyrmID++);
    public static final Item callouspodspawner = new WyrmSpawner(WyrmRegister.wyrmIDs[wyrmID][0]+"spawner", wyrmID++);
    public static final Item wyrmsoldierspawner = new WyrmSpawner(WyrmRegister.wyrmIDs[wyrmID][0]+"spawner", wyrmID++);
    public static final Item creepwyrmspawner = new WyrmSpawner(WyrmRegister.wyrmIDs[wyrmID][0]+"spawner", wyrmID++);
    public static final Item wyrmmyrmurspawner = new WyrmSpawner(WyrmRegister.wyrmIDs[wyrmID][0]+"spawner", wyrmID++);
    public static final Item wyrmsoldierevospawner = new WyrmSpawner(WyrmRegister.wyrmIDs[wyrmID][0]+"spawner", wyrmID++);
    public static final Item wyrmwarriorspawner = new WyrmSpawner(WyrmRegister.wyrmIDs[wyrmID][0]+"spawner", wyrmID++);
    public static final Item creepedbiterspawner = new WyrmSpawner(WyrmRegister.wyrmIDs[wyrmID][0]+"spawner", wyrmID++);

    public static final Item creepedhumanoidspawner = new WyrmSpawner(WyrmRegister.wyrmIDs[wyrmID][0]+"spawner", wyrmID++);
    public static final Item creeppodspawner = new WyrmSpawner(WyrmRegister.wyrmIDs[wyrmID][0]+"spawner", wyrmID++);
    public static final Item wyrmsoldierfrostspawner = new WyrmSpawner(WyrmRegister.wyrmIDs[wyrmID][0]+"spawner", wyrmID++);
    public static final Item creeplingspawner = new WyrmSpawner(WyrmRegister.wyrmIDs[wyrmID][0]+"spawner", wyrmID++);
    public static final Item strykelingspawner = new WyrmSpawner(WyrmRegister.wyrmIDs[wyrmID][0]+"spawner", wyrmID++, true);
    public static final Item nkagentspawner = new WyrmSpawner(WyrmRegister.wyrmIDs[wyrmID][0]+"spawner", wyrmID++);
}
