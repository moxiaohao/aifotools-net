package com.foryou.net.live;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class Resource<T> {

    @NonNull
    public Status status;

    @NonNull
    public int code = 0;

    @Nullable
    public String message;

    @Nullable
    public T data;


    public Resource(@NonNull Status status, @Nullable T data) {
        this.status = status;
        this.data = data;
    }

    public Resource(@NonNull Status status, @Nullable T data, @Nullable String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    public Resource(@NonNull Status status, int code, @Nullable T data, @Nullable String message) {
        this.status = status;
        this.data = data;
        this.message = message;
        this.code = code;
    }

    public static <T> Resource<T> success(@Nullable T data) {
        return new Resource<>(Status.SUCCESS, data, null);
    }

    public static <T> Resource<T> error(String msg, int code, @Nullable T data) {
        return new Resource<>(Status.ERROR, code, data, msg);
    }

    public static <T> Resource<T> loading(@Nullable T data) {
        return new Resource<>(Status.LOADING, data, null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Resource<?> resource = (Resource<?>) o;
        if (status != resource.status) {
            return false;
        }
        if (message != null ? !message.equals(resource.message) : resource.message != null) {
            return false;
        }
        return data != null ? data.equals(resource.data) : resource.data == null;
    }

    @Override
    public int hashCode() {
        int result = status.hashCode();
        result = 31 * result + (message != null ? message.hashCode() : 0);
        result = 31 * result + (data != null ? data.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Resource{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
