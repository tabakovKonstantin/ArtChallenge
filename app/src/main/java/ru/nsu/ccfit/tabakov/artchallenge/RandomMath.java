package ru.nsu.ccfit.tabakov.artchallenge;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * Created by Константин on 19.04.2015.
 */
public class RandomMath {
    public static int randomInt(int start, int end) {
        Random rand = new Random();
        while (true) {
            int value = rand.nextInt((end - start) + 1) + start;
            if(value != 0) {
                return value;
            }
        }

    }

    public static ArrayList<Integer> randomSet(int start, int end, int size) {
        Set<Integer> uniqSet = new HashSet<Integer>();
        while (uniqSet.size() < size) {
            uniqSet.add(randomInt(start,end));
        }
        return new ArrayList<Integer>(uniqSet);
    }
}
