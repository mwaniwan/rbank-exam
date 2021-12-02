import java.time.LocalDate;

public class Task {

    private Long id;
    private Long parentId;
    private String name;
    private Integer duration;
    private LocalDate startDate;
    private LocalDate endDate;
    private Boolean highPriority;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Boolean getHighPriority() {
        return highPriority;
    }

    public void setHighPriority(Boolean highPriority) {
        this.highPriority = highPriority;
    }

    public void print() {
        System.out.println("Id: " + id);
        System.out.println("Name: " + name);
        System.out.println("Duration (Day/s): " + duration);
        if (highPriority != null) System.out.println("High Priority: " + highPriority);
        if (parentId != null) System.out.println("Parent Id: " + parentId);
    }

    public void printDates() {
        System.out.println("Start Date: " + startDate);
        System.out.println("End Date: " + endDate);
    }

    public void printAll() {
        System.out.println("Id: " + id);
        System.out.println("Name: " + name);
        System.out.println("Duration (Day/s): " + duration);
        if (highPriority != null) System.out.println("High Priority: " + highPriority);
        if (parentId != null) System.out.println("Parent Id: " + parentId);
        System.out.println("Start Date: " + startDate);
        System.out.println("End Date: " + endDate);
    }
}
