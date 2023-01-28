package duke;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;


public class Storage {

    String filePath;
    String directoryPath;

    public Storage(String filePath, String directoryPath) {
        this.filePath = filePath;
        this.directoryPath = directoryPath;
    }

    TaskList<Task> readFile() throws NeroException {
        TaskList<Task> taskList = new TaskList<Task>();
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        try {
            File file = new File(filePath);
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] cleanedInputs = line.split(" \\| ");
                boolean isDone = (cleanedInputs[1].equals("0")) ? false : true;
                if (cleanedInputs[0].equals("T")) {
                    taskList.addTask(new ToDo(cleanedInputs[2], isDone));
                } else if (cleanedInputs[0].equals("D")) {
                    String cleanedDuration = cleanedInputs[3].replace("by:", "");
                    taskList.addTask(new Deadline(cleanedInputs[2], isDone, cleanedDuration));
                } else if (cleanedInputs[0].equals("E")) {
                    String[] splitDates = cleanedInputs[3].split(" ");
                    String cleanedStartDate = splitDates[0].replace("from: ", "");
                    String cleanedEndDate = splitDates[1].replace("to: ", "");
                    taskList.addTask(new Event(cleanedInputs[2], isDone, cleanedStartDate, cleanedEndDate));
                }
            }
            sc.close();
            return taskList;
        } catch (FileNotFoundException e) {
            throw new NeroException("File was not found!");
        }
    }

    void saveFile(TaskList<Task> taskList) throws IOException {
        try {
            FileWriter fw = new FileWriter(filePath);
            for (int i = 0; i < taskList.getSize(); i++) {
                fw.write(taskList.get(i).toSave() + "\n");
            }
            fw.close();
        } catch (IOException e) {
            System.out.println("The file was not found!");
        }
    }
}
