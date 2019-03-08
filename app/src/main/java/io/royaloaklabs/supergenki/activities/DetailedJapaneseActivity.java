package io.royaloaklabs.supergenki.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import io.royaloaklabs.supergenki.R;
import io.royaloaklabs.supergenki.database.DictionaryAdapter;
import io.royaloaklabs.supergenki.domain.DictionaryEntry;
import io.royaloaklabs.supergenki.domain.Sense;
import sh.drt.furiganaview.FuriganaView;

import java.util.List;

public class DetailedJapaneseActivity extends AppCompatActivity {

  public static final String ENT_SEQ = "entry-sequence";

  private TextView englishTitleView;
  private TextView englishText;
  private TextView romajiText;
  private FuriganaView furiganaView;

  private DictionaryAdapter dictionaryAdapter;
  private DictionaryEntry entry;

  private Long entryId;
  private AdView adView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setDisplayShowHomeEnabled(true);

    setContentView(R.layout.activity_detailed_japanese);

    //Enable Ads
    MobileAds.initialize(this, "ca-app-pub-8769234461659052~4596379422");
    adView = findViewById(R.id.adBanner1);
    AdRequest adRequest = new AdRequest.Builder().build();
    adView.loadAd(adRequest);

    adView.setAdListener(new AdListener() {
      @Override
      public void onAdLoaded() {
        Log.i("onAdLoaded", "Ad successfully Loaded");
        CardView adCardView = findViewById(R.id.adCardView);
        adCardView.setVisibility(View.VISIBLE);
      }

      @Override
      public void onAdFailedToLoad(int errorCode) {
        Log.i("onAdFailedToLoad", "Ad failed to load");
        CardView adCardView = findViewById(R.id.adCardView);
        adCardView.setVisibility(View.GONE);
      }
    });

    Intent intent = getIntent();
    entryId = intent.getLongExtra(ENT_SEQ, 0);

    romajiText = findViewById(R.id.detailedRomajiView);
    englishText = findViewById(R.id.detailedTranslationView);
    furiganaView = findViewById(R.id.japaneseTextView);
    englishTitleView = findViewById(R.id.detailedEnglishText);

    dictionaryAdapter = new DictionaryAdapter(this);
  }

  @Override
  protected void onResume() {
    super.onResume();
    this.dictionaryAdapter = new DictionaryAdapter(this);

    entry = dictionaryAdapter.getOne(entryId);

    String japaneseText = (entry.getFurigana().isEmpty()) ? entry.getJapanese() : String.format("{%s;%s}", entry.getJapanese(), entry.getFurigana());
    furiganaView.setText(japaneseText);
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
