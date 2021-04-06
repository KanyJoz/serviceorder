package com.kanyojozsef96.serviceorder;

import java.util.List;

public class ServiceOrder {
    private String id;
    private String category;
    private String description;
    private String state;
    private String priority;
    private String contact;

    private List<String> items;
    private List<String> parties;
    private List<String> notes;

    private String orderDate;
    private String expectedDate;

    public ServiceOrder(String id, String category, String description, String state, String priority, String contact, List<String> items, List<String> parties, List<String> notes, String orderDate, String expectedDate) {
        this.id = id;
        this.category = category;
        this.description = description;
        this.state = state;
        this.priority = priority;
        this.contact = contact;
        this.items = items;
        this.parties = parties;
        this.notes = notes;
        this.orderDate = orderDate;
        this.expectedDate = expectedDate;
    }

    public ServiceOrder() {
    }

    public String _getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getState() { return state; }
    public void setState(String state) { this.state = state; }

    public String getPriority() { return priority; }
    public void setPriority(String priority) { this.priority = priority; }

    public String getContact() { return contact; }
    public void setContact(String contact) { this.contact = contact; }

    public List<String> getItems() { return items; }
    public void setItems(List<String> items) { this.items = items; }

    public List<String> getParties() { return parties; }
    public void setParties(List<String> parties) { this.parties = parties; }

    public List<String> getNotes() { return notes; }
    public void setNotes(List<String> notes) { this.notes = notes; }

    public String getOrderDate() { return orderDate; }
    public void setOrderDate(String orderDate) { this.orderDate = orderDate; }

    public String getExpectedDate() { return expectedDate; }
    public void setExpectedDate(String expectedDate) { this.expectedDate = expectedDate; }
}