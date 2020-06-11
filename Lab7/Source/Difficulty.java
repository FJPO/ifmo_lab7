package Lab7.Source;

import Lab7.ModuleServer.OBSOLETE_FileInteraction.XmlTools;

import java.io.Serializable;

public enum Difficulty implements Xml_Interchangeable, Serializable {
    VERY_EASY("Очень просто"),
    NORMAL("Средне"),
    VERY_HARD("Очень сложно"),
    INSANE("Невозможно"),
    TERRIBLE("Ужасно");

    private final String name;
    Difficulty(String name){this.name = name;}

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Сложность: " + name + " (" + this.name() + ")";
    }


    @Override
    public String createXml() {
        return XmlTools.createXmlLine("Difficulty", "\n" + this.name() + "\n");

    }




}
