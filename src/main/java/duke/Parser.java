package duke;

/**
 * Handles the reading and execution of inputs
 */
public class Parser {

    int counter;


    enum Types {LIST, MARK, UNMARK, TODO, DEADLINE, EVENT, DELETE, BYE}

    Parser() {
    }

    /**
     * Reads input from user and splits it into cases based on Types specified
     * in enum Types
     * @param originalString Input from user
     * @param taskList List containing all current tasks
     * @param ui Ui that runs output
     * @return Boolean based on input. Only returns true when command is bye, which
     * exits the chat bot. Else, returns false which continues the chat bot
     * @throws NeroException Throws an exception depending on the exception faced
     */
    boolean parseCommand(String originalString, TaskList<Task> taskList, Ui ui)
            throws NeroException {
        try {
            String[] input = originalString.split(" ");
            switch (Enum.valueOf(Types.class, input[0].toUpperCase())) {
            case BYE:
                ui.printGoodbyeMessage();
                return true;
            case LIST:
                ui.printTasksMessage();
                taskList.printTasks();
                return false;
            case MARK: {
                try {
                    int taskToMark = Integer.parseInt(input[1]) - 1;
                    Task currTask = taskList.get(taskToMark);
                    currTask.setAsDone();
                    ui.printMarkedTaskMessage(currTask.toString());
                } catch (IndexOutOfBoundsException e) {
                    throw new NeroException("Please add the correct index from 0 to "
                            + taskList.getSize());
                }
                return false;
            }
            case UNMARK: {
                try {
                    int taskToUnmark = Integer.parseInt(input[1]) - 1;
                    Task currTask = taskList.get(taskToUnmark);
                    currTask.setAsUndone();
                    ui.printUnmarkedTaskMessage(currTask.toString());
                } catch (IndexOutOfBoundsException e) {
                    throw new NeroException("Please add the correct index from 0 to "
                            + taskList.getSize());
                }
                return false;
            }
            case TODO:
                int index = originalString.indexOf("todo");
                try {
                    String toAdd = originalString.substring(index + 5);
                    Task newTask = new ToDo(toAdd);
                    taskList.addTask(newTask);
                    ui.printAddedTasks(newTask.toString(), taskList.getSize());
                } catch (IndexOutOfBoundsException e) {
                    throw new NeroException("Description cannot be empty!!!");
                }
                return false;
            case DEADLINE:
                try {
                    String[] splitString = originalString.split("/");
                    String description = splitString[0].replace("deadline", "");
                    String duration = splitString[1].replace("by", "");
                    Task newTask = new Deadline(description, duration);
                    taskList.addTask(newTask);
                    ui.printAddedTasks(newTask.toString(), taskList.getSize());
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("Add a task description and deadline!!!");
                }
                return false;
            case EVENT:
                try {
                    String[] splitString = originalString.split("/");
                    String description = splitString[0].replace("event", "");
                    String startDate = splitString[1].replace("from", "") ;
                    String endDate = splitString[2].replace("to", "");
                    Task newTask = new Event(description, startDate, endDate);
                    taskList.addTask(newTask);
                    ui.printAddedTasks(newTask.toString(), taskList.getSize());
                } catch (IndexOutOfBoundsException e) {
                    throw new NeroException("Add a task description, from and to date!!!");
                }
                return false;
            case DELETE:
                try {
                    int toDelete = Integer.parseInt(input[1]) - 1;
                    Task removedTask = taskList.get(toDelete);
                    taskList.removeTask(toDelete);
                    ui.printDeletedTasks(removedTask.toString(), taskList.getSize());
                } catch (IndexOutOfBoundsException e) {
                    throw new NeroException("Add a correct task number");
                }
                return false;
            default:
                return false;
            }
        } catch (IllegalArgumentException e) {
            ui.printWrongInput();
            return false;
        }
    }
}
