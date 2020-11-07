package br.com.zup.proposta.handler;

import java.util.List;

public class ErroPadrao {

    private List<String> messages;

    public ErroPadrao(List<String> messages) {
        this.messages = messages;
    }

    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }

    @Override
    public String toString() {
        return "ErroPadrao{" +
                "messages=" + messages +
                '}';
    }
}
