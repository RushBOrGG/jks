package com.jks.pb.function;


public interface IParseFunction<T>{

  default boolean executeBefore(){
    return false;
  }

  String functionName();

  String apply(T value);
}
