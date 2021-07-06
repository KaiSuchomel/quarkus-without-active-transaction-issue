package io.gec.test.entity;

import java.time.OffsetDateTime;
import java.util.Random;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;

@Entity
public class Gift {

    private Long id;
    private String name;
    private Category category;
    private OffsetDateTime created;

    public Gift() {
        Random rand = new Random(System.currentTimeMillis());
        category = Category.values()[rand.nextInt(Category.values().length)];
    }
    
    @Id
    @SequenceGenerator(name = "giftSeq", sequenceName = "gift_id_seq", allocationSize = 1, initialValue = 1)
    @GeneratedValue(generator = "giftSeq")
    public Long getId() {
        return id;
    }

    public Gift setId(Long id) {
        this.id = id;
        return this;
    }

    
    public String getName() {
        return name;
    }

    public Gift setName(String name) {
        this.name = name;
        return this;
    }

    public Category getCategory() {
        return category;
    }

    @Column(name = "CREATED", nullable = false, updatable = false)
    public OffsetDateTime getCreated() {
        return created;
    }
    
    @PrePersist
    protected void onCreate() {
        created = OffsetDateTime.now();
    }

    @Override
    public String toString() {
        return "Gift{" + "id=" + id + ", name=" + name + ", category=" + category + ", created=" + created + '}';
    }

}
