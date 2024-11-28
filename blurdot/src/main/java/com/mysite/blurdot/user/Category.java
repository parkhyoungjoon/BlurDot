package com.mysite.blurdot.user;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "category_tb")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Category {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer category_id;

    @Column(length = 10, nullable = false)
    private String category_name;

    @OneToMany(mappedBy = "category", cascade = CascadeType.REMOVE)
    private List<SiteUser> user;
}
