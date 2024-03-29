package com.jks.pb.spelExt.ops;

import org.springframework.expression.AccessException;
import org.springframework.expression.EvaluationException;
import org.springframework.expression.TypedValue;
import org.springframework.expression.spel.ExpressionState;
import org.springframework.expression.spel.SpelEvaluationException;
import org.springframework.expression.spel.SpelMessage;
import org.springframework.expression.spel.ast.SpelNodeImpl;
import org.springframework.lang.Nullable;
import org.springframework.util.ReflectionUtils;

import com.jks.pb.factory.ParseFunctionFactory;
import com.jks.pb.function.IParseFunction;

import java.util.Map;
import java.util.StringJoiner;

import static org.springframework.expression.spel.SpelMessage.FUNCTION_NOT_DEFINED;


public class SelfFunctionReference extends SpelNodeImpl {

    private String selfFunctionName;

    @Nullable
    private volatile IParseFunction iParseFunction;

    private Object [] args;

    public String getSelfFunctionName() {
        return this.selfFunctionName;
    }

    public SelfFunctionReference(String selfFunctionName, int startPos, int endPos, SpelNodeImpl... arguments) {
        super(startPos, endPos, arguments);
        this.selfFunctionName = selfFunctionName;
    }

    @Override
    public TypedValue getValueInternal(ExpressionState state) throws EvaluationException {
        try {
            Object beanFactory = state.getEvaluationContext().lookupVariable("parseFunctionFactory");
            if(beanFactory != null && beanFactory instanceof ParseFunctionFactory) {
                IParseFunction iParseFunction = ((ParseFunctionFactory) beanFactory).getFunction(this.selfFunctionName);
                if(iParseFunction != null) {
                    Object [] args = getArguments(state);
                    if(args.length != 1) {
                        throw new SpelEvaluationException(SpelMessage.INCORRECT_NUMBER_OF_ARGUMENTS_TO_FUNCTION, args.length, ReflectionUtils.findMethod(iParseFunction.getClass(), "apply").getParameterCount());
                    }
                    this.iParseFunction = iParseFunction;
                    this.args = args;
                    Object resultValue = iParseFunction.apply(args[0]);
                    return new TypedValue(resultValue);
                }
            }
            throw new SpelEvaluationException(FUNCTION_NOT_DEFINED, this.selfFunctionName);
        } catch (EvaluationException e) {
            throw e;
        } 


    }
    



    @Override
    public String toStringAST() {
        StringJoiner sj = new StringJoiner(",", "(", ")");
        for (int i = 0; i < getChildCount(); i++) {
            sj.add(getChild(i).toStringAST());
        }
        return "@{" + this.selfFunctionName + sj.toString() + "}";
    }

    /**
     * Compute the arguments to the function, they are the children of this expression node.
     * @return an array of argument values for the function call
     */
    private Object[] getArguments(ExpressionState state) throws EvaluationException {
        // Compute arguments to the function
        Object[] arguments = new Object[getChildCount()];
        for (int i = 0; i < arguments.length; i++) {
            arguments[i] = this.children[i].getValueInternal(state).getValue();
        }
        return arguments;
    }

    @Override
    public boolean isCompilable() {

        return this.iParseFunction != null && args.length > 0;
    }

//    @Override
//    public void generateCode(MethodVisitor mv, CodeFlow cf) {
//        Assert.state(this.iParseFunction != null, "No selfFunction handle");
//
//        mv.visitLdcInsn(this.iParseFunction.apply());
//        cf.pushDescriptor(this.exitTypeDescriptor);
//    }

}
