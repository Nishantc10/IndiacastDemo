package com.example.indiacastdemo.Validation;

public class ConditionCheckClass {
    public boolean isNotNull(String str){
        if(str != null || str != "" || str != " " || str != "0" || str!="null")
            return true;
        else
            return false;
    }
}
