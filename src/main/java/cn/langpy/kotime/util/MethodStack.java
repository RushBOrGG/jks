package cn.langpy.kotime.util;

import org.aopalliance.intercept.MethodInvocation;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

/**
 * zhangchang
 */
public class MethodStack {

    private static ThreadLocal<Stack> threadMethods = new ThreadLocal<>();
    
    private static ThreadLocal<Set<String>> threadClassMethodSet = new ThreadLocal<Set<String>>();

    public static Set<String> record(MethodInvocation pjp,ClassPool cp) throws NotFoundException, CannotCompileException {
        String className = pjp.getMethod().getDeclaringClass().getName();
        String methodName = pjp.getMethod().getName();
        
       
        Stack<String> queue = null;
        if (null==threadMethods.get()) {
            queue = new Stack<String>();
        }else {
             queue = threadMethods.get();
        }
        Set<String> methodsSet=new HashSet<>();
        
        CtClass ctClass = cp.get(className);
        CtMethod method = ctClass.getDeclaredMethod(methodName);
        List<String[]> list=new ArrayList<>();
        method.instrument(
            new ExprEditor() {
                public void edit(MethodCall m)
                              throws CannotCompileException
                {
                	if(m.getClassName().startsWith(Context.getConfig().getPackagePath())){
                		
                    	list.add(new String[]{m.getClassName(), m.getMethodName()});
                	}
                	

                

                }
            });
        
        for (String[] strArr:list) {
        	methodsSet.add(strArr[0] + "#" + strArr[1]);
		}
       
        MethodType methodType = Common.getMethodType(pjp);
        queue.add(className+"#"+methodName+"#"+methodType);
        
        threadMethods.set(queue);
        return methodsSet;
    }

    public static Stack get() {
        return threadMethods.get();
    }
    
    public static Set<String> getClassMethod(){
    	return threadClassMethodSet.get()==null?new HashSet():threadClassMethodSet.get();
    }
    
    public static void addClassMethod(String classMethod){
    	Set<String> set=threadClassMethodSet.get()==null?new HashSet<>():threadClassMethodSet.get();
    	set.add(classMethod);
    	threadClassMethodSet.set(set);
    }
  

    public static void clear() {
        Stack<String> queue = threadMethods.get();
        if (queue==null) {
            return;
        }
        queue.pop();
        if (queue.isEmpty()) {
            threadMethods.remove();
        }
    }
}
