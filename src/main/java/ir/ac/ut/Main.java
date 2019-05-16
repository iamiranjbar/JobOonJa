package ir.ac.ut;

import ir.ac.ut.jwt.Jwt;

public class Main {
    public static void main(String[] args) {
        String a = Jwt.createJWT("1", "ali", "auth", 111123);
        System.out.println(a);
        System.out.println(Jwt.decodeJWT(a).getExpiration());
    }
}
