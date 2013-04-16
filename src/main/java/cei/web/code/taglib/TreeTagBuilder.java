package cei.web.code.taglib;

import java.io.StringWriter;
import java.io.Writer;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import cei.code.domains.Code;

public abstract class TreeTagBuilder {
	public static String getString( List<Code> items ) {
		return getString( getDocument( items ) );
	}

	public static String getString( Document document ) {
		Writer writer = new StringWriter();

		try {
			Transformer trans = TransformerFactory.newInstance().newTransformer();
			trans.setOutputProperty( OutputKeys.METHOD, "html" );
			trans.setOutputProperty( OutputKeys.INDENT, "yes" );
			trans.transform( new DOMSource( document ), new StreamResult( writer ) );
		}
		catch( TransformerException te ) {
			te.printStackTrace();
		}
			
		return writer.toString();
	}
	
	public static Document getDocument( List<Code> items ) {
		if(items == null || items.size() == 0) return null;

		Document document = null;
		
		try {
			document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
		}
		catch( ParserConfigurationException pce ) {
			pce.printStackTrace();
			return null;
		}

		Element li = liTag( document, items.get( 0 ) );

		Element ul = document.createElement( "ul" );
		ul.setAttribute( "class", "cei-tree" );
		ul.appendChild( li );
		document.appendChild( ul );

		int level = 0;
		for ( int i = 1; i < items.size(); i++ ) {
			Code code = items.get(i);

			if ( level < code.getLevel() ) {
				level = code.getLevel();

				Element _li = liTag( document, code );
				ul = document.createElement( "ul" );
				ul.appendChild( _li );

				li.setAttribute( "class", ( li.getAttribute( "class" ) + " parent" ).trim() );
				li.appendChild( ul );
				li = _li;
			}
			else if ( level > code.getLevel() ) {
				level -= code.getLevel();
				while ( level-- > 0 ) {
					li = ( Element )li.getParentNode().getParentNode();
				}

				level = code.getLevel();

				Element _li = liTag( document, code );
				li.getParentNode().appendChild( _li );
				li = _li;
			}
			else {
				Element _li = liTag( document, code );
				li.getParentNode().appendChild( _li );
				li = _li;
			}
		}

		NodeList nl = document.getElementsByTagName( "ul" );
		for ( int i = 0; i < nl.getLength(); i++ ) {
			li = ( Element )nl.item( i ).getLastChild();
			li.setAttribute( "class", ( li.getAttribute( "class" ) + " last" ).trim() );
		}

		return document;
	}
	
	private static Element liTag( Document document, Code code ) {
		Element a = document.createElement( "a" );
		a.setTextContent( code.getValue() );

		Element span = document.createElement( "span" );
		span.setAttribute( "class", "clip" );

		Element li = document.createElement( "li" );
		li.setAttribute( "data-code", code.getCode() );
		li.setAttribute( "data-parent", code.getParent() );
		li.setAttribute( "data-description", code.getDescription() );
		li.setAttribute( "data-level", Integer.toString( code.getLevel() ) );
		li.setAttribute( "data-order", Integer.toString( code.getOrder() ) );
		li.appendChild( span );
		li.appendChild( a );

		return li;
	}
}
