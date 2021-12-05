package fr.java2uml;


import java.util.ArrayList;

import fr.uml2java.UMLObject;

public class UMLSourceTargetRelation extends UMLObject {
    private String target;
	private String source;
	private String dependencyType = "";
    
    public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}


	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	@Override
    public String toString() {
        String s = super.toString();
        s = s.substring(0, s.length() - 2) + " with ends : \n";
        s += "\t\t\t" + target;
        s += "\t\t\t" + source;
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

	public String getDependencyType() {
		return dependencyType;
	}

	public void setDependencyType(String dependencyType) {
		this.dependencyType = dependencyType;
	}
}