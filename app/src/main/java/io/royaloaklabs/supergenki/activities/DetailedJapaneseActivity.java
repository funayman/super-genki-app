package io.royaloaklabs.supergenki.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import io.royaloaklabs.supergenki.R;
import io.royaloaklabs.supergenki.database.DictionaryAdapter;
import io.royaloaklabs.supergenki.domain.Entry;
import io.royaloaklabs.supergenki.domain.Sense;

import java.util.List;

public class DetailedJapaneseActivity extends AppCompatActivity {

  public static final String ENT_SEQ = "entry-sequence";

  private TextView japaneseText;
  private TextView englishText;
  private TextView kanaText;
  private TextView romajiText;

  private DictionaryAdapter dictionaryAdapter;
  private Entry entry;

  private Long entryId;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setDisplayShowHomeEnabled(true);

    setContentView(R.layout.activity_detailed_japanese);

    Intent intent = getIntent();
    entryId = intent.getLongExtra(ENT_SEQ, 0);

    japaneseText = findViewById(R.id.detailedJapaneseText);
    kanaText = findViewById(R.id.detailedKanaView);
    romajiText = findViewById(R.id.detailedRomajiView);
    englishText = findViewById(R.id.detailedTranslationView);

    dictionaryAdapter = new DictionaryAdapter(this);
  }

  @Override
  protected void onResume() {
    super.onResume();
    this.dictionaryAdapter = new DictionaryAdapter(this);

    entry = dictionaryAdapter.getOne(entryId);

    if(entry.getKanji().isEmpty()) {
      japaneseText.setText(entry.getKana());
      kanaText.setVisibility(View.GONE);
    } else {
      japaneseText.setText(entry.getKanji());
      kanaText.setText(entry.getKana());
    }

    romajiText.setText(entry.getRomaji());

    // add to other card view
    List<Sense> senses = entry.getSenses();
    if(senses.size() == 1) {
      englishText.setText(senses.get(0).toJoinedString());
    } else if(senses.size() == 2) {
      englishText.setText(String.format("1) %s\n2) %s\n",
          senses.get(0).toJoinedString(), senses.get(1).toJoinedString()));
    } else if(senses.size() == 3) {
      englishText.setText(String.format("1) %s\n2) %s\n3) %s\n",
          senses.get(0).toJoinedString(), senses.get(1).toJoinedString(), senses.get(2).toJoinedString()));
    } else {
      StringBuilder sb = new StringBuilder();
      for(int i = 0; i < senses.size(); i++) {
        sb.append(String.format("%d) %s\n", i + 1, entry.getSenses().get(i).toJoinedString()));
      }
      englishText.setText(sb.toString());
    }
  }

  @Override
  protected void onPause() {
    super.onPause();
    this.dictionaryAdapter = null;
  }

  @Override
  public boolean onSupportNavigateUp() {
    finish();
    return true;
  }
}
