# 🧞 Sasa User Guide
<img src="Ui.png" width="294" height="600">

**Sasa** is a lightweight, desktop-based task management assistant designed to help _you_ track to-dos, deadlines and events through an intuitive command-line interface 

---
## 🚀 Quick Start 

1. Ensure that you have **Java 17** installed on your computer
2. Download the **latest** `sasa.jar` (v0.3) from the [releases page](https://github.com/sherylphoa/ip/releases)
3. Open a command terminal, navigate to the folder containing the file, and run: `java -jar sasa.jar`
4. Type your commands into the text box and start making your wishes!

---
## ⌨️ Command Format

To ensure Sasa understands your wishes, please follow these formatting guidelines:

### Case Insensitivity
* **Commands** are case-insensitive. You can type `TODO`, `todo`, or `ToDo`

### Use of UPPERCASE
* Words in **UPPERCASE** are parameters you HAVE TO specify.
* *Example:* In `todo DESCRIPTION`, you should replace DESCRIPTION with your task (e.g., `todo buy bread`).

### 📅 Date and Time Format
* Sasa strictly uses the format: `d/M/yyyy HHmm`
* *Example:* `31/1/2025 2359` (January 31st, 2025 at 11:59 PM).
* Incorrect formats will trigger an error message.

### 🔍 Task Indicators
When viewing your list, you will see these symbols:
* `[T]` : **Todo** (No date attached)
* `[D]` : **Deadline** (Has a "by" date)
* `[E]` : **Event** (Has a "from" and "to" duration)

### ✅ Status Indicators
* `[X]` : Task is **Completed**.
* `[]` : Task is **Incomplete**.

### Other Important Notes
* **Indices**: When using `mark`, `unmark`, or `delete`, use the number shown in the list command.
* **Separators**: Ensure you include the spaces around `/by`, `/from`, and `/to`.

---
## ✨ Features 

### Adding a Todo: `todo`
* Adds a task that does not have any specific deadline or time constraint
* Command: `todo DESCRIPTION`
* Example: `todo read book`
* Expected output: Sasa confirms the addition and shows the updated total task count.
    
    > Got it. I've added this task:
  > 
    > [T][] read book
  > 
    > Now you have 1 task in the list.

### Adding a Deadline: `deadline`
* Adds a task that needs to be done by a specific date and time
* Command: `deadline DESCIPTION /by BYDATETIME`
* Example: `deadline submit assignment /by 31/1/2025 2359`
* Expected output: Sasa confirms the deadline and formats the dead for readability

    > Got it. I've added this task:
  > 
    > [D][] submit assignment (by: Jan 31 2025, 11:59 pm)
  > 
    > Now you have 2 tasks in the list.

### Adding an Event: `event`
* Add a task that has a specific start and end time
* Command: `event DESCRIPTION /from STARTDATETIME /to ENDDATETIME`
* Example: `event exam /from 20/4/2026 1000 /to 20/4/2026 1200`
* Expected output: Sasa confirms the event duration 

    > Got it. I've added this task:
  > 
    > [E][] exam (from: Apr 20 2026, 10:00 am to: Apr 20 2026, 12:00 pm)
  > 
    > Now you have 3 tasks in the list.

### Listing all Tasks: `list`
* Displays every task currently stored in your list with its status and type
* Command: list
* Expected output: A numbered list showing task types ([T], [D], [E]) and completion status ([X] for done, [] for not done)

    > Here are your tasks:
  > 
    > 1.[T][] read book
  > 
    > 2.[D][] submit assignment (by: Jan 31 2025, 11:59 pm)
  > 
    > 3.[E][] exam (from: Apr 20 2026, 10:00 am to: Apr 20 2026, 12:00 pm)

### Marking / Unmarking Tasks: `mark` / `unmark`
* Updates the completion status of a specific task
* Command: `mark INDEX`  or  `unmark INDEX`
* Example: `mark 1`
* Expected output: The task's checkbox changes to `[X]` (for mark) or `[]` (for unmark)

    > Nice! This task is marked:
  > 
    >   [T][X] read book

### Deleting a Task: `delete`
* Removes a task from your records permanently
* Command: `delete INDEX`
* Example: `delete 3`
* Expected output: Sasa displays the removed task and the new total count

    > I've removed this task:
  > 
    >   [E][] exam (from: Apr 20 2026, 10:00 am to: Apr 20 2026, 12:00 pm)
  > 
    > Now you have 2 tasks in the list.

### Finding Tasks: `find`
* Locates tasks that contain a specific keyword in their description
* Command: `find KEYWORD`
* Example: `find assignment`
* Expected output: A list of all tasks containing the word 'assignment'

    > Here are the matching tasks:
  > 
    > 2.[D][] submit assignment (by: Jan 31 2025, 11:59 pm)

### Sorting Tasks: `sort`
* Organises the list into a chronological order first by start time then end time (if applicable), todos are placed at the very end.
* Command: `sort`
* Expected output: Sasa reorders the tasks and displays the newly organised list

    > Here are your tasks:
  > 
    > 1.[D][] submit assignment (by: Jan 31 2025, 11:59 pm)
  > 
    > 2.[T][X] read book

---
## Exiting 

* Closes the Sasa application
* Command: `bye`
* Expected output: Sasa acknowledges that you want to close the app

    > Bye! Come back when you need me again!

---
## 🗄️ Data Storage

Sasa automatically saves your task list after every command. The data is stored in a file named `sasa.txt` within a 
`data` folder located in the same directory as the `.jar` file

| Operating System | Default Storage Path                                          |
| :--- |:--------------------------------------------------------------|
| **Windows** | `C:\Users\<User>\Sasa\data\sasa.txt` (or where the .jar is run) |
| **macOS** | `/Users/<User>/Sasa/data/sasa.txt`                              |
| **Linux** | `/home/<User>/Sasa/data/sasa.txt`                               |

> ⚠️ **Important:** Do not manually edit the sasa.txt file. If the formatting is altered, Sasa may fail to load your tasks correctly on the next launch.