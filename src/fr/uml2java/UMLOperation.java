package fr.uml2java;

public class UMLOperation extends UMLObject {
    private String type;

    public String getType() {
        return type;
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

    public void setType(String type) {
        this.type = type;
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
