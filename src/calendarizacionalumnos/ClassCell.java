/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calendarizacionalumnos;

import java.util.ArrayList;

public class ClassCell 
{
    public ArrayList<Integer> ids;
    public String nombre;
    public String inicio;
    public String fin;
    
    public ClassCell(int id, String nombre)
    {
        this(id, nombre, "", "");
    }
    
    public ClassCell(int id, String nombre, String inicio, String fin)
    {
        this.ids = new ArrayList<>();
        this.ids.add(id);
        this.nombre = nombre;
        this.inicio = inicio;
        this.fin = fin;
    }
}
