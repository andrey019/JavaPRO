package HomeWork.Lesson2.Task2;

class Address {
    private String country;
    private String city;
    private String street;

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\tCountry: " + country + "\n");
        stringBuilder.append("\tCity: " + city + "\n");
        stringBuilder.append("\tStreet: " + street + "\n");
        return stringBuilder.toString();
    }
}
