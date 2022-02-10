package fr.java2uml;

import java.io.*;
import org.json.JSONException;

public class MdjGenerator {
	UMLProject UMLProject;

	public MdjGenerator() {}

	public void generateUMLProject(UMLDiagram diagram) throws JSONException {
		UMLProject = new UMLProject(diagram);
	}

	public void generateJsonFileFromDiagram(String path, UMLDiagram diagram) throws JSONException {
		generateUMLProject(diagram);
		try {
			File file = new File(path + "/Diagram.mdj");
			if (file.createNewFile()) {
				System.out.println("Fichier créé: " + file.getName());
			} else {
				System.out.println("Le fichier existe déjà.");
			}
			FileWriter writer = new FileWriter(path + "/Diagram.mdj");
			writer.write(UMLProject.toString());
			writer.close();
			FileWriter writer2 = new FileWriter(path + "/Diagram.json");
			writer2.write(UMLProject.toString());
			writer2.close();

		} catch (IOException e) {
			System.out.println("Erreur.");
			e.printStackTrace();
		}
	}

}
