package fr.java2uml;

import java.io.*;
import java.util.*;

import fr.uml2java.*;

public class JavaAnalyser {

	public static int uniqueID;
	
	private File folder = new File("C:/Users/Mathys Garoui/Documents/PTUT/FolderToAnalyse");
	private List<File> files = new ArrayList<>();

	public JavaAnalyser() {}


	public void listFilesForFolder(File folder) {
		for (File fileEntry : folder.listFiles()) {
			if (fileEntry.isDirectory()) {
				listFilesForFolder(fileEntry);
			} else {
				files.add(fileEntry);
			}
		}
	}

	public void attributesAnalysis(String line, UMLClass newClass) {
		UMLAttribute newAttribute = new UMLAttribute();
		newAttribute.setId(Integer.toString(++uniqueID));
		newAttribute.setMyClassId(newClass.getId());

		String varName = "";
		String varType = "";
		int endOfVarNameIndex;
		int endOfVarTypeIndex;
		if(line.contains("=")) {
			endOfVarNameIndex = line.indexOf("=");
			while(!Character.isLetter(line.charAt(endOfVarNameIndex))) {
				endOfVarNameIndex--;
			}
		}
		else {
			endOfVarNameIndex = line.indexOf(";");
			while(!Character.isLetter(line.charAt(endOfVarNameIndex))) {
				endOfVarNameIndex--;
			}
		}
		for (int i = endOfVarNameIndex; Character.isLetter(line.charAt(i)) ; i--) {

			varName = line.charAt(i) + varName;
		}
		newAttribute.setName(varName);
		endOfVarTypeIndex = endOfVarNameIndex;
		while(Character.isLetter(line.charAt(endOfVarTypeIndex))) {
			endOfVarTypeIndex--;
		}
		while(line.charAt(endOfVarTypeIndex) == ' ') {
			endOfVarTypeIndex--;
		}
		
		boolean characterSeen = false;
		for (int i = endOfVarTypeIndex; characterSeen ? Character.isLetter(line.charAt(i)) : true ; i--) {
			if(Character.isLetter(line.charAt(i))) {
				characterSeen = true;
			}
			if(line.charAt(i) != ' ' && !(Character.isDigit(line.charAt(i)) && (!Character.isLetter(line.charAt(i+1)) && !Character.isDigit(line.charAt(i+1))))) {
				varType = line.charAt(i) + varType;
			}
		}
		newAttribute.setType(varType);
		ArrayList<UMLAttribute> newVarList = newClass.getAttributes();
		newVarList.add(newAttribute);
		newClass.setAttributes(newVarList);
	}

	
	public List<UMLParameter> detectParametersOnOperation(String line, UMLOperation operation) {
		List<UMLParameter> parameters = new ArrayList<>();
		int endOfOperationVar = line.indexOf(')');
		boolean isNameOfVar = true;

		for (int i = endOfOperationVar; i > line.indexOf('(') ; i--) {
			if(isNameOfVar) {
				if(Character.isLetter(line.charAt(i))) {
					if(parameters.size() == 0) {
						parameters.add(new UMLParameter());
						parameters.get(parameters.size()-1).setId(Integer.toString(++uniqueID));
						parameters.get(parameters.size()-1).setMyOperationId(operation.getId());
					}
					while(Character.isLetter(line.charAt(i))) {
						parameters.get(parameters.size()-1).
						setName(line.charAt(i) + parameters.get(parameters.size()-1).getName());
						i--; //!
					}
					isNameOfVar = false;
				}
			}
			else {
				if(Character.isLetter(line.charAt(i))) {
					while(line.charAt(i) != ',' && line.charAt(i) != '(' && line.charAt(i) != ' ') {

						parameters.get(parameters.size()-1).
						setType(line.charAt(i) + parameters.get(parameters.size()-1).getType());
						i--; //!
					}
					isNameOfVar = true;
					if(line.charAt(i) == ' ') {
						while(line.charAt(i) == ' ') {
							i--;
						}
						if(Character.isLetter(line.charAt(i))) {
							//prefix
						}
					}
					if(line.charAt(i) == ',') {
						parameters.add(new UMLParameter());
						parameters.get(parameters.size()-1).setId(Integer.toString(++uniqueID));
						parameters.get(parameters.size()-1).setMyOperationId(operation.getId());

					}
				} 
			}


		}
		return parameters;
	}
	
	public void operationAnalysis(String line, UMLClass newClass) {

		UMLOperation newOperation = new UMLOperation();
		newOperation.setId(Integer.toString(++uniqueID));
		newOperation.setMyClassId(newOperation.getId());
		String operationName = "";
		String operationReturnType = "";
		int endOfOperationNameIndex = line.indexOf('(');
		int endOfOperationReturnType;

		while(!Character.isLetter(line.charAt(endOfOperationNameIndex))) {
			endOfOperationNameIndex--;
		}

		for (int i = endOfOperationNameIndex; Character.isLetter(line.charAt(i)) ; i--) {
			operationName = line.charAt(i) + operationName;
			endOfOperationReturnType = i;
		}
		newOperation.setName(operationName);
		List<UMLParameter> parameters = detectParametersOnOperation(line, newOperation);
		newOperation.setUmlParameters(parameters);

		if(!newOperation.getName().equals(newClass.getName())) {
			endOfOperationReturnType = endOfOperationNameIndex;
			while (Character.isLetter(line.charAt(endOfOperationReturnType))){
				endOfOperationReturnType--;
			}
			
			while (!Character.isLetter(line.charAt(endOfOperationReturnType))){
				endOfOperationReturnType--;
			}
			for(int i = endOfOperationReturnType; Character.isLetter(line.charAt(i)) ; i--) {
				operationReturnType	= line.charAt(i) + operationReturnType;
			}
			newOperation.setReturnType(operationReturnType);
		}
		else {
			newOperation.setReturnType("");
		}


		ArrayList<UMLOperation> newOpList = newClass.getOperations();
		newOpList.add(newOperation);
		newClass.setOperations(newOpList);
	}


	public UMLDiagram analyseFiles() throws IOException {
		UMLDiagram newDiagram = new UMLDiagram();
		for(File file : files) {
			UMLClass newClass = new UMLClass();
			newClass.setId(Integer.toString(++uniqueID) );
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			while(reader.ready()) {
				String line = reader.readLine();
				if(line.contains("class ")){
					//ligne qui contient la déclaration de la classe
					int indexOfStartClass = line.indexOf("class ") + 6;
					String className = "";
					for(int i = indexOfStartClass; i <= line.length()-1 && Character.isLetter(line.charAt(i)); i++) { //+7 permet d'obtenir l'index de la premiere lettre du nom de la classe
						className += line.charAt(i);
					}
					System.out.println("class : " + className);
					newClass.setName(className);
				}
				else if((line.contains("private") || line.contains("public")) && line.contains(";")) {
					//Ligne qui contient la déclaration d'une variable
					attributesAnalysis(line, newClass);

				}
				else if((line.contains("private") || line.contains("public")) && line.contains("(")) {
					//Ligne qui contient la déclaration d'une d'une fonction
					operationAnalysis(line, newClass);
				}
			}
			
			List<UMLClass> newClasses = newDiagram.getMyClasses();
			newClasses.add(newClass);
			newDiagram.setMyClasses(newClasses) ;
		}
		
		detectAssociations(newDiagram);
		return newDiagram;
	}
	

	
	public void detectAssociations(UMLDiagram diagram) {
		for(UMLClass c : diagram.getMyClasses()) {
			for(UMLAttribute a : c.getAttributes()) {
				if(diagram.getClassWithName(a.getType()) != null) {
					UMLAssociation newAssociation = new UMLAssociation();
					newAssociation.setId(Integer.toString(++uniqueID));
					newAssociation.setName("Est compose de");
					UMLAssociationEnd end1 = new UMLAssociationEnd();
					UMLAssociationEnd end2 = new UMLAssociationEnd();
					end1.setId(Integer.toString(++uniqueID));
					end1.setAssociatedClassId(c.getId());
					end1.setName("");
					end1.setAggregationType("none");
					end1.setMultiplicity("");
					end2.setId(Integer.toString(++uniqueID));
					end2.setAssociatedClassId(diagram.getClassWithName(a.getType()).getId());
					end2.setName("my"+a.getName());
					end2.setAggregationType("shared");
					end2.setMultiplicity("1");
					if(a.getType().contains("[]") || (a.getType().contains("<") && a.getType().contains(">"))) {
						end2.setMultiplicity("*");
						end2.setName(end2.getName() + "s");
					}
					
					
					newAssociation.setEnd1(end1);
					newAssociation.setEnd2(end2);
					c.addAssociation(newAssociation);
				}
			}
		}
	}
	
	public void generateJsonFile(UMLDiagram diagram) {
		MdjGenerator j = new MdjGenerator();
		j.generateJsonFileFromDiagram("C:/Users/Mathys Garoui/Documents/PTUT/JsonGeneration", diagram);
	}

	public void startAnalyse() throws IOException {
		listFilesForFolder(folder);
		UMLDiagram d = analyseFiles();
		String indent = "";
		for(UMLClass c : d.getMyClasses()) {
			indent = "Class : ";
			System.out.println(indent+c.getName());
			indent = "    ";
			System.out.println(indent+"------Variables------");
			for(UMLAttribute v : c.getAttributes()) {
				System.out.println(indent+ v.getType() + " " + v.getName() + " "+ v.getId());
			}
			System.out.println(indent+"------Operations------");
			for(UMLOperation o : c.getOperations()) {
				indent = "    ";
				System.out.println(indent+ o.getReturnType() + " " + o.getName() + " ");
				indent = "        ";
				for(UMLParameter v : o.getUmlParameters()) {
					System.out.println(indent+ v.getType() + " " + v.getName());
				}
			}
		}
		generateJsonFile(d);

	}
	
	

	public void debug(String log) {
		System.out.println("!debug : " + log);
	}
	
	
	

}
/*
Contraintes du code :
	-la déclaration d'une fonction / variable se fait sur une seule ligne (hors implémentation)
	-les mots clés doivent être séparés d'un seul espace
	-une seule classe par fichier
	-une déclaration max par ligne
	-preciser public ou privé
	-valeur par défaut de variables dans la déclaration d'une fonction momentannément indisponible
	-le fichier ne peut pas contenir le mot clé "class" sauf pour la définition de la class
 */

