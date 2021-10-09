package fr.uml2java;

import java.util.ArrayList;

public class UMLAssociation extends UMLObject {
    private ArrayList<UMLAssociationEnd> associationEnds;

    @Override
    public String toString() {
        String s = "";
        for (UMLAssociationEnd umlAssociation : associationEnds) {
            s += umlAssociation.toString();
        }
        return super.toString() + "\t with associations " + s;
    }

    public ArrayList<UMLAssociationEnd> getAssociationEnds() {
        return associationEnds;
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

    public void setAssociationEnds(ArrayList<UMLAssociationEnd> associationEnds) {
        this.associationEnds = associationEnds;
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
