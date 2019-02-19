package io.royaloaklabs.supergenki.domain;

import java.util.ArrayList;
import java.util.Arrays;
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

  public String toJoinedString() {
    if(this.gloss.size() == 1) {
      return this.gloss.get(0);
    }

    if(this.gloss.size() == 2) {
      return String.format("%s, %s", this.gloss.get(0), this.gloss.get(1));
    }

    if(this.gloss.size() == 3) {
      return String.format("%s, %s, %s", this.gloss.get(0), this.gloss.get(1), this.gloss.get(2));
    }

    StringBuilder sb = new StringBuilder();
    sb.append(this.gloss.get(0));
    for(int i = 1; i < this.gloss.size(); i++) {
      sb.append(", ");
      sb.append(this.gloss.get(i));
    }
    return sb.toString();
  }
}
