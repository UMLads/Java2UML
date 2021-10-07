package fr.uml2java;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.Attributes;

public class UMLToCode extends Translator {
    private List<UMLClass> classes;

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
        this.setFile("basicUML.mdj");
        this.initializeReader();
    }

    @Override
    public void translate() throws IOException {
        String s = "prou";
        while (s != null) {
            s = this.getReader().readLine();
            if (s.contains("\"UMLClass\"")) {
                classes.add(new UMLClass());
                s = this.getReader().readLine();
                if (s.contains("_id")) {
                    s = this.tidy(s);
                    classes.get(classes.size() - 1).setId(s);
                } else if (s.contains("$ref")) {
                    s = this.tidy(s);
                    classes.get(classes.size() - 1).setParent(s);
                } else if (s.contains("name")) {
                    s = this.tidy(s);
                    classes.get(classes.size() - 1).setName(s);
                } else if (s.contains("attributes")) {
                    s = this.getReader().readLine();
                    if (s.contains("UMLAttribute")) {
                        classes.get(classes.size() - 1).getAttributes().add(new UMLAttribute());
                        s = this.getReader().readLine();
                        if (s.contains("_id")) {
                            s = this.tidy(s);
                            classes.get(classes.size() - 1).getAttributes()
                                    .get(classes.get(classes.size() - 1).getAttributes().size() - 1).setId(s);
                        } else if (s.contains("$ref")) {
                            s = this.tidy(s);
                            classes.get(classes.size() - 1).getAttributes()
                                    .get(classes.get(classes.size() - 1).getAttributes().size() - 1).setParent(s);
                        } else if (s.contains("name")) {
                            s = this.tidy(s);
                            classes.get(classes.size() - 1).getAttributes()
                                    .get(classes.get(classes.size() - 1).getAttributes().size() - 1).setName(s);
                        } else if (s.contains("type")) {
                            s = this.tidy(s);
                            classes.get(classes.size() - 1).getAttributes()
                                    .get(classes.get(classes.size() - 1).getAttributes().size() - 1).setType(s);
                        } else if (s.contains("]")) {
                            break;
                        }
                    }
                } else if (s.contains("operations")) {
                    s = this.getReader().readLine();
                    if (s.contains("UMLOperation")) {
                        classes.get(classes.size() - 1).getOperations().add(new UMLOperation());
                        s = this.getReader().readLine();
                        if (s.contains("_id")) {
                            s = this.tidy(s);
                            classes.get(classes.size() - 1).getOperations()
                                    .get(classes.get(classes.size() - 1).getOperations().size() - 1).setId(s);
                        } else if (s.contains("$ref")) {
                            s = this.tidy(s);
                            classes.get(classes.size() - 1).getOperations()
                                    .get(classes.get(classes.size() - 1).getOperations().size() - 1).setParent(s);
                        } else if (s.contains("name")) {
                            s = this.tidy(s);
                            classes.get(classes.size() - 1).getOperations()
                                    .get(classes.get(classes.size() - 1).getOperations().size() - 1).setName(s);
                        } else if (s.contains("type")) {
                            s = this.tidy(s);
                            classes.get(classes.size() - 1).getOperations()
                                    .get(classes.get(classes.size() - 1).getOperations().size() - 1).setType(s);
                        } else if (s.contains("]")) {
                            break;
                        }
                    }
                }
            }
        }
    }
}
