package com.zopacandidates.ratematcher;

import java.text.NumberFormat;

public class Quote {
  private double amount;
  private double rate;
  private int length;

  public Quote(double amount, double rate, int length) {
    this.amount = amount;
    this.rate = rate;
    this.length = length;
  }

  public double getAmount() {
    return amount;
  }

  public double getRate() {
    return rate;
  }

  public int getLength() {
    return length;
  }

  public double getMonthlyRepayment() {
    double r = Math.pow(1 + rate, 1.0 / 12) - 1;
    double d = (r * amount) / (1 - Math.pow(1 + r, -length));
    return Math.ceil(d * 100) / 100.0;
  }

  public double getTotalRepayment() {
    return Math.ceil(getMonthlyRepayment() * length * 100) / 100.0;
  }
}
