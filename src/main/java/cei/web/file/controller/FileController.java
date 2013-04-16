package cei.web.file.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cei.file.File;
import cei.file.IFile;
import cei.file.impl.FileImpl;
import cei.web.spring.view.Views;
import cei.web.spring.view.type.Download;
import cei.web.spring.view.type.Json;


@Controller
@RequestMapping("/file")
public class FileController {

	@Value( "${base.charset}" )
	private String BASE_CHARSET;

	@Resource( name = "cei-file" )
	IFile ifile;

	@RequestMapping( "/up" )
	public Views up( @RequestPart MultipartFile file ) {
		Json j = new Json();

		try {
			j.add( "file", ( ( FileImpl )ifile ).up( file ) );
			j.add( "success", true );
			j.add( "message", null );
		}
		catch ( Exception e ) {
			e.printStackTrace();
			j.add( "success", false );
			j.add( "message", e.getMessage() );
		}

		return j;
	}

	@RequestMapping( "/down/{seq}" )
	public Views down(
			HttpServletRequest request,
			@ModelAttribute File file,
			@RequestParam( required = false ) boolean afterCloseWindow ) {

		file = ifile.bySeq( file );

		String userAgent = request.getHeader( "User-Agent" );
		String originalName = file.getOriginalName();

		if ( userAgent != null ) {
			try {
				originalName = userAgent.indexOf( "MSIE" ) > -1 ? URLEncoder.encode( originalName, BASE_CHARSET ) : new String( originalName.getBytes( BASE_CHARSET ), "8859_1" );
			}
			catch ( UnsupportedEncodingException ue ) {
				ue.printStackTrace();
			}
		}

		return new Download( file.getPath() + java.io.File.separator + file.getSaveName(), originalName );
	}

	@ResponseBody
	@RequestMapping( "/garbage/{seq}" )
	public String garbage( @ModelAttribute File file ) {
		return Integer.toString( ifile.garbage( file ) );
	}

	@ResponseBody
	@RequestMapping( "/recycle/{seq}" )
	public String recycle( @ModelAttribute File file ) {
		return Integer.toString( ifile.recycle( file ) );
	}

	@ResponseBody
	@RequestMapping( "/remove/{seq}" )
	public String remove( @ModelAttribute File file ) {
		return Boolean.toString( ifile.remove( file ) );
	}
}
