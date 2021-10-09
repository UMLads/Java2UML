package fr.uml2java;

import javax.print.attribute.Attribute;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.Attributes;

public class UMLToCode extends Translator {
    private List<UMLClass> classes;

    @Override
    public String toString() {
        String s = "";
        for (UMLClass umlClass : classes) {
            s += umlClass.toString();
        }
        return s;
    }

    private String tidy(String s) {
        String r = "";
        s = s.strip();
        for (int i = 0; i < s.length() - 1; ++i) {
            if (s.charAt(i) == '"' || s.charAt(i) == ' ') continue;
            r += s.charAt(i);
        }
        int begin = 0; // index des chars a retirer
        for (; begin < r.length() - 1; ++begin) {
            if (r.charAt(begin) == ':') {
                break;
            }
        }
        r = r.substring(begin + 1);
        return r;
    }

    public UMLToCode() throws FileNotFoundException {
        super();
        classes = new ArrayList<>();
        this.setFile("simple_class.mdj");
        this.initializeReader();
    }

    public void displayFile() throws IOException {
        for (UMLClass umlClass : classes) {
            System.out.println(umlClass.toString());
        }
    }

    public UMLClass addAttributes(UMLClass umlClass) throws IOException {
        String s;
        while (true) {
            s = this.getReader().readLine();
            if (s.contains("]") || s.contains("],")) return umlClass;
            else if (s.contains("UMLAttribute")) {
                umlClass = this.addAttribute(umlClass);
            }
        }
    }

    public UMLClass addAttribute(UMLClass umlClass) throws IOException {
        String s;
        UMLAttribute attribute = new UMLAttribute();
        while (true) {
            s = this.getReader().readLine();
            if (s.contains("\"type\"")) {
                attribute.setType(tidy(s));
            } else if (s.contains("$ref")) {
                attribute.setParent(tidy(s));
            } else if (s.contains("name")) {
                attribute.setName(tidy(s));
            } else if (s.contains("_id")) {
                attribute.setId(tidy(s));
            } else if (s.equals("\t\t\t\t\t\t}") || s.equals("\t\t\t\t\t\t},")) {
                umlClass.getAttributes().add(attribute);
                return umlClass;
            }
        }
    }

    public UMLOperation addParameters(UMLOperation umlOperation) throws IOException {
        String s;
        UMLParameter umlParameter = new UMLParameter();
        while (true) {
            s = this.getReader().readLine();
            if (s.contains("\"type\"")) {
                umlParameter.setType(tidy(s));
            } else if (s.contains("_id")) {
                umlParameter.setId(tidy(s));
            } else if (s.contains("$ref")) {
                umlParameter.setParent(tidy(s));
            } else if (s.contains("direction")) {
                umlParameter.setReturn(Boolean.TRUE);
            } else if (s.equals("\t\t\t\t\t\t\t\t}")) {
                umlOperation.getUmlParameters().add(umlParameter);
                return umlOperation;
            } else if (s.equals("\t\t\t\t\t\t\t\t},")) {
                umlOperation.getUmlParameters().add(umlParameter);
                umlOperation = addParameters(umlOperation);
                return umlOperation;
            }
        }
    }

    public UMLClass addOperation(UMLClass umlClass) throws IOException {
        String s;
        UMLOperation umlOperation = new UMLOperation();
        while (true) {
            s = this.getReader().readLine();
            if (s.contains("$ref")) {
                umlOperation.setParent(tidy(s));
            } else if (s.contains("name")) {
                umlOperation.setName(tidy(s));
            } else if (s.contains("_id")) {
                umlOperation.setId(tidy(s));
            } else if (s.contains("parameters")) {
                umlOperation = addParameters(umlOperation);
            } else if (s.equals("\t\t\t\t\t\t}") || s.equals("\t\t\t\t\t\t},")) {
                umlClass.getOperations().add(umlOperation);
                return umlClass;
            }
        }
    }

    public UMLClass addOperations(UMLClass umlClass) throws IOException {
        String s;
        while (true) {
            s = this.getReader().readLine();
            if (s.contains("]")) return umlClass;
            else if (s.contains("UMLOperation")) {
                umlClass = this.addOperation(umlClass);
            }
        }
    }

    public void classCreationRoutine() throws IOException {
        String s = "prou";
        UMLClass umlClass = new UMLClass();
        while (true) {
            s = this.getReader().readLine();
            if (s.contains("_id")) {
                s = this.tidy(s);
                umlClass.setId(s);
            } else if (s.contains("$ref")) {
                s = this.tidy(s);
                umlClass.setParent(s);
            } else if (s.contains("name")) {
                s = this.tidy(s);
                umlClass.setName(s);
            } else if (s.contains("attributes")) {
                umlClass = this.addAttributes(umlClass);
            } else if (s.contains("operations")) {
                umlClass = this.addOperations(umlClass);
            } else if (s.equals("\t\t\t\t},") || s.equals("\t\t\t\t}")) {
                this.classes.add(umlClass);
                return;
            }
        }
    }

    @Override
    public void translate() throws IOException {
        String s = "prou";
        while (s != null) {
            if (s.contains("\"UMLClass\"")) {
                this.classCreationRoutine();
            }
            s = this.getReader().readLine();
        }
    }
}
