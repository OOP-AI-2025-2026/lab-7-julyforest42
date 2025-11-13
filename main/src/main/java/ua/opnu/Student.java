package ua.opnu;

class Student {
    private String firstName;
    private String lastName;
    private String group;
    private int[] marks;

    public Student() {}

    public Student(String firstName, String lastName, String group, int[] marks) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.group = group;
        this.marks = marks;
    }

    public String getFirstName() { return firstName; }
    public String getLastName()  { return lastName;  }
    public String getGroup()     { return group;     }
    public int[] getMarks()      { return marks;     }

    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName)   { this.lastName = lastName;   }
    public void setGroup(String group)         { this.group = group;         }
    public void setMarks(int[] marks)          { this.marks = marks;         }

    @Override
    public String toString() {
        return lastName + " " + firstName + " (" + group + ")";
    }
}