package com.company;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class MyXml {
    public static void AverageCheck(String inputFilePath, String outputFilePath){
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(inputFilePath);

            NodeList nl = doc.getElementsByTagName("*");
            for (int j = 0; j < nl.getLength(); j++){
                Element e = (Element) nl.item(j);
                if(e.getTagName().equals("student")){
                    ArrayList<Double> marks = new ArrayList<>();
                    Node avgNode = null;
                    for (Node currChild = e.getFirstChild(); currChild != e.getLastChild(); currChild = currChild.getNextSibling()){
                        if(currChild.getNodeName().equals("subject")){
                            marks.add(Double.valueOf(currChild.getAttributes().getNamedItem("mark").getNodeValue()));
                        }
                        else if (currChild.getNodeName().equals("average")){
                            avgNode = currChild;
                        }
                    }

                    System.out.println("calculate avg:" + getAvg(marks).toString());
                    Double avg = getAvg(marks);
                    if(avgNode == null && avg > 0){
                        Element newAvgNode = doc.createElement("avg");
                        newAvgNode.setTextContent(avg.toString());
                        e.appendChild(newAvgNode);
                    }
                    else if(avgNode !=null){
                        avgNode.setTextContent(avg.toString());
                    }
                }
            }
            FileOutputStream outputStream = new FileOutputStream(outputFilePath);
            writeXml(doc, outputStream);
        } catch (Exception e){
            System.out.println(e.toString());
        }
    }

    static Double getAvg(ArrayList<Double> arrayList){
        Double len = Double.valueOf(arrayList.size());
        if(len==0){return -1.0;}

        Double summ = arrayList.stream().reduce(0.0, Double::sum);
        return summ/len;
    }

    private static void writeXml(Document doc, OutputStream output) throws TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(output);

        transformer.transform(source, result);
    }
}
