package fr.uml2java;

import java.util.ArrayList;

public class UMLObject {
    private String name;
    private String id;
    private String parent;

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "; name : " + name + "; id : " + id
                + "; parent : " + parent + "; \n";
    }

    public UMLObject() {}

    public UMLObject(UMLObject umlObject) {
        this.id = umlObject.getId();
        this.name = umlObject.getName();
        this.parent = umlObject.parent;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getParent() {
        return parent;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }
}
