package cn.langpy.kotime.handler;

import cn.langpy.kotime.model.InvokedInfo;
import cn.langpy.kotime.model.MethodNode;
import cn.langpy.kotime.service.InvokedQueue;
import cn.langpy.kotime.service.MethodNodeService;
import cn.langpy.kotime.util.Common;
import cn.langpy.kotime.util.Context;
import cn.langpy.kotime.util.MethodStack;
import cn.langpy.kotime.util.ThrowException;
import javassist.ClassPool;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;


/**
 * zhangchang
 */
public class RunTimeHandler implements MethodInterceptor {
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        boolean kotimeEnable = Context.getConfig().getEnable();
        if (!kotimeEnable) {
            return invocation.proceed();
        }
        boolean exceptionEnable = Context.getConfig().getExceptionEnable();
        long begin = System.nanoTime();
        Object obj = null;
        MethodNode parent = MethodNodeService.getParentMethodNode();
        ClassPool cp = ClassPool.getDefault();
        Set<String> set=MethodStack.record(invocation,cp);
        InvokedInfo invokedInfo = new InvokedInfo();
        List<InvokedInfo> invokedList=new ArrayList<InvokedInfo>();
        try {
            obj = invocation.proceed();
            long end = System.nanoTime();
            MethodStack.get();
            invokedInfo = Common.getInvokedInfo(invocation, parent, ((end - begin) / 1000000.0));
            Set<String> cmSet=MethodStack.getClassMethod();
            set.removeAll(cmSet);
            for (String classMethod:set) {
            	InvokedInfo invokedChildInfo = new InvokedInfo();
            	invokedChildInfo=Common.getInvokedChildInfo(invocation, MethodNodeService.buildChildMethodNode(classMethod), 1.0);
            	invokedList.add(invokedChildInfo);
            }
            Class[] isArr=invocation.getMethod().getDeclaringClass().getInterfaces();
            for (Class  is:isArr) {
//            	System.out.println(is.getName()+"#"+invokedInfo.getCurrent().getMethodName());
    			MethodStack.addClassMethod(is.getName()+"#"+invokedInfo.getCurrent().getMethodName());
    		}
            MethodStack.addClassMethod(invokedInfo.getCurrent().getClassName()+"#"+invokedInfo.getCurrent().getMethodName());
            
        } catch (Exception te) {
            if (!exceptionEnable) {
                long end = System.nanoTime();
                invokedInfo = Common.getInvokedInfo(invocation, parent, ((end - begin) / 1000000.0));
                throw te;
            }
            Exception e = null;
            if (te instanceof ThrowException) {
                e = ((ThrowException) te).getOriginalException();
            } else {
                e = te;
            }
            long end = System.nanoTime();
            invokedInfo = Common.getInvokedInfoWithException(invocation, parent, e, ((end - begin) / 1000000.0));
            throw te;
        } finally {
        	
        	
            InvokedQueue.add(invokedInfo);
           
            InvokedQueue.add(invokedList);
            InvokedQueue.wake();
            MethodStack.clear();
        }
        return obj;
    }
}
