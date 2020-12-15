package pl.training.cloud.commons;

import org.javamoney.moneta.FastMoney;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import java.util.Locale;

public class LocalMoney {

    public static FastMoney of(Number number) {
        return FastMoney.of(number, getLocalCurrency());
    }

    public static FastMoney zero() {
        return FastMoney.zero(getLocalCurrency());
    }

    private static CurrencyUnit getLocalCurrency() {
        return Monetary.getCurrency(new Locale("pl", "PL"));
    }

}
