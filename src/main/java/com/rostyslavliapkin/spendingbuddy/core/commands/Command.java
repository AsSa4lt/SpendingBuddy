package com.rostyslavliapkin.spendingbuddy.core.commands;

public interface Command {
    boolean Execute();
    boolean Undo();
}
