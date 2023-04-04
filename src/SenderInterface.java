import javax.mail.Message;
import javax.mail.Session;

public interface SenderInterface {
    // dies ist ein kommentar

    Session getInstance();

    Message createMessage(Session session, Mail mail);

    boolean send(Message message);
}
