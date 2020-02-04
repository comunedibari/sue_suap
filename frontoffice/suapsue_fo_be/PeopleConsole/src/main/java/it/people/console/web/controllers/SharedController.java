/*   1:    */ package it.people.console.web.controllers;
/*   2:    */ 
/*   3:    */ import it.people.console.domain.exceptions.Errors;
/*   4:    */ import it.people.console.utils.StringUtils;
/*   5:    */ import it.people.console.web.controllers.validator.BeServiceValidator;
/*   6:    */ import it.people.console.web.servlet.mvc.MessageSourceAwareController;
/*   7:    */ import javax.servlet.http.HttpServletRequest;
/*   8:    */ import javax.servlet.http.HttpSession;
/*   9:    */ import javax.sql.DataSource;
/*  10:    */ import org.slf4j.LoggerFactory;
/*  11:    */ import org.slf4j.Logger;
/*  12:    */ import org.springframework.beans.factory.annotation.Autowired;
/*  13:    */ import org.springframework.stereotype.Controller;
/*  14:    */ import org.springframework.ui.ModelMap;
/*  15:    */ import org.springframework.web.bind.annotation.ModelAttribute;
/*  16:    */ import org.springframework.web.bind.annotation.RequestMapping;
/*  17:    */ import org.springframework.web.bind.annotation.SessionAttributes;
/*  18:    */ 
/*  19:    */ @Controller
/*  20:    */ @RequestMapping({"/"})
/*  21:    */ @SessionAttributes({"errors"})
/*  22:    */ public class SharedController
/*  23:    */   extends MessageSourceAwareController
/*  24:    */ {
/*  25:    */   private BeServiceValidator validator;
/*  26:    */   @Autowired
/*  27:    */   private DataSource dataSourcePeopleDB;
/*  28: 81 */   private static Logger logger = LoggerFactory.getLogger(SharedController.class);
/*  29:    */   
/*  30:    */   @RequestMapping(value={"/error.do"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
/*  31:    */   public String setupForm(ModelMap model, HttpServletRequest request)
/*  32:    */   {
/*  33: 86 */     Errors errors = new Errors();
/*  34:    */     
/*  35: 88 */     errors.setReferer(getReferer(request));
/*  36: 89 */     errors.setMessage(getProperty("error.generic"));
/*  37: 90 */     errors.setException(StringUtils.toString(popLastExceptionfromSession(request)));
/*  38:    */     
/*  39: 92 */     model.put("sidebar", "/WEB-INF/jsp/mainSidebar.jsp");
/*  40:    */     
/*  41: 94 */     model.put("includeTopbarLinks", Boolean.valueOf(false));
/*  42:    */     
/*  43: 96 */     model.put("errors", errors);
/*  44:    */     
/*  45: 98 */     return getStaticProperty("error.view");
/*  46:    */   }
/*  47:    */   
/*  48:    */   @RequestMapping(value={"/error.do"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
/*  49:    */   public String processSubmit(ModelMap model, @ModelAttribute("errors") Errors errors, HttpServletRequest request)
/*  50:    */   {
/*  51:106 */     String referer = errors.getReferer();
/*  52:107 */     errors.clear();
/*  53:108 */     request.getSession().removeAttribute("errors");
/*  54:    */     
/*  55:110 */     return "redirect:" + referer;
/*  56:    */   }
/*  57:    */ }


/* Location:           C:\Users\delpretea\Desktop\PeopleConsole\WEB-INF\classes\
 * Qualified Name:     it.people.console.web.controllers.SharedController
 * JD-Core Version:    0.7.0.1
 */