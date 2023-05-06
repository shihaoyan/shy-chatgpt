package com.shy.chat.dto;

import lombok.Data;

import java.util.List;

/**
 * @author shihaoyan
 * @date 2023/5/7 6:28
 * @since 1.0.0
 */
@Data
public class ChatMessageDTO {

    private List<String> text;

}
