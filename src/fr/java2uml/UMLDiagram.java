package fr.java2uml;

import java.util.*;

import fr.uml2java.*;

public class UMLDiagram {
	private List<UMLClass> myClasses = new ArrayList<>();

	public List<UMLClass> getMyClasses() {
		return myClasses;
	}

	public void setMyClasses(List<UMLClass> myClasses) {
		this.myClasses = myClasses;
	}
	
	public UMLClass getClassWithName(String className) {
		for(UMLClass c : getMyClasses()) {
			if(className.contains("[]")) {
				String trueClassName = "";
				for(int i = 0; i < className.length(); i++) {
					if(Character.isLetter(className.charAt(i)) || Character.isDigit(className.charAt(i))) {
						trueClassName+=className.charAt(i);
					}
				}
				return getClassWithName(trueClassName);
			}
			else if(className.contains("<") && className.contains(">")){
				String trueClassName = "";
				for(int i = className.indexOf('<'); i < className.indexOf('>'); i++) {
					if(Character.isLetter(className.charAt(i)) || Character.isDigit(className.charAt(i))) {
						trueClassName+=className.charAt(i);
					}
				}
				return getClassWithName(trueClassName);
			}
			else if(c.getName().equals(className)) {
				return c;
			}
		}
		return null;
	}

}
