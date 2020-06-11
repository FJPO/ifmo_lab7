package Lab7.CommandsM;

import Lab7.CommandsM.General.*;
import Lab7.Sercurity.User;
import Lab7.Source.LabWork;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

/**
 * Класс, осуществляющий чтение команд из файла
 */
public class ExecuteScript implements ExecutableWithInput, ExecutableCommandMaker, ExecutableWithRightsNeeded {

    private File file;
    private boolean success = true;
    private User user;

    @Override
    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String getName() {
        return "EXECUTE_SCRIPT";
    }

    @Override
    public void collectData(String arg) {
        recordData(arg);
        if(!success){
            System.out.println("Такого файла не существует, введите названия файла");
            recordData(new Scanner(System.in).nextLine());
        }
    }

    @Override
    public void recordData(String arg) {
        file = new File(arg);
        if(!file.exists()) success = false;
    }

    @Override
    public void execute(List<LabWork> list) {
        throw new RuntimeException("Не предполагается, что этот метод должен быть запущен");
    }

    @Override
    public void printAnswer() {
        throw new RuntimeException("Не предполагается, что этот метод должен быть запущен");
    }

    @Override
    public Executable[] getCommands() {

        ArrayList<Executable> commandsFromScript = new ArrayList<>();
        if(!FileOpenedController.canOpen(file.getName())){
            System.out.println("Файл не может быть исполнен: возможна ошибка зацикливания");
            return new Executable[0];
        }else{
            FileOpenedController.addToOpened(file.getName());
        }

        BufferedReader bufferedReader;
        try {
            bufferedReader = new BufferedReader(new FileReader(file));
        }catch(FileNotFoundException e){
            return null;
        }

        Object[] objLines = bufferedReader.lines().toArray();
        String[] lines;
        for (int i = objLines.length-1; i > 0; i--) {
            String tempo, commandName;
            tempo = objLines[i].toString();
            if (!tempo.contains(" ")) commandName = tempo;
            else commandName = String.valueOf(tempo.toCharArray(), 0, tempo.indexOf(" "));

            if (!CommandKeeper.exists(commandName)) {
                objLines[i - 1] = objLines[i - 1].toString() + " " + objLines[i].toString();
                objLines[i] = null;
            }
        }


        lines = Arrays.stream(objLines).filter(Objects::nonNull).toArray(String[]::new);

        for (String currentLine : lines) {
            String commandName, commandArg;
            if (!currentLine.contains(" ")) {
                commandName = currentLine;
                commandArg = "";
            } else {
                commandName = String.valueOf(currentLine.toCharArray(), 0, currentLine.indexOf(" "));
                commandArg = String.valueOf(currentLine.toCharArray(), currentLine.indexOf(" ") + 1,
                        currentLine.length() - currentLine.indexOf(" ") - 1);
            }
            commandsFromScript.add(CommandKeeper.getCommandFromScript(commandName, commandArg, user));
        }

        return commandsFromScript.stream().filter(element -> {
            if(element instanceof ExecutableWithInput){
                boolean t = ((ExecutableWithInput)element).isDataCorrect();
                if(!t) System.out.printf("Параметры команды %s неверны, она не будет исполнена\n", element.getName());

                return t;
            }else return true;
        }).toArray(Executable[]::new);

    }

    @Override
    public boolean isDataCorrect() {
        return success;
    }

    public File getFileReading(){ return file; }
}
