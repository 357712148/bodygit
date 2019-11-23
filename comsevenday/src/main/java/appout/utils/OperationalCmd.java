package appout.utils;

import appout.base.AndroidCapabilityType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author liwen
 * @Title: OperationalCmd
 * @Description: dos操作命令
 * @date 2019/11/21 / 9:50
 */
public class OperationalCmd {

    public OperationalCmd() {
    }
    /**
     * 项目所在的系统的类型（大写）
     */
    public static final String SYSTEM_TYPE_UPPER = System.getProperties().getProperty("os.name").toUpperCase();
    /**
     * 获取当前操作系统
     */
    public static String osName = System.getProperty("os.name");

    /**
     * 获取设备序列号
     */
    public static final String GET_DEVICE_NAME = "adb get-serialno";
    /**
     * 获取包名与 APP_ACTIVITY
     *
     * @param path
     * @return
     */
    public static ArrayList<String> getPackAct(String path) {
        ArrayList<String> list = new ArrayList<>(1);
        try {
            List<String> execute = execute(AndroidCapabilityType.APP_GET_PACK_ACTIVITY.replace("pathapk", path), true);
            for (String s : execute) {
                int i = s.indexOf("name='");
                int i1 = s.indexOf("' versionCode=");
                if (s.contains("versionCode")) {
                    String substring = s.substring(i + 6, i1);
                    list.add(substring);
                } else {
                    int i2 = s.indexOf("'  label='");
                    String substring = s.substring(i + 6, i2);
                    list.add(substring);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 根据命令执行，
     *
     * @param cmdstr
     * @param isNeedReturn
     * @return list
     * @throws Exception
     */
    public static List<String> execute(String cmdstr, boolean isNeedReturn) throws Exception {
        //存储结果
        List<String> lineList = new ArrayList<String>();
        String[] cmdarray;
        if (osName.startsWith("Windows")) {
            cmdarray = new String[]{"cmd", "/c", cmdstr};
        } else {
            cmdarray = new String[]{"/bin/bash", "-c", cmdstr};
        }
        //执行命令
        Process process = Runtime.getRuntime().exec(cmdarray);
        if (isNeedReturn) {
            //获取结果流
            InputStream fis = process.getInputStream();
            //读取结果流
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));
            String line = null;
            while ((line = br.readLine()) != null) {
                if (line.trim().length() != 0) {
                    lineList.add(line);
                }
            }
            br.close();
            return lineList;
        }
        return null;
    }

    /**
     * @return name of the device
     * @throws IOException
     */
    public static String getDeviceName() {
        return runCmdCommand(GET_DEVICE_NAME).trim();
    }

    /**
     * 执行命令
     *
     * @param command
     * @return
     * @throws IOException
     */
    public static String runCmdCommand(String command) {
        ProcessBuilder builder;
        if (SYSTEM_TYPE_UPPER.indexOf("WINDOWS") != -1) {
            builder = new ProcessBuilder("cmd.exe", "/c", command);
        } else {
            builder = new ProcessBuilder("/bin/sh", "-c", command);
        }
        StringBuffer content = null;
        try {
            builder.redirectErrorStream(true);
            Process p = builder.start();
            BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
            content = new StringBuffer();
            String line;
            while ((line = r.readLine()) != null) {
                content.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content.toString();
    }
    /**
     * 执行命令
     *
     * @param command
     * @return
     */
    public static String execReturnAndWait(String command) {
        StringBuffer sb = new StringBuffer(1024);
        Process process = null;

        try {
            process = Runtime.getRuntime().exec(command);
            BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException var5) {
            var5.printStackTrace();
        }

        return sb.toString();
    }
    /**
     * 获取手机版本
     *
     * @return
     */
    public static String getMobileModel() {
        /**
         * 获取手机版本
         */
        String mobileModel = "adb shell getprop ro.product.model";
        String s = OperationalCmd.execReturnAndWait(mobileModel);
        return s;
    }

    /**
     * 获取手机系统版本
     * 版本号
     *
     * @return
     */
    public static String getVersionNameInfo() {
        /**
         * 版本号
         */
        String versionName = "adb shell dumpsys package com.jingdong.th.app | findstr versionName";
        /**
         * 获取手机系统版本
         */
        String versionRelease = "adb shell getprop ro.build.version.release";
        List<String> devList = null;
        try {
            devList = OperationalCmd.execute(versionName,true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String s = devList.get(0).split("=")[1] + "/Android:" + OperationalCmd.execReturnAndWait(versionRelease);
        return s;
    }
}
