package com.github.zaibacu.graphql.providers;

import java.io.Serializable;


public class TestResult implements Serializable{
    public class TestInnerResult implements Serializable {
        private String other;

        public String getOther() {
            return other;
        }

        public void setOther(String other) {
            this.other = other;
        }
    }

    private String name;
    private int age;
    private TestInnerResult inner;

    public String getName(){
        return name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public TestInnerResult getInner() {
        return inner;
    }

    public void setInner(TestInnerResult inner) {
        this.inner = inner;
    }

    public void setName(String name){
        this.name = name;
    }
}