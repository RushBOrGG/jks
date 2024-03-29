package cn.langpy.kotime.service;

import cn.langpy.kotime.annotation.KoListener;
import cn.langpy.kotime.handler.InvokedHandler;
import cn.langpy.kotime.model.ExceptionNode;
import cn.langpy.kotime.model.MethodNode;
import cn.langpy.kotime.util.BloomFilter;
import cn.langpy.kotime.util.Common;
import cn.langpy.kotime.util.Context;

import java.lang.reflect.Parameter;
import java.util.logging.Logger;

@KoListener
public final class KoInvokedHandler implements InvokedHandler {
    private static Logger log = Logger.getLogger(KoInvokedHandler.class.toString());

    @Override
    public void onInvoked(MethodNode current, MethodNode parent, Parameter[] names, Object[] values) {
    	if (current == null || (current != null && current.getValue() == 0.0)) {
        //if (current == null || (current != null && current.getValue() == 0.0)) {
            return;
        }
        GraphService graphService = GraphService.getInstance();
        graphService.addMethodNode(filter(parent));
        graphService.addMethodNode(filter(current));
        graphService.addMethodRelation(parent, current);
        if (Context.getConfig().getParamAnalyse()) {
            graphService.addParamAnalyse(current.getId(), names, values, current.getValue());
        }
        if (Context.getConfig().getLogEnable()) {
            Common.showLog(current);
        }
    }


    @Override
    public void onException(MethodNode current, MethodNode parent, ExceptionNode exception, Parameter[] names, Object[] values) {
        GraphService graphService = GraphService.getInstance();
        graphService.addMethodNode(current);
        graphService.addExceptionNode(exception);
        graphService.addExceptionRelation(current, exception);
    }

    private MethodNode filter(MethodNode currentNode) {
        if (currentNode == null) {
            return null;
        }
        if (BloomFilter.exists(currentNode.getId())) {
            //allow controller's routes to be updated
            if (!Common.isEmpty(currentNode.getRouteName())) {
                return currentNode;
            }
            return null;
        } else {
            BloomFilter.add(currentNode.getId());
            return currentNode;
        }
    }
}
