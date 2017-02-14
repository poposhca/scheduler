/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calendarizacionalumnos;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.EnumSet;
import java.util.Date;

public class XmlHelper 
{
    public static int parseNodeIntValue(String val)
    {
        return Integer.parseInt(val);
    }
    
    public static Date parseNodeTimeValue(String time) throws Exception
    {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        try
        {
            return format.parse(time);
        }
        catch(ParseException e)
        {
            throw new Exception("Error al comvertir el valor " + time + "del XML: " + e.getMessage());
        }
    }
    
    public static void parseNodeDaysValues(String val, EnumSet<DiasDeClase> flags)
    {
        switch(val)
        {
            case "lunes":
                flags.add(DiasDeClase.lunes);
                break;
            case "martes":
                flags.add(DiasDeClase.martes);
                break;
            case "miercoles":
                flags.add(DiasDeClase.miercoles);
                break;
            case "jueves":
                flags.add(DiasDeClase.jueves);
                break;
            case "viernes":
                flags.add(DiasDeClase.viernes);
                break;
        }
    }
}
