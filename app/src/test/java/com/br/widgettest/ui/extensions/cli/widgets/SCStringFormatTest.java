package com.br.widgettest.ui.extensions.cli.widgets;

import org.junit.Test;

import static org.junit.Assert.*;

public class SCStringFormatTest {

    @Test
    public void format_substitutesCharForString() {
        String base     = "   # ";
        String expected = "   teste ";

        assertEquals(expected, SCStringFormat.format(base, "teste"));
    }

    @Test
    public void format_changeInPlace() {
        String base     = "0   #abcdef  0";
        String expected = "0   testeef  0";

        assertEquals(expected, SCStringFormat.format(SCStringFormat.Type.ChangeInPlace, base, "teste"));
    }

    @Test
    public void format_changeFromPlace() {
        String base      = "0   abcdef#  0";
        String expected  = "0   abteste  0";
        String expected2 = "0 teste1234  0";

        assertEquals(expected, SCStringFormat.format(SCStringFormat.Type.ChangeFromPlace, base, "teste"));
        assertEquals(expected2, SCStringFormat.format(SCStringFormat.Type.ChangeFromPlace, base, "teste1234"));
    }

    @Test
    public void format_evenParam_changeFromMiddle() {
        String base      = "012345#7890123";
        String expected  = "01234acdc90123";
        String expected2 = "0123abcdef0123";

        assertEquals(expected, SCStringFormat.format(SCStringFormat.Type.ChangeFromMiddle, base, "acdc"));
        assertEquals(expected2, SCStringFormat.format(SCStringFormat.Type.ChangeFromMiddle, base, "abcdef"));
    }

    @Test
    public void format_oddParam_changeFromMiddle() {
        String base      = "012345#7890123";
        String expected  = "0123teste90123";
        String expected2 = "012caderno0123";

        assertEquals(expected, SCStringFormat.format(SCStringFormat.Type.ChangeFromMiddle, base, "teste"));
        assertEquals(expected2, SCStringFormat.format(SCStringFormat.Type.ChangeFromMiddle, base, "caderno"));
    }
}