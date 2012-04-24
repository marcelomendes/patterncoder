package org.patterncoder.utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import org.patterncoder.dataModel.Pattern;
import org.patterncoder.dataModel.PatternComponent;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

/**
 *
 * @author Florian Siebler
 */
public class XMLUtils
{
    // Tags of pattern
    private static final String PATTERN_TAG = "pattern";
    private static final String PAT_NAME_TAG = "patternName";
    private static final String PAT_DESC_TAG = "patternDescription";
    private static final String PAT_IMG_TAG = "patternImage";
    // Tags of component
    private static final String CLASS_TAG = "class";
    private static final String CLASS_DESC = "classDescription";
    private static final String CLASS_TEMPLATE = "classTemplate";
    private static final String CLASS_DEF_NAME = "defaultName";
    private static final String CLASS_ID = "classId";
    private static final String CLASS_TYPE = "compType";
    private static final String DEPENDANT_CLASS = "dependantClass";
    //Pattern wizard tags
    private static final String WIZARD_TAG = "wizard";
    private static final String WIZARD_STEP_TAG = "step";
    private static final String STEP_COMP_ID = "compId";
    private static final String STEP_NAME = "stepName";
    private static final String STEP_DESC = "stepDesc";

    private XMLUtils()
    {
    }

    public static Pattern createPattern(Document xmlDoc) throws Exception
    {
        Pattern result = null;

        // Read pattern name and pattern description      
        NodeList nodeList = xmlDoc.getElementsByTagName(PATTERN_TAG);

        // First node in doc
        Node rootNode = nodeList.item(0);
        Element rootElement = (Element) nodeList.item(0);

        // Name and image of pattern
        NamedNodeMap parentAttributes = rootNode.getAttributes();
        Node nameNode = parentAttributes.getNamedItem(PAT_NAME_TAG);
        Node imageNode = parentAttributes.getNamedItem(PAT_IMG_TAG);
        String patternName = nameNode.getNodeValue();
        String patternImage = imageNode.getNodeValue();

        // Element description of pattern
        Element descPattern = (Element) rootElement.getElementsByTagName(PAT_DESC_TAG).item(0);
        Node descNode = descPattern.getFirstChild();
        String strPatternDesc = descNode.getNodeValue();

        // Create pattern
        if (patternName != null && strPatternDesc != null && patternImage != null)
        {
            result = new Pattern(patternName, strPatternDesc, patternImage);
        }
        else
        {
            throw new Exception("Missing pattern name, pattern description or pattern image\nSource file of pattern " + patternName);
        }

        // prepare wizard list
        NodeList wizardList = rootElement.getElementsByTagName(WIZARD_TAG);
        Element wizardElement = (Element) wizardList.item(0);
        NodeList stepList = wizardElement.getElementsByTagName(WIZARD_STEP_TAG);

        List<String[]> wizardTexts = new ArrayList<String[]>();
        for (int i = 0; i < stepList.getLength(); i++)
        {
            Node oneStep = stepList.item(i);
            NamedNodeMap attribStep = oneStep.getAttributes();
            Node stepId = attribStep.getNamedItem(STEP_COMP_ID);
            Node stepName = attribStep.getNamedItem(STEP_NAME);
            Node stepDesc = attribStep.getNamedItem(STEP_DESC);
            String strStepID = stepId.getNodeValue();
            String strStepName = stepName.getNodeValue();
            String strStepDesc = stepDesc.getNodeValue();
            wizardTexts.add(new String[]
                    {
                        strStepID, strStepName, strStepDesc
                    });
        }
        // List components
        PatternComponent tempComponent = null;
        NodeList allComponents = rootElement.getElementsByTagName(CLASS_TAG);
        for (int i = 0; i < allComponents.getLength(); i++)
        {
            // Data of component: id, name and description
            Element componentData = (Element) allComponents.item(i);
            NamedNodeMap componentAttribList = componentData.getAttributes();
            NodeList dependencyList = componentData.getElementsByTagName(DEPENDANT_CLASS);

            NodeList descriptionList = componentData.getElementsByTagName(CLASS_DESC);

            Node attribID = componentAttribList.getNamedItem(CLASS_ID);
            int id = Integer.parseInt(attribID.getNodeValue());

            Node attribCompType = componentAttribList.getNamedItem(CLASS_TYPE);
            String strCompType = attribCompType.getNodeValue();

            Node attribDefaultName = componentAttribList.getNamedItem(CLASS_DEF_NAME);
            String strDefaultName = attribDefaultName.getNodeValue();

            Node template = componentAttribList.getNamedItem(CLASS_TEMPLATE);
            String strTemplate = template.getNodeValue();

            Element item = (Element) descriptionList.item(0);
            Node firstChild = item.getFirstChild();
            String strDescription = firstChild.getNodeValue();

            tempComponent = new PatternComponent(id, strCompType, strDefaultName, strDescription, strTemplate);

            for (int t = 0; t < dependencyList.getLength(); t++)
            {
                NamedNodeMap depAtts = dependencyList.item(t).getAttributes();
                Node dep = depAtts.getNamedItem("value");
                tempComponent.addDependency(dep.getNodeValue());
            }
            tempComponent.addWizardText(wizardTexts.get(i));
            result.addComponent(tempComponent);
        }
        return result;
    }

    /**
     * Reads a source and validates it against the compiled schema. Exceptions
     * are thrown if the validation is unsuccessful
     *
     * @param contentXML The data to validate
     * @return Document containing the XML document
     * @throws SAXException Thrown if the source file cannot be validated
     * against the schema
     * @throws ParserConfigurationException
     * @throws IOException Thrown if the source file cannot be read or found for
     * any reason.
     */
    public static Document readAndValidate(String contentXML) throws SAXException, ParserConfigurationException, IOException
    {
        final String W3C_XML_SCHEMA = "http://www.w3.org/2001/XMLSchema";
        String schemaFile = "/org/patterncoder/resources/patternschema.xsd";
        InputStream inputStreamSchema = XMLUtils.class.getResourceAsStream(schemaFile);
        Source source = new StreamSource(inputStreamSchema);
        SchemaFactory schemaFactory = SchemaFactory.newInstance(W3C_XML_SCHEMA);
        Schema schema = schemaFactory.newSchema(source);
        DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
        docBuilderFactory.setNamespaceAware(true);
        docBuilderFactory.setSchema(schema);
        docBuilderFactory.setValidating(true);
        DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
        InputStream contentStream = new ByteArrayInputStream(contentXML.getBytes());
        Document doc = docBuilder.parse(contentStream);
        return doc;
    }
}