package duke.commands;

import duke.Ui;
import duke.exceptions.IncorrectIndexException;
import duke.task.Task;
import duke.task.TaskList;

/**
 * Command created when user types unmark as first word in input
 */
public class UnmarkCommand extends Command {

    public UnmarkCommand(Ui ui, TaskList taskList) {
        super(ui, taskList);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String executeCommand(String userInput) throws IncorrectIndexException {
        String[] input = userInput.split(" ");
        try {
            int taskToUnmark = Integer.parseInt(input[1]) - 1;
            Task currTask = taskList.get(taskToUnmark);
            currTask.setAsUndone();
            return ui.printUnmarkedTaskMessage(currTask.toString());
        } catch (IndexOutOfBoundsException e) {
            throw new IncorrectIndexException(taskList.getSize());
        }
    }
}
