package net.nemo.whatever.controller.exceptionresolver;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.druid.support.json.JSONUtils;

import net.nemo.whatever.exception.BusinessException;

public class MySimpleMappingExceptionResolver implements HandlerExceptionResolver {

	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception exception) {
		if (!(request.getHeader("accept").indexOf("application/json") > -1
				|| (request.getHeader("X-Requested-With") != null
						&& request.getHeader("X-Requested-With").indexOf("XMLHttpRequest") > -1))) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("success", false);
			if (exception instanceof BusinessException) {
				map.put("errorMsg", exception.getMessage());
			} else {
				map.put("errorMsg","");
			}
			exception.printStackTrace();
			return new ModelAndView("/error", map);
		} else {
			try {
				response.setContentType("application/json;charset=UTF-8");
				PrintWriter writer = response.getWriter();
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("success", false);
				if (exception instanceof BusinessException) {
					map.put("errorMsg", exception.getMessage());
				} else {
					map.put("errorMsg", "");
				}
				writer.write(JSONUtils.toJSONString(map));
				writer.flush();
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}
