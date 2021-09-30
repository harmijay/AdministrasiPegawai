package AdministrasiPegawai.pegawai;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table
public class Pegawai {
    @Id
    private Long id;
    private String name;
    private LocalDate dob;
    private String gender;
    private Long salary;
    private String position;
    private Long idKantor;

    public Pegawai(Long id, String name, LocalDate dob, String gender, Long salary, String position) {
        this.id = id;
        this.name = name;
        this.dob = dob;
        this.gender = gender;
        this.salary = salary;
        this.position = position;
    }

    public Pegawai() {
    }

    public Pegawai(Long id, String name, LocalDate dob, String gender, Long salary, String position, Long idKantor) {
        this.id = id;
        this.name = name;
        this.dob = dob;
        this.gender = gender;
        this.salary = salary;
        this.position = position;
        this.idKantor = idKantor;
    }

    public Long getIdKantor() {
        return idKantor;
    }

    public void setIdKantor(Long idKantor) {
        this.idKantor = idKantor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Long getSalary() {
        return salary;
    }

    public void setSalary(Long salary) {
        this.salary = salary;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
