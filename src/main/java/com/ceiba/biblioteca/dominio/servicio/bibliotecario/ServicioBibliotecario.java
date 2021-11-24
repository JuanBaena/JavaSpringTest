package com.ceiba.biblioteca.dominio.servicio.bibliotecario;

import com.ceiba.biblioteca.dominio.excepcion.PrestamoException;
import com.ceiba.biblioteca.dominio.repositorio.RepositorioLibro;
import com.ceiba.biblioteca.dominio.repositorio.RepositorioPrestamo;
import com.ceiba.biblioteca.dominio.Libro;
import com.ceiba.biblioteca.dominio.Prestamo;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.text.ParseException;

public class ServicioBibliotecario {

    public static final String EL_LIBRO_NO_SE_ENCUENTRA_DISPONIBLE = "El libro no se encuentra disponible";
    public static final String LOS_LIBROS_PALINDROMOS = "Los libros palíndromos sólo se" +
            " pueden utilizar en la biblioteca";

    private final RepositorioLibro repositorioLibro;
    private final RepositorioPrestamo repositorioPrestamo;

    public ServicioBibliotecario(RepositorioLibro repositorioLibro, RepositorioPrestamo repositorioPrestamo) {
        this.repositorioLibro = repositorioLibro;
        this.repositorioPrestamo = repositorioPrestamo;
    }

    public void prestar(String isbn, String nombreUsuario) {
        if(!this.esPrestado(isbn)){
            if(!this.esPalíndromo(isbn)) {
                Date InicialDate = new Date();
                Date MaxDate = null;
                if(ISBNMayorA30(isbn))
                    MaxDate = this.CalcularFechaEntrega(isbn, InicialDate);
                Libro libro = repositorioLibro.obtenerPorIsbn(isbn);
                Prestamo prestamo = new Prestamo(InicialDate, libro, MaxDate, nombreUsuario);
                repositorioPrestamo.agregar(prestamo);
            }else {
                throw new PrestamoException(LOS_LIBROS_PALINDROMOS);
            }
        }
        else{
            System.out.println(EL_LIBRO_NO_SE_ENCUENTRA_DISPONIBLE+" "+isbn);
            throw new PrestamoException(EL_LIBRO_NO_SE_ENCUENTRA_DISPONIBLE);
        }
    }

    public boolean esPrestado(String isbn) {
        Libro libro = repositorioPrestamo.obtenerLibroPrestadoPorIsbn(isbn);
        if (libro != null){
            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean esPalíndromo(String isbn) {
        String sCadenaInvertida = "";
        for (int x=isbn.length()-1;x>=0;x--){
            sCadenaInvertida = sCadenaInvertida + isbn.charAt(x);
        }
        if (isbn.equals(sCadenaInvertida)){
            return true;
        }
        else
        {
            return false;
        }
    }

    public Date CalcularFechaEntrega(String isbn, Date InicialDate){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String PrevDate = sdf.format(InicialDate);
        //System.out.println("Date before Addition: "+PrevDate);
        Calendar c = Calendar.getInstance();
        c.setTime(InicialDate);

        int daysCount = 0;
        while (daysCount < (15-1)) {
            if(c.get(Calendar.DAY_OF_WEEK) != 1){
                daysCount++;
            }
            c.add(Calendar.DAY_OF_MONTH, 1);
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 1)
            c.add(Calendar.DAY_OF_MONTH, 1);

        Date MaxDate = c.getTime();
        String inActiveDate = sdf.format(MaxDate);
        //System.out.println("Date after me: "+ inActiveDate);

        return MaxDate;
    }

    public boolean ISBNMayorA30(String isbn){
        int sumaSimbolosNumericos = 0;
        for(int i=0;i<isbn.length();i++){
            boolean flag = Character.isDigit(isbn.charAt(i));
            if(flag) {
                int a = Character.getNumericValue(isbn.charAt(i));
                sumaSimbolosNumericos = sumaSimbolosNumericos + a;
            }
        }
        //System.out.println(sumaSimbolosNumericos);
        if(sumaSimbolosNumericos>30)
            return true;
        else
            return false;
    }


}
