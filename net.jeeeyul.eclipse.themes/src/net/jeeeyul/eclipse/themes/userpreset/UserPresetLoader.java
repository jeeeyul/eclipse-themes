package net.jeeeyul.eclipse.themes.userpreset;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class UserPresetLoader extends DefaultHandler {
	private List<UserPreset> result = new ArrayList<UserPreset>();

	private UserPreset current;

	public List<UserPreset> getResult() {
		return result;
	}

	@Override
	public void startDocument() throws SAXException {
		result.clear();
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		if (qName.equals("preset")) {
			current = new UserPreset();
			current.setName(attributes.getValue("name"));
		}
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		if (current != null) {
			StringBuffer buffer = new StringBuffer();
			buffer.append(ch, start, length);
			current.setContents(buffer.toString());
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if (qName.equals("preset")) {
			result.add(current);
			System.out.println(current.getName());
			current = null;
		}
	}
}
