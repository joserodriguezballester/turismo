package Modelo;

import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author joser
 */
public class Correo {

    private final Properties properties = new Properties();
    private String password = "contraseña gmail";
    private Session session;
    
    private void mandarcorreo() {

        Correo correo = new Correo();
        correo.sendEmail();

    }

    private void init(String emisor) {      
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.port", 587);
        properties.put("mail.smtp.mail.sender", emisor);
        properties.put("mail.smtp.user", emisor);
        properties.put("mail.smtp.auth", "false");
        session = Session.getDefaultInstance(properties);
    }

    public void sendEmail() {
        String emisor = "emisor@gmail.com";
        int contraseña = 0;    /////ojo cuando pasemos contraseña
        String receptor = "j_r_ballester@hotmail.com";    /// pasar correo del usuario
        init(emisor);

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress((String) properties.get("mail.smtp.mail.sender")));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(receptor));
            message.setSubject("Agencia Turistica Pepita");
            message.setText("Sentimos tu Alzheimer tu nueva contraseña es "+contraseña+" te recomendamos la cambies nada mas entrar en la aplicacion");
            Transport t = session.getTransport("smtp");
            t.connect((String) properties.get("mail.smtp.user"), password);
            t.sendMessage(message, message.getAllRecipients());
            t.close();
            System.out.println("mensaje mandado");
        } catch (MessagingException me) {
            me.printStackTrace();

            System.out.println("error");
            //Aqui se deberia o mostrar un mensaje de error o en lugar
            //de no hacer nada con la excepcion, lanzarla para que el modulo
            //superior la capture y avise al usuario con un popup, por ejemplo.

        }

    }
}
