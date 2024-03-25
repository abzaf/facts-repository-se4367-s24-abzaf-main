package facts;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class FactListToXMLWriter {
    private String filePath;

    public FactListToXMLWriter(String filePath) {
        this.filePath = filePath;
    }

    public void writeFactList(FactList factList) throws Exception {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.newDocument();

        Element rootElement = doc.createElement("fact-list");
        doc.appendChild(rootElement);

        ArrayList<Fact> facts = factList.getFactList();
        for (Fact fact : facts) {
            Element factElement = doc.createElement("fact");

            Element authorElement = doc.createElement("author");
            authorElement.appendChild(doc.createTextNode(fact.getAuthor()));
            factElement.appendChild(authorElement);

            Element textElement = doc.createElement("fact-text");
            textElement.appendChild(doc.createTextNode(fact.getText()));
            factElement.appendChild(textElement);

            Element typeElement = doc.createElement("fact-type");
            typeElement.appendChild(doc.createTextNode(fact.getType()));
            factElement.appendChild(typeElement);

            rootElement.appendChild(factElement);
        }

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);

        File file = new File(filePath);
        FileOutputStream fos = new FileOutputStream(file);
        StreamResult result = new StreamResult(fos);
        transformer.transform(source, result);
        fos.close();
    }
}
