package Lab7.Source;

import Lab7.ModuleServer.OBSOLETE_FileInteraction.XmlTools;

import java.io.Serializable;

public class Coordinates implements Xml_Interchangeable, Serializable {

    private Float x; //Максимальное значение поля: 226, Поле не может быть null
    private int y; //Максимальное значение поля: 668

    public Coordinates(Float x, int y){
        if (x == null || (x > 226) || (y > 668)) throw new IllegalArgumentException();
        this.x = x;
        this.y = y;
    }

    public Float getX(){
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return String.format("Координаты:(%.1f; %d)", x, y);
    }

    @Override
    public String createXml() {
        return XmlTools.createXmlLine("Coordinates",
                "\n" + XmlTools.createXmlLine("x", String.valueOf(x)) +
                        XmlTools.createXmlLine("y", String.valueOf(y)));
    }

}

