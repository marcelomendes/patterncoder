package org.patterncoder.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import org.patterncoder.dataModel.Pattern;
import org.patterncoder.dataModel.PatternComponent;
import org.patterncoder.delegate.ErrorDialog;
import org.w3c.dom.*;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 *
 * @author Florian Siebler
 */
public class XMLUtils
{
    private static final String W3C_XML_SCHEMA = "http://www.w3.org/2001/XMLSchema";
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
    private static final String STEP_TYPE = "type";
    private static final String STEP_COMP_ID = "compId";
    private static final String STEP_ID = "stepId";
    private static final String STEP_NAME = "stepName";
    private static final String STEP_DESC = "stepDesc";
    private static final String STEP_NEXT = "nextStepId";
    private static final String STEP_PREVIOUS = "previousStepId";

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
            try
            {
                tempComponent.addWizardText(wizardTexts.get(i));
            }
            catch (Throwable t)
            {
                //
            }

            result.addComponent(tempComponent);
        }
        return result;
    }

    /**
     * Validates a source file against the compiled schema. Exceptions are
     * thrown if the validation is unsuccessful
     *
     * @param schemaFileDir Location of schema file
     * @param inputStream The data to validate
     * @throws IOException Thrown if the source file cannot be read or found for
     * any reason.
     * @throws SAXException Thrown if the source file cannot be validated
     * against the schema
     */
    public static void validate(String schemaFileDir, String contentXML) throws IOException, SAXException
    {
        File schemaFile = new File(schemaFileDir);
        SchemaFactory sf = SchemaFactory.newInstance(W3C_XML_SCHEMA);
        Schema schema = sf.newSchema(schemaFile);
        Validator validator = schema.newValidator();
        validator.setErrorHandler(new ValidatorErrorHandler());

        // convert String into InputStream
        InputStream is = new ByteArrayInputStream(contentXML.getBytes());

        Source streamSource = new StreamSource(is);
        validator.validate(streamSource);
    }

    public static Document readFile(String xml) throws ParserConfigurationException, SAXException, IOException
    {
        DocumentBuilder builder = getDocumentBuilder();
        Document document = builder.parse(new InputSource(new StringReader(xml)));
        return document;
    }

    private static DocumentBuilder getDocumentBuilder() throws ParserConfigurationException
    {
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        builderFactory.setNamespaceAware(true);
        DocumentBuilder builder = builderFactory.newDocumentBuilder();
        return builder;
    }

    private static class ValidatorErrorHandler implements ErrorHandler
    {
        /**
         * Catches all exceptions that cause an error, and rethrows them
         *
         * @param saxEx The exception thrown during validation.
         * @throws SAXException Throws a new SAXException, with slightly
         * modified message.
         */
        @Override
        public void error(SAXParseException saxEx) throws SAXException
        {
            throw new SAXException(saxEx);
        }

        /**
         * Catches all exceptions that cause an fatal error, and rethrows them
         *
         * @param saxEx The exception thrown during validation.
         * @throws org.xml.sax.SAXException Throws a new SAXException, with
         * slightly modified message.
         */
        @Override
        public void fatalError(SAXParseException saxEx) throws SAXException
        {
            throw new SAXException(saxEx);
        }

        /**
         * Catches all exceptions that cause a warning, and rethrows them
         *
         * @param saxEx The exception thrown during validation.
         * @throws org.xml.sax.SAXException Throws a new SAXException, with
         * slightly modified message.
         */
        @Override
        public void warning(SAXParseException saxEx) throws SAXException
        {
            throw new SAXException(saxEx);
        }
    }
}
