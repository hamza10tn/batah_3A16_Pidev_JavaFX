package Services.UserAdmineServices;

import DataBaseSource.DataSource;
import Entity.UserAdmin.Admin;
import InterfaceServices.IService;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdminService implements IService<Admin> {

    private Connection conn;
    private Statement ste;
    private PreparedStatement pst;
    public AdminService() {
        conn= DataSource.getInstance().getCnx();
    }

    @Override
    public void add(Admin admin) {
        String requete = "INSERT INTO utilisateur (nomUtilisateur, prenomUtilisateur, adresseEmail, dateDeNaissance, sexe, motDePasse,role) VALUES (?, ?, ?, ?, ?, ?,?)";

        try {
            PreparedStatement pst = conn.prepareStatement(requete);
            pst.setString(1, admin.getNomUtilisateur());
            pst.setString(2, admin.getPrenomUtilisateur());
            pst.setString(3, admin.getMailUtilisateur());
            pst.setDate(4, admin.getDateDeNaissance());
            pst.setString(5, String.valueOf(admin.getSexeUtilisateur()));
            pst.setString(6, admin.getMotDePassUtilisateur());
            pst.setString(7, String.valueOf(admin.getRoleUtilisateur()));

            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    @Override
    public void delete(Admin admin) {
        String requete = "DELETE FROM utilisateur WHERE id = ?";

        try {
            pst = conn.prepareStatement(requete);
            pst.setInt(1, admin.getIdUtilisateur());
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Admin admin) {
        String requete = "UPDATE utilisateur SET nomUtilisateur = ?, prenomUtilisateur = ?, adresseEmail = ?, motDePasse = ?, dateDeNaissance = ?, sexe = ?, numeroCin = ?, role = ?, numeroTelephone = ?, pays = ?, avatar = ? WHERE id = ?";

        try {
            pst = conn.prepareStatement(requete);
            pst.setString(1, admin.getNomUtilisateur());
            pst.setString(2, admin.getPrenomUtilisateur());
            pst.setString(3, admin.getMailUtilisateur());
            pst.setString(4, admin.getMotDePassUtilisateur());
            pst.setDate(5, new java.sql.Date(admin.getDateDeNaissance().getTime()));
            pst.setString(6, String.valueOf(admin.getSexeUtilisateur()));
            pst.setString(7, admin.getCinUtilisateur());
            pst.setString(8, String.valueOf(admin.getRoleUtilisateur()));
            pst.setString(9, admin.getNumUtilisateur());
            pst.setString(10, admin.getPays());
            pst.setString(11, admin.getAvatar());
            pst.setInt(12, admin.getIdUtilisateur());
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void updateCard(Admin admin) {
        String requete = "UPDATE utilisateur SET nomUtilisateur = ?, prenomUtilisateur = ?, adresseEmail = ?, numeroTelephone = ?, numeroCin = ?, dateDeNaissance = ? , avatar = ? WHERE id = ?";

        try {
            pst = conn.prepareStatement(requete);
            pst.setString(1, admin.getNomUtilisateur());
            pst.setString(2, admin.getPrenomUtilisateur());
            pst.setString(3, admin.getMailUtilisateur());
            pst.setString(4, admin.getNumUtilisateur());
            pst.setString(5, admin.getCinUtilisateur());
            pst.setDate(6, admin.getDateDeNaissance());
            pst.setString(7,admin.getAvatar());
            pst.setInt(8, admin.getIdUtilisateur());
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }





    public List<Admin> readAll() {
        List<Admin> admins = new ArrayList<>();
        String requete = "SELECT * FROM utilisateur";

        try {
            ste = conn.createStatement();
            ResultSet resultSet = ste.executeQuery(requete);
            while (resultSet.next()) {
                Admin admin = new Admin();
                admin.setIdUtilisateur(resultSet.getInt("id"));
                admin.setNomUtilisateur(resultSet.getString("nomUtilisateur"));
                admin.setPrenomUtilisateur(resultSet.getString("prenomUtilisateur"));
                admin.setMailUtilisateur(resultSet.getString("adresseEmail"));
                admin.setMotDePassUtilisateur(resultSet.getString("motDePasse"));
                admin.setDateDeNaissance(resultSet.getDate("dateDeNaissance"));
                // Handle potential null values for the 'sexe' column
                String sexe = resultSet.getString("sexe");
                if (sexe != null && !sexe.isEmpty()) {
                    admin.setSexeUtilisateur(sexe.charAt(0));
                }
                // Handle potential null values for the 'numeroCin' column
                String cin = resultSet.getString("numeroCin");
                if (cin != null && !cin.isEmpty()) {
                    admin.setCinUtilisateur(cin);
                }
                // Handle potential null values for the 'role' column
                String role = resultSet.getString("role");
                if (role != null && !role.isEmpty()) {
                    admin.setRoleUtilisateur(role.charAt(0));
                }
                // Handle potential null values for the 'numeroTelephone' column
                String numUtilisateur = resultSet.getString("numeroTelephone");
                if (numUtilisateur != null && !numUtilisateur.isEmpty()) {
                    admin.setNumUtilisateur(numUtilisateur);
                }
                // Handle potential null values for the 'pays' column
                String pays = resultSet.getString("pays");
                if (pays != null && !pays.isEmpty()) {
                    admin.setPays(pays);
                }
                // Handle potential null values for the 'avatar' column
                String avatar = resultSet.getString("avatar");
                if (avatar != null && !avatar.isEmpty()) {
                    admin.setAvatar(avatar);
                }
                admins.add(admin);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return admins;
    }



    @Override
    public Admin readById(int id) {
        Admin admin = null;
        String requete = "SELECT * FROM utilisateur WHERE id = ?";

        try {
            pst = conn.prepareStatement(requete);
            pst.setInt(1, id);
            ResultSet resultSet = pst.executeQuery();
            if (resultSet.next()) {
                admin = new Admin();
                admin.setIdUtilisateur(resultSet.getInt("id"));
                admin.setNomUtilisateur(resultSet.getString("nomUtilisateur"));
                admin.setPrenomUtilisateur(resultSet.getString("prenomUtilisateur"));
                admin.setMailUtilisateur(resultSet.getString("adresseEmail"));
                admin.setMotDePassUtilisateur(resultSet.getString("motDePasse"));
                admin.setDateDeNaissance(resultSet.getDate("dateDeNaissance"));

                // Handle potential null values for the 'sexe' column
                String sexe = resultSet.getString("sexe");
                if (sexe != null && !sexe.isEmpty()) {
                    admin.setSexeUtilisateur(sexe.charAt(0));
                }
                // Handle potential null values for the 'numeroCin' column
                String cin = resultSet.getString("numeroCin");
                if (cin != null && !cin.isEmpty()) {
                    admin.setCinUtilisateur(cin);
                }
                // Handle potential null values for the 'role' column
                String role = resultSet.getString("role");
                if (role != null && !role.isEmpty()) {
                    admin.setRoleUtilisateur(role.charAt(0));
                }
                // Handle potential null values for the 'numeroTelephone' column
                String numUtilisateur = resultSet.getString("numeroTelephone");
                if (numUtilisateur != null && !numUtilisateur.isEmpty()) {
                    admin.setNumUtilisateur(numUtilisateur);
                }
                // Handle potential null values for the 'pays' column
                String pays = resultSet.getString("pays");
                if (pays != null && !pays.isEmpty()) {
                    admin.setPays(pays);
                }
                // Handle potential null values for the 'avatar' column
                String avatar = resultSet.getString("avatar");
                if (avatar != null && !avatar.isEmpty()) {
                    admin.setAvatar(avatar);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return admin;
    }

    ////////////////////////////////////

    public Admin getUserByName(String nomUtilisateur, String prenomUtilisateur) throws SQLException {
        String query = "SELECT * FROM utilisateur WHERE nomUtilisateur = ? AND prenomUtilisateur = ?";
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.setString(1, nomUtilisateur);
        preparedStatement.setString(2, prenomUtilisateur);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {

            int idUtilisateur = resultSet.getInt("id");

            return new Admin(idUtilisateur);
        } else {
            // If no user was found, return null
            return null;
        }
    }
    public Admin  Login(Admin admin)
    {String plainPassword = admin.getMotDePassUtilisateur();
        String requete = "SELECT * FROM utilisateur WHERE adresseEmail = ? ";
        try {
            pst = conn.prepareStatement(requete);
            pst.setString(1, admin.getMailUtilisateur());
            ResultSet resultSet = pst.executeQuery();

            if (resultSet.next()) {
                admin.setIdUtilisateur(resultSet.getInt("id"));
                admin.setNomUtilisateur(resultSet.getString("nomUtilisateur"));
                admin.setPrenomUtilisateur(resultSet.getString("prenomUtilisateur"));
                admin.setMailUtilisateur(resultSet.getString("adresseEmail"));
                admin.setMotDePassUtilisateur(resultSet.getString("motDePasse"));
                admin.setDateDeNaissance(resultSet.getDate("dateDeNaissance"));
                admin.setSexeUtilisateur(resultSet.getString("sexe").charAt(0));
                admin.setCinUtilisateur(resultSet.getString("numeroCin"));
                admin.setRoleUtilisateur(resultSet.getString("role").charAt(0));
                admin.setNumUtilisateur(resultSet.getString("numeroTelephone"));
                admin.setPays(resultSet.getString("pays"));
                admin.setAvatar(resultSet.getString("avatar"));

            } else {
                // If no user found with the given email, set ID to -1 to indicate failure
                admin.setIdUtilisateur(-1);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // If the password does not match or admin object is null, set ID to -1
        if (admin.getIdUtilisateur() != -1 && (admin.getMotDePassUtilisateur() == null || !BCrypt.checkpw(plainPassword, admin.getMotDePassUtilisateur()))) {
            admin.setIdUtilisateur(-1);
        }

        return admin;
    }




    public boolean emailExists(String email) {
        boolean exists = false;
        String requete = "SELECT COUNT(*) FROM utilisateur WHERE adresseEmail = ?";

        try {
            pst = conn.prepareStatement(requete);
            pst.setString(1, email);
            ResultSet resultSet = pst.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                exists = (count > 0);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return exists;
    }

}