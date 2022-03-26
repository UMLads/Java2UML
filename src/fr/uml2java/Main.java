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
		if(args.length != 2){
			System.out.println("Bad argument count : " + args.length);
			return;
		}
		JavaAnalyser j = new JavaAnalyser(args[0], args[1]);
		j.startAnalyse();
	}




}
