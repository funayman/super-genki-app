package io.royaloaklabs.supergenki.domain;

public class JapaneseCard {
    public String japaneseText;
    public String englishMeaning;
    public String photoId;

    public JapaneseCard(String japaneseText, String englishMeaning, String photoId) {
        this.japaneseText = japaneseText;
        this.englishMeaning = englishMeaning;
        this.photoId = photoId;
    }
}