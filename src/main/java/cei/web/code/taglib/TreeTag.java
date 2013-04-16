package cei.web.code.taglib;

import java.io.IOException;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import cei.code.domains.Code;


public class TreeTag extends SimpleTagSupport {
	private String id = null;
	private String cssclass = "cei-tree";
	private String startcode = null;
	private List<Code> items;
	
	public void setId(String id) {
		this.id = id;
	}

	public void setCssclass(String cssclass) {
		this.cssclass = " " + cssclass;
	}

	public void setStartcode(String startcode) {
		this.startcode = startcode;
	}

	public void setItems(List<Code> items) {
		this.items = items;
	}

	public void doTag() throws JspException, IOException {
		if(items == null || items.size() == 0) return;

		JspWriter out = getJspContext().getOut();
		
		Document document = TreeTagBuilder.getDocument(items);
		Element root = document.getDocumentElement(); 

		if(id != null)  root.setAttribute("id", id);
		root.setAttribute("class", cssclass);
		root.setAttribute("data-startCode", startcode);

		out.println(TreeTagBuilder.getString(document));
	}
}
