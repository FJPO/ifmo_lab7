package Lab7.CommandsM;

import Lab7.CommandsM.General.Executable;
import Lab7.Source.LabWork;

import java.util.List;

/**
 * Класс, осуществляющий завршение работы клиента
 */
public class Exit implements Executable {
    @Override
    public String getName() {
        return "EXIT";
    }

    @Override
    public void execute(List<LabWork> list) {
        System.out.println("Клиент отключился");
    }

    @Override
    public void printAnswer() {
        System.out.println("Команда выполняется");
        System.exit(0);
    }
}
