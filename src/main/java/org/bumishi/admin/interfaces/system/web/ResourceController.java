package org.bumishi.admin.interfaces.system.web;

import org.apache.commons.lang3.StringUtils;
import org.bumishi.admin.application.ResourceService;
import org.bumishi.admin.domain.modle.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author qiang.xie
 * @date 2016/9/18
 */
@Controller
@RequestMapping("/resource")
public class ResourceController {

    @Autowired
    protected ResourceService resourceService;

    @RequestMapping(method = RequestMethod.POST,value = "/add")
    public ModelAndView create(@RequestBody Resource resource){
        resourceService.create(resource);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("code",200);
        modelAndView.addObject("msg","success");

        modelAndView.setViewName("redirect:/resource");
        return modelAndView;
    }

    @RequestMapping(value = "/{id}/modify", method = RequestMethod.POST)
    public String modify(@PathVariable("id") String id, Resource resource) {
        resource.setId(id);
        resourceService.modify(resource);
        return "redirect:/resource";
    }

    @RequestMapping(value = "/{id}/status",method = RequestMethod.PUT)
    @ResponseBody
    public void switchStatus(@PathVariable("id") String id,@RequestParam("disable") boolean disable){
        resourceService.switchStatus(id,disable);
    }

    @RequestMapping(value = "/{id}/delete",method = RequestMethod.DELETE)
    @ResponseBody
    public void delete(@PathVariable("id")String id){
         resourceService.delete(id);
    }

    @RequestMapping(value = "/form",method = RequestMethod.GET)
    public String toform(@RequestParam(name = "id",required = false)String id,Model model){
        String api="/resource/add";
        if(StringUtils.isNotBlank(id)){
            model.addAttribute("resource",resourceService.get(id));
            api="/resource/"+id+"/modify";
          }
          model.addAttribute("api",api);

        return "resource/form";
    }

    @RequestMapping(method = RequestMethod.GET)
    public String list(Model model){
        model.addAttribute("list",resourceService.list());
        return "resource/list";
        }


}
