/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calendarizacionalumnos;
import java.util.Date;
import java.util.EnumSet;
import java.io.File;
import java.util.HashMap;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.*;

enum DiasDeClase
{
    lunes, martes, miercoles, jueves, viernes
}

public class ClassXML implements Comparable<ClassXML>
{
    private final int id;
    private final int idm;
    private final String nombre;
    private final Date horaInicio;
    private final Date horaFin;
    private final EnumSet<DiasDeClase> dias;
    
    //Getters
    public int getId()
    {
        return this.id;
    }
    
    public int getIdm()
    {
        return this.idm;
    }
    public String getNombre()
    {
        return this.nombre;
    }
    public Date getHoraInicio()
    {
        return this.horaInicio;
    }
    public Date getHoraFin()
    {
        return this.horaFin;
    }
    public EnumSet<DiasDeClase> getDias()
    {
        return this.dias;
    }
    
    public ClassXML(int id, int idm, String nombre, Date horaInicio, Date horaFin, EnumSet<DiasDeClase> dias)
    {
        this.id = id;
        this.idm = idm;
        this.nombre = nombre;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.dias = dias;
    }
    
    public static HashMap<Integer, ClassXML> ListCrearDeXML(String path)
    {
        try
        {    
            File file = new File(path);
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = docFactory.newDocumentBuilder();
            Document doc = builder.parse(file);
            
            HashMap<Integer, ClassXML> materias = new HashMap<>();
            NodeList list = doc.getElementsByTagName("materia");
            for(int i=0; i < list.getLength(); i++)
            {
                if(list.item(i).getNodeType() == Node.ELEMENT_NODE)
                {
                    Element node = (Element)list.item(i);
                   
                    //ID
                    int id = Integer.parseInt(node.getElementsByTagName("id").item(0).getTextContent());
                    //IDm
                    int idm = Integer.parseInt(node.getElementsByTagName("idm").item(0).getTextContent());
                    //Nombre
                    String name = node.getElementsByTagName("nombre").item(0).getTextContent();
                    //Tiempo
                    Date horainicio = XmlHelper.parseNodeTimeValue(node.getElementsByTagName("hinicio").item(0).getTextContent());
                    Date horafin = XmlHelper.parseNodeTimeValue(node.getElementsByTagName("hfin").item(0).getTextContent());
                    //Dias
                    NodeList dias = node.getElementsByTagName("dias").item(0).getChildNodes();
                    EnumSet<DiasDeClase> flags = EnumSet.noneOf(DiasDeClase.class);
                    for(int j=0; j < dias.getLength(); j++)
                    {
                        String dia = dias.item(j).getTextContent();
                        XmlHelper.parseNodeDaysValues(dia, flags);
                    }
                    //Materia
                    ClassXML tmp = new ClassXML(id, idm, name, horainicio, horafin, flags);
                    materias.put(id, tmp);
                }
            }
            return materias;
        }
        catch(Exception e)
        {
            System.err.println("ERROR ClassXML.java:");
            System.err.println(e.getMessage());
            return null;
        }
    }

    @Override
    public int compareTo(ClassXML c) 
    {
        long dif = this.horaFin.getTime() - c.getHoraFin().getTime();
        return dif < 0 ? -1 : 1; 
    }
    
}
