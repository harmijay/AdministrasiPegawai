package AdministrasiPegawai.pegawai;

import AdministrasiPegawai.static_variable.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class PegawaiService {

    private final PegawaiRepository pegawaiRepository;

    @Autowired
    public PegawaiService(PegawaiRepository pegawaiRepository) {
        this.pegawaiRepository = pegawaiRepository;
    }

    public List<Pegawai> getPegawai() {
        return pegawaiRepository.findAll();
    }

    public HashMap<String, Object> addNewPegawai(Pegawai pegawai) {
        HashMap<String, Object> response = new HashMap<>();
        Optional<Pegawai> pegawaiById = pegawaiRepository.findPegawaiById(pegawai.getId());
        if (pegawaiById.isPresent()) {
            response.put(Status.KEY_STATUS,"501");
            response.put(Status.KEY_MESSAGE,"Pegawai ID Taken");
        }else{
            response.put(Status.KEY_STATUS,"200");
            response.put(Status.KEY_MESSAGE,"Success");
            pegawaiRepository.save(pegawai);
        }
        return response;
    }

    public void deletePegawai(Long pegawaiId) {
        boolean exists = pegawaiRepository.existsById(pegawaiId);
        if (!exists) {
            throw new IllegalStateException("Pegawai with id " + pegawaiId + " does not exists");
        }
        pegawaiRepository.deleteById(pegawaiId);
    }

    @Transactional
    public void updatePegawai(Long pegawaiId, String name, Long salary, String position) {
        Pegawai pegawai = pegawaiRepository.findById(pegawaiId)
                .orElseThrow(() -> new IllegalStateException("Pegawai with id " + pegawaiId + " does not exists"));

        if (name != null && !Objects.equals(pegawai.getName(), name)) {
            pegawai.setName(name);
        }
        if (salary != null && !Objects.equals(pegawai.getSalary(), salary)) {
            pegawai.setSalary(salary);
        }
        if (position != null && !Objects.equals(pegawai.getPosition(), position)) {
            pegawai.setPosition(position);
        }
    }
}
