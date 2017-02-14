package calendarizacionalumnos;

import java.util.ArrayList;
import java.util.HashMap;

public class schedule 
{   
    public static ArrayList<ArrayList<ClassXML>> multiSchedule(ArrayList<ClassXML> s)
    {
        //Arreglo de diccionarios que contiene los horarios alternativos
        ArrayList<ArrayList<ClassXML>> A = new ArrayList<>();
        //Arreglo temporal para armar iterativamente los horarios
        ArrayList<ClassXML> subs = new ArrayList<>();
        s.forEach((c)->subs.add(c));
        //Arreglo de aceptados
        HashMap<Integer, Boolean> acc = new HashMap<>();
        //Arreglo con lo indices rechazados
        ArrayList<Integer> r = new ArrayList<>();
        while(acc.size() < s.size())
        {
            ArrayList<ClassXML> res = greedySearch(subs, acc, r);
            A.add(res);
            subs.clear();
            if(!r.isEmpty())
            {
                //Rehace arreglo temporal subs
                for(int i = 0; i < s.size(); i++)
                {
                    if(!r.contains(i+1))
                        subs.add(s.get(i));
                }
                r.clear();
            }
        }
        return A;
    }
    
    public static ArrayList<ClassXML> greedySearch(ArrayList<ClassXML> s, HashMap<Integer, Boolean> c, ArrayList<Integer> r)
    {
        ArrayList<ClassXML> a = new ArrayList<>();
        HashMap<Integer, Boolean> acc = new HashMap<>();
        int i = 0;
        a.add(s.get(i));
        c.put(s.get(i).getId(), true);
        acc.put(s.get(i).getIdm(), true);
        for(int m = 1; m < s.size(); m++)
        {
            ClassXML sm = s.get(m);
            ClassXML fi = s.get(i);
            if(acc.containsKey(sm.getIdm()))
                r.add(m);
            else
            {
                if(sm.getHoraInicio().getTime() >= fi.getHoraFin().getTime())
                {
                    a.add(sm);
                    c.put(sm.getId(),true);
                    acc.put(sm.getIdm(),true);
                    i = m;
                }
                else if(sm.getIdm() != fi.getIdm())
                {
                    Boolean check = true;
                    for(DiasDeClase d : fi.getDias())
                    {
                        if(sm.getDias().contains(d))
                        {
                            check = false;
                            break;
                        }
                    }
                    if(check)
                    {
                        a.add(sm);
                        c.put(sm.getId(),true);
                        acc.put(sm.getIdm(),true);
                        i = m;
                    }
                    else
                        r.add(m);
                }
            }
        }
        return a;
    }
}
