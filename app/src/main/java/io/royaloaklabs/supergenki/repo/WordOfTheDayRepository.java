package io.royaloaklabs.supergenki.repo;

import android.content.Context;
import io.royaloaklabs.supergenki.database.DictionaryAdapter;
import io.royaloaklabs.supergenki.domain.DictionaryEntry;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class WordOfTheDayRepository {
  private DictionaryAdapter dictionaryAdapter;
  private static final int MAX_NUM_TRIES = 5;

  public WordOfTheDayRepository(Context context) {
    dictionaryAdapter = new DictionaryAdapter(context);
  }

  public DictionaryEntry get() {
    long index = this.getDailyIndex();

    try {
      return this.dictionaryAdapter.getOne(index);
    } catch(Exception e) {
      // do nothing; fix later
    }

    return this.dictionaryAdapter.getOne(50L);
  }

  private Long getDailyIndex() {
    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
    String stringDate = dateFormat.format(new Date());
    BigInteger tableHashResult = BigInteger.valueOf(0L);
    Long numOfEntries = dictionaryAdapter.getEntryTableCount();

    try {
      MessageDigest messageDigest = MessageDigest.getInstance("MD5");
      messageDigest.update(stringDate.getBytes(), 0, stringDate.length());
      BigInteger md5Base10 = new BigInteger(1, messageDigest.digest());
      tableHashResult = md5Base10.mod(BigInteger.valueOf(numOfEntries));
    } catch(Exception e) {
      e.printStackTrace();
      //Return a static number if for some reason the MD5 failed. (Maybe make this random #?)
      return 10L;
    }
    return (tableHashResult.longValue() * 10) + 1000000;
  }
}
