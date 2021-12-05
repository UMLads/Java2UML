package fr.uml2java;

import java.util.ArrayList;

import fr.java2uml.UMLSourceTargetRelation;

public class UMLClass extends UMLObject {
    private boolean isAbstract = false;

    private boolean isInterface = false;

	private ArrayList<UMLAttribute> attributes;
    private ArrayList<UMLOperation> operations;
    private ArrayList<UMLAssociation> associations;

    private ArrayList<UMLSourceTargetRelation> dependencies;

    private String extendedClass = "";
    private ArrayList<String> implementedClasses = new ArrayList<>();

	@Override
    public String toString() {
        String s = super.toString();
        s = s.substring(0, s.length()-2) + ((isAbstract) ? " isAbstract;" : "") + " contains :\n";
        s += "\tattributes : \n";
        for (UMLAttribute umlAttribute : attributes) {
            s += "\t\t" + umlAttribute.toString();
        }
        s += "\toprations : \n";
        for (UMLOperation umlOperation : operations) {
            s += "\t\t" + umlOperation.toString();
        }
        s += "\towned elements : \n";
        for (UMLAssociation umlAssociation : associations) {
            s += "\t\t" + umlAssociation.toString();
        }
        return s;
    }

    public UMLClass() {
        super();
        attributes = new ArrayList<>();
        operations = new ArrayList<>();
        associations = new ArrayList<>();
        dependencies = new ArrayList<>();
    }

    public ArrayList<UMLAttribute> getAttributes() {
        return attributes;
    }

    public ArrayList<UMLOperation> getOperations() {
        return operations;
    }

    public ArrayList<UMLAssociation> getAssociations() {
        return associations;
    }

    public void setAttributes(ArrayList<UMLAttribute> attributes) {
        this.attributes = attributes;
    }

    public void setOperations(ArrayList<UMLOperation> operations) {
        this.operations = operations;
    }

    public void setAssociations(ArrayList<UMLAssociation> associations) {
        this.associations = associations;
    }
    
    public ArrayList<UMLSourceTargetRelation> getDependencies() {
		return dependencies;
	}

	public void setDependencies(ArrayList<UMLSourceTargetRelation> dependencies) {
		this.dependencies = dependencies;
	}

    public boolean isInterface() {
		return isInterface;
	}

	public void setInterface(boolean isInterface) {
		this.isInterface = isInterface;
	}

    public String getName() {
        return super.getName();
    }

    public String getId() {
        return super.getId();
    }

    public String getParent() {
        return super.getParent();
    }

    public void setName(String name) {
        super.setName(name);
    }

    public void setId(String id) {
        super.setId(id);
    }

    public void setParent(String parent) {
         super.setParent(parent);
    }

    public void addAttribute(UMLAttribute umlAttribute) {
        attributes.add(umlAttribute);
    }
    
    public void removeAttribute(String umlAttributeName) {
        for(UMLAttribute attribute : attributes) {
        	if(attribute.getName().equals(umlAttributeName)) {
        		attributes.remove(attribute);
        		break;
        	}
        }
    }

    public void removeAttribute(UMLAttribute umlAttribute) {
        attributes.remove(umlAttribute);
    }
    
    public void addOperation(UMLOperation umlOperation) {
        operations.add(umlOperation);
    }

    public void addAssociation(UMLAssociation umlAssociation) {
        associations.add(umlAssociation);
    }
    
    public void addDependency(UMLSourceTargetRelation newDependency) {
        dependencies.add(newDependency);
    }
    

    public UMLAttribute getLastAtt() {
        return attributes.get(attributes.size() - 1);
    }

    public boolean isAbstract() {
        return isAbstract;
    }

    public void setAbstract() {
        isAbstract = true;
    }

	public String getExtendedClass() {
		return extendedClass;
	}

	public void setExtendedClass(String implementedClass) {
		this.extendedClass = implementedClass;
	}

	public ArrayList<String> getImplementedClasses() {
		return implementedClasses;
	}

	public void addImplementedClass(String implementedClass) {
		this.implementedClasses.add(implementedClass);
	}



	
}
