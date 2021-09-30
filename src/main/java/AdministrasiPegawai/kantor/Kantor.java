package AdministrasiPegawai.kantor;

import javax.persistence.*;

@Entity
@Table
public class Kantor {

    @Id
    @SequenceGenerator(
            name = "kantor_sequence",
            sequenceName = "kantor_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "kantor_sequence"
    )
    private Long id;
    private String name;
    private String alamat;
    private String status;

    public Kantor() {
    }

    public Kantor(Long id, String name, String alamat, String status) {
        this.id = id;
        this.name = name;
        this.alamat = alamat;
        this.status = status;
    }

    public Kantor(String name, String alamat, String status) {
        this.name = name;
        this.alamat = alamat;
        this.status = status;
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

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Kantor{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", alamat='" + alamat + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
