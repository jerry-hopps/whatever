package net.nemo.whatever.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.nemo.whatever.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tonyshi on 2017/2/27.
 */
@Controller
@RequestMapping("/todos")
@PropertySource("classpath:props/application.properties")
public class TodosController {

    @Autowired
    private Environment env;

    private final String PUBLIC_PATH = "static/build";


    @RequestMapping("/*")
    public ModelAndView index(HttpServletRequest request){

        ModelAndView mv = new ModelAndView(StringUtil.getUserAgentViewName(request,"todos/index"));
        mv.addObject("assets", assetsPath("index"));

        return mv;
    }

    private Map<String, String> assetsPath(String assetName){
        Map<String, String> assetsPath = new HashMap<>();

        if("development".equals(env.getProperty("DEV_ENV"))){
            String assetsDevDomain = env.getProperty("app.assets.development.domain.name");
            for(String type : new String[]{"css", "js"}){
                assetsPath.put(type, String.format("%s/%s/%s.%s", assetsDevDomain, PUBLIC_PATH, assetName, type));
            }
        }
        else{
            Map<String, List<String>> assetsByChunkName = (Map<String, List<String>>) jsonToMap("props/manifest.json").get("assetsByChunkName");
            for(String assets : assetsByChunkName.get(assetName)){
                assetsPath.put(assets.split("\\.")[1], String.format("/%s/%s", PUBLIC_PATH, assets));
            }
        }

        return assetsPath;
    }

    private Map<String, Object> jsonToMap(String fileName){
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