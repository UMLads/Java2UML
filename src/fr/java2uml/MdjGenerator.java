package fr.java2uml;

import java.io.*;
import java.util.*;

import fr.uml2java.*;

public class MdjGenerator {

	private List<String> UMLClassList = new ArrayList<>();
	private List<String> UMLClassViewList = new ArrayList<>();
	private List<String> UMLAssociationViewList = new ArrayList<>();
	private List<String> UMLDependencyViewList = new ArrayList<>();

	private String allUMLClass = "";
	private String allUMLClassView = "";
	private String allUMLAssociationView = "";
	private String allUMLDependencyView = "";

	private String UMLClassViewBase = "						{\r\n"
			+ "							\"_type\": \"UMLClassView\",\r\n"
			+ "							\"_id\": \"MyClass1View_id\",\r\n"
			+ "							\"_parent\": {\r\n"
			+ "								\"$ref\": \"diagram_id\"\r\n" + "							},\r\n"
			+ "							\"model\": {\r\n"
			+ "								\"$ref\": \"MyClass_id\"\r\n" + "							},\r\n"
			+ "							\"containerChangeable\": true,\r\n"
			+ "							\"left\": 248,\r\n" + "							\"top\": 320,\r\n"
			+ "							\"width\": 152.70166015625,\r\n"
			+ "							\"height\": 71\r\n" + "						}";

	private String UMLDependencyViewBase = "						{\r\n"
			+ "							\"_type\": \"UMLDependencyView\",\r\n"
			+ "							\"_id\": \"dependencyView_id\",\r\n"
			+ "							\"_parent\": {\r\n"
			+ "								\"$ref\": \"diagram_id\"\r\n" + "							},\r\n"
			+ "							\"model\": {\r\n"
			+ "								\"$ref\": \"dependency_id\"\r\n" + "							},\r\n"
			+ "							\"head\": {\r\n"
			+ "								\"$ref\": \"MyClass2View_id\"\r\n" + "							},\r\n"
			+ "							\"tail\": {\r\n"
			+ "								\"$ref\": \"MyClass1View_id\"\r\n" + "							},\r\n"
			+ "							\"lineStyle\": 1,\r\n"
			+ "							\"points\": \"178:325;248:423\",\r\n"
			+ "							\"showVisibility\": true\r\n" + "						}";

	private String UMLAssociationViewBase = "						{\r\n"
			+ "							\"_type\": \"UMLAssociationView\",\r\n"
			+ "							\"_id\": \"associationView_id\",\r\n"
			+ "							\"_parent\": {\r\n"
			+ "								\"$ref\": \"diagram_id\"\r\n" + "							},\r\n"
			+ "							\"model\": {\r\n"
			+ "								\"$ref\": \"association_id\"\r\n" + "							},\r\n"
			+ "							\"head\": {\r\n"
			+ "								\"$ref\": \"MyClass2View_id\"\r\n" + "							},\r\n"
			+ "							\"tail\": {\r\n"
			+ "								\"$ref\": \"MyClass1View_id\"\r\n" + "							},\r\n"
			+ "							\"lineStyle\": 1,\r\n"
			+ "							\"points\": \"178:325;248:423\",\r\n"
			+ "							\"showVisibility\": true\r\n" + "						}";
	private String UMLClassBase = "{\r\n" + "					\"_type\": \"UMLClass\",\r\n"
			+ "					\"_id\": \"MyClass_id\",\r\n" + "					\"_parent\": {\r\n"
			+ "						\"$ref\": \"model_id\"\r\n" + "					},\r\n"
			+ "					\"name\": \"MyClass_name\",\r\n"
			+ "					\"isAbstract\": MyClass_isAbstract,\r\n"
			+ "					\"ownedElements\": [\r\n" + "REPLACE_ASSOCIATIONS REPLACE_DEPENDENCIES"
			+ "					],\r\n" + "					\"attributes\": [\r\n" + "REPLACE_ATTRIBUTES"
			+ "					],\r\n" + "					\"operations\": [\r\n" + "REPLACE_OPERATIONS"
			+ "					]\r\n" + "				}";
	private String UMLOperationBase = "{\r\n" + "							\"_type\": \"UMLOperation\",\r\n"
			+ "							\"_id\": \"UMLOperation_id\",\r\n"
			+ "							\"_parent\": {\r\n"
			+ "								\"$ref\": \"MyClass_id\"\r\n" + "							},\r\n"
			+ "							\"name\": \"myOperation_name\",\r\n"
			+ "							\"isStatic\": myOperation_isStatic,\r\n"
			+ "							\"isAbstract\": myOperation_isAbstract,\r\n"
			+ "							\"visibility\": \"myOperation_visibility\",\r\n"
			+ "							\"parameters\": [\r\n" + "REPLACE_PARAMETERS"
			+ "								{\r\n"
			+ "									\"_type\": \"UMLParameter\",\r\n"
			+ "									\"_id\": \"return_id\",\r\n"
			+ "									\"_parent\": {\r\n"
			+ "										\"$ref\": \"UMLOperation_id\"\r\n"
			+ "									},\r\n"
			+ "									\"type\": \"return_type\",\r\n"
			+ "									\"direction\": \"return\"\r\n" + "								}"
			+ "							]\r\n" + "						}";
	private String UMLAttributeBase = "						{\r\n"
			+ "							\"_type\": \"UMLAttribute\",\r\n"
			+ "							\"_id\": \"UMLAttribute_id\",\r\n"
			+ "							\"_parent\": {\r\n"
			+ "								\"$ref\": \"MyClass_id\"\r\n" + "							},\r\n"
			+ "							\"name\": \"myAttribute_name\",\r\n"
			+ "							\"visibility\": \"myAttribute_visibility\",\r\n"
			+ "							\"isStatic\": myAttribute_isStatic,\r\n"
			+ "							\"type\": \"myAttribute_type\"\r\n" + "						}";
	private String UMLParameterBase = "								{\r\n"
			+ "									\"_type\": \"UMLParameter\",\r\n"
			+ "									\"_id\": \"UMLParameter_id\",\r\n"
			+ "									\"_parent\": {\r\n"
			+ "										\"$ref\": \"UMLOperation_id\"\r\n"
			+ "									},\r\n"
			+ "									\"name\": \"UMLParameter_name\",\r\n"
			+ "									\"type\": \"UMLParameter_type\"\r\n"
			+ "								}";
	private String UMLAssociationBase = "{\r\n" + "							\"_type\": \"UMLAssociation\",\r\n"
			+ "							\"_id\": \"association_id\",\r\n"
			+ "							\"_parent\": {\r\n"
			+ "								\"$ref\": \"MyClass_id\"\r\n" + "							},\r\n"
			+ "							\"name\": \"association_name\",\r\n"
			+ "							\"end1\": {\r\n"
			+ "								\"_type\": \"UMLAssociationEnd\",\r\n"
			+ "								\"_id\": \"UMLAssociationEnd1_id\",\r\n"
			+ "								\"_parent\": {\r\n"
			+ "									\"$ref\": \"association_id\"\r\n"
			+ "								},\r\n"
			+ "								\"name\": \"linkAttribute1_name\",\r\n"
			+ "								\"reference\": {\r\n"
			+ "									\"$ref\": \"MyClass_id\"\r\n" + "								},\r\n"
			+ "								\"multiplicity\": \"end_2_myMultiplicity\""
			+ "							},\r\n" + "							\"end2\": {\r\n"
			+ "								\"_type\": \"UMLAssociationEnd\",\r\n"
			+ "								\"_id\": \"UMLAssociationEnd2_id\",\r\n"
			+ "								\"_parent\": {\r\n"
			+ "									\"$ref\": \"association_id\"\r\n"
			+ "								},\r\n"
			+ "								\"name\": \"linkAttribute2_name\",\r\n"
			+ "								\"reference\": {\r\n"
			+ "									\"$ref\": \"MyClass2_id\"\r\n" + "								},\r\n"
			+ "								\"aggregation\": \"end_1_myAggregationType\"\r\n"
			+ "							}\r\n" + "						}";

	private String UMLDependencyBase = "{\r\n" + "							\"_type\": \"UMLDependency\",\r\n"
			+ "							\"_id\": \"dependency_id\",\r\n"
			+ "							\"_parent\": {\r\n"
			+ "								\"$ref\": \"MyClass_id\"\r\n" + "							},\r\n"
			+ "							\"name\": \"dependencyName\",\r\n"
			+ "							\"source\": {\r\n" + "								\"$ref\": \"source_id\"\r\n"
			+ "							},\r\n" + "							\"target\": {\r\n"
			+ "								\"$ref\": \"target_id\"\r\n" + "							}\r\n"
			+ "						}";

	private String data;

	public void generateClasses(UMLDiagram diagram) {
		for (UMLClass c : diagram.getMyClasses()) {
			String newUMLClass = UMLClassBase;
			newUMLClass = newUMLClass.replace("MyClass_id", c.getId());
			newUMLClass = newUMLClass.replace("MyClass_name", c.getName());
			newUMLClass = c.isInterface() ? newUMLClass.replace("UMLClass", "UMLInterface") : newUMLClass;
			newUMLClass = c.isAbstract() ? newUMLClass.replace("MyClass_isAbstract", "true")
					: newUMLClass.replace("MyClass_isAbstract", "false");

			String allUMLAttributes = "";
			for (UMLAttribute v : c.getAttributes()) {
				String UMLAttribute = UMLAttributeBase;
				UMLAttribute = UMLAttribute.replace("UMLAttribute_id", v.getId());
				UMLAttribute = UMLAttribute.replace("myAttribute_name", v.getName());
				UMLAttribute = UMLAttribute.replace("MyClass_id", v.getMyClassId());
				UMLAttribute = UMLAttribute.replace("myAttribute_type", v.getType());
				UMLAttribute = UMLAttribute.replace("myAttribute_visibility", v.getVisibility());
				UMLAttribute = v.isStatic() ? UMLAttribute.replace("myAttribute_isStatic", "true")
						: UMLAttribute.replace("myAttribute_isStatic", "false");

				allUMLAttributes += UMLAttribute + ",";
			}
			if (c.getAttributes().size() > 0)
				allUMLAttributes = allUMLAttributes.substring(0, allUMLAttributes.length() - 1);
			newUMLClass = newUMLClass.replace("REPLACE_ATTRIBUTES", allUMLAttributes);

			String allUMLOperations = "";
			for (UMLOperation o : c.getOperations()) {
				String UMLOperation = UMLOperationBase;
				UMLOperation = UMLOperation.replaceAll("UMLOperation_id", o.getId());
				UMLOperation = UMLOperation.replace("myOperation_name", o.getName());
				UMLOperation = UMLOperation.replace("MyClass_id", o.getMyClassId());
				UMLOperation = UMLOperation.replace("myOperation_visibility", o.getVisibility());
				UMLOperation = UMLOperation.replace("return_id", Integer.toString(++JavaAnalyser.uniqueID));
				UMLOperation = UMLOperation.replace("return_type", o.getReturnType());

				UMLOperation = o.isStatic() ? UMLOperation.replace("myOperation_isStatic", "true")
						: UMLOperation.replace("myOperation_isStatic", "false");
				UMLOperation = o.isAbstract() ? UMLOperation.replace("myOperation_isAbstract", "true")
						: UMLOperation.replace("myOperation_isAbstract", "false");

				String UMLParameters = "";
				for (UMLParameter v : o.getUmlParameters()) {
					String UMLParameter = UMLParameterBase;
					UMLParameter = UMLParameter.replace("UMLParameter_id", v.getId());
					UMLParameter = UMLParameter.replace("UMLParameter_name", v.getName());
					UMLParameter = UMLParameter.replace("UMLOperation_id", v.getMyOperationId());
					UMLParameter = UMLParameter.replace("UMLParameter_type", v.getType());
					UMLParameters += UMLParameter + ",";
				}
				if (o.getUmlParameters().size() > 0) {
					UMLOperation = UMLOperation.replace("REPLACE_PARAMETERS", UMLParameters);
				} else {
					UMLOperation = UMLOperation.replace("REPLACE_PARAMETERS", "");
				}

				allUMLOperations += UMLOperation + ",";
			}
			if (c.getOperations().size() > 0)
				allUMLOperations = allUMLOperations.substring(0, allUMLOperations.length() - 1);
			newUMLClass = newUMLClass.replace("REPLACE_OPERATIONS", allUMLOperations);

			String allUMLAssociations = "";
			for (UMLAssociation a : c.getAssociations()) {
				String UMLAssociation = UMLAssociationBase;
				UMLAssociation = UMLAssociation.replaceAll("association_id", a.getId());
				UMLAssociation = UMLAssociation.replaceAll("MyClass_id", c.getId());
				UMLAssociation = UMLAssociation.replace("association_name", a.getName());
				UMLAssociation = UMLAssociation.replace("UMLAssociationEnd1_id", a.getEnd1().getId());
				UMLAssociation = UMLAssociation.replace("linkAttribute1_name", a.getEnd1().getName());
				UMLAssociation = UMLAssociation.replace("UMLAssociationEnd2_id", a.getEnd2().getId());
				UMLAssociation = UMLAssociation.replace("linkAttribute2_name", a.getEnd2().getName());
				UMLAssociation = UMLAssociation.replace("MyClass2_id", a.getEnd2().getAssociatedClassId());
				UMLAssociation = UMLAssociation.replace("end_1_myAggregationType", a.getEnd2().getAggregationType());
				UMLAssociation = UMLAssociation.replace("end_2_myMultiplicity", a.getEnd1().getMultiplicity());
				allUMLAssociations += UMLAssociation + ",";
			}
			if (c.getAssociations().size() > 0 && c.getDependencies().size() == 0)
				allUMLAssociations = allUMLAssociations.substring(0, allUMLAssociations.length() - 1);
			newUMLClass = newUMLClass.replace("REPLACE_ASSOCIATIONS", allUMLAssociations);

			String allUMLDependencies = "";
			for (UMLSourceTargetRelation a : c.getDependencies()) {
				String UMLDependency = UMLDependencyBase;
				UMLDependency = UMLDependency.replaceAll("dependency_id", a.getId());
				UMLDependency = UMLDependency.replaceAll("MyClass_id", c.getId());
				UMLDependency = UMLDependency.replace("dependencyName", a.getName());
				UMLDependency = UMLDependency.replace("source_id", a.getSource());
				UMLDependency = UMLDependency.replace("target_id", a.getTarget());
				UMLDependency = UMLDependency.replace("UMLDependency", a.getDependencyType());
				allUMLDependencies += UMLDependency + ",";
			}
			if (c.getDependencies().size() > 0)
				allUMLDependencies = allUMLDependencies.substring(0, allUMLDependencies.length() - 1);
			newUMLClass = newUMLClass.replace("REPLACE_DEPENDENCIES", allUMLDependencies);

			UMLClassList.add(newUMLClass);
		}
	}

	public void generateViews(UMLDiagram diagram) {
		for (UMLClass c : diagram.getMyClasses()) {
			String newUMLClassView = UMLClassViewBase;

			newUMLClassView = newUMLClassView.replace("MyClass1View_id", (c.getId() + "view"));
			newUMLClassView = newUMLClassView.replace("MyClass_id", c.getId());
			newUMLClassView = c.isInterface() ? newUMLClassView.replace("UMLClassView", "UMLInterfaceView")
					: newUMLClassView;

			UMLClassViewList.add(newUMLClassView);
			for (UMLAssociation a : c.getAssociations()) {
				String newUMLAssociationView = UMLAssociationViewBase;
				newUMLAssociationView = newUMLAssociationView.replace("associationView_id",
						Integer.toString(++JavaAnalyser.uniqueID));
				newUMLAssociationView = newUMLAssociationView.replace("association_id", a.getId());
				newUMLAssociationView = newUMLAssociationView.replace("MyClass2View_id",
						a.getEnd2().getAssociatedClassId() + "view");
				newUMLAssociationView = newUMLAssociationView.replace("MyClass1View_id",
						a.getEnd1().getAssociatedClassId() + "view");
				UMLAssociationViewList.add(newUMLAssociationView);
			}

			for (UMLSourceTargetRelation a : c.getDependencies()) {
				String newUMLDependencyView = UMLDependencyViewBase;
				newUMLDependencyView = newUMLDependencyView.replace("dependencyView_id",
						Integer.toString(++JavaAnalyser.uniqueID));
				newUMLDependencyView = newUMLDependencyView.replace("dependency_id", a.getId());
				newUMLDependencyView = newUMLDependencyView.replace("MyClass2View_id", a.getTarget() + "view");
				newUMLDependencyView = newUMLDependencyView.replace("MyClass1View_id", a.getSource() + "view");
				newUMLDependencyView = newUMLDependencyView.replace("UMLDependencyView", a.getDependencyType()+"View");

				UMLDependencyViewList.add(newUMLDependencyView);
			}

		}

	}

	public void generateData(UMLDiagram diagram) {

		generateClasses(diagram);
		generateViews(diagram);

		for (String s : UMLClassList) {
			allUMLClass += (s + ",");
		}
		if (UMLClassList.size() > 0)
			allUMLClass = allUMLClass.substring(0, allUMLClass.length() - 1);

		for (String s : UMLClassViewList) {
			allUMLClassView += (s + ",");
		}
		if (UMLClassViewList.size() > 0 && UMLAssociationViewList.size() == 0 && UMLDependencyViewList.size() == 0)
			allUMLClassView = allUMLClassView.substring(0, allUMLClassView.length() - 1);

		for (String s : UMLAssociationViewList) {
			allUMLAssociationView += (s + ",");
		}
		if (UMLAssociationViewList.size() > 0 && UMLDependencyViewList.size() == 0)
			allUMLAssociationView = allUMLAssociationView.substring(0, allUMLAssociationView.length() - 1);

		for (String s : UMLDependencyViewList) {
			allUMLDependencyView += (s + ",");
		}
		if (UMLDependencyViewList.size() > 0)
			allUMLDependencyView = allUMLDependencyView.substring(0, allUMLDependencyView.length() - 1);

		data = "{\r\n" + "	\"_type\": \"Project\",\r\n" + "	\"_id\": \"project_id\",\r\n"
				+ "	\"name\": \"MyProject\",\r\n" + "	\"ownedElements\": [\r\n" + "		{\r\n"
				+ "			\"_type\": \"UMLModel\",\r\n" + "			\"_id\": \"model_id\",\r\n"
				+ "			\"_parent\": {\r\n" + "				\"$ref\": \"project_id\"\r\n" + "			},\r\n"
				+ "			\"name\": \"MyModel\",\r\n" + "			\"ownedElements\": [\r\n" + "				{\r\n"
				+ "					\"_type\": \"UMLClassDiagram\",\r\n"
				+ "					\"_id\": \"diagram_id\",\r\n" + "					\"_parent\": {\r\n"
				+ "						\"$ref\": \"model_id\"\r\n" + "					},\r\n"
				+ "					\"name\": \"MyDiagram\",\r\n" + "					\"defaultDiagram\": true,\r\n"
				+ "					\"ownedViews\": [\r\n" + allUMLClassView + allUMLAssociationView
				+ allUMLDependencyView + "					]\r\n" + "				},\r\n" + allUMLClass
				+ "			]\r\n" + "		}\r\n" + "	]\r\n" + "}";
	}

	public MdjGenerator() {

	}

	public void generateJsonFileFromDiagram(String path, UMLDiagram diagram) {
		generateData(diagram);
		try {
			File file = new File(path + "/Diagram.mdj");
			if (file.createNewFile()) {
				System.out.println("Fichier créé: " + file.getName());
			} else {
				System.out.println("Le fichier existe déjà.");
			}
			FileWriter writer = new FileWriter(path + "/Diagram.mdj");
			writer.write(data);
			writer.close();
			FileWriter writer2 = new FileWriter(path + "/Diagram.json");
			writer2.write(data);
			writer2.close();

		} catch (IOException e) {
			System.out.println("Erreur.");
			e.printStackTrace();
		}
	}

}
