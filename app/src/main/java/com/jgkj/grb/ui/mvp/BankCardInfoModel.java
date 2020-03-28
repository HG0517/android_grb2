package com.jgkj.grb.ui.mvp;

import java.util.List;

/**
 * Created by brightpoplar@163.com on 2019/9/17.
 */
public class BankCardInfoModel {

    /**
     * cardType : DC
     * bank : ZYB
     * key : 6213270000001635909
     * messages : []
     * validated : true
     * stat : ok
     */

    private String cardType;
    private String bank;
    private String key;
    private boolean validated;
    private String stat;
    private List<MessageBean> messages;

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public boolean isValidated() {
        return validated;
    }

    public void setValidated(boolean validated) {
        this.validated = validated;
    }

    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }

    public List<MessageBean> getMessages() {
        return messages;
    }

    public void setMessages(List<MessageBean> messages) {
        this.messages = messages;
    }

    public static class MessageBean {

        /**
         * errorCodes : CARD_BIN_NOT_MATCH
         * name : cardNo
         */

        private String errorCodes;
        private String name;

        public String getErrorCodes() {
            return errorCodes;
        }

        public void setErrorCodes(String errorCodes) {
            this.errorCodes = errorCodes;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
