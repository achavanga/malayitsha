package za.co.thinking.model.base;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * File Name : BaseEntity.java Project Name : MainThinkify-jar
 *
 * @since Dec 19, 2016, 3:47:30 PM
 * @author Abel Chavanga <achavanga@fnb.co.za>
 *
 */
@MappedSuperclass
public abstract class BaseEntity implements Serializable {

    private static final long serialVersionUID = 4690768631402672278L;

    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true, nullable = false, updatable = false, insertable = false)
    private Long id;

//    @Column(name = "CREATE_DATE")
//    @Temporal(TemporalType.TIMESTAMP)
//    private Date createdDate;
//
//    @Column(name = "ACTIVE", nullable = false)
//    private Boolean active = true;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
//
//    public Date getCreatedDate() {
//        return createdDate;
//    }
//
//    public void setCreatedDate(Date createdDate) {
//        this.createdDate = createdDate;
//    }
//
//    public Boolean getActive() {
//        return active;
//    }
//
//    public void setActive(Boolean active) {
//        this.active = active;
//    }

}
