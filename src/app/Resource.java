package app;

import java.io.Serializable;
import java.time.LocalDate;

public class Resource implements Serializable{

    private String title;
    private String priority;
    private LocalDate date = LocalDate.now();
    private String text;
    private String priorityColor;

    public Resource(String title, String priority, String text, int year, int month, int day) {
        this.title = title;
        this.priority = priority;
        this.text = text;
        date = LocalDate.of(year, month, day);

        if(priority == "LOW PRIORITY")  priorityColor ="pink";
        else if(priority == "MEDIUM PRIORITY")  priorityColor ="red";
        else if(priority == "HIGH PRIORITY") priorityColor ="black";
    }

    @Override
    public String toString() {
        return title;
    }
    public String getTitle() {
        return title;
    }
    public String getPriority() {
        return priority;
    }
    public LocalDate getDate() {
        return date;
    }
    public String getText() {
        return text;
    }
    public String getPriorityColor() {
        return priorityColor;
    }

}
