package com.example.controller;

import java.util.List;

import javax.validation.Valid;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.model.Module;
import com.example.model.Task;
import com.example.service.ModuleService;
import com.example.service.TaskService;

@Controller
@RequestMapping("/tasks")
public class TaskController {
	

	@Autowired
	private TaskService taskService;
	
	@Autowired
	private ModuleService moduleService; //module service

	@GetMapping
	public String index(Model model) {
		List<Task> all = taskService.findAll();
		model.addAttribute("listTask", all);
		model.addAttribute("");
		return "task/index";
	}
	
	@GetMapping("/{id}")
	public String show(Model model, @PathVariable("id") Integer id) {
		if (id != null) {
			Task task = taskService.findOne(id).get();
			model.addAttribute("task", task);
		}
		return "task/show";
	}

//	@Secured("ROLE_ADMIN")
	@GetMapping(value = "/new")
	public String create(Model model, @ModelAttribute Task entityTask, 
			             @ModelAttribute Module entityModule) {
		// model.addAttribute("student", entityStudent);
		List<Module> all = moduleService.findAll();
		model.addAttribute("modules", all);
		
		return "task/form";
	}
	
	// Processamento do formulario New Student (ou Alter Student)
//	@Secured("ROLE_ADMIN")
	@PostMapping
	public String create(@Valid @ModelAttribute Task entityTask, 
			             @Valid @ModelAttribute Module entityModule,
			             BindingResult result, RedirectAttributes redirectAttributes) {
		Task task = null;
		String pagina_retorno = "redirect:/tasks/" ;
	
		try {
			task = taskService.save(entityTask);
			redirectAttributes.addFlashAttribute("success", MSG_SUCESS_INSERT);
			pagina_retorno = pagina_retorno + task.getId();
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("error", MSG_ERROR);
		}catch (Throwable e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("error", MSG_ERROR);
		}
		
		return pagina_retorno;
	}
	
	@GetMapping("/{id}/edit")
	public String update(Model model, @PathVariable("id") Integer id) {
		
		try {
			if (id != null) {
				List<Module> all = moduleService.findAll();
				model.addAttribute("modules", all);
				
				Task entity = taskService.findOne(id).get();
				model.addAttribute("task", entity);
			}
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
		return "task/form";
	}
	
	@PutMapping
	public String update(@Valid @ModelAttribute Task entity, BindingResult result, 
			             RedirectAttributes redirectAttributes) {
		Task task = null;
		try {
			task = taskService.save(entity);
			redirectAttributes.addFlashAttribute("success", MSG_SUCESS_UPDATE);
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("error", MSG_ERROR);
			e.printStackTrace();
		}
		return "redirect:/tasks/" + task.getId();
	}
	
	@RequestMapping("/{id}/delete")
	public String delete(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
		try {
			if (id != null) {
				Task entity = taskService.findOne(id).get();
				taskService.delete(entity);
				redirectAttributes.addFlashAttribute("success", MSG_SUCESS_DELETE);
			}
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("error", MSG_ERROR);
			throw new ServiceException(e.getMessage());
		}
		return "redirect:/tasks/";
	}
	
	private static final String MSG_SUCESS_INSERT = "Task inserted successfully.";
	private static final String MSG_SUCESS_UPDATE = "Task successfully changed.";
	private static final String MSG_SUCESS_DELETE = "Deleted Task successfully.";
	private static final String MSG_ERROR = "Erro na inserção do Task";

}
