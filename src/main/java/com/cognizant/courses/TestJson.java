package com.cognizant.courses;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class TestJson {

    public static void main(String ...arg) throws Exception{

        FileInputStream f = new FileInputStream("data.json");
        byte[] buffer = new byte[1024];
        int b = 0;
        StringBuilder bd = new StringBuilder();

        // Copy requested file into the socket's output stream.
        while ((b = f.read(buffer)) != -1)
            bd.append(new String(buffer));

        Map jsonm = new HashMap();


    ObjectMapper mapper = new ObjectMapper();
     jsonm = mapper.readValue(bd.toString(), HashMap.class);

        System.out.println(bd.toString());
     System.out.println(jsonm.keySet());


}


static String read(Iterator it){
      return null;

}
}
