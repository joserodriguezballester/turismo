package Modelo;

import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.util.Pair;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author joser
 */
public class Correo {

    private final Properties properties = new Properties();

    private Session session;
    private Pair<String, String> pareja;
    private String numero; //por si variamos el metodo send email
    private Correo correo;
   
    private final String password = "dawGIAT4";

    public Correo() {
        
    }

    public void mandarcorreo() throws MessagingException {
      
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

    public void sendEmail() throws AddressException, MessagingException {
        String emisor = "grupo4amsterdam@gmail.com";  //correo de la agencia
//        System.out.println("pareja "+pareja);
        
        String receptor=pareja.getValue();    /// pasar correo del usuario
        
//        int contraseña = 0;    /////ojo cuando pasemos contraseña por parametro

        init(emisor);
       
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress((String) properties.get("mail.smtp.mail.sender")));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(receptor));
        message.setSubject("Agencia Turistica GRUPO4 AMSTERDAM");
        message.setText("Hola " + pareja.getKey() + " Sentimos mucho tu Alzheimer; tu nueva contraseña es " + numero + " te recomendamos la cambies nada mas entrar en la aplicacion");
        Transport t = session.getTransport("smtp");
        t.connect((String) properties.get("mail.smtp.user"),"dawGIAT4" );
        t.sendMessage(message, message.getAllRecipients());
        t.close();
//        System.out.println("mensaje mandado");

    }

    public void setparametros(Pair<String, String> pareja, String numero,Correo correo) {
        this.pareja = pareja;
        this.numero = numero;
        this.correo=correo;
    }
}
