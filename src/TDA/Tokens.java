package TDA;

public class Tokens {
    private String token;
    private int id;

    public Tokens(String token, int id) {
        this.token = token;
        this.id = id;
    }

    public Tokens() {
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public int getId() {
        return id;
    }
}
