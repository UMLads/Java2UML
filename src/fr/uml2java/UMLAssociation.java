package fr.uml2java;

import java.util.ArrayList;

public class UMLAssociation extends UMLObject {
    private UMLAssociationEnd end1;
    private UMLAssociationEnd end2;

    @Override
    public String toString() {
        String s = super.toString();
        s = s.substring(0, s.length() - 2) + " with ends : \n";
        s += "\t\t\t" + end1.toString();
        s += "\t\t\t" + end2.toString();
        return s;
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

    public UMLAssociationEnd getEnd1() {
        return end1;
    }

    public void setEnd1(UMLAssociationEnd end1) {
        this.end1 = end1;
    }

    public UMLAssociationEnd getEnd2() {
        return end2;
    }

    public void setEnd2(UMLAssociationEnd end2) {
        this.end2 = end2;
    }
}
