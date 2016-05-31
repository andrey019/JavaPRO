package HomeWork.Lesson2.Task2;

import java.util.ArrayList;

class Person {
    private String name;
    private String surname;
    private ArrayList<String> phones;
    private ArrayList<String> sites;
    private Address address;

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Name: " + name + "\n");
        stringBuilder.append("Surname: " + surname + "\n");
        stringBuilder.append("Phones: " + phones + "\n");
        stringBuilder.append("Sites: " + sites + "\n");
        stringBuilder.append("Address: {\n");
        stringBuilder.append(address);
        stringBuilder.append("\t}\n");
        return stringBuilder.toString();
    }
}
