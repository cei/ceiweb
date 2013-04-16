package cei.web.code.controller;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cei.code.ICode;
import cei.code.ITree;
import cei.support.spring.message.MessageSupport;

@Controller
@RequestMapping( "/@code" )
public class CodeController {
	
	@Resource( name = "cei.code" )
	ICode icode;
	
	@Resource( name = "cei.tree" )
	ITree itree;
	
	@Autowired
	MessageSupport message;

	@RequestMapping( value = { "", "/" } )
	public String test(Model model) {
		
		

		model.addAttribute( "date", new Date() );
		model.addAttribute( "code", icode.getCode("MENU") );
		model.addAttribute( "tree", itree.getTree("SYSTEM", "MENU") );

		return "cei.code";
	}
}
