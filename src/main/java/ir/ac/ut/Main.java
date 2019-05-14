package ir.ac.ut;

import ir.ac.ut.jwt.Jwt;

public class Main {
    public static void main(String[] args) {
        System.out.println(Jwt.createJWT("1","ali","login", 12345667));
    }
}
