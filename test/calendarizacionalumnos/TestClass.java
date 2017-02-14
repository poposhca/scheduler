/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package calendarizacionalumnos;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Date;
import java.util.EnumSet;
import java.util.HashMap;

/**
 *
 * @author poposhca
 */
public class TestClass {
    
    private final String path = "/Users/icloud/Desktop/test.xml";
    
    public TestClass() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    
    @Test
    public void HelperDays()
    {
        String dia1 = "lunes";
        String dia2 = "jueves";
        EnumSet<DiasDeClase> set = EnumSet.noneOf(DiasDeClase.class);
        
        XmlHelper.parseNodeDaysValues(dia1, set);
        assertTrue(set.contains(DiasDeClase.lunes));
        
        XmlHelper.parseNodeDaysValues(dia2, set);
        assertTrue(set.contains(DiasDeClase.lunes) && set.contains(DiasDeClase.jueves));
        assertFalse(set.contains(DiasDeClase.lunes) && set.contains(DiasDeClase.martes));
    }
    
    @Test
    public void HelperDate()
    {
        String hora = "13:32";
        
        try
        {
            Date date = XmlHelper.parseNodeTimeValue(hora);
            assertNotNull(date);
            assertTrue(date.getHours() == 13);
            assertTrue(date.getMinutes()== 32);
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
            assertTrue(false);
        }
        
    }
    
    @Test
    public void ReadXML()
    {
        HashMap<Integer, ClassXML> list = ClassXML.ListCrearDeXML(this.path);
        
        //Regresa Lista
        assertFalse(list == null);
        
        //Hay dos objetos de clase
        assertTrue(list.size()== 4);
    }
   
}
