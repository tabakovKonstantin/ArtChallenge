package ru.nsu.ccfit.tabakov.artchallenge;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by Константин on 18.04.2015.
 */
public class Translation implements Serializable{
    private Map<Integer, String> painters;
    private Map<Integer, String> badPhrases;
    private Map<Integer, String>  goodPhrases;
    private Map<String, String>  sets;

    public Map<Integer, String> getPainters() {
        return painters;
    }

    public void setPainters(Map<Integer, String> painters) {
        this.painters = painters;
    }

    public Map<Integer, String> getBadPhrases() {
        return badPhrases;
    }

    public void setBadPhrases(Map<Integer, String> badPhrases) {
        this.badPhrases = badPhrases;
    }

    public Map<Integer, String> getGoodPhrases() {
        return goodPhrases;
    }

    public void setGoodPhrases(Map<Integer, String> goodPhrases) {
        this.goodPhrases = goodPhrases;
    }

    public Map<String, String> getSets() {
        return sets;
    }

    public void setSets(Map<String, String> sets) {
        this.sets = sets;
    }

    public String getRandGoodPhrases() {
        int numText = RandomMath.randomInt(1, getGoodPhrases().size());
        return getGoodPhrases().get(numText);
    }

    public String getRandBadPhrases() {
        int numText = RandomMath.randomInt(1, getBadPhrases().size());
        return getBadPhrases().get(numText);
    }
}
