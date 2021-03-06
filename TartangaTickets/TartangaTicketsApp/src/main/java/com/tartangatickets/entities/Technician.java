package com.tartangatickets.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;


/**
 *  Encapsulates the data of the ticket.
 *  <ul>
 *      <li><strong>isAdmin:</strong> Admin user.</li>
 *      <li><strong>assignedTickets:</strong> Technician assigned ticket´s.</li>
 *  </ul>
 *
 *  @author Sergio López, Iker Jon Mediavilla, Ionut Savin, Jon Zaballa
 *  @version 1.0, Feb 21 2018
 */

@Entity(name="Technician")
@Table(name="technicians", schema="tartanga_ticket_db")
@NamedQueries({
    @NamedQuery(
            name="findAllTechnicians",
            query="SELECT u FROM Technician u ORDER BY u.lastName1, u.lastName2, u.name"
    )
})
public class Technician extends User implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private Boolean isAdmin;
    @OneToMany(mappedBy="technician",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ticket> assignedTickets;

    public Technician() {
        
    }
    
    public Technician(
            String login,
            Credential credential, 
            String name, 
            String lastName1, 
            String lastName2, 
            Department department, 
            List<Ticket> createdTickets, 
            List<Ticket> assignedTickets, 
            Boolean isAdmin) {
        super(login, credential, name, lastName1, lastName2, department, createdTickets);
        this.assignedTickets = assignedTickets;
        this.isAdmin = isAdmin;
    }
    
    public Boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public List<Ticket> getAssignedTickets() {
        return assignedTickets;
    }

    public void setAssignedTickets(List<Ticket> assignedTickets) {
        this.assignedTickets = assignedTickets;
    }
    
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Technician)) {
            return false;
        }
        Technician other = (Technician) object;
        if ((this.login == null && other.login != null) || (this.login != null && !this.login.equals(other.login))) {
            return false;
        }
        return true;
    } 

    @Override
    public String toString() {

        return getFullName();
        
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (login != null ? login.hashCode() : 0);
        return hash;
    }

}
