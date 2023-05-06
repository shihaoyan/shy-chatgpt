package com.shy.chat.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author shihaoyan
 * @date 2023/5/7 6:27
 * @since 1.0.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> {

    private int ret;

    private String msg;

    private T data;

    public static Result success(Object data) {

        return new Result<>(0, "success", data);
    }

}
