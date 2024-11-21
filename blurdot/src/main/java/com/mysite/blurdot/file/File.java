package com.mysite.blurdot.file;
import java.time.LocalDateTime;
import java.util.List;

import com.mysite.blurdot.user.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "file_tb")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fileId;

    @Column(length = 40, nullable = false)
    private String fileName;
    
    @Column(length = 80, nullable = false)
    private String filePath;
    
    @Column(nullable = false, columnDefinition = "SMALLINT")
    private Short fileSize;
    
    @Column(nullable = false, columnDefinition = "SMALLINT")
    private Short fileLength;
    
    @Column(nullable = false, columnDefinition = "TINYINT")
    private Short fileStatus;
    
    @Column(nullable = false)
    private LocalDateTime fileDate;
    
    @ManyToOne
	@JoinColumn(name="id") 
	private User user;
    
    @OneToMany(mappedBy = "file", cascade = CascadeType.REMOVE) 
    private List<Processing> processingList;
    
    @Builder
    public File(Long fileId,String fileName,String filePath, Short fileSize, Short fileLength, Short fileStatus, LocalDateTime fileDate){
    	this.fileId = fileId;
    	this.fileName = fileName;
    	this.filePath = filePath;
    	this.fileSize = fileSize;
    	this.fileLength = fileLength;
    	this.fileStatus=fileStatus;
    	this.fileDate=fileDate;
    	
    }
}