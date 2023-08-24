public class Coin {
    private String face_value;
    private String currency;
    private String country;

    private int year;

    public Coin(String face_value, String currency, String country, int year) {
        this.face_value = face_value;
        this.currency = currency;
        this.country = country;
        this.year = year;
    }

    public String getFace_value() {
        return face_value;
    }

    public String getCurrency() {
        return currency;
    }

    public String getCountry() {
        return country;
    }

    public int getYear() {
        return year;
    }
}
