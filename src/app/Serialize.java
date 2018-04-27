package app;

import java.io.Serializable;
import java.util.ArrayList;

public class Serialize implements Serializable {
    //klasa utworzona aby nie dawac kazdej listy do osobnego pliku
    public ArrayList<Resource> first;
    public ArrayList<Resource> second;
    public ArrayList<Resource> third;
}
