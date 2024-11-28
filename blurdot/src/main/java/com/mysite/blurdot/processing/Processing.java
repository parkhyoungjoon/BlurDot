package com.mysite.blurdot.processing;

import java.time.LocalDateTime;

import com.mysite.blurdot.file.StorageFile;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "processing_tb")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Processing {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long processingId;
	
	@Column(nullable = false, columnDefinition = "SMALLINT")
    private Short proStopwatch;
    
    @Column(nullable = false, columnDefinition = "SMALLINT")
    private Short proFileTxtCnt;
    
    @Column(nullable = false, columnDefinition = "DATETIME")
    private LocalDateTime proFileDate;
    
    @ManyToOne
	@JoinColumn(name="fileId") 
	private StorageFile file; 
    
    @Builder
    public Processing(Long processingId,Short proStopwatch,Short proFileTxtCnt,LocalDateTime proFileDate){
    	this.processingId = processingId;
    	this.proStopwatch = proStopwatch;
    	this.proFileTxtCnt = proFileTxtCnt;
    	this.proFileDate = proFileDate;
    	
    }
}
