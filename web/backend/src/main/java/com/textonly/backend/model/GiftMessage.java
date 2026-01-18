package com.textonly.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GiftMessage {
    private String giftName;
    private Integer giftValue;
    private String giftResource;
    private String senderPhone;
    private String receiverPhone;
}
