package Lab7.ModuleServer;

import Lab7.Sercurity.User;
import Lab7.Source.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class DataBaseHandler {
    private static Connection connection;
    static{
        try {
            Class.forName("org.postgresql.Driver");
            //"jdbc:postgresql://localhost:5432/postgres", "postgres", "\password"
            connection = DriverManager.getConnection("jdbc:postgresql://pg/studs", "//", "//");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void addLabWork(LabWork l){

        PreparedStatement stToAdd;
        PreparedStatement stForId;
        try {
            stToAdd = connection.prepareStatement("insert into lab_works (name, coordinate_x, coordinate_y, creation_date," +
                    " minimalpoint, tunedinworks, averagepoint, difficulty, author_name, author_height, author_loc_x," +
                    " author_loc_y, author_loc_z, creator) " +
                    "values (?, ?, ?, ?::timestamp, ?, ?, ?, ?::difficulty, ?, ?, ?, ?, ?, ?)");
            stForId = connection.prepareStatement("select id from lab_works where name=? and coordinate_x=? and coordinate_y=?" +
                    " and creation_date=?::timestamp and minimalpoint=? and tunedinworks=? and averagepoint=? and" +
                    " difficulty=?::difficulty and author_name=? and author_height=? and author_loc_x=? and author_loc_y=? and author_loc_z=? and creator=?");
            for (PreparedStatement st : new PreparedStatement[]{stToAdd, stForId}) {
                st.setString(1, l.getName());
                st.setFloat(2, l.getCoordinates().getX());
                st.setFloat(3, l.getCoordinates().getY());
                st.setString(4, l.getCreationDate().toString());
                st.setDouble(5, l.getMinimalPoint());
                st.setDouble(6, l.getTunedInWorks());
                if(l.getAveragePoint() != null) st.setDouble(7, l.getAveragePoint());
                else st.setNull(7, Types.NULL);
                if(l.getDifficulty() != null) st.setString(8, l.getDifficulty().name());
                else st.setNull(8, Types.NULL);
                st.setString(9, l.getAuthor().getName());
                st.setLong(10, l.getAuthor().getHeight());
                st.setFloat(11, l.getAuthor().getLocation().getX());
                st.setInt(12, l.getAuthor().getLocation().getY());
                st.setLong(13, l.getAuthor().getLocation().getZ());
                st.setString(14, l.getCreator().getLogin());
            }
            stToAdd.executeUpdate();
            ResultSet id = stForId.executeQuery();
            id.next();
            l.setId(id.getInt("id"));


        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public static void removeLabWork(LabWork element){

        try {
            PreparedStatement st = connection.prepareStatement("delete from lab_works where id = ?");
            st.setInt(1, element.getId());
            st.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void replaceLabWork(int id, LabWork l){


        try {
            PreparedStatement st = connection.prepareStatement("update lab_works  set name=?, coordinate_x=?, coordinate_y=?, creation_date=?::timestamp," +
                    " minimalpoint=?, tunedinworks=?, averagepoint=?, difficulty=?::difficulty, author_name=?," +
                    " author_height=?, author_loc_x=?, author_loc_y=?, author_loc_z=?, creator=? where id=?");
            st.setString(1, l.getName());
            st.setFloat(2, l.getCoordinates().getX());
            st.setFloat(3, l.getCoordinates().getY());
            st.setString(4, l.getCreationDate().toString());
            st.setDouble(5, l.getMinimalPoint());
            st.setDouble(6, l.getTunedInWorks());
            if(l.getAveragePoint() != null) st.setDouble(7, l.getAveragePoint());
            else st.setNull(7, Types.NULL);
            if(l.getDifficulty() != null) st.setString(8, l.getDifficulty().name());
            else st.setNull(8, Types.NULL);
            st.setString(9, l.getAuthor().getName());
            st.setLong(10, l.getAuthor().getHeight());
            st.setFloat(11, l.getAuthor().getLocation().getX());
            st.setInt(12, l.getAuthor().getLocation().getY());
            st.setLong(13, l.getAuthor().getLocation().getZ());
            st.setString(14, l.getCreator().getLogin());
            st.setInt(15, id);
            st.executeUpdate();

        } catch (SQLException e) {}
    }

    public static boolean addUser(User user){


        try {
            PreparedStatement st = connection.prepareStatement("insert into users (login, password) values (?, ?)");
            st.setString(1, user.getLogin());
            st.setString(2, user.getHash());
            st.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }

    }

    public static List<LabWork> getLabCollection(){
        List<LabWork> list = new ArrayList<>();



        try {
            ResultSet r = connection.createStatement().executeQuery("select * from lab_works");
            while(r.next()){
                PreparedStatement ps = connection.prepareStatement("select * from users where login=?");
                ps.setString(1, r.getString("creator"));
                ResultSet user = ps.executeQuery();
                user.next();
                String time = r.getString("creation_date");
                time = time.replace(' ', 'T');
                time = time.substring(0, time.indexOf('+'));
                String diff = r.getString("difficulty");
                Difficulty difficulty = (diff==null)?null:Difficulty.valueOf(diff);
                list.add(new LabWork(r.getInt("id"), r.getString("name"), new Coordinates(r.getFloat("coordinate_x"), r.getInt("coordinate_y")),
                        java.time.LocalDateTime.parse(time), r.getDouble("minimalpoint"),
                        r.getInt("tunedinworks"), r.getDouble("averagepoint"), difficulty,
                        new Person(r.getString("author_name"), r.getLong("author_height"),
                                new Location(r.getFloat("author_loc_x"), r.getInt("author_loc_y"), r.getLong("author_loc_z"))),
                        new User(user.getString("login"), user.getString("password"))
                        ));

            }
        } catch (SQLException e) {
            System.out.println("Ошибка при чтении из базы данных");
        }
        return list;
    }

    public static boolean checkLoginAccuracy(User user){

        PreparedStatement st;
        try {
            st = connection.prepareStatement("select * from users where login=? and password=?");
            st.setString(1, user.getLogin());
            st.setString(2, user.getHash());
            ResultSet res = st.executeQuery();
            int count = 0;
            while (res.next()){
                ++count;
            }
            if(count == 1) return true;
        } catch (SQLException e) {}

        return false;
    }


}

