import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class Sasa {
    public static void main(String[] args) {
        String horizontalLine = "____________________________________________________________";
        ArrayList<Task> tasks = new ArrayList<>();

        System.out.println(horizontalLine);
        System.out.println(" Hello! I'm Sasa\n Your wish is my command");
        System.out.println(horizontalLine);

        Scanner sc = new Scanner(System.in);

        while (true) {
            String input = sc.nextLine();

            if (input.equalsIgnoreCase("bye")) {
                break;
            }
            try {
                System.out.println(horizontalLine);
                if (input.equalsIgnoreCase("list")) {
                    System.out.println("Here are your tasks:");
                    for (int i = 0; i < tasks.size(); i++) {
                        System.out.println(" " + (i + 1) + "." + tasks.get(i));
                    }
                } else if (input.startsWith("mark")) {
                    int index = Integer.parseInt(input.substring(5)) - 1;
                    checkIndex(index, tasks);
                    tasks.get(index).markAsDone();
                    System.out.println(" Nice! This task is marked:");
                    System.out.println("   " + tasks.get(index));
                } else if (input.startsWith("unmark")) {
                    int index = Integer.parseInt(input.substring(7)) - 1;
                    checkIndex(index, tasks);
                    tasks.get(index).unmarkAsDone();
                    System.out.println(" OK, This task is unmarked:");
                    System.out.println("   " + tasks.get(index));
                } else if (input.startsWith("todo")) {
                    if (input.length() <= 5) {
                        throw new SasaException("Please include your task.");
                    }
                    tasks.add(new Todo(input.substring(5)));
                    addMessage(tasks);
                } else if (input.startsWith("deadline")) {
                    if (input.length() <= 9) {
                        throw new SasaException("Please include your task with the deadline.");
                    }

                    if (!input.contains(" /by ")) {
                        throw new SasaException("Deadlines must include ' /by '.");
                    }

                    String[] parts = input.substring(9).split(" /by ");
                    tasks.add(new Deadline(parts[0], parts[1]));

                    addMessage(tasks);
                } else if (input.startsWith("event")) {
                    if (input.length() <= 6) {
                        throw new SasaException("Please include your task with the duration.");
                    }

                    if (!input.contains(" /from ") || !input.contains(" /to ")) {
                        throw new SasaException("Events must include ' /from ' and ' /to '.");
                    }

                    String[] parts = input.substring(6).split(" /from ");
                    String[] timeParts = parts[1].split(" /to ");
                    tasks.add(new Event(parts[0], timeParts[0], timeParts[1]));
                    addMessage(tasks);
                } else if (input.startsWith("delete")) {
                    int index = Integer.parseInt(input.substring(7)) - 1;
                    checkIndex(index, tasks);

                    Task removed = tasks.remove(index);
                    System.out.println(" I've removed this task:\n   " + removed);
                    System.out.println(" Now you have " + tasks.size() + " tasks in the list.");
                } else {
                    throw new SasaException("Sorry, I do not know what that means :(");
                }
                System.out.println(horizontalLine);
            } catch (SasaException e) {
                System.out.println(" OOPS!!! " + e.getMessage());
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                System.out.println(" OOPS!!! Please provide a valid task number.");
            }
        }

        System.out.println(horizontalLine);
        System.out.println(" Bye. Come back when you need me!");
        System.out.println(horizontalLine);

        sc.close();
    }

    private static void addMessage(ArrayList<Task> tasks) {
        Task task = tasks.get(tasks.size() - 1);
        int count = tasks.size();
        System.out.println(" Got it. I've added this task:");
        System.out.println("   " + task);
        if (count == 1) {
            System.out.println(" Now you have 1 task in the list.");
        } else {
            System.out.println(" Now you have " + count + " tasks in the list.");
        }
    }

    private static void checkIndex(int index, ArrayList<Task> tasks) throws SasaException {
        if (index < 0 || index >= tasks.size()) {
            throw new SasaException("Task " + (index + 1) + " doesn't exist! You have " + tasks.size() + " tasks.");
        }
    }
}
