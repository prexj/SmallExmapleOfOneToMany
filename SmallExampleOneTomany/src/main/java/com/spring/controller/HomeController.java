package com.spring.controller;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


import org.springframework.web.bind.annotation.ResponseBody;

//import com.mkyong.stock.StockDailyRecord;
import com.spring.daos.SimpleDao;
import com.spring.model.Child;
import com.spring.model.Parent;
import com.spring.model.ParentModel;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	private SimpleDao simpleDao = new SimpleDao();
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
	
		
		return "child";
	}
	@RequestMapping(value = "insertData", method = RequestMethod.POST, produces="application/json")
	@ResponseBody
	public String insertData(@RequestParam("FirstName")String FirstName,
			@RequestParam("LastName")String LastName, Model model) {
		logger.info("Welcome insertData()");
		logger.info("FirstName"+FirstName);
		logger.info("LastName"+LastName);
		Parent parent = null;
		String result="";
		parent = simpleDao.readparent(LastName);
		if(parent.getId()==null || parent.getId() == 0){
		
								parent =new Parent();
								parent.setLastname(LastName);
								
								
								/*if(parent.getId()!=0){*/
								//ArrayList<Parent> parentList =simpleDao.find1(parent);
								int id =simpleDao.addParent(parent);
								System.out.println("add parent id :"+id);
								System.out.println("parent id :"+parent.getId());
								//System.out.println("controller"+id);
								parent =simpleDao.find(parent,id);
								
								Child child = new Child();
								child.setFirstname(FirstName);
								child.setParent(parent);
								parent.getChildren().add(child);
								int id1= simpleDao.addChild(child);
								if((id>0)&&(id1>0)){
									System.out.println("Data Inserted SuccessFully");
									result="1";
								}else{
									System.out.println("Data don't inserted SuccessFully");
									result="1";
								}
							}else{
								
								Child child = new Child();
								child.setFirstname(FirstName);
								child.setParent(parent);
								int id =simpleDao.addChild(child);
								if(id>0){
									System.out.println("Data Inserted SuccessFully");
									result="1";
								}else{
									System.out.println("Data don't inserted SuccessFully");
									result="1";
								}
			
		}
		/*parent.setLastName(LastName);
        parent = simpleDao.mergestock(parent);*/
		
		//parent =simpleDao.find(parent,id);
		
		
		/*child.setParent(parent);
		parent.getChildren().add(child);*/
		
		/*int id1 =simpleDao.addChild(child);
		System.out.println("controller child id: "+id);*/
		
		/*}else{
			
		}*/
		
	
		
		return result;
	
		
		
	}
	@RequestMapping(value = "addData", method = RequestMethod.POST)
	public String addData(@RequestParam("FirstName")String FirstName,
			@RequestParam("LastName")String LastName, Model model) {
		logger.info("Welcome addData()");
		logger.info("FirstName"+FirstName);
		logger.info("LastName"+LastName);
		Parent parent = null;
		
		parent = simpleDao.readparent(LastName);
		if(parent.getId()==null || parent.getId() == 0){
		
								parent =new Parent();
								parent.setLastname(LastName);
								
								
								/*if(parent.getId()!=0){*/
								//ArrayList<Parent> parentList =simpleDao.find1(parent);
								int id =simpleDao.addParent(parent);
								System.out.println("add parent id :"+id);
								System.out.println("parent id :"+parent.getId());
								//System.out.println("controller"+id);
								parent =simpleDao.find(parent,id);
								
								Child child = new Child();
								child.setFirstname(FirstName);
								child.setParent(parent);
								parent.getChildren().add(child);
								simpleDao.addChild(child);
							}else{
								
								Child child = new Child();
								child.setFirstname(FirstName);
								child.setParent(parent);
								simpleDao.addChild(child);
			
		}
		
		//parent =simpleDao.find(parent,id);
		
		
		/*child.setParent(parent);
		parent.getChildren().add(child);*/
		
		/*int id1 =simpleDao.addChild(child);
		System.out.println("controller child id: "+id);*/
		
		/*}else{
			
		}*/
		
	
		
		return "redirect:showchild";
	}
	
	@RequestMapping(value = "showchild", method = RequestMethod.GET)
	public String showchild(Locale locale, Model model) {
		logger.info("Welcome showchild()");
		model.addAttribute("show", simpleDao.showChild());
	
		
		return "showStudent";
	}
	@RequestMapping(value = "showAll", method = RequestMethod.GET , produces="application/json")
	@ResponseBody
	public ArrayList<Child> showAll(Locale locale, Model model) {
		logger.info("Welcome showchild()");
	//	model.addAttribute("show", simpleDao.showChild());
		ArrayList<Child> childlist= simpleDao.showChild();
		
		return childlist;
	}
	
	@RequestMapping(value = "addAll", method = RequestMethod.POST)
	public String addAll(@RequestParam("FirstName")String FirstName,
			@RequestParam("LastName")String LastName, Model model) {
		logger.info("Welcome addAll()");
		logger.info("FirstName"+FirstName);
		logger.info("LastName"+LastName);
		
		Parent parent =new Parent();
		parent.setLastname(LastName);
		
		simpleDao.addAll(parent);
		
		System.out.println("aaa First Name ==="+FirstName);
		
		Child child = new Child();
		child.setParent(parent);
		child.setFirstname(FirstName);
		List<Child> clist = new ArrayList<Child>();
		child.setParent(parent);//
		clist.add(child);
		parent.setChildren(clist);
		//simpleDao.addChild(9,child);
		//simpleDao.addParent(parent);
		
		System.out.println("OK");
		
		return "redirect:showchild";
	}
}
