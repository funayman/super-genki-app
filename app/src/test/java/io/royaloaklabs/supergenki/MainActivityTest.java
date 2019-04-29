package io.royaloaklabs.supergenki;

import io.royaloaklabs.supergenki.domain.DictionaryEntry;
import io.royaloaklabs.supergenki.domain.Sense;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class MainActivityTest {

  @Test
  public void isEntryVulgar_test() {
    MainActivity mainActivity = new MainActivity();

    Sense senseVulgar = new Sense("very fucking");
    Sense senseNonVulgar = new Sense("very Pretty");
    ArrayList<Sense> senses = new ArrayList<>();
    senses.add(senseVulgar);

    DictionaryEntry dictionaryEntry = new DictionaryEntry(100L);
    dictionaryEntry.setSenses(senses);

    assertTrue(mainActivity.isEntryVulgar(dictionaryEntry));

    senses = new ArrayList<>();
    senses.add(senseNonVulgar);
    dictionaryEntry.setSenses(senses);

    assertFalse(mainActivity.isEntryVulgar(dictionaryEntry));


  }

  @Test
  public void isEntryVulgar_combined_test() {
    MainActivity mainActivity = new MainActivity();

    Sense senseVulgar = new Sense("very fucking");
    Sense senseNonVulgar = new Sense("very Pretty");
    ArrayList<Sense> senses = new ArrayList<>();
    senses.add(senseNonVulgar);
    senses.add(senseVulgar);

    DictionaryEntry dictionaryEntry = new DictionaryEntry(100L);
    dictionaryEntry.setSenses(senses);

    assertTrue(mainActivity.isEntryVulgar(dictionaryEntry));

  }
}
