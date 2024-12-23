package chapter6.beans;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {
	//private static final long serialVersionUID = 1L;
	private int id;
    private String account;
    private String name;
    private String email;
    private String password;
    private String description;
    private Date createdDate;
    private Date updatedDate;

    // getter/setterは省略されているので、自分で記述しましょう。
    //idのgettter/setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    //accountのgettter/setter
    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    //nameのgettter/setter
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //emailのgettter/setter
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    //passwordのgettter/setter
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    //descriptionのgettter/setter
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    //createdDateのgettter/setter
    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    //updatedDateのgettter/setter
    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }
}
