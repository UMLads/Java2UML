package fr.uml2java;

public class UMLParameter extends UMLAttribute {
    boolean isReturn;

    @Override
    public String toString() {
        String s = super.toString();
        s = s.substring(0, s.length() - 1);
        return s + " and " + (isReturn ? "is return type" : "is not return type") + '\n';
    }

    public boolean isReturn() {
        return isReturn;
    }

    public void setReturn(boolean isReturn) {
        this.isReturn = isReturn;
    }

    public String getType() {
        return super.getType();
    }

    public void setType(String type) {
        super.setType(type);
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
