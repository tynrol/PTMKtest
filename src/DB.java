import java.sql.*;
import java.util.Random;
import java.time.LocalDate;


public class DB {
    static Connection connection;
    static PreparedStatement preparedStatement;


    public DB() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:5433/mysql?useUnicode=true&serverTimezone=UTC", "tynrol", "1234");
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Couldnt connect to DB");
            //e.printStackTrace();
        }
    }
    public static String create (){
        try {
            preparedStatement = connection.prepareStatement("CREATE TABLE people ( " +
                    "id serial PRIMARY KEY," +
                    "FIO TEXT NOT NULL," +
                    "DATE TIMESTAMP," +
                    "SEX TEXT NOT NULL)");

            preparedStatement.executeUpdate();
        } catch(SQLException e){
            //e.printStackTrace();
            System.out.println("Unable to create");
        }
        return "Database created";
    }
    public static String add (String fio,LocalDate birthDate,String sex){
        try {
            preparedStatement = connection.prepareStatement("INSERT INTO people (fio, date, sex) VALUES(?,?,?)");
            preparedStatement.setString(1,fio);
            preparedStatement.setDate(2,java.sql.Date.valueOf(birthDate));
            preparedStatement.setString(3,sex);
            preparedStatement.executeUpdate();
        } catch(SQLException e){
            e.printStackTrace();
            System.out.println("Cant add");
        }
        return "Person added";
    }
    public static String printUnique (){
        String result = "In database \n";
        try {
            preparedStatement = connection.prepareStatement("SELECT DISTINCT fio, date, sex FROM people ORDER BY fio, date");
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                String fio = resultSet.getString("fio");
                LocalDate date = resultSet.getDate("date").toLocalDate();
                String sex = resultSet.getString("sex");
                int age = LocalDate.now().minusYears(date.getYear()).getYear();
                result += (fio + " " + age + " " + date + " " + sex + "\n");
            }
        } catch(SQLException e){
            //e.printStackTrace();
            System.out.println("Cant print");
            result = "";
        }
        return result;
    }
    public static String getF(){
        String result = "In database \n";
        try {
            preparedStatement = connection.prepareStatement("SELECT fio, date, sex FROM people " +
                    "WHERE SEX = \'Male\' AND " +
                    "FIO LIKE \'F%\'");
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                String fio = resultSet.getString("fio");
                LocalDate date = resultSet.getDate("date").toLocalDate();
                String sex = resultSet.getString("sex");
                result += (fio + " " + date + " " + sex + "\n");
            }
        } catch(SQLException e){
            //e.printStackTrace();
            System.out.println("Cant print");
            result = "";
        }
        return result;
    }
    public static String randomString(){
        String result = "";
        Random random = new Random();
        String values = "abcdefghijklmnopqrstuvwxyzg";
        for (int i = 0; i<7; i++){
            int select = random.nextInt(values.length());
            result += values.charAt(select);
        }
        result = result.substring(0, 1).toUpperCase() + result.substring(1);
        return result;
    }
    public static LocalDate createRandomDate(int startYear, int endYear) {
        int day = createRandomIntBetween(1, 28);
        int month = createRandomIntBetween(1, 12);
        int year = createRandomIntBetween(startYear, endYear);
        return LocalDate.of(year, month, day);
    }
    public static int createRandomIntBetween(int start, int end) {
        return start + (int) Math.round(Math.random() * (end - start));
    }
    public static String randomSex(){
        Random random = new Random();
        if (random.nextBoolean())
            return "Male";
        else
            return "Female";
    }
}