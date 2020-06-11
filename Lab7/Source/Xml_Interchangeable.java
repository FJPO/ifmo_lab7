package Lab7.Source;

/**
 * Интерфейс-родитель для класов-испочников, позволяющтй сохранять их как xml
 */
public interface Xml_Interchangeable {
    /**
     * Преобразует поля элемента в xml-строку
     * @return Поля элемента, организованные в xml-строку
     */
    String createXml();
}
