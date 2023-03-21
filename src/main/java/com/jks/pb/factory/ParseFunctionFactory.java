package com.jks.pb.factory;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.jks.pb.function.IParseFunction;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 类型描述 spel自定义函数工厂
 *
 * @since  2023年3月14日
 * @author 朱晓帆
 *
 */
@Component
public class ParseFunctionFactory {
   @Autowired
   Map<String, IParseFunction> allFunctionMap=new HashMap<>();;



  public IParseFunction getFunction(String functionName) {
	  IParseFunction function = allFunctionMap.get(functionName);
      if(function == null) {
          throw new RuntimeException("no function defined");
      }
      return function;
  }

  public boolean isBeforeFunction(String functionName) {
    return allFunctionMap.get(functionName) != null && allFunctionMap.get(functionName).executeBefore();
  }
}
