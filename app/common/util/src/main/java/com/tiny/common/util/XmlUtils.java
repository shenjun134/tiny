/**
 * XmlUtils.java
 *
 *
 */
package com.tiny.common.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamResult;

import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import com.tiny.common.exception.InitializationException;

/**
 * @author e521907
 * @version 1.0
 *
 */
public class XmlUtils {

	/**
	 * LOGGER
	 */
	private static final Logger	LOGGER	= Logger.getLogger(XmlUtils.class);

	/**
	 * @param xmlPath
	 * @param xsltPath
	 * @param params
	 */
	public static void parse(String xmlPath, String xsltPath, Map<String, Object> params) {

		ClassPathXmlApplicationContext tempContext = null;
		try {
			tempContext = new ClassPathXmlApplicationContext();
			String xmlString = FileSupportUtils.getString(tempContext.getResource(xmlPath).getFile()).toString();
			String xsltString = FileSupportUtils.getString(tempContext.getResource(xsltPath).getFile()).toString();
			InputStream srcInputStream = new ByteArrayInputStream(xmlString.getBytes("UTF-8"));

			javax.xml.transform.Source xsltSource = new javax.xml.transform.stream.StreamSource(
					new ByteArrayInputStream(xsltString.getBytes()));
			javax.xml.transform.TransformerFactory transFact = javax.xml.transform.TransformerFactory.newInstance();
			// create the transformer
			javax.xml.transform.Transformer trans = transFact.newTransformer(xsltSource);

			// create a parser with a fake entity resolver to disable DTD download and validation
			XMLReader xmlReader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
			xmlReader.setEntityResolver(new EntityResolver() {
				public InputSource resolveEntity(String pid, String sid) throws SAXException {
					return new InputSource(new ByteArrayInputStream(new byte[] {}));
				}
			});

			// create the source for the XML document which uses the reader with fake entity resolver
			// try "ISO-8859-1" to tolerate special characters.
			Source xmlSource = new javax.xml.transform.sax.SAXSource(xmlReader, new org.xml.sax.InputSource(
					srcInputStream));
			for (Entry<String, Object> entry : params.entrySet()) {
				trans.setParameter(entry.getKey(), entry.getValue());
			}

			StringWriter writer = new StringWriter();
			trans.transform(xmlSource, new StreamResult(writer));

		} catch (Exception e) {
			LogUtil.error(LOGGER, e, "XML parse error , xmlPath={0}, xsltPath={1}, params={2}", xmlPath, xsltPath,
					params);
			throw new InitializationException("XML parse error", e);
		} finally {
			if (tempContext != null) {
				tempContext.close();
			}
		}

	}
}
