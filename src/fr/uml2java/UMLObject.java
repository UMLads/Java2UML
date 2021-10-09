package fr.uml2java;

import java.util.ArrayList;

public class UMLObject {
    private String name;
    private String id;
    private String reference;
    private String parent;

    @Override
    public String toString() {
        return this.getClass().getSimpleName().toString() + "; name : " + name + "; id : " + id
                + "; reference : " + reference + "; \n";
    }

    public UMLObject() {}

    public UMLObject(String name, String id, String reference, String parent) {
        this.name = name;
        this.id = id;
        this.reference = reference;
        this.parent = parent;
    }

    public UMLObject(UMLObject umlObject) {
        this.id = umlObject.getId();
        this.name = umlObject.getName();
        this.reference = umlObject.getReference();
        this.parent = umlObject.parent;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getReference() {
        return reference;
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

    public void setReference(String reference) {
        this.reference = reference;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public static class UMLObjectBuilder {
        private String name = "";
        private String id = "";
        private String reference = "";
        private String parent = "";

        UMLObjectBuilder name(String name) {
            this.name = name;
            return this;
        }

        UMLObjectBuilder id(String id) {
            this.id = id;
            return this;
        }

        UMLObjectBuilder reference(String reference) {
            this.reference = reference;
            return this;
        }

        UMLObjectBuilder parent(String parent) {
            this.parent = parent;
            return this;
        }

        UMLObject build() {
            return new UMLObject(name, id, reference, parent);
        }
    }
}
