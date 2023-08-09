package com.uol.pb.challenge3.jws;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Message implements Serializable {
    private int messageId;
    private String message;

}
