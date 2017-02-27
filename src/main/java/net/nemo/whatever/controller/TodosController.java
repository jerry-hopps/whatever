package net.nemo.whatever.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.nemo.whatever.util.StringUtil;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tonyshi on 2017/2/27.
 */
@Controller
@RequestMapping("/todos")
public class TodosController {

    @RequestMapping("/*")
    public ModelAndView index(HttpServletRequest request){

        ModelAndView mv = new ModelAndView(StringUtil.getUserAgentViewName(request,"todos/index"));
        mv.addObject("assets", assetsPath());

        return mv;
    }

    private Map<String, String> assetsPath(){
        Map<String, String> assetsPath = new HashMap<>();

        Map<String, List<String>> assetsByChunkName = (Map<String, List<String>>) fileToMap("props/manifest.json").get("assetsByChunkName");
        for(String asset : assetsByChunkName.get("index")){
            assetsPath.put(asset.split("\\.")[1],String.format("/static/build/%s",  asset));
        }

        return assetsPath;
    }

    private Map<String, Object> fileToMap(String fileName){
        ObjectMapper mapper = new ObjectMapper();
        Resource r = new ClassPathResource(fileName);
        try {
            Map<String, Object> map = mapper.readValue(r.getFile(), Map.class);
            return map;
        }catch (IOException e){
            return null;
        }
    }
}