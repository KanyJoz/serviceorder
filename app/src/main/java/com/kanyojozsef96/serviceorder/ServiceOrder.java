package com.kanyojozsef96.serviceorder;

import java.util.List;

public class ServiceOrder {
    private String id;
    private String category;
    private String description;
    private String contact;
    private String state;
    private String priority;

    private List<String> items;
    private List<String> parties;
    private List<String> notes;

    // Auto-fill
    private String orderDate;
    private String expectedDate;
    // Default
    private String cancellationDate = "";
    private String cancellationReason = "";

    public ServiceOrder(String id, String category, String description, String contact, String state, String priority, List<String> items, List<String> parties, List<String> notes, String orderDate, String expectedDate, String cancellationDate, String cancellationReason) {
        this.id = id;
        this.category = category;
        this.description = description;
        this.contact = contact;
        this.state = state;
        this.priority = priority;
        this.items = items;
        this.parties = parties;
        this.notes = notes;
        this.orderDate = orderDate;
        this.expectedDate = expectedDate;
        this.cancellationDate = cancellationDate;
        this.cancellationReason = cancellationReason;
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

    public String getCancellationDate() { return cancellationDate; }
    public void setCancellationDate(String cancellationDate) { this.cancellationDate = cancellationDate; }

    public String getCancellationReason() { return cancellationReason; }
    public void setCancellationReason(String cancellationReason) { this.cancellationReason = cancellationReason; }

    public String _getItemsString() {
        String result = "";
        for(int i = 0; i < items.size(); ++i) {
            result += items.get(i);
            if(i != items.size() - 1) {
                result += "\n";
            }
        }

        return result;
    }

    public String _getPartiesString() {
        String result = "";
        for(int i = 0; i < parties.size(); ++i) {
            result += parties.get(i);
            if(i != parties.size() - 1) {
                result += "\n";
            }
        }

        return result;
    }

    public String _getNotesString() {
        String result = "";
        for(int i = 0; i < notes.size(); ++i) {
            result += notes.get(i);
            if(i != notes.size() - 1) {
                result += "\n";
            }
        }

        return result;
    }

    @Override
    public String toString() {
        return "ServiceOrder{" +
                "id='" + id + '\'' +
                ", category='" + category + '\'' +
                ", description='" + description + '\'' +
                ", contact='" + contact + '\'' +
                ", state='" + state + '\'' +
                ", priority='" + priority + '\'' +
                ", items=" + items +
                ", parties=" + parties +
                ", notes=" + notes +
                ", orderDate='" + orderDate + '\'' +
                ", expectedDate='" + expectedDate + '\'' +
                ", cancellationDate='" + cancellationDate + '\'' +
                ", cancellationReason='" + cancellationReason + '\'' +
                '}';
    }
}
