package fr.uml2java;

import java.util.ArrayList;

public class UMLClass extends UMLObject {
    private ArrayList<UMLAttribute> attributes;
    private ArrayList<UMLOperation> operations;
    private ArrayList<UMLAssociation> associations;

    @Override
    public String toString() {
        String s = super.toString();
        s = s.substring(0, s.length()-2) + " contains :\n";
        for (UMLAttribute umlAttribute : attributes) {
            s += '\t' + umlAttribute.toString();
        }
        for (UMLOperation umlOperation : operations) {
            s += '\t' + umlOperation.toString();
        }
        for (UMLAssociation umlAssociation : associations) {
            s += '\t' + umlAssociation.toString();
        }
        return s;
    }

    public UMLClass() {
        super();
        attributes = new ArrayList<>();
        operations = new ArrayList<>();
        associations = new ArrayList<>();
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

    public String getName() {
        return super.getName();
    }

    public String getId() {
        return super.getId();
    }

    public String getReference() {
        return super.getReference();
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

    public void setReference(String reference) {
        super.setReference(reference);
    }

    public void setParent(String parent) {
         super.setParent(parent);
    }
}
