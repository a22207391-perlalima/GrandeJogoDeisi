package pt.ulusofona.lp2.deisiGreatGame;

import org.junit.Test;

import static org.junit.Assert.*;

public class TestProgram {
    @Test
    public void test2() {

        Programmer p = new Programmer();
        p.setCor(ProgrammerColor.BLUE);

        ProgrammerColor esperado = ProgrammerColor.BLUE;
        ProgrammerColor real = p.getColor();
        assertEquals(esperado, real);
    }
}