package Lab7.CommandsM.General;

/**
 * Интерфейс для работы с командой execute_script
 */
public interface ExecutableCommandMaker extends Executable{
    /**
     * Возвращает массив команд, готовых к отправке на сервер
     */
    Executable[] getCommands();
}
