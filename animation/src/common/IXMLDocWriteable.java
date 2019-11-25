package common;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public interface IXMLDocWriteable {
	void saveData(Document d, Element e);
}
