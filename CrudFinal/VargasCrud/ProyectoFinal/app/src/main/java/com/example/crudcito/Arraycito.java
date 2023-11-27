package com.example.crudcito;
import java.util.ArrayList;
public class Arraycito {
    ArrayList<Basurita> adatitos = new ArrayList<>();
    public void agregar(int dato) {
        int claves[] = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        String nombres[] = {"Celtics", "Bulls", "Nets", "Magic", "Bucks",
                "Clippers", "Suns", "Mavericks", "Kings", "Spurs"};
        if (dato == 1)
            for (int i = 0; i < 5; ++i) {
                Basurita objetito = new Basurita();
                objetito.setClave(claves[i]);
                objetito.setNombre(nombres[i]);
                adatitos.add(objetito);
            }
        else if (dato == 2)
            for (int i = 5; i < 10; ++i) {
                Basurita objetito = new Basurita();
                objetito.setClave(claves[i]);
                objetito.setNombre(nombres[i]);
                adatitos.add(objetito);
            }
    }
    public ArrayList<Basurita> regresar() {
        return adatitos;
    }
}