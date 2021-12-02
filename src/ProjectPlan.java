
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

public class ProjectPlan {
    public static void main(String args[]) {
        Scanner scanner = new Scanner(System.in).useDelimiter("\n");;
        System.out.println("=============================");
        System.out.println("Welcome to your Project Plan");
        System.out.println("=============================");

        System.out.print("Enter Target Project Plan Start Date (dd-MM-yyyy):");
        LocalDate ppStartDate = LocalDate.parse(scanner.next(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));

        System.out.println();
        List<Task> taskList = new ArrayList<>();
        boolean newTask = true;
        Long uuid = 1L;

        System.out.println("=============================");
        System.out.println("Add Tasks");
        System.out.println();

        while (newTask) {
            // Add Task
            System.out.print("Would you like to enter a new Task? (yes/no): ");
            String decisionTask = scanner.next();

            if (decisionTask.equalsIgnoreCase("yes")) {
                Task task = addNewTask(scanner, uuid, null);
                taskList.add(task);
                uuid++;

                //Add Child Task
                System.out.println();
                System.out.print("Would you like to add child task? (yes/no): ");
                String decision = scanner.next();

                switch (decision) {
                    case "yes":
                        uuid = addChildTask(scanner, uuid, task.getId(), taskList);
                        break;
                    case "no":
                        break;
                    default:
                        System.out.println("please enter again ");
                }
            } else {
                System.out.println();
                System.out.println();
                System.out.println("============== Printing Task Schedules: ==============");
                System.out.println();
                printList(taskList, ppStartDate);
                newTask = false;
            }

        }

    }

    public static Task addNewTask(Scanner scanner, Long uuid, Long parentId) {
        Boolean highPriority = null;
        String taskName;
        int taskDuration;

        System.out.print("Enter Task Name: ");
        taskName = scanner.next();
        System.out.print("Task Duration (Day/s): ");
        taskDuration = scanner.nextInt();
        if (parentId == null) {
            System.out.print("Is this High Priority (true/false): ");
            highPriority = scanner.nextBoolean();
        }

        Task task = new Task();
        task.setId(uuid);
        task.setName(taskName);
        task.setDuration(taskDuration);
        task.setParentId(parentId);
        task.setHighPriority(highPriority);

        return task;
    }

    public static Long addChildTask(Scanner scanner, Long uuid, Long parentId, List<Task> taskList) {
        boolean addMore = true;
        do {
            taskList.add(addNewTask(scanner, uuid, parentId));
            uuid++;

            System.out.println();
            System.out.print("Would you like to add more child task? (yes/no): ");
            String decision = scanner.next();
            switch (decision) {
                case "yes":
                    addMore = true;
                    break;
                case "no":
                    addMore = false;
                    break;
                default:
                    System.out.println("please enter again ");
            }
        } while (addMore);

        return uuid;
    }

    private static void printList(List<Task> taskList, LocalDate ppStartDate) {
        LocalDate startDate = ppStartDate;
        Map<Long, List<Task>> taskWithChildMap = taskList
                .stream()
                .filter(task -> task.getParentId() != null)
                .collect(groupingBy(Task::getParentId));

        List<Task> mainTasks = taskList
                .stream()
                .filter(task -> task.getParentId() == null)
                .sorted(Comparator.comparing(Task::getHighPriority).reversed())
                .collect(Collectors.toList());

        for (Task task: mainTasks) {
            System.out.println();
            System.out.println("###====" + task.getName() + "====###");
            task.print();

            if(taskWithChildMap.containsKey(task.getId())) {
                List<Task> childTasks = taskWithChildMap.get(task.getId());
                System.out.println("== Child Tasks ==");
                for (Task child : childTasks.stream().sorted(Comparator.comparing(Task::getId)).collect(Collectors.toList())) {
                    child.setStartDate(startDate);
                    child.setEndDate(startDate.plusDays(child.getDuration()));

                    child.printAll();
                    System.out.println("== Child Tasks ==");
                    System.out.println();

                    //Update next start date
                    startDate = child.getEndDate().plusDays(1);
                }
                System.out.println("== Child Tasks ==");
            }

            System.out.println();

            task.setStartDate(startDate);
            task.setEndDate(startDate.plusDays(task.getDuration()));
            task.printDates();

            startDate = task.getEndDate().plusDays(1);

            System.out.println();
        }
    }
}
