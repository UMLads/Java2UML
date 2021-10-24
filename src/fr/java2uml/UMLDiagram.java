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
			if(c.getName().equals(className)) {
				return c;
			}
		}
		return null;
	}

}
