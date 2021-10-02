/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package btlltw.girl;

import groovy.transform.ToString;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 *
 * @author tuenguyen
 */
public class Girl {
    private String name;
    public  Girl(){
        
    }
    public  Girl(String name){
        this.name=name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public  String toString(){
        return "Girl(" + this.name + ")";
    }
    
    @PostConstruct
    public void postConstruct(){
        System.out.println("\t>> Đối tượng Girl sau khi khởi tạo xong sẽ chạy hàm này");
    }
    @PreDestroy
    public void preDestroy(){
        System.out.println("\t>> Đối tượng Girl trước khi bị destroy thì chạy hàm này");
    }
        
}
