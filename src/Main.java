import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.util.Scanner;

public class Main {

    private static final String DB_USERNAME = "postgres";
    private static final String DB_PASSWORD = "89225523232";
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/postgres";


    public static void main(String[] args) throws Exception{
        Scanner scanner = new Scanner(System.in);
        Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
        System.out.println("Выберите опцию:" + "\n" +
                "1. Вывести список задач" + "\n" +
                "2. Отметить выполненную задачу" + "\n" +
                "3. Создать новую задачу" + "\n" +
                "4. Удалить все выполненные задачи" + "\n" +
                "5. Выйти из программы" + "\n");
        while (true) {
            int command = scanner.nextInt();

            Statement statement = connection.createStatement();
            String sql = "select * from Tasks order by id asc";
            ResultSet result = statement.executeQuery(sql);

            switch (command) {
                case 1: {
                    while (result.next()) {
                        System.out.println(result.getInt("id") + " "
                                + result.getString("name") + " "
                                + result.getBoolean("isdone"));
                    }
                    System.out.println("");
                    break;
                }
                case 2: {
                    sql = "update Tasks set isdone = 'True' where id = ?";
                    PreparedStatement preparedStatement = connection.prepareStatement(sql);
                    System.out.println("Выберите номер задачи:");
                    command = scanner.nextInt();
                    preparedStatement.setInt(1, command);
                    preparedStatement.executeUpdate();
                    break;
                }
                case 3: {
                    sql = "insert into Tasks (name, isdone) values (?,FALSE)";
                    PreparedStatement preparedStatement = connection.prepareStatement(sql);
                    System.out.println("Введите название задачи:");
                    scanner.nextLine();
                    String sql_name = scanner.nextLine();
                    preparedStatement.setString(1, sql_name);
                    preparedStatement.executeUpdate();
                    break;
                }
                case 4: {
                    sql = "delete from Tasks where isdone=TRUE";
                    statement.executeUpdate(sql);
                    break;
                }
                case 5: {
                    System.exit(0);
                    break;
                }
                default: {

                }
            }
        }
    }
}