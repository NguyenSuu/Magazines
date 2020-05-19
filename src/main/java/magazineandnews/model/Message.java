package magazineandnews.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "message")
@Component
public class Message implements Validator {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String writer;
    private String email;
    private String info;
    private LocalDate time;
    private Long id_post;
    private boolean contact;
    private boolean checked;
    private String subject;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "posts_id")
    private Posts posts;


    public Message() {

    }

    public Message(Long id, String writer, String info, LocalDate time) {
        this.id = id;
        this.writer = writer;
        this.info = info;
        this.time = time;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public boolean isContact() {
        return contact;
    }

    public void setContact(boolean contact) {
        this.contact = contact;
    }

    public Long getId_post() {
        return id_post;
    }

    public void setId_post(Long id_post) {
        this.id_post = id_post;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public LocalDate getTime() {
        return time;
    }

    public void setTime(LocalDate time) {
        this.time = time;
    }

    public Posts getPosts() {
        return posts;
    }

    public void setPosts(Posts posts) {
        this.posts = posts;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Message.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Message message = (Message) target;
        String writer = message.getWriter();
        ValidationUtils.rejectIfEmpty(errors, "writer", "writer.empty");
//        if(writer.isEmpty()){
//            errors.rejectValue("writer","writer.empty");
//        }
    }

}
