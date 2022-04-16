# Java Email Project
This project is used to send emails to multiple recepients with some attachments along with the mail.

## Prerequisite
- JDK 8
- [Javax Mail API](https://jar-download.com/artifacts/com.sun.mail/javax.mail)
- Java IDEs (Ex: Eclipse, VsCode)

## Road Map
1. Creating a Java Program
2. Running the Java Program
3. Verifying the output

## Demo
The below Images shows the output of the Java Program for two recepients.
1. Output on Eclipse console

![eclipse_output](https://github.com/CHANDRASEKAR98/java-email-project/blob/main/images/eclipse_output.JPG)

2. Recepient 1 Mail output

![user1_mail](https://github.com/CHANDRASEKAR98/java-email-project/blob/main/images/email_user1.JPG)

4. Recepient 2 Mail output

![user2_mail](https://github.com/CHANDRASEKAR98/java-email-project/blob/main/images/email_user2.JPG)

## Installation
### JDK 8 Installation
Download and install Java 8 JDK on your system if not already done.
You can download Java 8 from the official [Oracle Website](https://www.oracle.com/in/java/technologies/javase/javase8-archive-downloads.html) 

Once downloaded, Install and setup your environemntal variables for Java 8 properly. If you don't know how to setup your environmental variables, kindly follow the steps given in this [link](https://www.javatpoint.com/how-to-set-path-in-java).

To check the version of Java installed in your system, kindly execute the following command on your CMD.

```bash
  java -version
```
The output will be displayed like the following.

```bash
  java version "1.8.0_101"
  Java(TM) SE Runtime Environment (build 1.8.0_101-b13)
  Java HotSpot(TM) 64-Bit Server VM (build 25.101-b13, mixed mode)
```

## Creating Java Project
1. Create a Java Project and configure it with JDK 1.8 version.

2. Create a new package name and a new class file.

3. Import the following JARs into your project that you have downloaded on this [Step](https://github.com/CHANDRASEKAR98/java-email-project/edit/main/README.md#prerequisite). If not, please download it.

    - Javax Mail API
    - Javax Activation Framework API

## Code Flow
1. Create a list of mail recipient addresses like below.

```bash
  List<String> recipientList = new ArrayList<>();
  recipientList.add("<recipient_mail_id_1>");
  recipientList.add("<recipient_mail_id_2>");
```

2. Set system properties like below code for sending email in Java.

```bash
  Properties prop = new Properties();
  prop.put("mail.smtp.host", "smtp.gmail.com");
	prop.put("mail.smtp.port", "465");
	prop.put("mail.smtp.auth", "true");
	prop.put("mail.smtp.socketFactory.port", "465");
	prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
```

3. For sending the email, the current email account must be authenticated and verified with the mail server. Inorder to authenticate with the mail server, `Session` class in `javax.mail` will be useful to authenticate with the mail server using the Email ID and password of the sender.

```bash
  Session session = Session.getInstance(prop, new Authenticator() {
      @Override
      protected PasswordAuthentication getPasswordAuthentication() {
        String username = "<your_mail_id_here>";
        String password = "<your_mail_password_here>";
        return new PasswordAuthentication(username, password);
      }
  });
```

4. Then using the authenticated `session` object, the `message` object is created from `MimeMessage` class. Set **From** address using the `message` object.

```bash
  Message message = new MimeMessage(session);
  message.setFrom(new InternetAddress("<sender_mail_id_here>"));
```

5. Inorder to set multiple recipient addresses, `addRecipients` method is used which requires two parameters.
    - RecipientType: Set as **TO**
    - Address[]: An array of type Address

6. To convert the list of mail recipient addresses from the type List to Address[], the following `getRecepients` method is created.

```bash
  private static Address[] getRecepients(List<String> recipientList) {
      List<Address> address = new ArrayList<>();
      recipientList.stream().forEach(recipient -> {
        try {
          address.add(new InternetAddress(recipient));
        } catch (AddressException e) {
          System.out.println("Error while converting the List of Recipient Address: " + e);
        }
      });
      return address.stream().toArray(Address[] :: new);
    }
```

7. Now you can set multiple recipient addresses in `addRecipients` method using `message` object.

```bash
  message.addRecipients(Message.RecipientType.TO, getRecepients(recipientList));
```

8. Set a **Subject** for the email using `message` object.

```bash
  message.setSubject("Email from CHANDRASEKAR98 GitHub user");
```

9. Create an `MimeBodyPart` object for setting the body content of the mail.

```bash
  MimeBodyPart mimeBodyPart = new MimeBodyPart();
```

10. To set the body content for the email, you need to frame the body content first.
Create a new method `setBodyContent` of return the body content for the email.

```bash
  private static String setBodyContent() {
      String content = "Hello User,<br><br>This email is sent inorder to check Java Mail API.<br>"
          + "<br>Hope to see you soon!!!"
          + "<br><br>Thanks and regards<br>"
          + "Chandrasekar Balakumar,<br>"
          + "Software Developer.";
      return content;
    }
```

11. Now set the body content of the mail on the `setContent` method using `message` object.

```bash
mimeBodyPart.setContent(setBodyContent(), "text/html; charset=utf-8");
```

12. Inorder to attach an attachment with the mail, create another `MimeBodyPart` object and fetch the file data from the local system and set it to the `MimeBodyPart` object.

```bash
  MimeBodyPart mimeattachmentPart = new MimeBodyPart();
  DataSource dataSource = new FileDataSource(new File("F:/hearticon.jpg"));
  mimeattachmentPart.setDataHandler(new DataHandler(dataSource));
```
Name the attachment file with the original file name

```bash
mimeattachmentPart.setFileName(dataSource.getName());
```

13. Create `MultiPart` object to set all the body parts into it.

```bash
Multipart multiPart = new MimeMultipart();
```

First set the body content `mimeBodyPart` to the `MultiPart` object.

```bash
  multiPart.addBodyPart(mimeBodyPart);
```

Then set the mail attachment `mimeBodyPart` to the `MultiPart` object.

```bash
  multiPart.addBodyPart(mimeattachmentPart);
```

14. Set the entire body contents into the `message` object.

```bash
  message.setContent(multiPart);
```

15. Finally send the email !!!!

```
  Transport.send(message);
```

16. Now run the Java Program to verify the output.

The output will be displayed as shown in the images on this [Step](https://github.com/CHANDRASEKAR98/java-email-project/edit/main/README.md#demo)

## Acknowledgement
- [Sending Email in Java](https://www.baeldung.com/java-email)
- [Convert List to Array - Java 8](https://www.geeksforgeeks.org/convert-list-to-array-in-java/)

## Authors
- [@Chandrasekar Balakumar](https://github.com/CHANDRASEKAR98)

## Feedback
If you have any feedback, please reach out to me [@Chandrasekar Balakumar](https://www.linkedin.com/in/chandrasekarbalakumar98/) on LinkedIn.
