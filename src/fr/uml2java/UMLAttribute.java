package fr.uml2java;

public class UMLAttribute extends UMLObject {
    private String type;
    private String myClassId;

    @Override
    public String toString() {
        String s = super.toString();
        s = s.substring(0, s.length() - 2);
        return s + " type : "  + type + '\n';
    }

    public UMLAttribute() {
        super();
    }

    public String getMyClassId() {
    	return myClassId;
    }

    public void setMyClassId(String myClassId) {
    	this.myClassId = myClassId;
    }
    
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
}
