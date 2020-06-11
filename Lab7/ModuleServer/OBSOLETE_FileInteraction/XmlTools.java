package Lab7.ModuleServer.OBSOLETE_FileInteraction;

/**
 * Вспомогательный класс для работы с xml
 */
public class XmlTools {

    /**
     * Получает таг из строки xml формата
     * @param line Строка xml формата
     * @return Таг строки
     */
    public static String getTag(String line) {
        try {
            String tempo = String.valueOf(line.toCharArray(), line.indexOf("<") + 1, line.indexOf(">")
                    - line.indexOf("<") - 1);
            if (tempo.charAt(0) != '/') return tempo;

            return String.valueOf(tempo.toCharArray(), 1, tempo.length() - 1);
        } catch (NullPointerException e){
            return "";
        }

    }

    /**
     * Получает сохраненныцю информацию из строки xml формата
     * @param line Строка xml формата
     * @return Информация из строки
     */
    public static String getData(String line) {
        try {
            return String.valueOf(line.toCharArray(), line.indexOf(">") + 1,
                    line.indexOf("</") - line.indexOf(">") - 1);
        } catch (StringIndexOutOfBoundsException e) {
            return "";
        }
    }

    /**
     * Создает строку xml формата
     * @param tag Таг xml-строки
     * @param data Информация xml-строки
     * @return Строка xml формата
     */
    public static String createXmlLine(String tag, String data) {
        return String.format("<%s>%s</%s>\n", tag, data, tag);
    }
}

