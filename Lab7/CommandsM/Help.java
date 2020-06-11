package Lab7.CommandsM;

import Lab7.CommandsM.General.Executable;
import Lab7.Source.LabWork;

import java.util.List;

/**
 * Класс, выводящий список команд
 */
public class Help implements Executable {

    @Override
    public String getName() {
        return "HELP";
    }

    @Override
    public void execute(List<LabWork> list) {}

    @Override
    public void printAnswer() {
        System.out.println("\nСписок команд представлен ниже\n" +
                "help : вывести справку по доступным командам\n" +
                "info : вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)\n" +
                "show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении\n" +
                "add {element} : добавить новый элемент в коллекцию\n" +
                "update id {element} : обновить значение элемента коллекции, id которого равен заданному\n" +
                "remove_by_id id : удалить элемент из коллекции по его id\n" +
                "clear : очистить коллекцию\n" +
                "save : сохранить коллекцию в файл(не доступен с клиента)\n" +
                "execute_script file_name : считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.\n" +
                "exit : завершить программу\n" +
                "remove_at index : удалить элемент, находящийся в заданной позиции коллекции (index)\n" +
                "add_if_max {element} : добавить новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции\n" +
                "shuffle : перемешать элементы коллекции в случайном порядке\n" +
                "filter_greater_than_difficulty difficulty : вывести элементы, значение поля difficulty которых больше заданного\n" +
                "print_ascending : вывести элементы коллекции в порядке возрастания\n" +
                "print_descending : вывести элементы коллекции в порядке убывания");
    }
}
