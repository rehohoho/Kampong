package com.example.kampung.models;

public class RequestAction {

    private Request request;
    private int actionId;

    public RequestAction(Request request, int actionId) {
        this.request = request;
        this.actionId = actionId;
    }

    public Request getRequest() {
        return request;
    }

    public int getActionId() {
        return actionId;
    }

    public static class ACTION_ID {
        public static final int ADDED = 0;
        public static final int CHANGED = 1;
        public static final int REMOVED = 2;
        public static final int MOVED = 3;
    }

}
