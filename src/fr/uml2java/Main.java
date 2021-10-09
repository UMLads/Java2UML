package fr.uml2java;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        UMLToCode translator = new UMLToCode();
        translator.translate();
        System.out.println(translator.toString());
    }
}
