package com.tartangatickets.entities;

import java.io.Serializable;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *  Encapsulates the data of each department.
 *  <ul>
 *      <li><strong>code:</strong> Identifier of the department.</li>
 *      <li><strong>name:</strong> Name of the department.</li>
 *  </ul>
 * 
 *  @author Sergio López, Iker Jon Mediavilla, Ionut Savin, Jon Zaballa
 *  @version 1.0, Feb 21 2018
 */

@Entity(name="Department")
@Table(name="departments", schema="tartanga_ticket_db")
@NamedQueries({
    @NamedQuery(
            name="findAllDepartments",
            query="SELECT d FROM Department d ORDER BY d.name"
    ),
    @NamedQuery(
            name="findDepartmentsByCode",
            query="SELECT d FROM Department d where d.code = :code ORDER BY d.name"
    ),
    @NamedQuery(
            name="findDepartmentsByName",
            query="SELECT d FROM Department d where d.name = :name ORDER BY d.name"
    )
})

public class Department implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    private String code;
    private String name;
    
    public Department() {
    }
    
    public Department(String code, String name){
        this.code = code;
        this.name = name;
    }
    
    public StringProperty codeProperty(){
        StringProperty cod = new SimpleStringProperty(code);
        return cod;
    }
    
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (code != null ? code.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Department)) {
            return false;
        }
        Department other = (Department) object;
        if ((this.code == null && other.code != null) || (this.code != null && !this.code.equals(other.code))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tartangatickets.entities.Department[ code=" + code + " ]";
    }
    
}
