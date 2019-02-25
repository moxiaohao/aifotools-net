package com.foryou.net.http;

import android.os.NetworkOnMainThreadException;

import com.google.gson.JsonSyntaxException;

import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.net.UnknownServiceException;

import retrofit2.HttpException;

/**
 * Description:
 * Created by moxiaohao
 * Date: 23/11/2018
 * Email: shenlei@foryou56.com
 */
public class ErrorStatus {

    public int code;
    public String msg;

    public ErrorStatus(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * Throwable convert to Exception
     * @param e
     * @return
     */
    public static ErrorStatus getStatus(Throwable e) {
        if (e instanceof HttpException)//服务器异常
            return getStatusByCode(((HttpException) e).code());
        if (e instanceof SocketTimeoutException)//连接超时
            return convert(ErrorCode.CODE_EXCEPTION_TIMEOUT);
        if (e instanceof UnknownHostException)//未知主机
            return convert(ErrorCode.CODE_UNKNOW_HOST);
        if (e instanceof UnknownServiceException)//未知的服务器错误
            return convert(ErrorCode.RESPONSE_FATAL_EOR);
        if (e instanceof ConnectException || e instanceof SocketException)//连接异常
            return convert(ErrorCode.CODE_CONNECTION_EXCEPTION);
        if (e instanceof NetworkOnMainThreadException)//主线程不能网络请求
            return convert(ErrorCode.RESPONSE_FATAL_EOR_NETON_MIANTHREAD);
        if (e instanceof javax.net.ssl.SSLHandshakeException)//证书异常
            return convert(ErrorCode.CODE_SSLHANDSHAKE_EXCEPTION);
        if (e instanceof JsonSyntaxException)
            return convert(ErrorCode.CODE_JSON_SYNTAX_EXCEPTION);
        return new ErrorStatus(699,e.getMessage());
//      return convert(ErrorCode.CODE_UNKNOW);//未知异常
    }

    public static ErrorStatus getStatusByCode(int code) {
        return new ErrorStatus(code, "服务器内部错误(" + code + ")，请稍后重试");
    }

    private static ErrorStatus convert(ErrorCode errorCode) {
        return new ErrorStatus(errorCode.code, errorCode.name);
    }

    public enum ErrorCode {

        CODE_PARSE_ERROR(600, "数据解析异常"),
        CODE_EXCEPTION_TIMEOUT(601, "连接服务器超时"),
        CODE_UNKNOW_HOST(602, "网络异常，请稍后重试"),
        RESPONSE_FATAL_EOR(603, "未知的服务器错误"),
        RESPONSE_FATAL_EOR_NETON_MIANTHREAD(604, "主线程不能网络请求"),
        CODE_CONNECTION_EXCEPTION(605, "无法建立连接，请检查网路"),
        CODE_SERVER_ERROR(606, "服务器状态异常"),
        CODE_NET_ERROR(607, "网络异常，请稍后重试"),
        CODE_SSLHANDSHAKE_EXCEPTION(606, "证书验证失败"),
        CODE_JSON_SYNTAX_EXCEPTION(607, "JSON解析失败"),
        CODE_UNKNOW(699, "未知错误");

        public int code;
        public String name;

        ErrorCode(int code, String name) {
            this.code = code;
            this.name = name;
        }
    }

}

