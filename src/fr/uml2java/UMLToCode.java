package fr.uml2java;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UMLToCode extends Translator {
    private List<UMLClass> classes;


    /**
     * @return list of UMLClasses of the class
     */
    public List<UMLClass> getClasses() {
        return classes;
    }

    /**
     * @param classes list of UMLClasses to set the class attribute to
     */
    public void setClasses(List<UMLClass> classes) {
        this.classes = classes;
    }


    /**
     * This method requires to be called after the file was searched
     * It returns all the info gathered by calling the toString method of each UMLClass contained
     * in the member attribute classes
     *
     * @return the result of the search
     */
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (UMLClass umlClass : classes) {
            s.append(umlClass.toString());
        }
        return s.toString();
    }


    /**
     * This methods returns the important information from a string which was built based on the line the reader read
     * For example : <\t\t\t\t\t\t\t\t"$ref": "AAAAAAF8ZCjeHiHzri4="> becomes <AAAAAAF8ZCjeHiHzri4=>
     * Its purely made to clear .mdj lines
     *
     * @param s the string to extract the important information from
     * @return the cleared string ready to be used as parameter for any setters of UMLObjects or its children
     */
    private String tidy(String s) {
        String r = "";
        s = s.strip();
        for (int i = 0; i < s.length() - 1; ++i) {
            if (s.charAt(i) == '"' || s.charAt(i) == ' ') continue;
            r += s.charAt(i);
        }
        int begin = 0;
        for (; begin < r.length() - 1; ++begin) {
            if (r.charAt(begin) == ':') {
                break;
            }
        }
        r = r.substring(begin + 1);
        return r;
    }


    /**
     * //TODO
     */
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

    /**
     * This function adds the UMLAttributes to an UMLClass passed in parameters.
     * Ìt is called after the keyword ""attributes"" was found in the function calling it which means all the following
     * data lists the attributes of the class until the character ']' is seen
     * It keeps reading the file until it sees the keyword "UMLAttribute". Then it calls the function addAttribute()
     * with his umlClass in as a parameter and updates umlClass with the attributes added by addAttribute()
     * If it sees the keywords "]" is return the class with all the attributes added if there was any to add
     *
     * @param umlClass UMLClass to add the attributes to its list of attributes
     * @return the class once all the attributes were added
     * @throws IOException throws IOException if the there's an issue with the file being read
     */
    public UMLClass addAttributes(UMLClass umlClass) throws IOException {
        String s;
        while (true) {
            s = this.getReader().readLine();
            if (s.contains("]")) return umlClass;
            else if (s.contains("UMLAttribute")) {
                umlClass = this.addAttribute(umlClass);
            }
        }
    }

    /**
     * This function is called by addAttributes everytime the keyword "UMLAttribute" is found
     * It adds all the information needed for an UMLObject
     * It ends when the line read is "\t\t\t\t\t\t}" or "\t\t\t\t\t\t}," meaning the end of the definition of an
     * UMLAttribute. The method then returns the UMLClass with the attribute list updated with a new attribute
     *
     * @param umlClass Class to add an attribute to
     * @return the umlClass in parameters with an attribute added to its list of attributes
     * @throws IOException throws IOException if the there's an issue with the file being read
     */
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

    /**
     * This function adds the UMLParameters to an UMLOperation passed in parameters.
     * Ìt is called after the keyword "parameters" was found in the function calling it which means all the following
     * data is lists the parameters of the UMLOperation until the character ']' is seen
     * It keeps reading the file until it sees the keyword "UMLParameters".
     * Then it calls the function addParameter() each time its seen with his umlClass in as a parameter
     * and updates umlClass with the umlClass modifier with all the UMLParameters added by addPamameter()
     * If it sees the keywords "]" is return the UMLOperation with all the UMLParameters added if there was any to add
     *
     * @param umlOperation umlOperation to add the UMLParameters to
     * @return returns umlOperation once all the UMLParameters were added
     * @throws IOException throws IOException if the there's an issue with the file being read
     */
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


    /**
     * This function is called by addOperations() everytime the keyword "UMLOperation" is found
     * It adds all the information needed for an UMLObject
     * It ends when the line read is "\t\t\t\t\t\t}" or "\t\t\t\t\t\t}," meaning the end of the definition of an
     * UMLOperation. The method then returns the UMLClass with the UMLOperation list updated with a new UMLOperation
     * The method calls addParameter() when the keyword "parameters" is encountered with the umlOperation it creates
     * as a parameter to add the UMLParameters to the operation.
     *
     * @param umlClass class to add the operation to
     * @return umlClass after it's been updated with a new operation
     * @throws IOException throws IOException if the there's an issue with the file being read
     */
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

    /**
     * This function adds the UMLOperations to an UMLClass passed in parameters.
     * Ìt is called after the keyword ""operations"" was found in the function calling it which means all the following
     * data lists the UMLOperations of the UMLClass until the character ']' is seen
     * It keeps reading the file until it sees the keyword "UMLOperation". Then it calls the function addOperation()
     * with umlClass in as a parameter and updates umlClass with the operation added by addOperation()
     * If it sees the keywords "]" is return the class with all the operations added if there was any to add
     *
     * @param umlClass class to add the operations to
     * @return class updated with all the operations if there was any
     * @throws IOException throws IOException if the there's an issue with the file being read
     */
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

    public UMLClass addAssociations(UMLClass umlClass) throws IOException {
        String s;
        UMLAssociation umlAssociation = new UMLAssociation();
        while (true) {
            s = this.getReader().readLine();
            if (s.contains("]")) return umlClass;
            else if (s.contains("UMLAssociation")) {
                //umlAssociation = this.addAssociationEnd(umlClass);
            }
        }
    }

    public UMLAssociation addAssociationEnd(UMLAssociation umlAssociation) {
        return null;
    }

    public void addClass() throws IOException {
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
            } else if (s.contains("ownedElements")) {
                umlClass = this.addAssociations(umlClass);
            } else if (s.equals("\t\t\t\t},") || s.equals("\t\t\t\t}")) {
                this.classes.add(umlClass);
                return;
            }
        }
    }

    public void extractInformation() throws IOException {
        String s = "prou";
        while (s != null) {
            if (s.contains("\"UMLClass\"")) {
                this.addClass();
            }
            s = this.getReader().readLine();
        }
    }

    @Override
    public void translate() throws IOException {
        extractInformation();
    }
}
