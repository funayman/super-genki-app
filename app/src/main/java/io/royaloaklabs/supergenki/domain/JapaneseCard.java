package io.royaloaklabs.supergenki.domain;

public class JapaneseCard {
    public String japaneseText;
    public String englishMeaning;
    public String photoId;

    public JapaneseCard() { } // empty constructor

    public JapaneseCard(String japaneseText, String englishMeaning, String photoId) {
        this.japaneseText = japaneseText;
        this.englishMeaning = englishMeaning;
        this.photoId = photoId;
    }

    public void setJapaneseText(String japaneseText) {
        this.japaneseText = japaneseText;
    }

    public void setEnglishMeaning(String englishMeaning) {
        this.englishMeaning = englishMeaning;
    }

    public void setPhotoId(String photoId) {
        this.photoId = photoId;
    }

}