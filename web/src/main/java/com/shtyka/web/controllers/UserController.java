package com.shtyka.web.controllers;

import com.shtyka.services.UserService;
import com.shtyka.services.exceptions.ServiceException;
import com.shtyka.web.webManager.ConfigurationManager;
import com.shtyka.web.webManager.LanguageBundle;
import com.shtyka.web.webManager.MessageManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Locale;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(@RequestParam(value = "language", defaultValue = "RU") String language,
						RedirectAttributes redirectAttributes) {
		redirectAttributes.addFlashAttribute("language", "RU");
        return "index";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String getLogin(ModelMap model,
                               @RequestParam(value = "login", defaultValue = "") String login,
                               @RequestParam(value = "password", defaultValue = "") String password,
						   @ModelAttribute("language") String language,
                               final RedirectAttributes redirectAttributes) throws ServiceException {
		model = LanguageBundle.addLanguage(language, model);
        String amdinProfile = "administrator";
        String userProfile = "user";
        String page;
        String status = userService.checkLoginAdmin(login, password);
        redirectAttributes.addFlashAttribute("login", login);
        if (status.equals(amdinProfile)) {
            page = "redirect:admin";
        } else if (status.equals(userProfile)) {
            page = "redirect:client/client";
        } else {
            model.addAttribute("errorLoginPasswordMessage", MessageManager.getProperty("message.loginerror"));
            page = "login";
        }
        return page;
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(Model model) throws ServiceException {
 //       requestContent.setSessionAttributes(null);
        model.addAttribute("language", "RU");
        Locale.setDefault(new Locale ("ru", "RU"));
//        LanguageBundle.addLanguage("RU.properties", model);
//        requestContent.invalidate();
//        requestContent.insertRequestAttributes("language", "RU");
//        LanguageBundle.addLanguage("RU.properties", requestContent);
//
//               requestContent.insertRequestAttributes("language", "RU");
//		Locale.setDefault(new Locale("ru", "RU"));
        return ConfigurationManager.getProperty("path.page.index");
    }

    @RequestMapping(value = "/language", method = RequestMethod.GET)
    public String getLanguage(ModelMap model,
							  @ModelAttribute("language") String languageDefault,
                         	  @RequestParam(value = "language") String language,
                         RedirectAttributes redirectAttributes) throws ServiceException {
		String lang;
		if(language != null) {
			lang = language.concat(".properties");
		}else {
			lang = languageDefault.concat(".properties");
		}
		model = LanguageBundle.addLanguage(lang, model);
		model.addAttribute("language", language);
		redirectAttributes.addFlashAttribute("language", lang);
        return "login";
    }
}
