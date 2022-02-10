package fr.uml2java;
import fr.java2uml.JavaAnalyser;
import java.io.IOException;
import org.json.*;

public class Main {
	/*
	//UML -> Java
    public static void main(String[] args) throws IOException {
        UMLToCode translator = new UMLToCode();
        translator.translate();
        System.out.println(translator);
    }
    */
    
	//Java -> UML

    public static void main(String[] args) throws IOException, JSONException {
		JavaAnalyser j = new JavaAnalyser();
		j.startAnalyse();
	}




}
