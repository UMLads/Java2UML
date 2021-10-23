package fr.uml2java;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UMLToCode extends Translator {
    private List<UMLClass> classes;
    private String readLine = "nonull";

    public void addSpecs(UMLObject object) throws IOException {
        readLine = this.getLine();
        object.setId(tidy(readLine));
        this.getLine();
        readLine = this.getLine();
        object.setParent(tidy(readLine));
    }

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
        this.setFile("abstractClass.mdj");
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
     * @throws IOException throws IOException if the there's an issue with the file being read
     */
    public void addAttributes(UMLClass umlClass) throws IOException {
        while (true) {
            readLine = this.getReader().readLine();
            if (readLine.contains("]")) return;
            else if (readLine.contains("UMLAttribute")) {
                addAttribute(umlClass);
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
     * @throws IOException throws IOException if the there's an issue with the file being read
     */
    public void addAttribute(UMLClass umlClass) throws IOException {
        UMLAttribute attribute = new UMLAttribute();
        addSpecs(attribute);
        while (true) {
            readLine = this.getReader().readLine();
            if (readLine.contains("\"type\"")) {
                attribute.setType(tidy(readLine));
            } else if (readLine.contains("\"name\"")) {
                attribute.setName(tidy(readLine));
            } else if (readLine.contains("\"isDerived\"")) {
                attribute.setDerived();
            } else if (readLine.contains("\"isStatic\"")) {
                attribute.setStatic();
            } else if (readLine.equals("\t\t\t\t\t\t}") || readLine.equals("\t\t\t\t\t\t},")) {
                umlClass.addAttribute(attribute);
                return;
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
     * @throws IOException throws IOException if the there's an issue with the file being read
     */
    public void addParameters(UMLOperation umlOperation) throws IOException {
        UMLParameter umlParameter = new UMLParameter();
        this.getLine();
        this.getLine();
        addSpecs(umlParameter);
        while (true) {
            readLine = this.getReader().readLine();
            if (readLine.contains("\"type\"")) {
                umlParameter.setType(tidy(readLine));
            } else if (readLine.contains("direction")) {
                umlParameter.setReturn(Boolean.TRUE);
            } else if (readLine.contains("\"name\"")) {
                umlParameter.setName(tidy(readLine));
            } else if (readLine.equals("\t\t\t\t\t\t\t\t}")) {
                umlOperation.addParameter(umlParameter);
                return;
            } else if (readLine.equals("\t\t\t\t\t\t\t\t},")) {
                umlOperation.addParameter(umlParameter);
                addParameters(umlOperation);
                return;
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
     * @throws IOException throws IOException if the there's an issue with the file being read
     */
    public void addOperation(UMLClass umlClass) throws IOException {
        UMLOperation umlOperation = new UMLOperation();
        addSpecs(umlOperation);
        while (true) {
            readLine = this.getReader().readLine();
            if (readLine.contains("name")) {
                umlOperation.setName(tidy(readLine));
            } else if (readLine.contains("\"isAbstract\"")) {
                umlOperation.setAbstract();
            } else if (readLine.contains("\"isDerived\"")) {
                umlOperation.setDerived();
            } else if (readLine.contains("\"isStatic\"")) {
                umlClass.setStatic();
            } else if (readLine.contains("parameters")) {
                addParameters(umlOperation);
            } else if (readLine.equals("\t\t\t\t\t\t}") || readLine.equals("\t\t\t\t\t\t},")) {
                umlClass.addOperation(umlOperation);
                return;
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
     * @throws IOException throws IOException if the there's an issue with the file being read
     */
    public void addOperations(UMLClass umlClass) throws IOException {
        while (true) {
            readLine = this.getReader().readLine();
            if (readLine.contains("]")) return;
            else if (readLine.contains("UMLOperation")) {
                addOperation(umlClass);
            }
        }
    }

    public void addAssociations(UMLClass umlClass) throws IOException {
        while (true) {
            readLine = this.getReader().readLine();
            if (readLine.contains("]")) {
                return;
            } else if (readLine.contains("UMLAssociation")) {
                addAssociation(umlClass);
            }
        }
    }

    public void addAssociation(UMLClass umlClass) throws IOException {
        UMLAssociation umlAssociation = new UMLAssociation();
        addSpecs(umlAssociation);
        while (true) {
            readLine = this.getReader().readLine();
            if (readLine.equals("\t\t\t\t\t\t}")) {
                umlClass.addAssociation(umlAssociation);
                return;
            } else if (readLine.contains("\"name\"")) {
                umlAssociation.setName(tidy(readLine));
            } else if (readLine.contains("end1")) {
                setAssociationEnd(umlAssociation, 1);
            } else if (readLine.contains("end2")) {
                setAssociationEnd(umlAssociation, 2);
            }
        }
    }

    public void setAssociationEnd(UMLAssociation umlAssociation, int end) throws IOException {
        UMLAssociationEnd umlAssoEnd = new UMLAssociationEnd();
        this.getLine();
        addSpecs(umlAssoEnd);
        while (true) {
            readLine = this.getReader().readLine();
            if (readLine.contains("\"reference\"")) {
                readLine = this.getLine();
                umlAssoEnd.setReference(tidy(readLine));
            } else if (readLine.contains("\"name\"")) {
                umlAssoEnd.setName(tidy(readLine));
            } else if (readLine.contains("\"visibility\"")) {
                umlAssoEnd.setVisibility(tidy(readLine));
            } else if (readLine.contains("\"multiplicity\"")) {
                umlAssoEnd.setMultiplicity(tidy(readLine));
            } else if (readLine.equals("\t\t\t\t\t\t\t},") || readLine.equals("\t\t\t\t\t\t\t}")) {
                if (end == 1) {
                    umlAssociation.setEnd1(umlAssoEnd);
                } else {
                    umlAssociation.setEnd2(umlAssoEnd);
                }
                return;
            }
        }
    }

    public void addClass() throws IOException {
        UMLClass umlClass = new UMLClass();
        addSpecs(umlClass);
        while (true) {
            readLine = this.getReader().readLine();
            if (readLine.contains("name")) {
                readLine = this.tidy(readLine);
                umlClass.setName(readLine);
            } else if (readLine.contains("\"isAbstract\"")) {
                umlClass.setAbstract();
            } else if (readLine.contains("\"isStatic\"")) {
                umlClass.setStatic();
            } else if (readLine.contains("attributes")) {
                addAttributes(umlClass);
            } else if (readLine.contains("operations")) {
                addOperations(umlClass);
            } else if (readLine.contains("ownedElements")) {
                addAssociations(umlClass);
            } else if (readLine.equals("\t\t\t\t},") || readLine.equals("\t\t\t\t}")) {
                this.classes.add(umlClass);
                return;
            }
        }
    }

    public void extractInformation() throws IOException {
        while (readLine != null) {
            if (readLine.contains("\"UMLClass\"")) {
                this.addClass();
            }
            readLine = this.getReader().readLine();
        }
    }

    @Override
    public void translate() throws IOException {
        extractInformation();
    }
}
