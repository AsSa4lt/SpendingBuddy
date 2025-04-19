package com.rostyslavliapkin.spendingbuddy.core.commands;

import java.net.URL;
import java.time.YearMonth;

public interface Command {
    boolean Execute();
    boolean Undo();
    URL GetImageURL();
    String GetDescription();
    double GetAmount();
    YearMonth GetYearMonth();
}
