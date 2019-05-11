/**
 Create class XmlParser in package inputOutput; it should do the 
following:
1.
Member variable
a.
ConnectionData connectionData
b.
Document document
2.
Getter
 for member variable connectionData
3.
Constructor should do the following:
a.
Defined one parameter
 of type String storing the 
name of
 the XML file to
 parse
b.
Call method
 parseXmlFile()
 passing the parameter
as an argument
4.
Method parseXmlFile() should do the following:
a.
return type void
b.
Define one parameter
 of type String storing the 
name of
 the XML file to
 parse
c.
Create an instance of
 class DocumentBuilderFactory
d.
Create an instance of
 class DocumentBuilder
e.
Instantiate
 member
 variable document to the parse 
method of
 class
 DocumentBuilder passing argument 
ClassLoader.getSystemResourceAsStream() passing 
the parameter for method parseXmlFile as an 
argument 
f.
Create an instance of
 class NodeList, instantiate it 
with method 
getDocumentElement().getChildNodes() from 
member variable document
g.
Loop through the
 length of the NodeList instance
i.
Create an instance of
 class Node, instantiate
it to method item() of
 instance NodeList
 */
package inputOutput;

import java.io.IOException;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


public class XmlParser
{
    private ConnectionData connectionData;
    private Document document;
    private static final Logger logger = Logger.getLogger(XmlParser.class.getName());
    
    public XmlParser(String file) 
    {
        parseXmlFile(file);
    }

 

    private void parseXmlFile(String fileName)
    {        
        //Get the DOM Builder Factory
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        try 
        {
            //Using factory get an instance of document builder
            DocumentBuilder db = dbf.newDocumentBuilder();

            //Load and Parse the XML document
            //document contains the complete XML as a Tree.
            document = db.parse(ClassLoader.getSystemResourceAsStream(fileName));
            
            //Iterating through the nodes and extracting the data.
            NodeList nodeList = document.getDocumentElement().getChildNodes();

            for(int i = 0; i < nodeList.getLength(); i++) 
            {
                //get the <Employee> element
                Node node = nodeList.item(i);

                if(node instanceof Element)
                {
                    //for each <employee> element get text or int values of
                    //name ,id, age and name
                    String type = node.getAttributes().getNamedItem("type").getNodeValue();
                    connectionData = new ConnectionData();
                    connectionData.setType(type);
                    
                    NodeList childNodes = node.getChildNodes();

                    for (int j = 0; j < childNodes.getLength(); j++) 
                    {
                        Node cNode = childNodes.item(j);  
 
                        //Identifying the child tag of employee encountered.
                        if (cNode instanceof Element) 
                        {
                            String content = cNode.getLastChild().getTextContent().trim();
                            switch (cNode.getNodeName()) 
                            {
                                case "url":
                                 connectionData.setUrl(content);
                                 break;
                                case "ipaddress":
                                    connectionData.setIpaddress(content);
                                    break;
                                case "port":
                                    connectionData.setPort(content);
                                    break;
                                case "database":
                                    connectionData.setDatabase(content);
                                    break;
                                case "login":
                                    connectionData.setLogin(content);
                                    break;
                                case "password":
                                    connectionData.setPassword(content);
                            }
                        }
                    }             
               }
            }
        }
        catch(ParserConfigurationException pce) 
        {
            pce.printStackTrace();
        }
        catch(SAXException se) 
        {
            se.printStackTrace();
        }
        catch(IOException ioe) 
        {
            ioe.printStackTrace();
        }
    }
    
    public ConnectionData getConnectionData()
    {
        return connectionData;
    }
    
}

