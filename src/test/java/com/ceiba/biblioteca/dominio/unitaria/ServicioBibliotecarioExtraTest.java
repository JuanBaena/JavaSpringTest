package com.ceiba.biblioteca.dominio.unitaria;

import com.ceiba.biblioteca.dominio.Libro;
import com.ceiba.biblioteca.dominio.Prestamo;
import com.ceiba.biblioteca.dominio.repositorio.RepositorioLibro;
import com.ceiba.biblioteca.dominio.repositorio.RepositorioPrestamo;
import com.ceiba.biblioteca.dominio.servicio.bibliotecario.ServicioBibliotecario;
import com.ceiba.biblioteca.testdatabuilder.LibroTestDataBuilder;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.text.ParseException;

public class ServicioBibliotecarioExtraTest {

    @Test
    public void libroEsPalindromo() {

        Libro libro = new Libro("12B3B21","El mundo de sofía",2010);
        RepositorioPrestamo repositorioPrestamo = mock(RepositorioPrestamo.class);
        RepositorioLibro repositorioLibro = mock(RepositorioLibro.class);
        ServicioBibliotecario servicioBibliotecario = new ServicioBibliotecario(repositorioLibro, repositorioPrestamo);

        boolean palindromo = servicioBibliotecario.esPalíndromo(libro.getIsbn()) ;
        assertTrue(palindromo);
    }

    @Test
    public void libroNoEsPalindromo() {

        Libro libro = new Libro("12B3B22","El mundo de sofía",2010);
        RepositorioPrestamo repositorioPrestamo = mock(RepositorioPrestamo.class);
        RepositorioLibro repositorioLibro = mock(RepositorioLibro.class);
        ServicioBibliotecario servicioBibliotecario = new ServicioBibliotecario(repositorioLibro, repositorioPrestamo);

        boolean palindromo = servicioBibliotecario.esPalíndromo(libro.getIsbn());
        assertFalse(palindromo);
    }

    @Test
    public void NumerosISBNMayorA30() {

        RepositorioPrestamo repositorioPrestamo = mock(RepositorioPrestamo.class);
        RepositorioLibro repositorioLibro = mock(RepositorioLibro.class);
        ServicioBibliotecario servicioBibliotecario = new ServicioBibliotecario(repositorioLibro, repositorioPrestamo);

        boolean up30 = servicioBibliotecario.ISBNMayorA30("99abc76");
        assertTrue(up30);
        boolean less30 = servicioBibliotecario.ISBNMayorA30("123abc123");
        assertFalse(less30);
    }

    @Test
    public void DiferenciaFecha() throws ParseException {

        RepositorioPrestamo repositorioPrestamo = mock(RepositorioPrestamo.class);
        RepositorioLibro repositorioLibro = mock(RepositorioLibro.class);
        ServicioBibliotecario servicioBibliotecario = new ServicioBibliotecario(repositorioLibro, repositorioPrestamo);
        String initialDate = "2017-05-26";
        String finalDate = "2017-06-12";
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        cal.setTime(sdf.parse(initialDate));
        Date oldDate = cal.getTime();
        cal.setTime(sdf.parse(finalDate));
        Date newDate = cal.getTime();
        Date givenDate = servicioBibliotecario.CalcularFechaEntrega("1234",oldDate);

        boolean equalDates = givenDate.equals(newDate);
        assertTrue(equalDates);
    }

    @Test
    public void Prestar() {

        RepositorioPrestamo repositorioPrestamo = mock(RepositorioPrestamo.class);
        RepositorioLibro repositorioLibro = mock(RepositorioLibro.class);
        ServicioBibliotecario servicioBibliotecario = new ServicioBibliotecario(repositorioLibro, repositorioPrestamo);

        servicioBibliotecario.prestar("123aaaa999","Juan Baena");
    }
}