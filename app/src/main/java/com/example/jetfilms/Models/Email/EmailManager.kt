package com.example.jetfilms.Models.Email

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Properties
import javax.mail.Authenticator
import javax.mail.Message
import javax.mail.PasswordAuthentication
import javax.mail.Session
import javax.mail.Transport
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

class EmailManager {
    companion object {

        fun sendEmail(senderEmail: String, senderUsername: String , messageText: String) {

            CoroutineScope(Dispatchers.IO).launch {
                val host = "smtp.gmail.com"
                val port = 587
                val username = "romavzr3011@gmail.com"
                val password = "indt dszt ezqt mdof"

                val receiver = "romavzr3011@gmail.com"

                val properties = Properties()
                properties["mail.smtp.auth"] = "true"
                properties["mail.smtp.starttls.enable"] = "true"
                properties["mail.smtp.host"] = host
                properties["mail.smtp.port"] = port

                val session = Session.getInstance(properties,object : Authenticator() {
                    override fun getPasswordAuthentication(): PasswordAuthentication {
                        return PasswordAuthentication(username,password)
                    }
                }
                )

                try {
                    val message = MimeMessage(session)
                    message.setFrom(InternetAddress(username))
                    message.setRecipients(
                        Message.RecipientType.TO,
                        InternetAddress.parse(receiver)
                    )
                    message.subject = "Contact Form"
                    message.setText("From $senderEmail - $senderUsername\n\n$messageText")

                    Transport.send(message)
                }
                catch (e: Exception) {
                    e.printStackTrace()

                    CoroutineScope(Dispatchers.IO).launch {

                    }
                }
            }
        }
    }
}