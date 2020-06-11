package Lab7.CommandsM.General;

import Lab7.CommandsM.*;
import Lab7.Sercurity.User;

import java.util.HashMap;
import java.util.Scanner;

/**
 * Класс, через который происходит работа с командами
 */
public class CommandKeeper {

    private static final HashMap<String, Constructor> commands = new HashMap<>();

    private interface Constructor{
        Executable create();
    }

    static{
        commands.put(new Info().getName(), Info::new);
        commands.put(new RemoveById().getName(), RemoveById::new);
        commands.put(new AddElement().getName(), AddElement::new);
        commands.put(new Show().getName(), Show::new);
        commands.put(new Update().getName(), Update::new);
        commands.put(new ExecuteScript().getName(), ExecuteScript::new);
        commands.put(new Help().getName(), Help::new);
        commands.put(new AddIfMax().getName(), AddIfMax::new);
        commands.put(new Clear().getName(), Clear::new);
        commands.put(new Exit().getName(), Exit::new);
        commands.put(new FilterGreaterThanDifficulty().getName(), FilterGreaterThanDifficulty::new);
        commands.put(new PrintAscending().getName(), PrintAscending::new);
        commands.put(new PrintDescending().getName(), PrintDescending::new);
        commands.put(new RemoveAtIndex().getName(), RemoveAtIndex::new);
        commands.put(new Shuffle().getName(), Shuffle::new);
        commands.put(new AddUser().getName(), AddUser::new);
    }


    /**
     * Возвращает команду, готовую к выполнению (к отправке на сервер)
     * @param name Stirng-название команды
     * @param arg Аргумент команды или пустая строка, если аргумента нет
     * @return Комада, готовая к выполнению
     */
    public static Executable getPreparedCommand(String name, String arg){

        Executable result;
        name = name.toUpperCase();

        if(commands.containsKey(name))
            result = commands.get(name).create();
        else return null;

        if(result instanceof ExecutableWithRightsNeeded) {
            Scanner scanner = new Scanner(System.in);
            String login, password;
            System.out.println("Введите логин:"); login = scanner.nextLine();
            System.out.println("Введите пароль:");
            //password = scanner.nextLine();
            password = new String(System.console().readPassword());
            ((ExecutableWithRightsNeeded) result).setUser(new User(login, User.getPasswordHash(password)));
        }

        if(result instanceof ExecutableWithInput)((ExecutableWithInput)result).collectData(arg);

        return result;
    }

    public static Executable getCommandFromScript(String name, String arg, User user){
        Executable result;
        name = name.toUpperCase();

        if(commands.containsKey(name))
            result = commands.get(name).create();
        else return null;

        if(result instanceof ExecutableWithRightsNeeded) ((ExecutableWithRightsNeeded) result).setUser(user);
        if(result instanceof ExecutableWithInput) ((ExecutableWithInput)result).recordData(arg);



        return result;
    }
    /**
     * Проверяет, существует ли команда с таким названием
     * @param commandName String-название команды
     */
    public static boolean exists(String commandName){
        boolean result;
        result = commands.containsKey(commandName.toUpperCase());
        return result;

    }
}
