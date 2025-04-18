package com.rostyslavliapkin.spendingbuddy.managers;

import com.rostyslavliapkin.spendingbuddy.core.commands.Command;

import java.util.LinkedList;
import java.util.Queue;

public class CommandsManager {
    /**
     * Queue to store the history of the command
     */
    Queue<Command> history;

    /**
     * Constructor for CommandsManager
     */
    public CommandsManager(){
        history = new LinkedList<>();
    }

    /**
     *
     * @param command Command to execute
     * @return boolean value that means the success of the operation
     */
    public boolean ExecuteCommand(Command command){
        // check if null
        if (command == null)
            return false;
        // try to execute
        if(command.Execute()){
            // if success with execution -> return true
            history.add(command);
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
    public boolean UndoLastCommand(){
        if (history.isEmpty())
            return false;

        Command command = history.remove();
        // check if there are any commands in the history
        if (command != null){
            // try to undo command
            if (command.Undo()){
                // if we can undo -> return true (success)
                return true;
            } else {
                // if undo fails -> return false (fail)
                return false;
            }
        } else {
            // if there are no commands in the history -> return false
            return  false;
        }
    }
}
