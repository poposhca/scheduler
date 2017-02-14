/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calendarizacionalumnos;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.Collections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;

/**
 * FXML Controller class
 *
 * @author poposhca
 */
public class MainUIController implements Initializable 
{
    @FXML
    private ListView<ClassCell> listViewCatalogo;
    @FXML
    private ListView listViewSeleccionado;
    @FXML
    private ListView listViewSchedule;
    @FXML
    private Label resultLabel;
    
    private final String path = "/Users/icloud/OneDrive/MCC/Algoritmos/CalendarizacionAlumnos/test.xml";
    private HashMap<Integer, ClassXML> clases;
    private ObservableList<ClassCell> observableClass;
    private ObservableList<ClassCell> observableSelected;
    private ObservableList<ClassXML> observableSchedule;
    private ArrayList<ArrayList<ClassXML>> resSchedule;
    
    private Boolean catalogSelected;
    private int selectedCell;
    private int selectedSchedule = 0;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        // Carga las clases del catalogo
        try
        {
            //Llena e inicializa el listview que muestra todo el catalogo
            this.clases = ClassXML.ListCrearDeXML(this.path);
            ArrayList<ClassCell> cells = fillCells();
            this.observableClass = FXCollections.observableArrayList(cells);
            this.listViewCatalogo.setItems(this.observableClass);
            setlistViewProp(this.listViewCatalogo);
            
            //Inicializa el listview donde el usuario selecciona las materias
            this.observableSelected = FXCollections.observableArrayList();
            this.listViewSeleccionado.setItems(this.observableSelected);
            setlistViewProp(this.listViewSeleccionado);
            
            //Inicializa el listview donde se ve el horario
            this.observableSchedule = FXCollections.observableArrayList();
            this.listViewSchedule.setItems(this.observableSchedule);
            setlistViewSchedule(this.listViewSchedule);
            
            //Eventos en el listview que muestra todo el catalogo
            this.listViewCatalogo.getSelectionModel().selectedIndexProperty()
                    .addListener((observable, oldValue, newValue)->{
                        this.selectedCell = newValue.intValue();
                        this.catalogSelected = true;
                    });
            
            //Eventos en la listview donde el usuario selecciona las materias
            this.listViewSeleccionado.getSelectionModel().selectedIndexProperty()
                    .addListener((observable, oldValue, newValue)->{
                        this.selectedCell = newValue.intValue();
                        this.catalogSelected = false;
                    });
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
    
    private ArrayList<ClassCell> fillCells()
    {
        ArrayList<ClassCell> cells = new ArrayList<>();
        HashMap<String, Integer> check = new HashMap<>();
        this.clases.values().forEach((c) -> {
            if(check.containsKey(c.getNombre()))
            {
                ClassCell cell = cells.get(check.get(c.getNombre()));
                cell.ids.add(c.getId());
            }
            else
            {
                String hi = c.getHoraInicio().getHours() + ":" + c.getHoraInicio().getMinutes();
                String hf = c.getHoraFin().getHours() + ":" + c.getHoraFin().getMinutes();
                ClassCell cell = new ClassCell(c.getId(), c.getNombre(), hi, hf);
                check.put(c.getNombre(), check.size());
                cells.add(cell);
            }
        });
        return cells;
    }
    
    private void setlistViewProp(ListView<ClassCell> listView)
    {
        listView.setCellFactory((list)->{
            return new ListCell<ClassCell>(){
                @Override
                protected void updateItem(ClassCell item, boolean empty)
                {
                    super.updateItem(item, empty);
                    if(item == null || empty)
                        setText(null);
                    else
                        setText(item.nombre);
                }
            };
        });
    }
    
    private void setlistViewSchedule(ListView<ClassXML> listView)
    {
        listView.setCellFactory((list)->{
            return new ListCell<ClassXML>(){
                @Override
                protected void updateItem(ClassXML item, boolean empty)
                {
                    super.updateItem(item, empty);
                    if(item == null || empty)
                        setText(null);
                    else
                    {
                        String hinicio = item.getHoraInicio().getHours() + ":" + item.getHoraInicio().getMinutes();
                        String hfinal = item.getHoraFin().getHours() + ":" + item.getHoraFin().getMinutes();
                        setText(item.getNombre() + "  " + hinicio + " - " + hfinal + " " + item.getDias());
                    }
                }
            };
        });
    }
    
    private void changeObservableSchedule(int i)
    {
        ArrayList<ClassXML> h = this.resSchedule.get(i);
        this.observableSchedule.setAll(h);
    }
    
    @FXML
    public void agregarMateria()
    {
        if(catalogSelected)
        {
            ClassCell tmp = this.observableClass.get(this.selectedCell);
            this.observableSelected.add(tmp);
            this.observableClass.remove(this.selectedCell);
        }
    }
    
    @FXML
    public void quitarMateria()
    {
        if(!catalogSelected)
        {
            ClassCell tmp = this.observableSelected.get(this.selectedCell);
            this.observableSelected.remove(this.selectedCell);
            this.observableClass.add(tmp);
        }
    }
    
    @FXML
    public void nextSchedule()
    {
        if(this.selectedSchedule + 1 < this.resSchedule.size())
        {
            this.selectedSchedule += 1;
            changeObservableSchedule(this.selectedSchedule);
        }
    }
    
    @FXML
    public void prevSchedule()
    {
        if(0 <= this.selectedSchedule - 1)
        {
            this.selectedSchedule -= 1;
            changeObservableSchedule(this.selectedSchedule);
        }
    }
    
    @FXML
    public void schedule()
    {
        if(this.observableSelected.isEmpty())
            return;
        ArrayList<ClassXML> selected = new ArrayList<>();
        this.observableSelected.forEach((c) -> {
            c.ids.forEach((i) -> {
                selected.add(this.clases.get(i));
            });
        });
        Collections.sort(selected);
        this.resSchedule = schedule.multiSchedule(selected);
        changeObservableSchedule(0);
        this.resultLabel.setText(Integer.toString(this.resSchedule.size()));
        
        //DEBUG
        System.out.println("Vuelta:");
        resSchedule.forEach((h)->{
            System.out.println("Horario:");
            h.forEach((c->{
                System.out.println(c.getNombre()+"--"+c.getDias().toString()+"--"+c.getHoraFin().getTime());
            }));
        });
    }
}
