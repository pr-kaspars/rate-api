package com.zopacandidates.ratematcher;

import java.io.File;
import java.nio.file.Paths;

public class Validator {

  public Either<String, Request> validate(String[] args) {
    if (args == null) {
      return Either.left("Oh la la la");
    }

    if (args.length != 2) {
      return Either.left("Application accepts 2 arguments: [market_file] [loan_amount]");
    }

    File file = Paths.get(args[0].trim()).toFile();
    if (!file.exists() || !file.isFile() || !file.canRead()) {
      return Either.left("invalids market file");
    }

    if (!args[1].trim().matches("\\d+")) {
      return Either.left("amount is not a number");
    }

    double amount = Double.parseDouble(args[1].trim());
    if (amount % 100 != 0 || amount < 1000 || amount > 15000) {
      return Either.left("amount must be increment of 100 between 1000 and 15000");
    }

    return Either.right(new Request(file.getAbsolutePath(), amount));
  }
}
