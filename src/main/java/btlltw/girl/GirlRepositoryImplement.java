/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package btlltw.girl;

import org.springframework.stereotype.Repository;

/**
 *
 * @author tuenguyen
 */
@Repository
public class GirlRepositoryImplement implements GirlRepository{

    @Override
    public Girl getGirlByName(String name) {
        return new Girl(name);
    }
    
    
}
