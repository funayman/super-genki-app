package io.royaloaklabs.supergenki.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import io.royaloaklabs.supergenki.MainActivity;
import io.royaloaklabs.supergenki.R;

public class DetailedJapaneseActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setDisplayShowHomeEnabled(true);

    Intent intent = getIntent();
    setContentView(R.layout.activity_detailed_japanese);
    String serializedForm = intent.getStringExtra(MainActivity.MESSAGE);
    System.out.println(serializedForm);
    String[] extractedText = serializedForm.split("\\|\\|");

    //Update text
    TextView japaneseText = (TextView) findViewById(R.id.detailedJapaneseText);
    TextView englishText  = (TextView) findViewById(R.id.detailedTranslationView);
    TextView kanaText     = (TextView) findViewById(R.id.detailedKanaView);
    TextView romajiText   = (TextView) findViewById(R.id.detailedRomajiView);

    japaneseText.setText(extractedText[0]);
    if(extractedText[1] != null) {
      String[] splits = extractedText[1].split(";");
      String output   = "";
      for(String fragment : splits) {
        output = output + fragment.trim() + "\n";
      }
      englishText.setText("English: " + output);
    } else {
      englishText.setVisibility(View.GONE);
    }

    if(extractedText[2] != null) {
      kanaText.setText("Kana: " + extractedText[2]);
    } else {
      kanaText.setVisibility(View.GONE);
    }

    if(extractedText[3] != null) {
      romajiText.setText("Romaji: " + extractedText[3]);
    } else {
      romajiText.setVisibility(View.GONE);
    }

  }

  @Override
  public boolean onSupportNavigateUp(){
    finish();
    return true;
  }
}
