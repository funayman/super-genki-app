package io.royaloaklabs.supergenki.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Sense {

  private String partOfSpeech;

  private List<String> gloss;

  public Sense() { }  // empty constructor

  public Sense(String[] phrases) {
    gloss = Arrays.asList(phrases);
  }

  public Sense(String unsplitString) {
    this(unsplitString.split(Entry.GLOSS_DELIMITER));
  }

  public static List<Sense> buildFromRawData(String query) {
    String[] senseArray = query.split(Entry.SENSE_DELIMITER);
    List<Sense> senseList = new ArrayList<>(senseArray.length);
    for(String sense : senseArray) {
      senseList.add(new Sense(sense));
    }
    return senseList;
  }
}
