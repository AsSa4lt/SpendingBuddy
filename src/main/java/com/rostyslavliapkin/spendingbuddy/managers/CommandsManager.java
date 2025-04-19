package com.rostyslavliapkin.spendingbuddy.managers;

import com.rostyslavliapkin.spendingbuddy.core.commands.Command;
import java.time.LocalDate;

import java.time.MonthDay;
import java.util.*;


public class CommandsManager {
    public Map<MonthDay, List<Command>> allCommands;

    /**
     * Queue to store the history of the command
     */
    Queue<Command> history;

    /**
     * Constructor for CommandsManager
     */
    public CommandsManager(){
        history = new LinkedList<>();
        allCommands = new HashMap<>();
    }

    /**
     *
     * @param command Command to execute
     * @return boolean success of execution
     */
    public boolean ExecuteCommand(Command command){
        // check if null
        if (command == null)
            return false;
        // try to execute
        if(command.Execute()){
            // if success with execution -> return true
            history.add(command);
            MonthDay today = MonthDay.from(LocalDate.now());
            allCommands.computeIfAbsent(today, k -> new ArrayList<>()).add(command);
            return true;
        } else {
            // if something fails with execution -> return false
            return false;
        }
    }

    /**
     * Function to try to undo the last command
     * @return boolean value that returns the success of the operation
     */
    public boolean UndoLastCommand() {
        if (history.isEmpty())
            return false;
        Command command = history.remove();
        if (command != null && command.Undo()) {
            // Remove the command from the map
            MonthDay today = MonthDay.from(LocalDate.now());

            List<Command> commandsForDay = allCommands.get(today);
            if (commandsForDay != null) {
                commandsForDay.remove(command);
                // Clean up if list becomes empty
                if (commandsForDay.isEmpty()) {
                    allCommands.remove(today);
                }
            }

            return true;
        }
        return false;
    }


    /**
     * Get all commands for a specific day
     * @param day the MonthDay to query
     * @return list of commands or empty list
     */
    public List<Command> getCommandsForDay(MonthDay day) {
        return allCommands.getOrDefault(day, Collections.emptyList());
    }

    /**
     * Get the list of all days in the order they were added
     * @return Set of MonthDay in order
     */
    public Set<MonthDay> getAllDaysInOrder() {
        return allCommands.keySet();
    }

}
