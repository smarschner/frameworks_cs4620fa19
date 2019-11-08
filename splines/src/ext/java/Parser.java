package ext.java;

import java.io.File;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import common.Image;
import egl.math.Color;
import egl.math.Colord;
import egl.math.Vector2;
import egl.math.Vector2d;
import egl.math.Vector3;
import egl.math.Vector3d;
import egl.math.Vector4;
import egl.math.Vector4d;

/**
 * Simple XML based scene parser.  The parser works recursively on the
 * nodes of the XML file.  Each node will be interpreted as a Java Object.
 * The type of each node is inferred from the nodes name and the class
 * type of the parent node (the class type of the root node
 * is always assumed to be Scene).  If <name> is the name of
 * the node, the class type of the parent node is searched for methods
 * named, first, set<name> and then add<name>.  If no such method is found
 * or if the found method does not take exactly one parameter, parsing
 * fails.  If a valid method is found, the type of the node is assumed
 * to be the type of the parameter of the found method.
 *
 * After the node is parsed, the method found in the parent object is
 * called with the parsed value of the node as the only argument.  It
 * is possible to specify that class type of the parsed node be a subclass
 * of the automatically determined class type by setting the "type"
 * attribute in the node.  The value of the type node must be the fully 
 * qualified name for class (i.e. ray.surface.Sphere) Additionally, nodes 
 * can be named with the "name" attribute.  After they are added to the 
 * parent, named nodes are added to a hash table internal to the parser.  
 * Later, other nodes can reference a named node by using a "ref" attribute.  
 * The value of the  reference attribute is used to index in the hash table 
 * and the previously stored node with the matching name is used instead of
 * a parsed value.
 *
 * There are special routines for explicitly parsing primitives,
 * arrays of primitives, Tuple3 (either Vector3 or Point3), Colors, Strings,
 * Images, and Meshes.  If a node is found, as above, to describe any of
 * these types a special method is used instead of the above
 * recursive procedure.
 *
 * An example is, hopefully, even clearer.  Consider a simple example
 * input:
 *
 * <scene>
 *    <camera>
 *      <eye> 1 1 1 </eye>
 *    </camera>
 *    <shader name="white" type="ray.shader.Lambertian">
 *      <diffuseColor>1 1 1</diffuseColor>
 *    </shader>
 *    <surface type="ray1.surface.Sphere">
 *      <shader ref="white"/>
 *      <center>1 1 1</center>
 *      <radius>1</radius>
 *    </surface>
 * </scene>
 *
 * This would be parsed as follows:
 *
 *  -- The scene tag is automatically supplied the type Scene
 *  -- The camera tag is read, and the setCamera method is
 *     found in the Scene class.
 *  -- Set camera takes a Camera as an argument, so the <camera>
 *     node is parsed as a Camera object
 *  -- The <eye> node is processed similarly, finding setEye
 *     in camera, but setEye takes a Vector3 as an argument
 *     so its value is parsed directly as a Vector3 (whitespace
 *     delimited)
 *  -- After <eye> is processed, setEye(eye) is called in Camera
 *  -- Then setCamera(camera) is called on Scene
 *  -- The <shader> tag is processed in the same fashion, but
 *     the type attribute specifies that it be parsed additionally
 *     as a Lambertian shader.
 *  -- The name attribute in the shader tag stores it for
 *     later use in the <surface> tag.  The <shader>
 *     tag in the <surface> tag is not parsed, but the same object
 *     produced by parsing the above "white" shader is supplied
 *     instead.
 *
 * The parser is completely generic and can be used with new classes
 * that you write for your project.  There are only two limitations.
 * First, every object used as a node must have a zero argument constructor
 * so the parser an instantiate an empty object to add elements too.
 * Second, every node that will have children must have setXXX or addXXX
 * methods for each child type and they must take exactly one parameter
 * of the type of the child node.
 *
 * @author arbree Aug 18, 2005
 * Parser.java
 * Copyright 2005 Program of Computer Graphics, Cornell University
 */
@SuppressWarnings("rawtypes")
public class Parser {

	/** Java document builder used to parse XML * */
	private DocumentBuilder db;

	/** Map of references to their names **/
	public static HashMap<String, Object> references = new HashMap<String, Object>();

	/** Creates a new Parser. */
	public Parser() {

		try {
			db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		} catch (Exception e) {

			throw new Error("Error instantiating the parser.");

		}
	}

	/**
	 * Parses the String and generates either an Integer object or a Double
	 * object, depending on the given class.
	 *
	 * @param c the class to parse the string as
	 * @param text the text to parse
	 * @return a new object of the given type
	 */
	private static Object parsePrimitive(Class c, String text) {
		if (c == Byte.TYPE) {
			return new Byte(text);
		} else if (c == Short.TYPE) {
			return new Short(text);
		} else if (c == Integer.TYPE) {
			return new Integer(text);
		} else if (c == Long.TYPE) {
			return new Long(text);
		} else if (c == Float.TYPE) {
			return new Float(text);
		} else if (c == Double.TYPE) {
			return new Double(text);
		} else if (c == Boolean.TYPE) {
			return new Boolean(text);
		} else {
			throw new Error("Cannot parse primitive of type " + c);
		}
	}

	/**
	 * Parse an array
	 *
	 * @param componentType the type of each component in the array
	 * @param text the text in the node to be parsed
	 * @return an ArrayList of temporary values used to create the array at the
	 *         return point
	 */
	private static ArrayList<Object> parseArray(Class componentType, String text) {

		StringTokenizer t = new StringTokenizer(text);
		ArrayList<Object> result = new ArrayList<Object>();
		while (t.hasMoreTokens()) {
			String token = t.nextToken();
			result.add(parsePrimitive(componentType, token));
		}
		return result;
	}

	/**
	 * Return the Method object representing the string methodName in Class c.
	 * Return null if no such method if found.
	 *
	 * @param c the class to find a method in
	 * @param methodName the name of the method to find
	 * @return the Method object if a method is found, null otherwise
	 */
	private Method findMethod(Class c, String methodName) {

		// Get the method list
		Method[] methods = c.getMethods();
		for (int j = 0; j < methods.length; j++) {
			Method m = methods[j];
			if (methodName.equalsIgnoreCase(m.getName())) {
				return m;
			}
		}
		return null;

	}

	/**
	 * Parse an object node. The node is assumed to be of Class c and is
	 * represented by Node n.
	 *
	 * @param c Class type to read from node n
	 * @param n the node to parse into an instance of c
	 * @return the object read
	 */
	private Object parseObject(Class<?> c, Node n) {

		Object resultingObject = null;
		NamedNodeMap attributes = n.getAttributes();
		Node typeAttribute = attributes.getNamedItem("type");
		Node nameAttribute = attributes.getNamedItem("name");
		Node refAttribute = attributes.getNamedItem("ref");

		if (typeAttribute != null) {
			String className = typeAttribute.getNodeValue();
			try {
				Class<?> possibleClass;
				// Try to look up the class; if it fails, try looking in the package
				// of the given base type
				try {
					possibleClass = Class.forName(className);
				} catch (ClassNotFoundException e) {
					possibleClass = Class.forName(c.getPackage().getName()
							+ "." + className);
				}
				if (c.isAssignableFrom(possibleClass)) {
					c = possibleClass; // Set the current active class to user specified type
				} else {
					throw new Error("Type " + className
							+ " does not extend or implement " + c.getName());
				}
			} catch (ClassNotFoundException e) {
				throw new Error("Class could not be found: " + className);
			}
		}

		// If the node specifies a type, check that it is assignable to the current
		// output type for this node
		if (typeAttribute != null) {
			String className = typeAttribute.getNodeValue();
			try {
				Class<?> possibleClass;
				// Try to look up the class; if it fails, try looking in the package
				// of the given base type
				try {
					possibleClass = Class.forName(className);
				} catch (ClassNotFoundException e) {
					possibleClass = Class.forName(c.getPackage().getName()
							+ "." + className);
				}
				if (c.isAssignableFrom(possibleClass)) {
					c = possibleClass; // Set the current active class to user specified type
				} else {
					throw new Error("Type " + className
							+ " does not extend or implement " + c.getName());
				}
			} catch (ClassNotFoundException e) {
				throw new Error("Class could not be found: " + className);
			}
		}

		// Check that our current type is valid
		if (c.isArray() && !c.getComponentType().isPrimitive()) {
			throw new Error("Cannot parse arrays of non-primitive types");
		}

		// Get the sub elements of this node
		NodeList children = n.getChildNodes();

		//If the object is a reference, just return the value referenced
		if (refAttribute != null) {
			String name = refAttribute.getNodeValue();
			resultingObject = references.get(name);
			if (resultingObject == null) {
				throw new Error("Unresolved reference: " + name);
			}
		}

		// Check for certain special classes of the current node
		else {
			// Interpret the text values of all children nodes as objects
			for (int i = 0; i < children.getLength(); i++) {
				Node child = children.item(i);
				if (child.getNodeType() == Node.TEXT_NODE) {
					resultingObject = parseObject(c, child.getNodeValue());
					break;
				}
			}
		}

		// Otherwise the node represents a general object
		if(resultingObject == null) {

			// Create one!
			try {
				resultingObject = c.newInstance();
			} catch (Exception e) {
				throw new Error("Error instantiating object of class: "
						+ c.getName());
			}

			// For each child
			for (int i = 0; i < children.getLength(); i++) {
				Node child = children.item(i);

				// Skip non-element nodes
				if (child.getNodeType() != Node.ELEMENT_NODE) {
					continue;
				}

				//Find the method to use for adding
				Method foundMethod = null;
				String childName = child.getNodeName();

				// Check setXXX
				foundMethod = findMethod(c, "set" + childName);

				// If can't find setXXX method, look for addXXX method instead
				if (foundMethod == null)
					foundMethod = findMethod(c, "add" + childName);

				// Its an error if no method was found
				if (foundMethod == null) {
					throw new Error("Could not find a method to use to add "
							+ childName + " to the class type " + c.getName()
							+ ".");
				}

				// Check that the method has the right number of parameters
				Class[] parameterTypes = foundMethod.getParameterTypes();
				if (parameterTypes.length != 1) {
					throw new Error("Method " + foundMethod.getName()
							+ " must take exactly one parameter.");
				}

				// If the type is primitive, switch to corresponding Object type
				// to parse. Method invocation will automatically take care of
				// converting Object types back into primitives.
				Class parameterType = parameterTypes[0];
				if (parameterType.isPrimitive()) {
					if (parameterType == Integer.TYPE) {
						parameterType = Integer.class;
					} else if (parameterType == Float.TYPE) {
						parameterType = Float.class;
					} else if (parameterType == Double.TYPE) {
						parameterType = Double.class;
					} else if (parameterType == Boolean.TYPE) {
						parameterType = Boolean.class;
					} else {
						throw new Error("Cannot parse primitives of type "
								+ parameterType);
					}
				}

				// Recursively parse value of child element
				Object childValue = parseObject(parameterType, child);

				// Call the setter method with the parsed value;
				try {

					System.out.println(foundMethod.getName());
					//Invoke the setter method
					foundMethod.invoke(resultingObject,
							new Object[] { childValue });

				} catch (Exception e) {
					System.err.println("Error invoking the method "
							+ foundMethod.getName() + ".");
					e.printStackTrace();
				}
			}
		}

		//Place the object in the reference list
		if (nameAttribute != null) {
			String name = nameAttribute.getNodeValue();
			references.put(name, resultingObject);
		}

		return resultingObject;

	}

	/**
	 * Parses a given file to generate an object of the given class.
	 *
	 * @param filename the name of the XML file to parse
	 * @param c the class of the object to parse
	 * @return a new object of the given class
	 */
	public Object parse(String filename, Class<?> c) {

		File file = new File(filename);

		// Parse the XML
		Object result = null;
		try {

			Document doc = db.parse(file);
			Element root = doc.getDocumentElement();
			result = parseObject(c, root);

		} catch (Exception e) {
			System.out.println("Exception occurred while parsing: " + filename);
			e.printStackTrace();
		}

		return result;
	}

	/**
	 * Parse some special types of objects
	 *
	 * @param c the class type to parse
	 * @param text the text to interpret as an object
	 * @return the object parsed
	 */
	private Object parseObject(Class c, String text) {
		// Parse The Basic Types
		Object basicObject = null;
		
		// If non-string expected and text is whitespace, terminate silently.
		if (c != String.class && text.matches("\\s+")) {
			return null;
		}
		
		if (c == String.class) { return text; }
		else if (c == Byte.class) { return new Byte(text); }
		else if (c == Short.class) { return new Short(text); }
		else if (c == Integer.class) { return new Integer(text); }
		else if (c == Long.class) { return new Long(text); }
		else if (c == Float.class) { return new Float(text); }
		else if (c == Double.class) { return new Double(text); }
		else if (c == Boolean.class) { return new Boolean(text); }
		else if ((basicObject = tryParseFromArray(c, text)) != null) { return basicObject; }
		else if ((basicObject = tryParseFromVector2(c, text)) != null) { return basicObject; }
		else if ((basicObject = tryParseFromVector3(c, text)) != null) { return basicObject; }
		else if ((basicObject = tryParseFromVector4(c, text)) != null) { return basicObject; }
		else if ((basicObject = tryParseFromVector2d(c, text)) != null) { return basicObject; }
		else if ((basicObject = tryParseFromVector3d(c, text)) != null) { return basicObject; }
		else if ((basicObject = tryParseFromVector4d(c, text)) != null) { return basicObject; }
		else if ((basicObject = tryParseFromColor(c, text)) != null) { return basicObject; }
		else if ((basicObject = tryParseFromColord(c, text)) != null) { return basicObject; }
		else if ((basicObject = tryParseFromImage(c, text)) != null) { return basicObject; }
		else { 
			System.out.println("Cannot parse type: " + c);
			return null;
		}
	}
	@SuppressWarnings("unchecked")
	public static Object tryParseFromImage(Class c, String text) {
		if (Image.class.isAssignableFrom(c)) {
			ArrayList tempArray = parseArray(Integer.TYPE, text);
			if (tempArray.size() != 2) {
				throw new Error("Image declaration specifies only width and height");
			}
			Image result;
			try {
				Constructor imageConstructor = c.getConstructor(new Class[] { Integer.TYPE, Integer.TYPE });
				result = (Image)imageConstructor
						.newInstance(new Object[] { (Integer) tempArray.get(0),
								(Integer) tempArray.get(1) });
			} catch (Exception e) {
				throw new Error("Error instantiating object of class: " + c.getName());
			}
			return result;
		}
		return null;
	}
	public static Object tryParseFromArray(Class c, String text) {
		if ((c.isArray() && c.getComponentType().isPrimitive())) {
			ArrayList tempArray = parseArray(c.getComponentType(), text);
			Object result = Array.newInstance(c.getComponentType(), tempArray.size());
			for (int i = 0; i < tempArray.size(); i++) {
				Array.set(result, i, tempArray.get(i));
			}
			return result;
		}
		return null;
	}
	public static Object tryParseFromVector2d(Class c, String text) {
		if (Vector2d.class.isAssignableFrom(c)) {
			ArrayList tempArray = parseArray(Double.TYPE, text);
			if (tempArray.size() != 2) {
				throw new Error("Vector is not of length 2 (" + tempArray.size() + ")");
			}
			return new Vector2d(
				(Double)tempArray.get(0),
				(Double)tempArray.get(1)
				);
		}
		return null;
	}
	public static Object tryParseFromVector3d(Class c, String text) {
		if (Vector3d.class.isAssignableFrom(c)) {
			ArrayList tempArray = parseArray(Double.TYPE, text);
			if (tempArray.size() != 3) {
				throw new Error("Vector is not of length 3 (" + tempArray.size() + ")");
			}
			return new Vector3d(
				(Double)tempArray.get(0),
				(Double)tempArray.get(1),
				(Double)tempArray.get(2)
				);
		}
		return null;
	}
	public static Object tryParseFromVector4d(Class c, String text) {
		if (Vector4d.class.isAssignableFrom(c)) {
			ArrayList tempArray = parseArray(Double.TYPE, text);
			if (tempArray.size() != 4) {
				throw new Error("Vector is not of length 4 (" + tempArray.size() + ")");
			}
			return new Vector4d(
				(Double)tempArray.get(0),
				(Double)tempArray.get(1),
				(Double)tempArray.get(2),
				(Double)tempArray.get(3)
				);
		}
		return null;
	}
	public static Object tryParseFromVector2(Class c, String text) {
		if (Vector2.class.isAssignableFrom(c)) {
			ArrayList tempArray = parseArray(Float.TYPE, text);
			if (tempArray.size() != 2) {
				throw new Error("Vector is not of length 2 (" + tempArray.size() + ")");
			}
			return new Vector2(
				(Float)tempArray.get(0),
				(Float)tempArray.get(1)
				);
		}
		return null;
	}
	public static Object tryParseFromVector3(Class c, String text) {
		if (Vector3.class.isAssignableFrom(c)) {
			ArrayList tempArray = parseArray(Float.TYPE, text);
			if (tempArray.size() != 3) {
				throw new Error("Vector is not of length 3 (" + tempArray.size() + ")");
			}
			return new Vector3(
				(Float)tempArray.get(0),
				(Float)tempArray.get(1),
				(Float)tempArray.get(2)
				);
		}
		return null;
	}
	public static Object tryParseFromVector4(Class c, String text) {
		if (Vector4.class.isAssignableFrom(c)) {
			ArrayList tempArray = parseArray(Float.TYPE, text);
			if (tempArray.size() != 4) {
				throw new Error("Vector is not of length 4 (" + tempArray.size() + ")");
			}
			return new Vector4(
				(Float)tempArray.get(0),
				(Float)tempArray.get(1),
				(Float)tempArray.get(2),
				(Float)tempArray.get(3)
				);
		}
		return null;
	}
	public static Object tryParseFromColord(Class c, String text) {
		if(Colord.class.isAssignableFrom(c)) {
			ArrayList tempArray = parseArray(Double.TYPE, text);
			if (tempArray.size() != 3) {
				throw new Error("Color is not of length 3 (" + tempArray.size() + ")");
			}
			return new Colord(
				(Double)tempArray.get(0),
				(Double)tempArray.get(1),
				(Double)tempArray.get(2)
				);
		}
		return null;
	}
	public static Object tryParseFromColor(Class c, String text) {
		if(Color.class.isAssignableFrom(c)) {
			ArrayList tempArray = parseArray(Integer.TYPE, text);
			if (tempArray.size() != 4) {
				throw new Error("Color is not of length 4 (" + tempArray.size() + ")");
			}
			return new Color(
				(Integer)tempArray.get(0),
				(Integer)tempArray.get(1),
				(Integer)tempArray.get(2),
				(Integer)tempArray.get(3)
				);
		}
		return null;
	}
}
