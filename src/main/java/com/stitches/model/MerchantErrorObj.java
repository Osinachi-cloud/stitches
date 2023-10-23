package com.stitches.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MerchantErrorObj {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String message;
    private String errorCode;
    public MerchantErrorObj(String errorCode){
        System.out.println("Merchant error constructor was called");
        this.errorCode = errorCode;
        if(errorCode.equals("300")){
            this.message = "Invalid Acquirer Identifier";
        }
        if(errorCode.equals("301")){
            this.message = "Invalid Request Date";
        }
        if(errorCode.equals("302")){
            this.message = "Invalid Merchant ID";
        }
        if(errorCode.equals("303")){
            this.message = "Invalid Merchant Name";
        }
        if(errorCode.equals("304")){
            System.out.println(304);
            this.message = "Invalid Request Type";
        }
        if(errorCode.equals("305")){
            this.message = "Invalid MCC Code";
        }
        if(errorCode.equals("306")){
            this.message = " Registration already exists";
        }
        if(errorCode.equals("307")){
            this.message = "Trying to delete a merchant that does not exist";
        }
    }
}

