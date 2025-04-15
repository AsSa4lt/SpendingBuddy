package com.rostyslavliapkin.spendingbuddy.core.commands;

public class SpendingCommand implements Command {
    @Override
    public boolean Execute() {
        return true;
    }

    @Override
    public boolean Undo() {
        return true;
    }
}
