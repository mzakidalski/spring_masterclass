package pl.training.shop.payments;

import org.javamoney.moneta.FastMoney;

import javax.money.Monetary;
import java.util.Locale;

public class LocalMoney {
    public static FastMoney of (Number number) {
        var local = Locale.getDefault();
        var currencyUnit = Monetary.getCurrency(local);
        return FastMoney.of(number, currencyUnit);
    }
}
