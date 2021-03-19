package com.onlinesjtu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CallPythonTest {

    public static void main(String[] args) {
        Process proc;
        try {
            String[] cmds = new String[]{"python", "C:\\workspace\\DLCPlus\\tooltest_distance_random.py", args[0]};
            proc = Runtime.getRuntime().exec(cmds);// 执行py文件
            //用输入输出流来截取结果
            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            String line = null;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
//				System.err.println(Arrays.asList(line.split(" ")));
            }
            in.close();
            proc.waitFor();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
