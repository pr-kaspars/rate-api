package com.zopacandidates.ratematcher;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class RateRepositoryTest {

  @Test
  @DisplayName("constructor should throw exception when file not found")
  public void constructorThrowsExceptionWhenFileNotFound() {
    assertThrows(IOException.class, () -> new RateRepository("/foo.csv"));
  }

  @Test
  @DisplayName("getRates should return ordered list of rates")
  public void getRatesShouldReturnRates() throws IOException {
    String fileName = getClass().getResource("/market.csv").getFile();
    RateRepository rateRepository = new RateRepository(fileName);
    List<Rate> rates = rateRepository.getRates();
    assertEquals(7, rates.size());
    assertEquals(0.069, rates.get(0).getRate());
    assertEquals(0.104, rates.get(6).getRate());
  }
}