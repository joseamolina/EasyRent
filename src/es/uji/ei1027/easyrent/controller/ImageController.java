package es.uji.ei1027.easyrent.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import es.uji.ei1027.easyrent.controller.validator.FileFormBeanValidator;
import es.uji.ei1027.easyrent.dao.ImageDao;
import es.uji.ei1027.easyrent.domain.Credentials;
import es.uji.ei1027.easyrent.domain.FileFormBean;
import es.uji.ei1027.easyrent.domain.Image;
import es.uji.ei1027.easyrent.domain.Property;

@Controller
@RequestMapping("/image")
public class ImageController implements IPermissionsControl {
	public static final String APP_DIR_PATH = "/Users/joseangelmolina/Documents/workspace/EasyRentAdJos/WebContent";
	
	@Autowired
	private ImageDao imageDao;
	
	@RequestMapping("/add")
	public String addImage(Model model, HttpSession session) {
		
		// Checking if the user has been authenticated and he has permissions
		if(!authenticated(session) || !hasPermissions(session, new Object[0])) {
			session.setAttribute("nextUrl", "/property/add.html");
			return "redirect:/login.html";
		}
		
		if(session.getAttribute("images") == null)
			session.setAttribute("images", new LinkedList<Image>());

		model.addAttribute("images", session.getAttribute("images"));
		model.addAttribute("fileFormBean", new FileFormBean());
		return "image/add";
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/add", method=RequestMethod.POST)
	public String processAddSubmit(@ModelAttribute("fileFormBean") FileFormBean fileFormBean, 
			BindingResult bindingResult, HttpSession session) {
		
		// Checking if the user has been authenticated and he has permissions
		if(!authenticated(session) || !hasPermissions(session, new Object[0])) {
			session.setAttribute("nextUrl", "/property/add.html");
			return "redirect:/login.html";
		}
				
		// Checking introduced data errors
		FileFormBeanValidator fileFormBeanValidator = new FileFormBeanValidator();
		fileFormBeanValidator.validate(fileFormBean, bindingResult);
		if(bindingResult.hasErrors())
			return "image/add";
		
		Property property = (Property) session.getAttribute("property");
		// Images are added later, jointly with property, services and periods
		List<Image> images = (List<Image>) session.getAttribute("images");
		String imageName = property.getId()+"_"+images.size();
		
		// Saving the image 
		try {
			storeFileToLocal(fileFormBean, imageName);
		} catch (Exception e) {
			bindingResult.rejectValue("file", "uploadError", "Upload error");
			return "image/add";
		}
		
		// Creating the image
		Image image = new Image();
		// Image id_prop corresponds to property id
		image.setId_prop(property.getId());
		image.setCaption(fileFormBean.getCaption());
		image.setHref("img/"+imageName);
				
		images.add(image);
		return "redirect:/image/add.html";
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/cancel/{code}")
	public String processAddCancel(@PathVariable int code, HttpSession session) {

		List<Image> images = (List<Image>) session.getAttribute("images");
		Image toDelete = new Image();
		for(Image image:images)
			if(image.getHref().hashCode() == code)
				toDelete = image;
		images.remove(toDelete);
		
		// Removing the image 
		try {
			deleteLocalFile(toDelete.getHref());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "redirect:/image/add.html";
	}
	
	@RequestMapping("/update/{id_prop}")
	public String updateImages(Model model, HttpSession session, @PathVariable("id_prop") String id_prop) {

		// Checking if the user has been authenticated and he has permissions
		if(!authenticated(session) || !hasPermissions(session, new Object[0])) {
			session.setAttribute("nextUrl", "/image/update/"+id_prop+".html");
			return "redirect:/login.html";
		}
		
		Property property = new Property();
		property.setId(id_prop);
		model.addAttribute("property", property);
		model.addAttribute("images", imageDao.getPropertyImages(id_prop));
		model.addAttribute("image", new Image());
		
		model.addAttribute("fileFormBean", new FileFormBean());
		return "image/update";
	}
	
	@RequestMapping(value="/update/{id_prop}", method=RequestMethod.POST)
	public String processUpdateSubmit(Model model, @ModelAttribute("fileFormBean") FileFormBean fileFormBean, 
			BindingResult bindingResult, HttpSession session, @PathVariable("id_prop") String id_prop) {
		
		// Necessary if an error occurs in order to keep the property id in the model
		Property property = new Property();
		property.setId(id_prop);
		model.addAttribute("property", property);
		
		List<Image> images = (List<Image>) imageDao.getPropertyImages(id_prop);
		
		// Checking if the user has been authenticated and he has permissions
		if(!authenticated(session) || !hasPermissions(session, new Object[0])) {
			session.setAttribute("nextUrl", "/image/update/"+id_prop+".html");
			return "redirect:/login.html";
		}
		
		// Checking introduced data errors
		FileFormBeanValidator fileFormBeanValidator = new FileFormBeanValidator();
		fileFormBeanValidator.validate(fileFormBean, bindingResult);
		if(bindingResult.hasErrors()) {
			model.addAttribute("images", images);
			return "image/update";
		}
		
		
		String imageName = property.getId()+"_"+images.size();
		
		// Saving the image 
		try {
			storeFileToLocal(fileFormBean, imageName);
		} catch (Exception e) {
			model.addAttribute("images", images);
			bindingResult.rejectValue("file", "uploadError", "Upload error");
			return "image/update";
		}
				
		// Creating the image
		Image image = new Image();
		// Image id_prop corresponds to property id
		image.setId_prop(property.getId());
		image.setCaption(fileFormBean.getCaption());
		image.setHref("img/"+imageName);
				
		imageDao.addImage(image);
		return "redirect:/image/update/"+id_prop+".html";
	}
	
	@RequestMapping("/delete/{code},{id_prop}")
	public String processDelete(@PathVariable("code") int code , @PathVariable("id_prop") String id_prop,
			HttpSession session) {
		
		// Checking if the user has been authenticated and he has permissions
		if(!authenticated(session) || !hasPermissions(session, new Object[0])) {
			session.setAttribute("nextUrl", "/image/update/"+id_prop+".html");
			return "redirect:/login.html";
		}
		
		// Finding image by hashCode of its href
		Image toDelete = new Image();
		List<Image> images = imageDao.getPropertyImages(id_prop);
		for(Image image:images)
			if(image.getHref().hashCode() == code)
				toDelete = image;
		imageDao.deleteImage(toDelete);
		
		// Removing the image 
		try {
			deleteLocalFile(toDelete.getHref());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "redirect:/image/update/"+id_prop+".html";
	}
	
	private void storeFileToLocal(FileFormBean fileFormBean, String imageName) throws Exception {
		CommonsMultipartFile uploaded = fileFormBean.getFile();
		File localFile = new File(APP_DIR_PATH+"\\img\\"+imageName);
		FileOutputStream os = null;
		
		try {
			os = new FileOutputStream(localFile);
    		os.write(uploaded.getBytes());
		} finally {
    		if (os != null) {
    			try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
    		}
		}
	}
	
	private void deleteLocalFile(String href) throws Exception {
		File localFile = new File(APP_DIR_PATH+"\\"+href);
		localFile.delete();
	}
	
	@Override
	public boolean hasPermissions(HttpSession session, Object[] args) {
		Credentials credentials = (Credentials) session.getAttribute("credentials");
		return credentials.getRole().equals("owner");
	}
	
}
