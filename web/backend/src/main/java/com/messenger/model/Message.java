package com.messenger.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String from;
    private String to;
    private String content;
    private LocalDateTime timestamp;

    // Message type: TEXT, FILE, INVITE, GIFT
    private String type = "TEXT";

    // File message fields
    private String fileName;
    private Integer filePrice;
    private String filePriceUnit; // "COINS" or resource name
    private String filePriceGiftName;
    private String fileUrl;
    private Boolean isUnlocked = false;

    // Invite message fields
    private String inviteCode;
    private String inviteServerName;
    private String inviteInviterName;
    private Long inviteExpiry;

    // Gift message fields
    private String giftName;
    private Integer giftValue;
    private String giftResource;

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getFrom() { return from; }
    public void setFrom(String from) { this.from = from; }
    public String getTo() { return to; }
    public void setTo(String to) { this.to = to; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
    
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    
    public String getFileName() { return fileName; }
    public void setFileName(String fileName) { this.fileName = fileName; }
    public Integer getFilePrice() { return filePrice; }
    public void setFilePrice(Integer filePrice) { this.filePrice = filePrice; }
    public String getFilePriceUnit() { return filePriceUnit; }
    public void setFilePriceUnit(String filePriceUnit) { this.filePriceUnit = filePriceUnit; }
    public String getFilePriceGiftName() { return filePriceGiftName; }
    public void setFilePriceGiftName(String filePriceGiftName) { this.filePriceGiftName = filePriceGiftName; }
    public String getFileUrl() { return fileUrl; }
    public void setFileUrl(String fileUrl) { this.fileUrl = fileUrl; }
    public Boolean getIsUnlocked() { return isUnlocked; }
    public void setIsUnlocked(Boolean isUnlocked) { this.isUnlocked = isUnlocked; }
    
    public String getInviteCode() { return inviteCode; }
    public void setInviteCode(String inviteCode) { this.inviteCode = inviteCode; }
    public String getInviteServerName() { return inviteServerName; }
    public void setInviteServerName(String inviteServerName) { this.inviteServerName = inviteServerName; }
    public String getInviteInviterName() { return inviteInviterName; }
    public void setInviteInviterName(String inviteInviterName) { this.inviteInviterName = inviteInviterName; }
    public Long getInviteExpiry() { return inviteExpiry; }
    public void setInviteExpiry(Long inviteExpiry) { this.inviteExpiry = inviteExpiry; }
    
    public String getGiftName() { return giftName; }
    public void setGiftName(String giftName) { this.giftName = giftName; }
    public Integer getGiftValue() { return giftValue; }
    public void setGiftValue(Integer giftValue) { this.giftValue = giftValue; }
    public String getGiftResource() { return giftResource; }
    public void setGiftResource(String giftResource) { this.giftResource = giftResource; }
}
