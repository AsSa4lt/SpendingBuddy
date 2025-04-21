package com.rostyslavliapkin.spendingbuddy.core.commands;

import com.rostyslavliapkin.spendingbuddy.core.ResourceEntity;
import java.net.URL;
import java.time.YearMonth;

/**
 * Interface for commands
 */
public interface Command {
    /**
     * Function to execute the command
     * @return result of the execution
     */
    boolean Execute();

    /**
     * Function to undo the execution of the command
     * @return result of undoing the execution
     */
    boolean Undo();

    /**
     * Image to display this command in UI
     * @return link to an image
     */
    URL GetImageURL();

    /**
     * Function to get description of the command
     * @return description of the command
     */
    String GetDescription();

    /**
     * Function to get amount of the command
     * @return double value for this command
     */
    double GetAmount();

    /**
     * Function to get the YearMonth of the command
     * @return a YearMonth of the command
     */
    YearMonth GetYearMonth();

    /**
     * Function to check if the entity is involved in the command
     * @param entity to be checked
     * @return
     */
    boolean InvolvesEntity(ResourceEntity entity);
}
