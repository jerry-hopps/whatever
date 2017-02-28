package net.nemo.whatever.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by tonyshi on 2017/2/23.
 */
@Controller
@RequestMapping("/api")
public class ApiController {

    @Autowired
    private JedisPool jedisPool;

    @CrossOrigin(origins = "http://localhost:3000", methods = { RequestMethod.GET, RequestMethod.DELETE, RequestMethod.PUT, RequestMethod.POST, RequestMethod.OPTIONS })
    @RequestMapping(value = "/todos.json", method = { RequestMethod.GET, RequestMethod.DELETE, RequestMethod.PUT, RequestMethod.POST })
    @ResponseBody
    public Map<String, Object> addTodo(@RequestParam(value = "id", required = false) String id, @RequestParam(value = "text", required = false) String text, HttpServletRequest request){
        Map<String, Object> result = new HashMap<String, Object>();

        Jedis jedis = jedisPool.getResource();

        try {
            switch (request.getMethod()) {
                case "POST":
                    if(id == null || text == null)
                        throw new IllegalArgumentException("parameter `id` and `text` are required");
                    jedis.hset(String.valueOf(id), "text", text);
                    jedis.hset(String.valueOf(id), "completed", String.valueOf(Boolean.FALSE));
                    jedis.hset(String.valueOf(id), "createdAt", now());
                    jedis.sadd("todos", String.valueOf(id));
                    break;
                case "PUT":
                    if(id == null)
                        throw new IllegalArgumentException("parameter `id` is required");
                    boolean completed = Boolean.valueOf(jedisPool.getResource().hget(String.valueOf(id), "completed"));
                    jedis.hset(String.valueOf(id), "completed", String.valueOf(!completed));
                    break;
                case "DELETE":
                    if(id == null)
                        throw new IllegalArgumentException("parameter `id` is required");
                    jedis.del(String.valueOf(id));
                    jedis.srem("todos", String.valueOf(id));
                    break;
                default:

            }
            result.put("todos", getAllTodos());
            result.put("success", true);
        }catch(Exception e){
            result.put("error", e.getMessage());
            result.put("success", false);
        }

        return result;
    }

    private List<Map<String, Object>> getAllTodos(){
        List<Map<String, Object>> todos = new ArrayList<>();

        for( String id : jedisPool.getResource().smembers("todos")){
            Map<String, String> r = jedisPool.getResource().hgetAll(id);

            Map<String, Object> todo = new HashMap<String, Object>();
            todo.put("id", id);
            todo.put("completed", Boolean.valueOf(r.get("completed")));
            todo.put("text", r.get("text"));
            todo.put("createdAt", Long.valueOf(r.get("createdAt")));
            todos.add(todo);
        }

        Collections.sort(todos, new Comparator<Map<String, Object>>() {
            @Override
            public int compare(Map<String, Object> todo1, Map<String, Object> todo2) {
                Long l = ((Long)todo1.get("createdAt") - (Long)todo2.get("createdAt")) ;
                return l.intValue();
            }
        });

        return todos;
    }

    private String now() {
        return String.valueOf(new Date().getTime());
    }
}
