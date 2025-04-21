package com.rostyslavliapkin.spendingbuddy.managers;

import com.rostyslavliapkin.spendingbuddy.core.ResourceEntity;
import com.rostyslavliapkin.spendingbuddy.core.commands.Command;
import java.time.LocalDate;

import java.util.*;

/**
 * Class that controls all the commands
 */
public class CommandsManager {
    /**
     * Map of all commands
     */
    public Map<LocalDate, List<Command>> allCommands;

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
        if (command == null)
            return false;

        if (command.Execute()) {
            history.add(command);
            LocalDate today = LocalDate.now();
            allCommands.computeIfAbsent(today, _ -> new ArrayList<>()).add(command);
            return true;
        } else {
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
            LocalDate today = LocalDate.now();
            List<Command> commandsForDay = allCommands.get(today);

            if (commandsForDay != null) {
                commandsForDay.remove(command);
                if (commandsForDay.isEmpty()) {
                    allCommands.remove(today);
                }
            }

            return true;
        }

        return false;
    }

    /**
     * Removes all commands where this entity was
     * @param entity to be removed
     */
    public void RemoveEntity(ResourceEntity entity) {
        for (Iterator<Map.Entry<LocalDate, List<Command>>> it = allCommands.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry<LocalDate, List<Command>> entry = it.next();
            List<Command> commands = entry.getValue();

            // Remove commands that involve the entity
            commands.removeIf(command -> command.InvolvesEntity(entity));

            // Remove the date entry if the list is now empty
            if (commands.isEmpty()) {
                it.remove();
            }
        }

        // Also clean from history queue
        history.removeIf(command -> command.InvolvesEntity(entity));
    }

    /**
     * Remove a specific command from the manager, both from history and from the allCommands map.
     * @param command the command to remove
     * @return true if removed, false otherwise
     */
    public boolean RemoveCommand(Command command) {
        boolean removed = false;

        // Remove from history queue
        removed |= history.remove(command);

        // Remove from allCommands map
        for (Iterator<Map.Entry<LocalDate, List<Command>>> it = allCommands.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry<LocalDate, List<Command>> entry = it.next();
            List<Command> commands = entry.getValue();
            if (commands.remove(command)) {
                removed = true;
                if (commands.isEmpty()) {
                    it.remove();
                }
                break; // stop after first match
            }
        }

        return removed;
    }




    /**
     * Get all commands for a specific date.
     * @param date the LocalDate to query
     * @return list of commands or empty list
     */
    public List<Command> getCommandsForDate(LocalDate date) {
        return allCommands.getOrDefault(date, Collections.emptyList());
    }

    /**
     * Get all dates for which commands exist, in insertion order.
     * @return set of LocalDate
     */
    public Set<LocalDate> getAllDatesInOrder() {
        return allCommands.keySet();
    }

}
