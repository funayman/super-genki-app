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

    Intent intent = getIntent();
    setContentView(R.layout.activity_detailed_japanese);
    String serializedForm = intent.getStringExtra(MainActivity.MESSAGE);
    System.out.println(serializedForm);
    String[] extractedText = serializedForm.split("\\|\\|");

    //Update text
    TextView japaneseText = (TextView) findViewById(R.id.detailedJapaneseText);
    TextView englishText  = (TextView) findViewById(R.id.detailedTranslationView);
    TextView kanaText     = (TextView) findViewById(R.id.detailedPronounceView);

    japaneseText.setText(extractedText[0]);
    if(extractedText[1] != null) {
      englishText.setText("English: " + extractedText[1]);
    } else {
      englishText.setVisibility(View.GONE);
    }

    if(extractedText[2] != null) {
      kanaText.setText("Kana: " + extractedText[2]);
    } else {
      kanaText.setVisibility(View.GONE);
    }

  }
}
