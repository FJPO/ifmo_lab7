package Lab7.CommandsM.General;

/**
 * Интерфейс для реализации команд, которые принамают какой-либо параметр на вход
 */
public interface ExecutableWithInput extends Executable {

    //Проверяет правильность

    /**
     * Сохраняет параметр, создан для работы через консоль
     * @param arg Параметр
     */
    void collectData(String arg);

    //не проверяет правильность

    /**
     * Сохраняет параметр без проверки на соответствие условиям, если произведена неверная запись данных,
     * метод isDataCorrect вернет false
     * @param arg Параметр
     */
    void recordData(String arg);

    /**
     * Возвращает true или false в зависимости от того, правильно ли сработал метод recordData
     */
    boolean isDataCorrect();
}
