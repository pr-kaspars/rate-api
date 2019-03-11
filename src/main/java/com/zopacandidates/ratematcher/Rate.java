package com.zopacandidates.ratematcher;

public class Rate {
  private String lender;
  private double rate;
  private int available;

  public Rate(String lender, double rate, int available) {
    this.lender = lender;
    this.rate = rate;
    this.available = available;
  }

  public String getLender() {
    return lender;
  }

  public double getRate() {
    return rate;
  }

  public int getAvailable() {
    return available;
  }
}
