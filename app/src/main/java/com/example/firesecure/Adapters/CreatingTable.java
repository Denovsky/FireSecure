package com.example.firesecure.Adapters;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CreatingTable {

    private static Map<Integer, Map<Integer, ArrayList<Integer>>> allTable;
    private static String Typikovya = "Тупиковая"; // 0 index
    private static String Kolcivya = "Кольцевая"; // 1 index
    private static int push_int;

    public CreatingTable(int index, int diametr, int push) {
        createConstTable(index,
                diametr,
                push);
    }

    private void createConstTable(int index, int diametr, int push) {
        allTable = new HashMap<>();
        getPushOne();
        getPushTwo();
        getPushThree();
        getPushFour();
        getPushFive();
        getPushSix();
        getPushSeven();
        getPushEight();
        getResult(index,
                diametr,
                push);
    }

    public int getPush(){
        return push_int;
    }

    public static void getResult(int index, int diametr, int push) {
        Map<Integer, ArrayList<Integer>> partTable = new HashMap<Integer, ArrayList<Integer>>();
        ArrayList<Integer> integers = new ArrayList<>();

        partTable = allTable.get(push);
        integers = partTable.get(diametr);
        push_int = integers.get(index);
    }

    public static void getPushOne() {
        Map<Integer, ArrayList<Integer>> partTable = new HashMap<Integer, ArrayList<Integer>>();

        partTable.put(100, new ArrayList<Integer>(Arrays.asList(10, 25)));

        partTable.put(125, new ArrayList<Integer>(Arrays.asList(20, 40)));

        partTable.put(150, new ArrayList<Integer>(Arrays.asList(25, 55)));

        partTable.put(200, new ArrayList<Integer>(Arrays.asList(30, 65)));

        partTable.put(250, new ArrayList<Integer>(Arrays.asList(40, 85)));

        partTable.put(300, new ArrayList<Integer>(Arrays.asList(55, 115)));

        partTable.put(350, new ArrayList<Integer>(Arrays.asList(65, 130)));

        allTable.put(10, partTable);
    }

    public static void getPushTwo() {
        Map<Integer, ArrayList<Integer>> partTable = new HashMap<Integer, ArrayList<Integer>>();

        partTable.put(100, new ArrayList<Integer>(Arrays.asList(14, 30)));

        partTable.put(125, new ArrayList<Integer>(Arrays.asList(25, 60)));

        partTable.put(150, new ArrayList<Integer>(Arrays.asList(30, 70)));

        partTable.put(200, new ArrayList<Integer>(Arrays.asList(45, 90)));

        partTable.put(250, new ArrayList<Integer>(Arrays.asList(55, 115)));

        partTable.put(300, new ArrayList<Integer>(Arrays.asList(80, 170)));

        partTable.put(350, new ArrayList<Integer>(Arrays.asList(90, 195)));

        allTable.put(20, partTable);
    }

    public static void getPushThree() {
        Map<Integer, ArrayList<Integer>> partTable = new HashMap<Integer, ArrayList<Integer>>();

        partTable.put(100, new ArrayList<Integer>(Arrays.asList(17, 40)));

        partTable.put(125, new ArrayList<Integer>(Arrays.asList(35, 70)));

        partTable.put(150, new ArrayList<Integer>(Arrays.asList(40, 80)));

        partTable.put(200, new ArrayList<Integer>(Arrays.asList(55, 110)));

        partTable.put(250, new ArrayList<Integer>(Arrays.asList(70, 145)));

        partTable.put(300, new ArrayList<Integer>(Arrays.asList(95, 205)));

        partTable.put(350, new ArrayList<Integer>(Arrays.asList(110, 235)));

        allTable.put(30, partTable);
    }

    public static void getPushFour() {
        Map<Integer, ArrayList<Integer>> partTable = new HashMap<Integer, ArrayList<Integer>>();

        partTable.put(100, new ArrayList<Integer>(Arrays.asList(21, 45)));

        partTable.put(125, new ArrayList<Integer>(Arrays.asList(40, 85)));

        partTable.put(150, new ArrayList<Integer>(Arrays.asList(45, 95)));

        partTable.put(200, new ArrayList<Integer>(Arrays.asList(60, 130)));

        partTable.put(250, new ArrayList<Integer>(Arrays.asList(80, 185)));

        partTable.put(300, new ArrayList<Integer>(Arrays.asList(110, 235)));

        partTable.put(350, new ArrayList<Integer>(Arrays.asList(140, 280)));

        allTable.put(40, partTable);
    }

    public static void getPushFive() {
        Map<Integer, ArrayList<Integer>> partTable = new HashMap<Integer, ArrayList<Integer>>();

        partTable.put(100, new ArrayList<Integer>(Arrays.asList(24, 50)));

        partTable.put(125, new ArrayList<Integer>(Arrays.asList(45, 90)));

        partTable.put(150, new ArrayList<Integer>(Arrays.asList(50, 105)));

        partTable.put(200, new ArrayList<Integer>(Arrays.asList(70, 145)));

        partTable.put(250, new ArrayList<Integer>(Arrays.asList(90, 200)));

        partTable.put(300, new ArrayList<Integer>(Arrays.asList(120, 265)));

        partTable.put(350, new ArrayList<Integer>(Arrays.asList(160, 325)));

        allTable.put(50, partTable);
    }

    public static void getPushSix() {
        Map<Integer, ArrayList<Integer>> partTable = new HashMap<Integer, ArrayList<Integer>>();

        partTable.put(100, new ArrayList<Integer>(Arrays.asList(26, 52)));

        partTable.put(125, new ArrayList<Integer>(Arrays.asList(47, 95)));

        partTable.put(150, new ArrayList<Integer>(Arrays.asList(55, 110)));

        partTable.put(200, new ArrayList<Integer>(Arrays.asList(80, 163)));

        partTable.put(250, new ArrayList<Integer>(Arrays.asList(110, 225)));

        partTable.put(300, new ArrayList<Integer>(Arrays.asList(140, 290)));

        partTable.put(350, new ArrayList<Integer>(Arrays.asList(190, 380)));

        allTable.put(60, partTable);
    }

    public static void getPushSeven() {
        Map<Integer, ArrayList<Integer>> partTable = new HashMap<Integer, ArrayList<Integer>>();

        partTable.put(100, new ArrayList<Integer>(Arrays.asList(29, 58)));

        partTable.put(125, new ArrayList<Integer>(Arrays.asList(50, 105)));

        partTable.put(150, new ArrayList<Integer>(Arrays.asList(65, 130)));

        partTable.put(200, new ArrayList<Integer>(Arrays.asList(90, 198)));

        partTable.put(250, new ArrayList<Integer>(Arrays.asList(125, 255)));

        partTable.put(300, new ArrayList<Integer>(Arrays.asList(160, 330)));

        partTable.put(350, new ArrayList<Integer>(Arrays.asList(210, 440)));

        allTable.put(70, partTable);
    }

    public static void getPushEight() {
        Map<Integer, ArrayList<Integer>> partTable = new HashMap<Integer, ArrayList<Integer>>();

        partTable.put(100, new ArrayList<Integer>(Arrays.asList(32, 64)));

        partTable.put(125, new ArrayList<Integer>(Arrays.asList(55, 115)));

        partTable.put(150, new ArrayList<Integer>(Arrays.asList(70, 140)));

        partTable.put(200, new ArrayList<Integer>(Arrays.asList(100, 205)));

        partTable.put(250, new ArrayList<Integer>(Arrays.asList(140, 287)));

        partTable.put(300, new ArrayList<Integer>(Arrays.asList(180, 370)));

        partTable.put(350, new ArrayList<Integer>(Arrays.asList(250, 500)));

        allTable.put(80, partTable);
    }
}
