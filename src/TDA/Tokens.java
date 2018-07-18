package TDA;

public class Tokens {
    private String token, id;

    public Tokens(String token, String id) {
        this.token = token;
        this.id = id;
    }

    public Tokens() {
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public String getId() {
        return id;
    }
}
