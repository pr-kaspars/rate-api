package com.zopacandidates.ratematcher;

public class Request {
  private String fileName;
  private double amount;

  public Request(String fileName, double amount) {
    this.fileName = fileName;
    this.amount = amount;
  }

  public String getFileName() {
    return fileName;
  }

  public double getAmount() {
    return amount;
  }
}
