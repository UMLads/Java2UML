package fr.uml2java;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        Translator translator = new UMLToCode();
        translator.translate();
    }
}
