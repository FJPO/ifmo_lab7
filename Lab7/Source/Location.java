package Lab7.Source;

import Lab7.ModuleServer.OBSOLETE_FileInteraction.XmlTools;

import java.io.Serializable;

public class Location implements Xml_Interchangeable, Serializable {
    private Float x; //Поле не может быть null
    private int y;
    private Long z; //Поле не может быть null

    public Location(Float x, int y, Long z){
        if (x == null || z == null) throw new IllegalArgumentException();
        this.x = x;
        this.y = y;
        this.z = z;
    }

    //getters
    public Float getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Long getZ() {
        return z;
    }

    @Override
    public String toString() {
        return String.format("Местонахождение: (%.1f; %d; %d)", x, y, z);
    }

    @Override
    public String createXml() {
        return XmlTools.createXmlLine("Location",  "\n" + XmlTools.createXmlLine("x" , x.toString()) +
                XmlTools.createXmlLine("y", String.valueOf(y)) + XmlTools.createXmlLine("z", z.toString()) );
    }
}
