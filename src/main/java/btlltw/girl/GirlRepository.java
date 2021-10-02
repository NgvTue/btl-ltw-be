/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package btlltw.girl;

import org.springframework.stereotype.Repository;

/**
 *
 * @author tuenguyen
 */

public interface GirlRepository {
    
    Girl getGirlByName(String name);
    
}
