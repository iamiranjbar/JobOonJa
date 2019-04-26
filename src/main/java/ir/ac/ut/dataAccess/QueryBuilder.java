package ir.ac.ut.dataAccess;

import ir.ac.ut.models.User.User;

import java.lang.reflect.Field;

public class QueryBuilder {

    public static String createInsertStatement(Class<?> className, String tableName) {
        StringBuilder fields= new StringBuilder();
        StringBuilder vars= new StringBuilder();
        for(Field field : className.getDeclaredFields())
        {
            System.out.println(field.getType());
            if(field.getType().isPrimitive()) {
                String name = field.getName();
                if (fields.length() > 1) {
                    fields.append(",");
                    vars.append(",");
                }
                fields.append(name);
                vars.append("?");
            }
        }
        return "INSERT INTO " + tableName + "(" + fields.toString() + ") VALUES(" + vars.toString() + ");";
    }

    public static void main(String[] args){
        System.out.println(createInsertStatement(User.class,"user"));
    }

}
