package com.sky.jvm.oom;

import java.util.ArrayList;
import java.util.List;

public class HeapOOmTest {
    static class OOMObject {
        public OOMObject() {
            System.out.println("test");
        }
    }
    public static void main(String[] args) {
        List<OOMObject> list = new ArrayList<OOMObject>();
        while (true){
            list.add(new OOMObject());
        }

    }
}
