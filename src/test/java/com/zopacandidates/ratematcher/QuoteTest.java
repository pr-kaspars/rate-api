package com.zopacandidates.ratematcher;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class QuoteTest {

  @Test
  public void getMonthlyRepayment() {
    Quote quote = new Quote(1000, 0.07, 36);
    assertEquals(30.78, quote.getMonthlyRepayment());
  }

  @Test
  public void getTotalRepayment() {
    Quote quote = new Quote(1000, 0.07, 36);
    assertEquals(1108.08, quote.getTotalRepayment());
  }
}