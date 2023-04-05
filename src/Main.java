import javax.mail.*;
import javax.mail.internet.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;
import java.util.Scanner;

public class Main {
    private static final String PROPERTY_NAME = "mail.properties";

    public boolean checkIfPropertyExists(){
        String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        System.out.println(rootPath.split("out")[0]);
        String appConfigPath = rootPath.split("out")[0] + PROPERTY_NAME;

        File file = new File(appConfigPath);

        return file.exists();
    }
    public Properties requestProperties(){
        Properties props = new Properties();
        Scanner sc = new Scanner(System.in);

        System.out.println("Create new properties:");

        System.out.print("SMTP-Hostname > ");
        String hostname = sc.nextLine();
        props.put("mail.smtp.host", hostname);

        System.out.print("From E-Mail > ");
        String fromMail = sc.nextLine();
        props.put("mail.smtp.from", fromMail);

        System.out.print("Username > ");
        String username = sc.nextLine();
        props.put("mail.smtp.username", username);

        System.out.print("Password > ");
        String password = sc.nextLine();
        props.put("mail.smtp.password", password);

//        System.out.println(props);
        return props;
    }
    public void writeProperties(Properties properties) throws Exception{
        properties.store(new FileOutputStream(PROPERTY_NAME), null);
    }
    public Properties readProperties() throws FileNotFoundException, IOException {
        //TODO: make it generic
        Properties props = new Properties();
        String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        System.out.println(rootPath.split("out")[0]);
        String appConfigPath = rootPath.split("out")[0] + PROPERTY_NAME;
        System.out.println(appConfigPath);

        props.load(new FileInputStream(appConfigPath));

        return props;
    }
    public static void main(String[] args) throws Exception {
        Main main = new Main();
        if(!main.checkIfPropertyExists()){
            System.out.println("WARNING: properties not found.");
            System.out.println("WARNING: creating new properties.");
            Properties properties = main.requestProperties();
            main.writeProperties(properties);
            return;
        }

        Properties props = new Main().readProperties();

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter receiver > ");
        String receiver = scanner.nextLine();
        System.out.print("Enter Subject > ");
        String subject = scanner.nextLine();
        System.out.println("Enter Message > ");
        String message = scanner.nextLine();

        Mail mail = new Mail();
        mail.setReceiver(receiver);
        mail.setSubject(subject);
        mail.setMessage(message);
        //TODO: mail und secret ausladen
        SMTPSSLSender smtp = new SMTPSSLSender(props);
        Session session = smtp.getInstance();
        Message msg = smtp.createMessage(session, mail);
        smtp.send(msg);

        //java-mail@beck-homes.com
        //axv8@50T4
        //SMTP: mail.beck-homes.com
        //SMTP-Port: 465
    }
}