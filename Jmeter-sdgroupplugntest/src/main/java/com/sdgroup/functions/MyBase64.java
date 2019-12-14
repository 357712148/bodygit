package com.sdgroup.functions;

import org.apache.jmeter.engine.util.CompoundVariable;
import org.apache.jmeter.functions.AbstractFunction;
import org.apache.jmeter.functions.InvalidVariableException;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.samplers.Sampler;
import org.apache.jmeter.threads.JMeterVariables;
import sun.misc.BASE64Encoder;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * @author liwen
 * @Title: MyBase64
 * @Description: 包名要包含functions，因为jmeter.properties对这块有配置，可见该文件的classfinder.functions.contain=.functions.
 * 在该包下新建一个类并继承AbstractFunction，重写该类的所有方法
 * @date 2019/12/13 / 14:08
 */
public class MyBase64 extends AbstractFunction {

    /**
     * 自定义function的描述
     */
    private static final List<String> desc = new LinkedList<>();

    static {
        desc.add("图片路径");
    }

    static {
        desc.add("图片base64后存放变量");
    }

    private static final String KEY = "__MyBase64";

    /**
     * 存放传入参数的值的变量
     */
    private Object[] values;


    public String getImgBase64(String filePath) {
        InputStream in = null;
        byte[] data = null;
        String result = null;
        try {
            in = new FileInputStream(filePath);
            data = new byte[in.available()];
            in.read(data);
            in.close();

            BASE64Encoder encoder = new BASE64Encoder();
            result = encoder.encode(data);
        } catch (Exception e) {
            e.printStackTrace();
        }


        return result;
    }

    @Override
    public String execute(SampleResult sampleResult, Sampler sampler) throws InvalidVariableException {
        JMeterVariables localJMeterVariables = getVariables();
        String str1 = ((CompoundVariable) this.values[0]).execute();
        String str2 = getImgBase64(str1);
        if ((localJMeterVariables != null) && (this.values.length > 1)) {
            String str3 = ((CompoundVariable) this.values[1]).execute().trim();
            localJMeterVariables.put(str3, str2);
        }
        return str2;
    }

    @Override
    public void setParameters(Collection<CompoundVariable> collection) throws InvalidVariableException {
        checkParameterCount(collection, 1, 2);
        //将参数值存入变量中
        this.values = collection.toArray();

    }

    @Override
    public String getReferenceKey() {
        return KEY;
    }

    @Override
    public List<String> getArgumentDesc() {
        return desc;
    }
}
