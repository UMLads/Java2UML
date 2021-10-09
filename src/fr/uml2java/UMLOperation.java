package fr.uml2java;

import java.util.ArrayList;
import java.util.List;

public class UMLOperation extends UMLObject {
    private List<UMLParameter> umlParameters;

    public UMLOperation() {
        umlParameters = new ArrayList<>();
    }

    @Override
    public String toString() {
        String s = super.toString();
        s = s.substring(0, s.length()-2) + " with parameters :\n";
        for (UMLParameter umlParameter : umlParameters) {
            s += "\t\t" + umlParameter.toString();
        }
        return s;
    }

    public List<UMLParameter> getUmlParameters() {
        return umlParameters;
    }

    public void setUmlParameters(List<UMLParameter> umlParameters) {
        this.umlParameters = umlParameters;
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
