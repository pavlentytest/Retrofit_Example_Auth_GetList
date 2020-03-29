package com.example.myapplication;

public class ServerRequest {
    private String operation;
    private String id;

    public ServerRequest(String operation, String id) {
        this.operation = operation;
        this.id = id;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public void setId(String id) {
        this.id = id;
    }
}
