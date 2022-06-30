package wiki.jixing.myblog.utils;

import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class Log {
    private String ip;
    private String url;
    private String method;
    private Object[] args;
    private Object result;

    public Log() {
    }

    public Log(String ip, String url, String method, Object[] args) {
        this.ip = ip;
        this.url = url;
        this.method = method;
        this.args = args;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "{" +
                "ip='" + ip + '\'' +
                ", url='" + url + '\'' +
                ", method='" + method + '\'' +
                ", args=" + Arrays.toString(args) +
                ", result=" + result +
                '}';
    }
}
