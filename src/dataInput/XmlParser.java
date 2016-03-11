package dataInput;

import java.io.IOException;

import javax.xml.parsers.*;
import org.xml.sax.*;
import org.w3c.dom.*;

import java.util.ArrayList;

public class XmlParser {
	
	private String name = null;
	private String afm = null;
	private String status = null;
	private String income = null;
	private ArrayList<String> person;
		
	public boolean readXML(String xml) {
		
        person = new ArrayList<String>();
        Document dom;
        // Make an  instance of the DocumentBuilderFactory
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            // use the factory to take an instance of the document builder
            DocumentBuilder db = dbf.newDocumentBuilder();
            // parse using the builder to get the DOM mapping of the    
            // XML file
            dom = db.parse(xml);

            Element doc = dom.getDocumentElement();

            name = getTextValue(name, doc, "Name");
            if (name != null) {
                if (!name.isEmpty())
                    person.add(name);
            }
            afm = getTextValue(afm, doc, "AFM");
            if (afm != null) {
                if (!afm.isEmpty())
                    person.add(afm);
            }
            status = getTextValue(status, doc, "Status");
            if (status != null) {
                if (!status.isEmpty())
                    person.add(status);
            }
            income = getTextValue(income, doc, "Income");
            if ( income != null) {
                if (!income.isEmpty())
                    person.add(income);
            }
            
            // For debugging:
            printResults();
            
            return true;

        } catch (ParserConfigurationException pce) {
            System.out.println(pce.getMessage());
        } catch (SAXException se) {
            System.out.println(se.getMessage());
        } catch (IOException ioe) {
            System.err.println(ioe.getMessage());
        }
        
        return false;
    }
	
	private String getTextValue(String def, Element doc, String tag) {
	    String value = def;
	    NodeList nl;
	    nl = doc.getElementsByTagName(tag);
	    if (nl.getLength() > 0 && nl.item(0).hasChildNodes()) {
	        value = nl.item(0).getFirstChild().getNodeValue();
	    }
	    return value;
	}
	
	public String getName() {
		return name;
	}

	public String getAfm() {
		return afm;
	}

	public String getStatus() {
		return status;
	}

	public String getIncome() {
		return income;
	}

	private void printResults(){
		for (int i=0; i<person.size(); i++) {
			System.out.println(person.get(i).toString());
		}
	}
}
